package comp2150.reservatron.server;

import java.util.ArrayList;

import comp2150.reservatron.server.hotel.HotelManager;
import comp2150.reservatron.server.reserve.ReservationManagerList;
import comp2150.reservatron.server.session.UserManager;

/**
 * A3Server
 *
 * @author Max Waldner, 7889322
 *
 *         REMARKS: Implementation of the A3ReservationServer interface
 */

public class A3Server implements A3ReservationServer {

    public static final String USERS_FILE = "comp2150/reservatron/server/data/a2users.txt"; // users file
    public static final String HOTELS_FILE = "comp2150/reservatron/server/data/hotels.txt"; // hotels file
    public static final String RESERVATIONS_FILE = "comp2150/reservatron/server/data/reservations.txt"; // reservations file

    private UserManager users; // manager for users
    private HotelManager hotels; // manager for hotels
    private ReservationManagerList rm; // manager for reservationsManagers

    /**
     * Constructor for A3Server. Initializes the user, hotel, and reservation
     * managers.
     */
    public A3Server() {
        users = new UserManager();
        hotels = new HotelManager();
        rm = new ReservationManagerList();
    }

    /**
     * Starts up the server by reading user, hotel, and reservation data from files
     * 
     * RETURNS:
     * A String from reading the user file null on success or error message.
     */
    @Override
    public String startup() {

        String result;
        result = users.readUserFile(USERS_FILE);
        hotels.readHotels(HOTELS_FILE);
        rm.readReservations(RESERVATIONS_FILE, hotels);

        return result;
    }

    /**
     * Attempts to log in a user with the provided credentials.
     * 
     * PARAMETERS:
     * user - the username to log in.
     * password - the password for the user.
     * 
     * RETURNS:
     * An integer indicating the result of the login attempt (0 for success)
     */
    @Override
    public int login(String user, String password) {
        return users.login(user, password);
    }

    /**
     * Provides a message corresponding to the login attempt failure.
     * 
     * PARAMETERS:
     * result - the integer from the login attempt.
     * 
     * RETURNS:
     * A String message explaining the login attempt.
     */
    @Override
    public String loginError(int result) {
        String message;
        if (result == 0) {
            message = "Login successful";
        } else if (result == -1) {
            message = "User not found";
        } else if (result == -2) {
            message = "Incorrect password";
        } else if (result == -3) {
            message = "User already logged in";
        } else {
            message = "Unknown error";
        }
        return message;
    }

    /**
     * Logs out a user.
     * 
     * PARAMETERS:
     * session - the session ID
     * 
     * RETURNS:
     * A String indicating the result of the logout process null on success, Error
     * message on failure.
     */
    @Override
    public String logout(int session) {
        return users.logout(session);
    }

    /**
     * gets the login type of the current user.
     * 
     * PARAMETERS:
     * session - the session ID
     * 
     * RETURNS:
     * A String indicating the login type of the current user or null on failure.
     */
    @Override
    public String loginType(int session) {
        return users.loginType();
    }

    /**
     * gets the current user ID based on the session.
     *
     * PARAMETERS:
     * session - the session ID
     *
     * RETURNS:
     * A String representing the current user's ID or null on failure.
     */
    @Override
    public String currentUserID(int session) {
        return users.currentUserID();
    }

    /**
     * get the login type ID for a given user.
     *
     * PARAMETERS:
     * session - the session ID
     * user - the username to get the login type ID.
     *
     * RETURNS:
     * A String indicating the login type ID of the given user or null on failure.
     */
    @Override
    public String loginTypeID(int session, String user) {
        return users.loginTypeID(user);
    }

    /**
     * gets the real name of a given user.
     *
     * PARAMETERS:
     * session - the session ID
     * user - the username whose real name is to be retrieved.
     *
     * RETURNS:
     * A String representing the real name of the given user or null on failure.
     */
    @Override
    public String realName(int session, String user) {
        return users.realName(user);
    }

    /**
     * Creates a new hotel from a file.
     *
     * PARAMETERS:
     * session - the session ID
     * filename - the name of the file containing hotel data.
     *
     * RETURNS:
     * A String indicating the result of the hotel creation process null on success
     * or error message.
     */
    @Override
    public String createHotel(int session, String filename) {
        String result = users.canCreateHotel();
        if (result == null) {
            result = hotels.createHotel(filename);
        }
        return result;
    }

    /**
     * Creates a new user.
     *
     * PARAMETERS:
     * session - the session ID
     * user - the username for the new user.
     * name - the real name of the new user.
     * type - the type of the new user (e.g., admin, manager).
     * password - the password for the new user.
     *
     * RETURNS:
     * A String indicating the result of the user creation process null on success
     * or error message.
     */
    @Override
    public String createUser(int session, String user, String name, String type, String password) {
        return users.createUser(user, name, type, password);
    }

    /**
     * Assigns a user as the manager of a given hotel.
     *
     * PARAMETERS:
     * session - the session ID
     * user - the username of the user to be made a hotel manager.
     * hotelName - the name of the hotel for which the user will be manager.
     *
     * RETURNS:
     * A String indicating the result assignment process null on success or error
     * message.
     */
    @Override
    public String makeHotelManager(int session, String user, String hotelName) {
        String message = users.makeHotelManager(user);
        if (message == null) {
            message = hotels.makeHotelManager(user, hotelName);
        }
        return message;
    }

