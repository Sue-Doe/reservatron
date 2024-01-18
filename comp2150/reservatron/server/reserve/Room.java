package comp2150.reservatron.server.reserve;

/**
 * Room class: The description of a room in a hotel. Includes a list of all
 * reservations of that room. Orderable by ascending room rate.
 * 
 * @author john
 */

public class Room implements Comparable<Room> {
  private int number;
  private String type;
  private int capacity;
  private int rate;
  private ReservationsList reservations; // specific to this room

  public Room(int number, String type, int capacity, int rate) {
    this.number = number;
    this.type = type;
    this.capacity = capacity;
    this.rate = rate;
    reservations = new ReservationsList();
  }

  /**
   * Determine if a reservation conflicts with others in the room.
   */
  public boolean conflicts(Reservation r) {
    return reservations.conflicts(r);
  }

  public void addReservation(Reservation r) {
    assert !conflicts(r);
    reservations.add(r);
  }

  public boolean removeReservation(Reservation r) {
    return reservations.remove(r);
  }
  
  public int getNumber() {
    return number;
  }

  public int getRate() {
    return rate;
  }

  public int getCapacity() {
    return capacity;
  }

  public int compareTo(Room other) {
    return this.rate - other.rate;
  }

  public String toShortString() {
    return number + " (" + type.toString().toLowerCase() + ")";
  }

  public String toOutString() {
    return type+","+capacity+","+rate;
  }
  
  @Override
  public String toString() {
    return type.toString().toLowerCase() + " " + capacity + " ($" + rate + ")" + reservations.toRoomString();
  }
}
