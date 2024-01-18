package comp2150.reservatron.server.session;

/**
 * COMP 2150 Summer 23 Assignment 2: Reserv-a-tron admin class (solution)
 */

public class Admin extends User {
  public Admin(String id, String name, String password) {
    super(id, name, password);
  }
  
  public String userType() {
    return "administrator";
  }
  
  public String canReserve(String id) {
    return "admin can't manage reservations";
  }

  public String canPrintCustomer(String id) {
    return null;
  }

  public String canManageHotel() {
    return "admin can't manage hotel";
  }

  public String canViewUsers() {
    return null;
  }

  public String canManageUsers() {
    return null;
  }
  public String canCreateHotels() {
    return null;
  }
}