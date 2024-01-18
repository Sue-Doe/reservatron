package comp2150.reservatron.server.session;

public interface ManageSessionServer {
    /*** ManageSessionServer interface, comp2150.reservatron.server.session ***/
  /*** The following two calls can only be made if a "manager" or "administrator" is logged in ***/
  
  // Check the type of a user ID
  // Returns null on failure or one of the three strings above
  String loginTypeID(int session, String user);
  
  // Get the user's real name
  // Returns null on failure or the real name of the user
  String realName(int session, String user);


}
