package comp2150.reservatron.server.reserve;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import comp2150.reservatron.server.A1;
import comp2150.reservatron.server.hotel.HotelManager;

public class ReservationManagerList {

    private HashMap<Hotel, ReservationManager> reservationMap;

    public ReservationManagerList() {
        reservationMap = new HashMap<Hotel, ReservationManager>();
    }

    public void readReservations(String filename, HotelManager hotels) {

        try {
            Scanner scanner = new Scanner(new FileReader(filename));
            A1.readReservations(scanner, this, hotels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String makeReservation(Hotel hotelName, String name, int numGuests, int start, int duration) {
        String message = "Error: Cannot Make Reservation";
        if (reservationMap.containsKey(hotelName)) {
            message = reservationMap.get(hotelName).reserve(name, numGuests, start, duration);
        } else {
            ReservationManager rm = new ReservationManager(hotelName);
            reservationMap.put(hotelName, rm);
            message = rm.reserve(name, numGuests, start, duration);
        }
        return message;
    }


    public void deleteUser(String user) {
        for (Hotel hotel: reservationMap.keySet()) {
            reservationMap.get(hotel).deleteUser(user);
        }
    }

    public String cancelReservation(Hotel hotelName, String name, int start) {
        String message = "Error: Cannot Cancel Reservation";
        if (reservationMap.containsKey(hotelName)) {
            message = reservationMap.get(hotelName).cancel(name, start);
        }
        return message;
    }


    public String printCustomer(String user) {

        String message = "";
        String points = "";
        updateCustomerPoints(user);

        for (Hotel hotel: reservationMap.keySet()) {
            if (!reservationMap.get(hotel).printCustomerString(user).equals("")) {
                message += hotel.getHotelName() + " ";
                message += reservationMap.get(hotel).printCustomerString(user) + "\n";   
            }
            points = reservationMap.get(hotel).printRewardString(user) + "\n";
        }

        if (message.equals("")) {
            message = " No Reservations Found";
        }


        return message + points;
    }

    public String printDay(Hotel hotel, int day) {
        String message = null;
        if (hotel != null) {
            if (reservationMap.containsKey(hotel)) {
                message = reservationMap.get(hotel).printDayString(day);
            }
        }
        
        return message;
    }

    public String printRoom(Hotel hotel, int room) {
        String message = null;
        if (hotel != null) {
            if (reservationMap.containsKey(hotel)) {
                message = reservationMap.get(hotel).printRoomString(room)
                ;
            }
        }
        return message;
    }






    private void updateCustomerPoints(String user) {

        int points = 0;
        for (Hotel hotel: reservationMap.keySet()) {
            points += reservationMap.get(hotel).getCustomerPoints(user);
        }

        for (Hotel hotel: reservationMap.keySet()) {
            reservationMap.get(hotel).setCustomerPoints(user, points);
        }

    }





    public void exit(String reservationFile) {

        try {
            FileWriter writerReservations = new FileWriter(reservationFile);
            for (Hotel hotel : reservationMap.keySet()) {
                writerReservations.write("----------Hotel--Start----------" + "\n" + hotel.getHotelName() + "\n");
                writerReservations.write(reservationMap.get(hotel).toOutputString());
                writerReservations.write("----------Hotel--End----------" + "\n\n");
            }
            writerReservations.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