    /**
     * Changes the password for a given user.
     *
     * PARAMETERS:
     * session - the session ID
     * user - the username of the user whose password is to be changed.
     * password - the new password for the user.
     *
     * RETURNS:
     * A String indicating the result of the password change process null on success
     * or error message.
     */
    @Override
    public String changePassword(int session, String user, String password) {
        return users.changePassword(user, password);
    }

    /**
     * Deletes a user from the system. This process also involves removing any
     * associated
     * data with the user, such as their reservations.
     *
     * PARAMETERS:
     * session - the session ID
     * user - the username of the user to be deleted from the system.
     *
     * RETURNS:
     * A String indicating the result of the user deletion process null on success
     * or error message.
     */
    @Override
    public String deleteUser(int session, String user) {
        String message = users.deleteUser(user);

        if (message == null) {
            rm.deleteUser(user);
        }
        return message;
    }

    /**
     * Safely shuts down the server, ensuring all data files are updated.
     * This method should only be called once upon quitting, and exclusively by an
     * admin.
     * It updates all the data files, including user, reservation, and hotel data.
     *
     * PARAMETERS:
     * session - the session ID
     *
     * RETURNS:
     * A String indicating the result of the shutdown process null on success or
     * error message.
     */
    public String exit(int session) {
        hotels.exit(HOTELS_FILE);
        users.exit(USERS_FILE);
        rm.exit(RESERVATIONS_FILE);
        return null;
    }

    /**
     * Retrieves a list of hotel names managed by the current user.
     *
     * PARAMETERS:
     * session - the session ID
     *
     * RETURNS:
     * An ArrayList<String> containing the names of hotels managed by the current
     * user. Returns null on error.
     */
    public ArrayList<String> getMyHotels(int session) {
        return hotels.getMyHotels(users.currentManager());
    }

    /**
     * Prints reservations for a given day at a given hotel.
     *
     * PARAMETERS:
     * session - the session ID
     * hotel - the name of the hotel.
     * day - the day for which reservations are to be printed.
     *
     * RETURNS:
     * A String containing the reservations for the given day at the given
     * hotel. Returns null on failure.
     */
    public String printDay(int session, String hotel, int day) {
        String message = hotels.canPrintDay(hotel, users.currentUserID());
        if (message == null) {
            message = rm.printDay(hotels.getHotel(hotel), day);
        }
        return message;
    }

    /**
     * Prints reservations for a given room at a given hotel.
     *
     * PARAMETERS:
     * session - the session ID
     * hotel - the name of the hotel.
     * room - the room number for which reservations are to be printed.
     *
     * RETURNS:
     * A String containing the reservations for the given room at the given
     * hotel. Returns null on failure.
     */
    public String printRoom(int session, String hotel, int room) {
        String message = hotels.canPrintRoom(hotel, users.currentUserID());
        if (message == null) {
            message = rm.printRoom(hotels.getHotel(hotel), room);
        }
        return message;
    }

    /**
     * Makes a reservation for a given user at a given hotel.
     *
     * PARAMETERS:
     * session - the session ID
     * user - the username for who the reservation is made.
     * hotel - the name of the hotel.
     * numGuests - the number of guests for the reservation.
     * start - the start day of the reservation.
     * duration - the duration of the stay.
     *
     * RETURNS:
     * A String indicating the result of the reservation process. Returns null on
     * success, or an error message
     */
    public String makeReservation(int session, String user, String hotel, int numGuests, int start, int duration) {

        String message = "Error: Cannot Make Reservation";

        if (users.canMakeReservations(user) == null
                || hotels.canMakeReservations(hotel, users.currentUserID()) == null) {
            message = rm.makeReservation(hotels.getHotel(hotel), user, numGuests, start, duration);
        } else {
            message = hotels.canMakeReservations(hotel, user);
        }
        return message;
    }

    /**
     * Cancels a reservation for a given user at a given hotel starting from a
     * given day.
     *
     * PARAMETERS:
     * session - the session ID
     * user - the username for whom the reservation is cancelled.
     * hotel - the name of the hotel.
     * start - the start day of the reservation to be cancelled.
     *
     * RETURNS:
     * A String indicating the result of the cancellation process null on success, or an error message.
     */
    public String cancelReservation(int session, String user, String hotel, int start) {
        String message = "Error: Cannot Cancel Reservation";

        if (users.canMakeReservations(user) == null
                || hotels.canMakeReservations(hotel, users.currentUserID()) == null) {
            message = rm.cancelReservation(hotels.getHotel(hotel), user, start);
        } else {
            message = hotels.canMakeReservations(hotel, user);
        }
        return message;
    }

    /**
     * Retrieves a list of all hotel names.
     *
     * PARAMETERS:
     * session - the session ID
     *
     * RETURNS:
     * An ArrayList<String> containing the names of all hotels. Returns null on failure.
     */
    public ArrayList<String> getHotels(int session) {
        return hotels.getHotels();
    }

    /**
     * Prints the name and reservations for a given user ID for all hotels.
     *
     * PARAMETERS:
     * session - the session ID
     * user - the username whose reservations are printed.
     *
     * RETURNS:
     * A String containing the reservations of the given user across all hotels.
     * Returns null on failure.
     */
    public String printCustomer(int session, String user) {
        String message = users.canMakeReservations(user);

        if (message == null) {
            message = "Reservations for " + users.stringHelper(user) + ":\n";
            message += rm.printCustomer(user);
        }
        return message;
    }

}
