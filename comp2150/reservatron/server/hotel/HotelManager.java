package comp2150.reservatron.server.hotel;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import comp2150.reservatron.server.A1;
import comp2150.reservatron.server.reserve.Hotel;
import comp2150.reservatron.server.reserve.ReservationManager;

/**
 * HotelManager
 *
 * @author Max Waldner, 7889322
 *
 *         REMARKS: Manager for all hotels
 */
public class HotelManager {

    private static final String HOTEL_MANAGER = "comp2150/reservatron/server/Data/managers.txt"; // file of managers

    private HashMap<String, Hotel> hotelMap; // map of hotels
    private HashMap<String, ReservationManager> reservationMap; // map of reservation managers

    /**
     * Constructor for HotelManager. Initializes the hotel and reservation maps.
     */
    public HotelManager() {
        hotelMap = new HashMap<String, Hotel>();
        reservationMap = new HashMap<String, ReservationManager>();
    }

    /**
     * Reads hotel data from a file and loads it into the HotelManager.
     *
     * PARAMETERS:
     * filename - The name of the file containing hotel data.
     */
    public void readHotels(String filename) {

        try {
            Scanner scanner = new Scanner(new FileReader(filename));
            A1.readHotels(scanner, this);
            readManagers(HOTEL_MANAGER);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reads manager data from a file and adds managers with hotels.
     *
     * PARAMETERS:
     * fileName - The name of the file containing manager data.
     */
    public void readManagers(String fileName) {

        try {
            Scanner scanner = new Scanner(new FileReader(fileName));

            String line = scanner.nextLine();

            while (line != null) {
                String[] tokens = line.split(",");
                String hotelName = tokens[0];

                for (int i = 1; i < tokens.length; i++) {
                    hotelMap.get(hotelName).addManager(tokens[i]);
                }
                line = scanner.nextLine();
            }
        } catch (Exception e) {

        }
    }

    /**
     * Checks if a user can make reservations at a specific hotel.
     *
     * PARAMETERS:
     * hotel - The name of the hotel.
     * user - The username of the user.
     *
     * RETURNS:
     * A String on whether the user can make reservations or an error message.
     */

    public String canMakeReservations(String hotel, String user) {
        String message = "Error: Cannot Make Reservation";
        if (hotelMap.containsKey(hotel)) {
            message = "Error: hotel does not exist";
            if (hotelMap.get(hotel).isManager(user)) {
                message = null;
            } else {
                message = "Error: User is not a manager";
            }
        }
        return message;
    }

    /**
     * Checks if a user can print the reservations for a specific day at a specific
     * hotel.
     *
     * PARAMETERS:
     * hotel - The name of the hotel.
     * user - The username of the user.
     *
     * RETURNS:
     * A String of whether the user can print the reservations or an error message.
     */
    public String canPrintDay(String hotel, String user) {
        String message;
        if (user != null) {

            message = "Error: " + user + " cannot print day";
            if (hotelMap.containsKey(hotel)) {
                message = "Error: hotel does not exist";
                if (hotelMap.get(hotel).isManager(user)) {
                    message = null;
                } else {
                    message = "Error: User is not a manager";
                }
            }
        } else {
            message = "Error: User Null, Cannot Print Day";
        }
        return message;
    }

    /**
     * Checks if a user can print the reservations for a given room at a specific
     * hotel.
     *
     * PARAMETERS:
     * hotel - The name of the hotel.
     * user - The username of the user.
     *
     * RETURNS:
     * A String of whether the user can print the reservations or an error message.
     */
    public String canPrintRoom(String hotel, String user) {
        String message;
        if (user != null) {
            message = "Error: " + user + " cannot print room";
            if (hotelMap.containsKey(hotel)) {
                message = "Error: hotel does not exist";
                if (hotelMap.get(hotel).isManager(user)) {
                    message = null;
                } else {
                    message = "Error: User is not a manager";
                }
            }
        } else {
            message = "Error: User Null, Cannot Print Room";
        }
        return message;
    }

    /**
     * Retrieves a list of hotels managed by a specific user.
     *
     * PARAMETERS:
     * user - The username of the manager.
     *
     * RETURNS:
     * An ArrayList<String> containing the names of hotels managed by the user.
     */
    public ArrayList<String> getMyHotels(String user) {
        ArrayList<String> myHotels;
        if (user == null) {
            myHotels = null;
        } else {
            myHotels = new ArrayList<>();
            for (String i : hotelMap.keySet()) {
                if (hotelMap.get(i).isManager(user)) {
                    myHotels.add(i);
                }
            }
        }
        return myHotels;
    }

    /**
     * Retrieves a given hotel by its name.
     *
     * PARAMETERS:
     * hotelName - The name of the hotel.
     *
     * RETURNS:
     * The Hotel object corresponding to the given name.
     */
    public Hotel getHotel(String hotelName) {
        return hotelMap.get(hotelName);
    }

    /**
     * Creates a hotel by reading its data from a file.
     *
     * PARAMETERS:
     * fileName - The name of the file containing hotel data.
     *
     * RETURNS:
     * A String of the result of the hotel creation process or an error message.
     */
    public String createHotel(String fileName) {
        String message = "Error: Cannot Create hotel";
        int currSize = hotelMap.size();
        A1.readSingleHotelFloorPlan(fileName, this);
        if (currSize < hotelMap.size()) {
            message = null;
        }
        return message;
    }

    /**
     * Adds a hotel to the HotelManager.
     *
     * PARAMETERS:
     * hotel - The Hotel object to be added.
     *
     * RETURNS:
     * A String of the result of adding the hotel or an error message.
     */

    public String addHotel(Hotel hotel) {

        String message = "Error: Cannot Add hotel";
        if (hotel != null) {
            String name = hotel.getHotelName();
            if (hotelMap.containsKey(name)) {
                message = "Hotel Already Exists";
            } else {
                hotelMap.put(name, hotel);
                ReservationManager rm = new ReservationManager(hotel);
                reservationMap.put(name, rm);
                message = null;
            }
        }
        return message;
    }

    /**
     * Assigns a user as a manager for a given hotel.
     *
     * PARAMETERS:
     * user - The username of the user to be assigned as manager.
     * hotelName - The name of the hotel.
     *
     * RETURNS:
     * A String of the result of the assignment process or an error message.
     */
    public String makeHotelManager(String user, String hotelName) {

        String message = "Error: Cannot Make Hotel Manager";

        Hotel foundHotel = hotelMap.get(hotelName);
        if (foundHotel != null) {
            message = foundHotel.addManager(user);
        }
        return message;
    }

    /**
     * Saves the current state of hotels and managers to files on exit.
     *
     * PARAMETERS:
     * hotelFile - The name of the file to save hotel data.
     */
    public void exit(String hotelFile) {

        try {
            FileWriter writerHotels = new FileWriter(hotelFile);
            FileWriter writerManagers = new FileWriter(HOTEL_MANAGER);
            for (String name : hotelMap.keySet()) {
                writerHotels.write(hotelMap.get(name).toOutputString() + "\n");
                writerManagers.write(hotelMap.get(name).toManagerString() + "\n");
            }
            writerHotels.close();
            writerManagers.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints the reservations for a given day at a given hotel.
     *
     * PARAMETERS:
     * hotel - The name of the hotel.
     * day - The day for which to print reservations.
     *
     * RETURNS:
     * A String of the reservation details or null if no data.
     */
    public String printDay(String hotel, int day) {
        String message = null;
        if (hotelMap.containsKey(hotel)) {
            message = reservationMap.get(hotel).printDayString(day);
        }
        return message;
    }

    /**
     * Prints the reservations for a given room at a given hotel.
     *
     * PARAMETERS:
     * hotel - The name of the hotel.
     * room - The room number for which to print reservations.
     *
     * RETURNS:
     * A String of the reservation details or null if no data.
     */
    public String printRoom(String hotel, int room) {
        String message = null;
        if (hotelMap.containsKey(hotel)) {
            message = reservationMap.get(hotel).printRoomString(room);
        }
        return message;
    }

    /**
     * Retrieves a list of all hotels.
     *
     * RETURNS:
     * An ArrayList<String> containing the names of all hotels.
     */
    public ArrayList<String> getHotels() {

        ArrayList<String> hotelNames = new ArrayList<>();
        for (String i : hotelMap.keySet()) {
            hotelNames.add(i);
        }
        return hotelNames;
    }

    /**
     * Prints the reservations for a specific customer across all hotels.
     *
     * PARAMETERS:
     * user - The username of the customer.
     *
     * RETURNS:
     * A String containing the reservation details or an error message.
     */
    public String printCustomer(String user) {

        String message = "Error: Cannot Print Customer";
        if (user != null) {
            message = "Error: User does not exist";
            if (reservationMap.containsKey(user)) {
                message = reservationMap.get(user).printCustomerString(user);
            }
        }
        return message;
    }
}
