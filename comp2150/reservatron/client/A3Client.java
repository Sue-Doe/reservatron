package comp2150.reservatron.client;

import java.util.Scanner;
import java.util.ArrayList;

import comp2150.reservatron.server.A3ReservationServer;
import comp2150.reservatron.server.A3Server;
import comp2150.reservatron.server.LaunchServer;
import comp2150.reservatron.server.CustomerServer;
import comp2150.reservatron.server.session.ManageSessionServer;
import comp2150.reservatron.server.session.SessionServer;
import comp2150.reservatron.server.admin.AdminServer;
import comp2150.reservatron.server.manager.ManagerServer;
import comp2150.reservatron.server.reserve.ReserveServer;
import comp2150.reservatron.server.hotel.HotelServer;

/**
 * COMP 2150 Summer 23 Assignment 3: Hotel reservations client
 */

public class A3Client {
  // private static ArrayList<Integer> sessions = new ArrayList<>();
  
  public static void main(String[] args) {
    A3ReservationServer server = new A3Server();

    Scanner in = new Scanner(System.in);
    boolean quit = false;
    int session = 0;
    String response;
    
    response = server.startup();
    if (response != null) {
      System.out.println(response);
    } else {
      while (!quit && session >= 0) {
        session = login(in, server);
        if (!quit && session >= 0) {
          response = server.loginType(session);
          assert null != response;
          if ("customer".equals(response)) {
            handleCustomerRequests(session, in, server);
          } else if ("manager".equals(response)) {
            handleManagerRequests(session, in, server);
          } else if ("administrator".equals(response)) {
            quit = handleAdministratorRequests(session, in, server);
          }
        }
      }
      if (session < 0) {
        // eof on login
        server.exit(-1);
      }
    }

    System.out.println("\nEnd of processing.");
  }
  
  public static int login(Scanner in, SessionServer server) {
    String user = null, password = null;
    int response = -1;
    
    while (true) {
      System.out.println("\nWelcome to Reserv-a-tron!");
      user = nextNonEmptyLine(in, "Please enter your user ID to login: ");
      if (user != null) {
        password = nextNonEmptyLine(in, "Welcome " + user + ", please enter your password: ");
        if (password != null) {
          response = server.login(user, password);
          if (response < 0) {
            System.out.print("Unable to login as " + user + ": ");
            System.out.println(server.loginError(response));
          } else {
            return response;
          }
        }
      }
      if (user == null || password == null) {
        System.out.println("\nUnexpected end of file.");
        return -1;
      }
    }
  }
  
  public static void handleCustomerRequests(int session, Scanner in, CustomerServer server) {
    int option = -1;
    String line = "", response, hotel;

    while (option != 0) {
      option = menuOption(in, "\nWelcome " + server.currentUserID(session) + "! Enter a number to select an option:\n" +
        "   1 - Make a reservation\n" +
        "   2 - Cancel a reservation\n" + 
        "   3 - View your current reservations\n" +
        "   0 - Log out", 0, 3);
      if (option == 1) {
        try {
          hotel = hotelSelector(session, in, server);
          if (hotel != null) {
            line = nextNonEmptyLine(in, "How many guests in the reservation? ");
            int numGuests = Integer.parseInt(line);
            line = nextNonEmptyLine(in, "Enter the starting day of the reservation (1-365): ");
            int start = Integer.parseInt(line);
            line = nextNonEmptyLine(in, "How many days will the reservation be? ");
            int duration = Integer.parseInt(line);
            response = server.makeReservation(session, server.currentUserID(session), hotel, numGuests, start, duration);
            if (response != null) {
              System.out.println(response);
            }
          }
        } catch (NumberFormatException nfe) {
          System.out.println("Invalid input: " + line);
        }
      } else if (option == 2) {
        try {
          hotel = hotelSelector(session, in, server);
          if (hotel != null) {
            line = nextNonEmptyLine(in, "Enter the starting day to cancel (1-365): ");
            response = server.cancelReservation(session, server.currentUserID(session), hotel, Integer.parseInt(line));
            if (response != null) {
              System.out.println(response);
            }
          }
        } catch (NumberFormatException nfe) {
          System.out.println("Invalid input: " + line);
        }
      } else if (option == 3) {
        System.out.println(server.printCustomer(session, server.currentUserID(session)));
      }
    }

    if ((response = server.logout(session)) != null) {
      System.out.println(response);
    }
  }

