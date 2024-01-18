package comp2150.reservatron.server.session;

public interface SessionServer {
    /*** SessionServer interface, comp2150.reservatron.server.session ***/

  // This is called to allow a user to log in
  // !Returns a sesion ID or a negative value if something went wrong
  int login(String user, String password);
  
  // This is called when a login fails, to get the resulting error message
  // It is passed the result from the failed call to login()
  // Returns null on failure or an error message
  String loginError(int result);

  // This is called to allow the user with the given session ID
  // Returns null on success, or an error message if something went wrong
  String logout(int session);
    
  // Determine the type of user ("customer" or "manager" or "administrator")
  // Returns null on failure or one of the three strings above
  String loginType(int session);
  
  // Get the user ID for the indicated session
  // Returns null on failure or the currently logged in user ID
  String currentUserID(int session);

}
