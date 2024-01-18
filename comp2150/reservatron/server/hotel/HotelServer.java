package comp2150.reservatron.server.hotel;

import java.util.ArrayList;
/**
 * HotelServer
 *
 * @author Max Waldner, 7889322
 *
 *         REMARKS: Hotel interface for a reservationServer
 */
public interface HotelServer {
      /*** HotelServer interface, comp2150.reservatron.server.hotel ***/
  
  // Return a list of all hotel names
  // Return null on error
  ArrayList<String> getHotels(int session);





  
}
