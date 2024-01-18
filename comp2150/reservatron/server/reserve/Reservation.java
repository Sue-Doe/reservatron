package comp2150.reservatron.server.reserve;

import java.util.ArrayList;
import java.util.Iterator;

import comp2150.reservatron.server.session.Customer;

/**
 * COMP 2150 Summer 23 Assignment 1: Hotel reservations
 */

/**
 * Reservation class: The details of a reservation. This points back to
 * both the customer who made the reservation and the room(s) that were
 * reserved.
 *
 * Comparable sorting order is by start date.
 * Can iterate over the rooms in the reservation.
 * 
 * @author john
 */

public class Reservation implements Comparable<Reservation>, Iterable<Room> {
  private int start, duration, numGuests;
  
  Customer customer;
  ArrayList<Room> rooms;
  
  public Reservation(Customer customer, int numGuests, int start, int duration) {
    this.start = start;
    this.duration = duration;
    this.numGuests = numGuests;
    this.customer = customer;
    rooms = new ArrayList<Room>();
  }
  
  // Deep copy
  public Reservation(Reservation other) {
    this(other.customer, other.numGuests, other.start, other.duration);
    rooms.addAll(other.rooms);
  }

  // Deep copy, with a different number of guests
  public Reservation(Reservation other, int numGuests) {
    this(other.customer, numGuests, other.start, other.duration);
    rooms.addAll(other.rooms);
  }
  
  public void add(Room r) {
    rooms.add(r);
  }
  
  public boolean contains(int room) {
    for (Room r : rooms) {
      if (r.getNumber() == room) {
        return true;
      }
    }
    return false;
  }
  
  public boolean matches(Reservation other) {
    return this.start == other.start && this.duration == other.duration && this.customer.equals(other.customer);
  }

  public void merge(Reservation other) {
    for (Room r: other) {
      rooms.add(r);
    }
  }

  public int totalCost() {
    int cost = 0;
    
    for (Room r : rooms) {
      cost += r.getRate();
    }
    
    return cost;
  }

  public int getStart() {
    return start;
  }

  public int getEnd() {
    return start + duration;
  }

  public int getNumGuests() {
    return numGuests;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void roomBooked() {
    this.customer.roomBooked(this.duration);
  }

  public void revokePoints() {
    this.customer.roomCancelled(this.duration);
  }

  /**
   * Determine if this reservation matches the given hotel ID and start date.
   */
  // public boolean match(int id, int start) {
  //   return this.start == start;
  // }

  /**
   * Determine if this reservation conflicts with anotehr.
   */
  public boolean conflicts(Reservation other) {
    return (this.getStart() >= other.getStart() && this.getStart() < other.getEnd()) ||
        (other.getStart() >= this.getStart() && other.getStart() < this.getEnd());
  }
  
  /**
   * Cancel the portion of the reservation starting at the given "from" day.
   *
   * @return true if the entire reservation was cancelled (zero days)
   */
  public boolean cancel(int from) {
    if (start == from) {
      duration = 0;
      return true;
    } else {
      duration = from - start;
      return false;
    }
  }

  public int compareTo(Reservation r) {
    return start - r.start;
  }

  public Iterator<Room> iterator() {
    return rooms.iterator();
  }

  /**
   * A specialized toString for converting the reservation to a readable customer-focused string.
   */
  public String toCustomerString() {
    int cost = 0;
    String roomNumbers = "";
    
    for (Room r : rooms) {
      roomNumbers += r.toShortString() + ", ";
      cost += r.getRate();
    }
    if (roomNumbers.length() > 0)
      roomNumbers = roomNumbers.substring(0, roomNumbers.length() - 2);
    
    return "Day " + start + ", " + duration + " night" + (duration == 1 ? "" : "s") + ": room " + roomNumbers + " ($" + (cost * duration) + ")";
  }
  
  /**
   * A specialized toString for converting the reservation to a string where the room number(s) are already known
   */
  public String toRoomString() {
    return "Day " + start + ", " + duration + " night" + (duration == 1 ? "" : "s") + ": " + customer;
  }

  public String toOutput() {
    return "RESERVE,"+ getCustomer().getID()+","+getNumGuests()+","+getStart()+","+getNumGuests();
  }

  public String toString() {
    return " { " + toCustomerString() + " Guests: " + numGuests + " Customer: " + customer + " }";
  }
}
