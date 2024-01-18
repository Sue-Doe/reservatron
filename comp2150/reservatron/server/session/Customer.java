package comp2150.reservatron.server.session;

/**
 * COMP 2150 Summer 23 Assignment 2: Reserv-a-tron customer class (solution)
 */

public class Customer extends User implements Comparable<Customer> {
  private RewardCard rewards;


  public Customer(String id, String name, String password) {
    super(id, name, password);
    this.rewards = new RewardCard();
  }

  public Customer(String id) {
    super(id,id,"");
    this.rewards = new RewardCard();
  }

  public String userType() {
    return "customer";
  }

  public void roomBooked(int duration) {
    rewards.pointUpdate(duration);
  }

  public int getPoints() {
    return this.rewards.getPoints();
  }

  public void roomCancelled(int duration) {
    this.rewards.revokePoints(duration);
  }

  public void setPoints(int points) {
    this.rewards.setPoints(points);
  }


  public String canReserve(String id) {
    if (matchesID(id)) {
      return null;
    }
    return "customer can't manage another customer's reservations";
  }

  public String canPrintCustomer(String id) {
    if (matchesID(id)) {
      return null;
    }
    return "customer can't print another customer's reservations";
  }

  public String canManageHotel() {
    return "customer can't manage hotel";
  }

  public String canViewUsers() {
    return "customer can't view users";
  }

  public String canManageUsers() {
    return "customer can't manage users";
  }

  public boolean equals(Object c) {
    if(c instanceof Customer) {
      return this.getName().equals(((Customer)c).getName());
    }
    return false;
  }

  @Override
  public int compareTo(Customer c) {
    if(c instanceof Customer) {
      return this.getName().compareTo(((Customer)c).getName());
    }
    return 0;
  }

  public String toString() {
    return super.toString() + " " + this.rewards;
  }


  public String canCreateHotels() {
      return "customer can't create hotels";
  }


}