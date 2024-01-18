package comp2150.reservatron.server.manager;
import java.util.ArrayList;

import comp2150.reservatron.server.hotel.HotelServer;
import comp2150.reservatron.server.reserve.PrintCust;
import comp2150.reservatron.server.reserve.ReserveServer;
import comp2150.reservatron.server.session.ManageSessionServer;



/**
 * ManagerServer
 *
 * @author Max Waldner, 7889322
 *
 *         REMARKS: Manager interface for a reservationServer
 */

public interface ManagerServer extends PrintCust, HotelServer, ManageSessionServer, ReserveServer {
     /*** ManagerServer interface, comp2150.reservatron.server.manager ***/
  /*** The next group of calls can only be made if the session belongs to a "manager" ***/
  
  // Return a list of hotel names available to this manager
  // Return null on error
  ArrayList<String> getMyHotels(int session);
  
  // Print reservations on a day
  // Returns null on failure or a single string to print
  String printDay(int session, String hotel, int day);
  
  // Print reservations for a room
  // Returns null on failure or a single string to print
  String printRoom(int session, String hotel, int room);

  String currentUserID(int session);

}
