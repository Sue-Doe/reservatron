package comp2150.reservatron.client;

import comp2150.reservatron.server.A3ReservationServer;
import comp2150.reservatron.server.A3Server;

public class test {
    public static void main(String[] args) {

        A3ReservationServer bruh = new A3Server();
        bruh.startup();
        for (int i = 0; i < bruh.getHotels(i).size(); i++) {
        }    
    }
}
