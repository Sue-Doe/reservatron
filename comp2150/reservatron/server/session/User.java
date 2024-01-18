package comp2150.reservatron.server.session;

/**
 * COMP 2150 Summer 23 Assignment 2: Reserv-a-tron user class (solution)
 */

public abstract class User {
  private String id;
  private String name;
  private String password;
  
  public User(String id, String name, String password) {
    this.id = id;
    this.name = name;
    this.password = password;
  }

  public String getID() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }
  
  public boolean matchesID(String id) {
    return id.equals(this.id);
  }
  
  public boolean matchesPassword(String password) {
    return password.equals(this.password);
  }

  public void changePassword(String password) {
    this.password = password;
  }
  
  public String toOutputString() {
    return id + "," + name + "," + password + "," + userType();
  }

  public String toString() {
    return this.name;
  }
  
  // This particular implementation is not required:

  public abstract String userType();
  public abstract String canReserve(String id);
  public abstract String canPrintCustomer(String id);
  public abstract String canManageHotel();
  public abstract String canViewUsers();
  public abstract String canManageUsers();
  public abstract String canCreateHotels();
}