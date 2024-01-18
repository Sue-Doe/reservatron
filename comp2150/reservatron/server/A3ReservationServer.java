package comp2150.reservatron.server;

import comp2150.reservatron.server.admin.AdminServer;
import comp2150.reservatron.server.hotel.HotelServer;
import comp2150.reservatron.server.manager.ManagerServer;
import comp2150.reservatron.server.reserve.ReserveServer;
import comp2150.reservatron.server.session.ManageSessionServer;
import comp2150.reservatron.server.session.SessionServer;

/**
 * A3Server
 *
 * @author Max Waldner, 7889322
 *
 *         REMARKS: Central Interface of the A3ReservationServer
 */

// Told these warnings are fine from Instructor
public interface A3ReservationServer extends LaunchServer, CustomerServer, ManageSessionServer, SessionServer,
        AdminServer, ManagerServer, ReserveServer, HotelServer {
}
