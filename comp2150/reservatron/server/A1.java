package comp2150.reservatron.server;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;

import comp2150.reservatron.server.hotel.HotelManager;
import comp2150.reservatron.server.reserve.Hotel;
import comp2150.reservatron.server.reserve.ReservationManagerList;
import comp2150.reservatron.server.reserve.Room;
import comp2150.reservatron.server.session.Admin;
import comp2150.reservatron.server.session.Customer;
import comp2150.reservatron.server.session.Manager;
import comp2150.reservatron.server.session.UserManager;

/**
 * COMP 2150 Summer 23 Assignment 2: Hotel reservations (slightly modified from A1)
 */

/**
 * Main program: read in a floor plan, uses and reservation requests.
 * 
 * @author john and max
 */
public class A1 {
  /**
   * Reads data from a Scanner object to construct and add hotels to the
   * HotelManager.
   *
   * PARAMETERS:
   * in - A Scanner object for reading the hotel data.
   * hotels - A HotelManager object to which the hotels are added.
   */
  public static void readHotels(Scanner in, HotelManager hotels) {

    String line;
    while (in.hasNextLine()) {
      line = in.nextLine();
      if (line.equals("----------Hotel--Start----------")) {
        Hotel hotel = readFloorPlan(in);
        if (hotel != null) {
          hotels.addHotel(hotel);
        }
      }
    }
  }

  /**
   * Reads reservation data from a Scanner object and processes them using a given
   * ReservationManagerList and HotelManager.
   *
   * PARAMETERS:
   * in - A Scanner object for reading the reservations.
   * rm - A ReservationManagerList object used to add reservations.
   * hotel - A HotelManager object.
   */
  public static void readReservations(Scanner in, ReservationManagerList rm, HotelManager hotel) {

    String line;
    while (in.hasNextLine()) {
      line = in.nextLine();
      if (line.equals("----------Hotel--Start----------")) {
        processReservations(in, rm, hotel);
      }
    }
  }

