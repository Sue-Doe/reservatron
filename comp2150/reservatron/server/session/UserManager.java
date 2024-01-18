package comp2150.reservatron.server.session;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import comp2150.reservatron.server.A1;

/**
 * A3Server
 *
 * @author Max Waldner, 7889322
 *
 *         REMARKS: Manager Class for all users
 */
public class UserManager {

    private HashMap<String, User> userMap; // Hashmap of all users
    private User currentUser; // Current user logged in

    /**
     * Constructor for UserManager. Initializes the user map and sets the current
     * user to null.
     */
    public UserManager() {
        userMap = new HashMap<String, User>();
        currentUser = null;
    }

    /**
     * Reads a user file and loads users into the UserManager.
     *
     * PARAMETERS:
     * filename - The name of the file user data.
     *
     * RETURNS:
     * A String indicating the result of the user file reading process. Returns null
     * on success and error message on failure.
     */
    public String readUserFile(String filename) {
        String message = "Error: Cannot read user file";
        try {

            Scanner scanner = new Scanner(new FileReader(filename));
            message = A1.readUsers(scanner, this);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return message;
    }

    /**
     * Returns the number of users in the UserManager.
     *
     * RETURNS:
     * An integer representing the number of users.
     */
    public int size() {
        return userMap.size();
    }

    /**
     * Adds a user to the UserManager.
     *
     * PARAMETERS:
     * user - The User object to be added.
     */

    public void addUser(User user) {
        if (user != null) {
            userMap.put(user.getID(), user);
        }
    }

    /**
     * Attempts to log in a user with the given username and password.
     *
     * PARAMETERS:
     * user - The username of the user to log in.
     * password - The password for the user.
     *
     * RETURNS:
     * An integer result of the login attempt (success or various error codes).
     */
    public int login(String user, String password) {

        int result = Integer.MIN_VALUE;

        if (currentUser == null) {
            result = -1;
            if (userMap.containsKey(user)) {
                User u = userMap.get(user);
                if (u.matchesPassword(password)) {
                    currentUser = u;
                    result = 0;
                } else {
                    result = -2;
                }
            }
        } else {
            result = -3;
        }
        return result;
    }

    /**
     * Logs out the currently logged-in user.
     *
     * PARAMETERS:
     * session - The session ID of the user logging out.
     *
     * RETURNS:
     * A String indicating the result of the logout process, null on success error
     * message on failure.
     */
    public String logout(int session) {

        String result;
        if (currentUser == null) {
            result = "No user logged in";
        } else {
            currentUser = null;
            result = null;
        }
        return result;
    }

    /**
     * Returns the login type of the current user.
     *
     * RETURNS:
     * A String representing the login type of the current user, null if no user is
     * logged in.
     */
    public String loginType() {

        String message = null;
        if (currentUser != null) {
            message = currentUser.userType();
        }
        return message;
    }

    /**
     * Returns the ID of the current user.
     *
     * RETURNS:
     * A String representing the ID of the current user, null if no user is logged
     * in.
     */
    public String currentUserID() {

        String message = null;
        if (currentUser != null) {
            message = currentUser.getID();
        }
        return message;
    }

    /**
     * Returns the login type ID of a given user.
     *
     * PARAMETERS:
     * user - The username whose login type ID is to get.
     *
     * RETURNS:
     * A String representing the login type ID of the given user, null on failure.
     */
    public String loginTypeID(String user) {

        String message = null;
        if (currentUser != null) {
            if (currentUser.canViewUsers() == null) {
                User u = userMap.get(user);
                if (u != null) {
                    message = u.userType();
                }
            }
        }
        return message;
    }

    /**
     * Retrieves the real name of a gievn user.
     *
     * PARAMETERS:
     * user - The username whose real name is to be gotten.
     *
     * RETURNS:
     * A String of the real name of the user, null on failure.
     */
    public String realName(String user) {

        String message = null;
        if (currentUser != null) {
            if (currentUser.canViewUsers() == null) {
                User u = userMap.get(user);
                if (u != null) {
                    message = u.getName();
                }
            }
        }
        return message;
    }

    /**
     * Checks if the current user can create a hotel.
     *
     * RETURNS:
     * A String indicating whether the current user can create a hotel, or an error
     * message
     */
    public String canCreateHotel() {

        String message = "Error: No User Logged In";
        if (currentUser != null) {
            message = currentUser.canCreateHotels();
        }
        return message;
    }

    /**
     * Attempts to create a new user with the specified details.
     *
     * PARAMETERS:
     * user - The username for the new user.
     * name - The real name of the new user.
     * type - The type of the new user
     * password - The password for the new user.
     *
     * RETURNS:
     * A String indicating the result of the user creation process, null on success
     * or an error message on failure.
     */
    public String createUser(String user, String name, String type, String password) {

        String result = "Error: No User Logged In";
        if (currentUser != null) {
            result = currentUser.canManageUsers();
            if (result == null) {
                if (userMap.containsKey(user)) {
                    result = "Error: User already exists";
                } else {
                    User u = null;
                    if (type.equals("administrator")) {
                        u = new Admin(user, name, password);
                    } else if (type.equals("manager")) {
                        u = new Manager(user, name, password);
                    } else if (type.equals("customer")) {
                        u = new Customer(user, name, password);
                    } else {
                        result = "Error: Unknown user type";
                    }
                    if (u != null) {
                        userMap.put(user, u);
                        result = null;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Attempts to give manager to a specified user.
     *
     * PARAMETERS:
     * user - The username of the user to be hotel manager.
     *
     * RETURNS:
     * A String of the result of the assignment process, null on success or an error
     * message on failure.
     */
    public String makeHotelManager(String user) {
        String message = "Error: No User Logged In";
        if (currentUser != null) {
            if (currentUser.canManageUsers() == null) {
                User u = userMap.get(user);
                if (u != null) {
                    if (u.canManageHotel() == null) {
                        message = null;
                    } else {
                        message = "Error: User is not a manager";
                    }
                } else {
                    message = "Error: User does not exist";
                }
            }
        }
        return message;
    }

    /**
     * Changes the password for a given user.
     *
     * PARAMETERS:
     * user - The username of the user whose password is to be changed.
     * password - The new password for the user.
     *
     * RETURNS:
     * A String of the result of the password, null on success or an error message
     * on failure.
     */
    public String changePassword(String user, String password) {

        String result = "Error: No User Logged In";
        if (currentUser != null) {
            result = currentUser.canManageUsers();
            if (result == null) {
                User u = userMap.get(user);
                if (u != null) {
                    u.changePassword(password);
                    result = null;
                } else {
                    result = "Error: User does not exist";
                }
            }
        }
        return result;
    }

    /**
     * Deletes a specified user from the UserManager.
     *
     * PARAMETERS:
     * user - The username of the user to be deleted.
     *
     * RETURNS:
     * A String indicating the result of the user deletion process,
     * null on success or an error message on failure.
     */
    public String deleteUser(String user) {

        String result = "Error: No User Logged In";
        if (currentUser != null) {
            result = currentUser.canManageUsers();
            if (result == null) {
                User u = userMap.get(user);
                if (u != null) {
                    if (userMap.remove(user) != null) {
                        result = null;
                    } else {
                        result = "Error: User does not exist";
                    }

                }
            }
        }
        return result;
    }

    /**
     * Checks if the current user can manage a hotel.
     *
     * RETURNS:
     * A String indicating whether the current user can manage a hotel, or an error
     * message if not logged in or not permitted.
     */
    public String canManageHotel() {

        String message = "Error: No User Logged In";
        if (currentUser != null) {
            message = currentUser.canManageHotel();
        }
        return message;
    }

    /**
     * Writes all the users to a file on exit.
     *
     * PARAMETERS:
     * userFile - The name of the file where user data will be saved.
     */
    public void exit(String userFile) {

        try {
            FileWriter writer = new FileWriter(userFile);
            for (String name : userMap.keySet()) {
                writer.write(userMap.get(name).toOutputString() + "\n");
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * gets the ID of the current manager.
     *
     * RETURNS:
     * A String of the ID of the current manager, null if the current user is not a
     * manager or not logged in.
     */
    public String currentManager() {

        String message = null;
        if (currentUser != null) {
            if (currentUser.canManageHotel() == null) {
                message = currentUser.getID();
            }
        }
        return message;
    }

    /**
     * Checks if the current user or a specified user can make reservations.
     *
     * PARAMETERS:
     * user - The username to check for.
     *
     * RETURNS:
     * A String of whether the user can make reservations, or an error message null
     * on success.
     */
    public String canMakeReservations(String user) {
        String message = "Error: user not logged in";

        if (currentUser != null) {
            message = "Error: User cannot make Reservation for " + user;
            if (currentUser.getID().equals(user) || currentUser.canViewUsers() == null) {
                message = null;
            }
        }
        return message;
    }

    /**
     * Helper method to provide a string of a user.
     *
     * PARAMETERS:
     * user - The username for the string representation.
     *
     * RETURNS:
     * A String of the user, or an error message.
     */
    public String stringHelper(String user) {
        String message = "Error User not logged in";
        if (currentUser != null) {
            message = "Error: User does not exist";
            if (userMap.containsKey(user)) {
                if (currentUser.getName().equals(userMap.get(user).getName()) || currentUser.canViewUsers() == null) {
                    message = userMap.get(user).getName();
                }

            }
        }
        return message;
    }

}
