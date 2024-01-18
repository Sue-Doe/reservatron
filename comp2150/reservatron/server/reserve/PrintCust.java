package comp2150.reservatron.server.reserve;

public interface PrintCust {
      /*** The following method is available to all users but doesn't belong to any of the other interfaces ***/
  /*** Put it somewhere good ***/

  // Print the name and reservations for the given user ID
  // !For all hotels
  // Returns null on failure or a single string to print
  // !If the session belongs to is a customer, the user **must** match the customer or be null
  String printCustomer(int session, String user);
  String logout(int session);

}