  /**
   * Reads and constructs a Hotel  based on hotel data from a Scanner
   * object.
   *
   * PARAMETERS:
   * in - A Scanner object for reading the floor plan data.
   *
   * RETURNS:
   * A Hotel object constructed based on hotel data. Returns null on success error message on failure 
   */
  public static Hotel readFloorPlan(Scanner in) {

    String line;
    String[] tokens;

    int num, floor, room;
    String name = null;

    // In order to make the floor plan immutable(!) we're going to build
    // an intermediate data representation that will be passed to the
    // Hotel constructor.
    Room[][] rooms = null;

    if ((name = nextNonEmptyLine(in)) == null) {
      System.out.println("Missing hotel name.");
    } else {
      if ((line = nextNonEmptyLine(in)) == null) {
        System.out.println("Missing number of floors.");
      } else {
        try {
          num = Integer.parseInt(line);
          rooms = new Room[num][];
        } catch (NumberFormatException nfe) {
          System.out.println("Invalid number of floors: " + line);
        }
      }
    }

    if (rooms != null) {
      for (int i = 0; i < rooms.length; i++) {
        floor = i + 1;
        num = 0;
        if ((line = nextNonEmptyLine(in)) == null) {
          System.out.println("Missing number of rooms on floor " + floor + ".");
        } else {
          try {
            num = Integer.parseInt(line);
            rooms[i] = new Room[num];
          } catch (NumberFormatException nfe) {
            System.out.println("Invalid number of rooms on floor " + floor + ": " + line);
          }
        }

        if (rooms[i] != null) {
          for (int j = 0; j < rooms[i].length; j++) {
            room = floor * 100 + (j + 1);
            if ((line = nextNonEmptyLine(in)) == null) {
              System.out.println("Missing description for room " + room + ".");
            } else {
              tokens = line.split(",");
              if (tokens.length >= 3) {
                if (tokens.length > 3) {
                  System.out.println("Ignoring extra tokens on room description for " + room + ":" + line);
                }
                try {
                  rooms[i][j] = new Room(room, tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
                } catch (NumberFormatException nfe) {
                  // error handled below because rooms[i][j] == null
                }
              }
              if (rooms[i][j] == null) {
                System.out.println("Invalid room description for " + room + ": " + line);
              }
            }
          }
        }
      }
    }

    if (rooms == null) {
      return null;
    } else {
      return new Hotel(name, rooms);

    }
  }

  /**
   * Reads user data from a Scanner object and adds users to the UserManager.
   *
   * PARAMETERS:
   * in - A Scanner object for reading the user data.
   * users - A UserManager object to which the read users are added.
   *
   * RETURNS:
   * A String indicating the result of the user data loading process. Returns null
   * on success error message on failure
   */
  public static String readUsers(Scanner in, UserManager users) {

    String line;
    String[] tokens;

    while (in.hasNextLine()) {
      line = in.nextLine();
      tokens = line.split(",");
      if (tokens.length < 4) {
        System.out.println("line too short: " + line);
      } else {
        if (tokens[3].equals("administrator")) {
          users.addUser(new Admin(tokens[0], tokens[1], tokens[2]));
        } else if (tokens[3].equals("customer")) {
          users.addUser(new Customer(tokens[0], tokens[1], tokens[2]));
        } else if (tokens[3].equals("manager")) {
          users.addUser(new Manager(tokens[0], tokens[1], tokens[2]));
        } else {
          System.out.println("unknown user type: " + line);
        }
      }
    }
    // If we got this far, load in old reservations
    // A1.processRequests(rm, RESERVATIONS_FILE, hotel);

    if (users.size() == 0) {
      return "Error: No users loaded";
    } else {
      return null;
    }

  }

  /**
   * Reads a single hotel's floor plan from a file and adds it to the
   * HotelManager.
   *
   * PARAMETERS:
   * filename - The name of the file containing the hotel data.
   * hotel - A HotelManager object to which the hotel is added.
   */
  public static void readSingleHotelFloorPlan(String filename, HotelManager hotel) {
    Scanner in;
    String line;
    String[] tokens;

    int num, floor, room;
    String name = null;

    // In order to make the floor plan immutable(!) we're going to build
    // an intermediate data representation that will be passed to the
    // Hotel constructor.
    Room[][] rooms = null;

    try {
      in = new Scanner(new FileReader(filename));
      if ((name = nextNonEmptyLine(in)) == null) {
        System.out.println("Missing hotel name.");
      } else {
        if ((line = nextNonEmptyLine(in)) == null) {
          System.out.println("Missing number of floors.");
        } else {
          try {
            num = Integer.parseInt(line);
            rooms = new Room[num][];
          } catch (NumberFormatException nfe) {
            System.out.println("Invalid number of floors: " + line);
          }
        }
      }

      if (rooms != null) {
        for (int i = 0; i < rooms.length; i++) {
          floor = i + 1;
          num = 0;
          if ((line = nextNonEmptyLine(in)) == null) {
            System.out.println("Missing number of rooms on floor " + floor + ".");
          } else {
            try {
              num = Integer.parseInt(line);
              rooms[i] = new Room[num];
            } catch (NumberFormatException nfe) {
              System.out.println("Invalid number of rooms on floor " + floor + ": " + line);
            }
          }

          if (rooms[i] != null) {
            for (int j = 0; j < rooms[i].length; j++) {
              room = floor * 100 + (j + 1);
              if ((line = nextNonEmptyLine(in)) == null) {
                System.out.println("Missing description for room " + room + ".");
              } else {
                tokens = line.split(",");
                if (tokens.length >= 3) {
                  if (tokens.length > 3) {
                    System.out.println("Ignoring extra tokens on room description for " + room + ":" + line);
                  }
                  try {
                    rooms[i][j] = new Room(room, tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
                  } catch (NumberFormatException nfe) {
                    // error handled below because rooms[i][j] == null
                  }
                }
                if (rooms[i][j] == null) {
                  System.out.println("Invalid room description for " + room + ": " + line);
                }
              }
            }
          }
        }
      }
    } catch (FileNotFoundException fnf) {
      System.out.println(fnf.getMessage());
    }

    if (rooms != null) {
      hotel.addHotel(new Hotel(name, rooms));
    }

  }

  /**
   * Processes a list of reservations a file.
   *
   * PARAMETERS:
   * in - A Scanner object for reading the reservation data.
   * rm - A ReservationManagerList for making reservations.
   * hotels - A HotelManager object used to get hotel data.
   */
  public static void processReservations(Scanner in, ReservationManagerList rm, HotelManager hotels) {

    String line, result;
    String[] tokens;
    int num1, num2, num3;
    boolean valid;

    String hotelName = nextNonEmptyLine(in);

    line = nextNonEmptyLine(in);
    while (line != null && !line.equals("----------Hotel--End----------")) {
      tokens = line.split(",");
      valid = false;

      if (tokens.length < 1) {
        valid = false;
      } else if (tokens[0].equals("RESERVE")) {
        try {
          if (tokens.length >= 5) {
            num1 = Integer.parseInt(tokens[2]);
            num2 = Integer.parseInt(tokens[3]);
            num3 = Integer.parseInt(tokens[4]);
            if (num1 <= 0 || num2 <= 0 || num3 <= 0) {
              valid = false;
            } else {

              Hotel hotel = hotels.getHotel(hotelName);
              result = rm.makeReservation(hotel, tokens[1], num1, num2, num3);

              if (result != null) {
                System.out.println(result);
              }
              valid = true;
            }
          }
        } catch (NumberFormatException nfe) {
        }
        if (!valid) {
          System.out.println("Invalid reservation: " + line);
        }

      } else {
        System.out.println("Unknown command on line: " + line);
      }
      line = nextNonEmptyLine(in);
    }
  }

  /**
   * Helper method to read the next non-empty line using a Scanner.
   *
   * PARAMETERS:
   * in - A Scanner object
   *
   * RETURNS:
   * A String containing the next non-empty line. Returns null if there are no
   * more non-empty lines.
   */
  private static String nextNonEmptyLine(Scanner in) {
    String line = null;

    while (in.hasNextLine() && line == null) {
      line = in.nextLine();
      if (line.trim().length() == 0) {
        line = null;
      }
    }

    return line;
  }
}
