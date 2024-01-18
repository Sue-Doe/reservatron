package comp2150.reservatron.server;

import comp2150.reservatron.server.hotel.HotelServer;
import comp2150.reservatron.server.reserve.PrintCust;
import comp2150.reservatron.server.reserve.ReserveServer;
import comp2150.reservatron.server.session.SessionServer;

/**
 * CustomerServer
 *
 * @author Max Waldner, 7889322
 *
 *         REMARKS: Customer interface for a user
 */
public interface CustomerServer extends SessionServer, ReserveServer, HotelServer, PrintCust {

}