  public static void handleManagerRequests(int session, Scanner in, ManagerServer server) {
    int option = -1;
    String line = "", response, customer = null, hotel;

    while (option != 0) {
      option = menuOption(in, "\nWelcome " + server.currentUserID(session) + " (manager)! Enter a number to select an option:\n" +
        "   1 - Make a reservation for a customer\n" +
        "   2 - Cancel a reservation for a customer\n" + 
        "   3 - View your current reservations for a customer\n" +
        "   4 - View your current reservations for a day\n" +
        "   5 - View your current reservations for a room\n" +
        "   0 - Log out", 0, 5);
      
      if (option >= 1 && option <= 3) {
        customer = nextNonEmptyLine(in, "What is the customer's user ID? ");
        if (!"customer".equals(server.loginTypeID(session, customer))) {
          System.out.println("Invalid user ID " + customer);
          customer = null;
        }
      }
      
      if (option == 1 && customer != null) {
        {
          try {
            hotel = hotelSelector(session, in, server);
            if (hotel != null) {
              line = nextNonEmptyLine(in, "How many guests in the reservation? ");
              int numGuests = Integer.parseInt(line);
              line = nextNonEmptyLine(in, "Enter the starting day of the reservation (1-365): ");
              int start = Integer.parseInt(line);
              line = nextNonEmptyLine(in, "How many days will the reservation be? ");
              int duration = Integer.parseInt(line);
              response = server.makeReservation(session, customer, hotel, numGuests, start, duration);
              if (response != null) {
                System.out.println(response);
              }
            }
          } catch (NumberFormatException nfe) {
            System.out.println("Invalid input: " + line);
          }
        }
      } else if (option == 2 && customer != null) {
        try {
          hotel = hotelSelector(session, in, server);
          if (hotel != null) {
            line = nextNonEmptyLine(in, "Enter the starting day to cancel (1-365): ");
            response = server.cancelReservation(session, customer, hotel, Integer.parseInt(line));
            if (response != null) {
              System.out.println(response);
            }
          }
        } catch (NumberFormatException nfe) {
          System.out.println("Invalid input: " + line);
        }
      } else if (option == 3 && customer != null) {
        System.out.println(server.printCustomer(session, customer));
      } else if (option == 4) {
        try {
          hotel = hotelSelector(session, in, server);
          if (hotel != null) {
            line = nextNonEmptyLine(in, "Enter a day (1-365): ");
            response = server.printDay(session, hotel, Integer.parseInt(line));
            if (response != null) {
              System.out.println(response);
            }
          }
        } catch (NumberFormatException nfe) {
          System.out.println("Invalid input: " + line);
        }
      } else if (option == 5) {
        try {
          hotel = hotelSelector(session, in, server);
          if (hotel != null) {
            line = nextNonEmptyLine(in, "Enter a room: ");
            response = server.printRoom(session, hotel, Integer.parseInt(line));
            if (response != null) {
              System.out.println(response);
            }
          }
        } catch (NumberFormatException nfe) {
          System.out.println("Invalid input: " + line);
        }
      }
    }

    if ((response = server.logout(session)) != null) {
      System.out.println(response);
    }
  }

