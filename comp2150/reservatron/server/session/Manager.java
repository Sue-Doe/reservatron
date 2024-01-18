package comp2150.reservatron.server.session;

import java.util.ArrayList;

/**
 * COMP 2150 Summer 23 Assignment 2: Reserv-a-tron manager class (solution)
 */

public class Manager extends User {
  ArrayList <String> hotelList;
  public Manager(String id, String name, String password) {
    super(id, name, password);
    hotelList = new ArrayList<String>();

  }
  
  public String userType() {
    return "manager";
  }

  public String canReserve(String id) {
    return null;
  }

  public String canPrintCustomer(String id) {
    return null;
  }

  public String canManageHotel() {
    return null;
  }

  public String canViewUsers() {
    return null;
  }

  public void addHotel(String hotelname) {
    hotelList.add(hotelname);
  }

    public ArrayList<String> getHotelList() {
    return hotelList;
  }

  public String canManageUsers() {
    return "manager can't edit users";
  }
  
  public String canCreateHotels() {
    return "manager can't create hotels";
  }
}