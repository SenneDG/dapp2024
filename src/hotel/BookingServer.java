package hotel;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BookingServer {
    public static void main(String[] args) {
        try {
            // Create an instance of the remote object
            BookingManager bookingManager = new BookingManager();
            Registry registry = LocateRegistry.createRegistry(1024);
            registry.rebind("BookingManager", bookingManager);

            System.out.println("BookingManager bound and ready for client requests.");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
