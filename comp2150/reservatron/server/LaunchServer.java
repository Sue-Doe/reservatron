package comp2150.reservatron.server;

/**
 * LaunchServer
 *
 * @author Max Waldner, 7889322
 *
 *         REMARKS: Startup interface for a server
 */
public interface LaunchServer {
  /*** LaunchServer interface, comp2150.reservatron.server ***/

  // This can only be called, once, on creation
  // Returns null on success, or an error message if something went wrong
  String startup();

}
