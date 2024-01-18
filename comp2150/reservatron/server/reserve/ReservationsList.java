package comp2150.reservatron.server.reserve;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * COMP 2150 Summer 23 Assignment 1: Hotel reservations
 */

/**
 * ReservationsList class: A list of reservations ordered by starting date.
 */

public class ReservationsList implements Iterable<Reservation> {
  private ArrayList<Reservation> list;

  public ReservationsList() {
    list = new ArrayList<>();
  }

  /**
   * Perform an ordered insertion (by start date) of the reservation in the list.
   */
  public void add(Reservation r) {
    for (int i = 0; i < list.size(); i++) {
      if (r.compareTo(list.get(i)) < 0) {
        list.add(i, r);
        return;
      }
    }
    list.add(r);
  }

  public boolean remove(Reservation res) {
    return list.remove(res);
  }

  // public boolean remove(Reservation r) {
  //   for (Reservation current : list) {
  //     if (current == r) {
  //       list.remove(current);
  //       return true;
  //     }
  //   }
  //   return false;
  // }

  /**
   * Determine if a reservation conflicts with others in the list.
   */
  public boolean conflicts(Reservation r) {
    for (Reservation check : list) {
      if (r.conflicts(check))
        return true;
    }
    return false;
  }
  
  /**
   * Return a subset of reservations containing all that conflict with the given one.
   */
  public ReservationsList allConflicts(Reservation r) {
    ReservationsList result = new ReservationsList();
    
    for (Reservation check : list) {
      if (r.conflicts(check))
        result.add(check);
    }
    
    return result;
  }

  /**
   * Return a reservation that is has a matching start, duration, and customer, if there is one.
   */
  public Reservation findMatchingReservation(Reservation res) {
    for (Reservation r : list) {
      if (r.matches(res)) {
        return r;
      }
    }

    return null;
  }

  public ReservationsList reservationsForRoom(int room) {
    ReservationsList result = new ReservationsList();

    for (Reservation r : list) {
      if (r.contains(room)) {
        result.add(r);
      }
    }

    return result;
  }

  public ReservationsList reservationsForCustomer(String customerName) {
    ReservationsList result = new ReservationsList();

    for (Reservation r : list) {
      if (customerName.equals(r.getCustomer().getName())) {
        result.add(r);
      }
    }

    return result;
  }

  // Could have just used the method above and checked the size...
  public int countForCustomer(String customerName) {
    int count = 0;

    for (Reservation r : list) {
      if (customerName.equals(r.getCustomer().getName())) {
        count++;
      }
    }

    return count;
  }

  public Iterator<Reservation> iterator() {
    return list.iterator();
  }
  
  public int size() {
    return list.size();
  }
  
  /**
   * A specialized toString for converting the list to a readable room-focused string.
   */
  public String toRoomString() {
    String result = " Reservations: ";
    boolean first = true;

    if (list.size() == 0) {
      result += "none";
    } else {
      for (Reservation r : list) {
        if (!first)
          result += "\n               ";
        result += r.toRoomString();
        first = false;
      }
    }

    return result;
  }

  public String toOutput() {
    String result = "";
    for (int i = 0; i < list.size(); i++) {
      result += list.get(i).toOutput() + "\n";
    }
    return result;
  }



  @Override
  public String toString() {
    return list.toString();
  }
}