  public static boolean handleAdministratorRequests(int session, Scanner in, AdminServer server) {
    int option = -1;
    String user, name, type, password, response;

    while (option != 0 && option != 6) {
      option = menuOption(in, "\nWelcome " + server.currentUserID(session) + " (administrator)! Enter a number to select an option:\n" +
        "   1 - Create a new user\n" +
        "   2 - Change a user's password\n" + 
        "   3 - Delete a user\n" +
        "   4 - Create a new hotel\n" +
        "   5 - Add a manager to a hotel\n" +
        "   6 - Shut down the server\n" +
        "   0 - Log out", 0, 6);
      if (option == 1) {
        user = nextNonEmptyLine(in, "Enter a new user ID: ");
        response = server.loginTypeID(session, user);
        if (response != null) {
          System.out.println("User " + user + " already exists.");
        } else {
          name = nextNonEmptyLine(in, "Enter their real name: ");
          type = nextNonEmptyLine(in, "Enter their user type (administrator, customer, manager): ");
          response = server.createUser(session, user, name, type, "password");
          if (response != null) {
            System.out.println(response);
          }
        }
      } else if (option == 2) {
        user = nextNonEmptyLine(in, "Enter a user ID: ");
        response = server.loginTypeID(session, user);
        if (response == null) {
          System.out.println("User " + user + " doesn't exist.");
        } else {
          password = nextNonEmptyLine(in, "Enter their password: ");
          response = server.changePassword(session, user, password);
          if (response != null) {
            System.out.println(response);
          }
        }
      } else if (option == 3) {
        user = nextNonEmptyLine(in, "Enter a user ID: ");
        response = server.loginTypeID(session, user);
        if (response == null) {
          System.out.println("User " + user + " doesn't exist.");
        } else {
          if ("customer".equals(response)) {
            System.out.println(server.printCustomer(session, user));
            System.out.println("Warning! This will delete all the customer's reservations!");
          }
          response = nextNonEmptyLine(in, String.format("Are you certain you want to delete %s (%s) (yes or no)? ", user, server.realName(session, user)));
          if ("yes".equals(response)) {
            response = server.deleteUser(session, user);
            if (response != null) {
              System.out.println(response);
            }
          }
        }
      } else if (option == 4) {
        name = nextNonEmptyLine(in, "Enter a floor plan filename: ");
        response = server.createHotel(session, name);
        if (response != null) {
          System.out.println(response);
        }
      } else if (option == 5) {
        name = hotelSelector(session, in, server);
        if (name != null) {
          user = nextNonEmptyLine(in, "Enter the manager's user ID: ");
          response = server.makeHotelManager(session, user, name);
          if (response != null) {
            System.out.println(response);
          }
        }
      }
    }

    if (option == 6) {
      if ((response = server.exit(session)) != null) {
        System.out.println(response);
      } else {
        return true;
      }
    }

    if ((response = server.logout(session)) != null) {
      System.out.println(response);
    }
    
    return false;
  }

  /**
   * Helper method to select a hotel.
   * Print the prompt on each line of input.
   * @return a hotel name (String) or null on error
   */
  private static String hotelSelector(int session, Scanner in, HotelServer server) {
    ArrayList<String> hotels = server.getHotels(session);
    
    if (hotels.size() == 0) {
      System.out.println("No hotels available");
    } else {
      String menu = "\nChoose a hotel:\n";
      int result;
      for (int i = 0; i < hotels.size(); i++) {
        menu = menu + "  " + (i+1) + " - " + hotels.get(i) + "\n";
      }
      menu += "  0 - Cancel";
      result = menuOption(in, menu, 0, hotels.size());
      if (result == 0) {
        return null;
      } else {
        return hotels.get(result - 1);
      }
    }
    
    return null;
  }

  /**
   * Helper method to handle menu items.
   * Print the prompt on each line of input.
   * @return a number between min and max
   */
  private static int menuOption(Scanner in, String menu, int min, int max) {
    int option = Integer.MIN_VALUE;
    String line;

    do {
      System.out.println(menu);
      line = nextNonEmptyLine(in, String.format("\nChoose an option between %d and %d: ", min, max));
      try {
        option = Integer.parseInt(line);
      } catch (NumberFormatException nfe) {
        System.out.println("Invalid input: " + line);
      }
    } while (option < min || option > max);
    
    return option;
  }

  /**
   * Helper method for Scanner to skip over empty lines.
   * Print the prompt on each line of input.
   */
  private static String nextNonEmptyLine(Scanner in, String prompt) {
    String line = null;

    System.out.print(prompt);
    while (line == null && in.hasNextLine()) {
      line = in.nextLine();
      if (line.trim().length() == 0) {
        line = null;
        System.out.print(prompt);
      }
    }

    return line;
  }
}
