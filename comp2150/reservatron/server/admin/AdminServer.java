package comp2150.reservatron.server.admin;

import comp2150.reservatron.server.hotel.HotelServer;
import comp2150.reservatron.server.reserve.PrintCust;
import comp2150.reservatron.server.session.ManageSessionServer;
import comp2150.reservatron.server.session.SessionServer;

/**
 * AdminServer
 * 
 * REMARKS: Admin interface for a reservationServer
 */
public interface AdminServer  extends ManageSessionServer, PrintCust, HotelServer, SessionServer{
    /*** AdminServer interface, comp2150.reservatron.server.admin ***/
  /*** The next group of calls can only be made if the session belongs to an "administrator" ***/
  
  // Create a new hotel, read in from the given text file
  // Returns null on success, or an error message if something went wrong
  String createHotel(int session, String filename);
  
  // Create a new user with the given user ID, name, type, and password
  // Returns null on success, or an error message if something went wrong
  String createUser(int session, String user, String name, String type, String password);

  // Add an existing manager user ID to a hotel, allowing them to manage it
  // Returns null on success, or an error message if something went wrong
  String makeHotelManager(int session, String user, String hotelName);
  
  // Change a user's password
  // Returns null on success, or an error message if something went wrong
  String changePassword(int session, String user, String password);
  
  // Delete a user
  // Returns null on success, or an error message if something went wrong
  String deleteUser(int session, String user);

  // This can only be called once, on quit (and only by an admin)
  // !All of the data files (user file, reservations, hotels!) must be updated!
  // !Returns null on success, or an error message if something went wrong
  String exit(int session);

  String currentUserID(int session);

}
