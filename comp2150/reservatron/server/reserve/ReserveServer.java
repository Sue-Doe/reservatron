package comp2150.reservatron.server.reserve;

/**
 * ReserveServer
 *
 * @author Max Waldner, 7889322
 *
 *         REMARKS: Reserve interface for a reservationServer
 */

public interface ReserveServer {
  /*** ReserveServer interface, comp2150.reservatron.server.reserve ***/

  // Make a reservation for the given user, with the given options
  // !At the given hotel
  // Returns null on success, or an error message if something went wrong
  // !If the session belongs to is a customer, the user **must** match the
  // customer or be null
  String makeReservation(int session, String user, String hotel, int numGuests, int start, int duration);

  // Cancel reservations for the given user, starting from the given day
  // !At the given hotel
  // Returns null on success, or an error message if something went wrong
  // !If the session belongs to is a customer, the user **must** match the
  // customer or be null
  String cancelReservation(int session, String user, String hotel, int start);

}
