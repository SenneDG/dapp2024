package staff;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

import hotel.BookingDetail;
import hotel.BookingManagerInterface;

public class BookingClient extends AbstractScriptedSimpleTest {

	BookingManagerInterface bm = null;

	public static void main(String[] args) throws Exception {
		BookingClient client = new BookingClient();
		client.run();
	}

	public BookingClient() {
		String serverHostname = "distrs.japaneast.cloudapp.azure.com";
//        String serverHostname = "localhost";

		try {
			Registry registry = LocateRegistry.getRegistry(serverHostname);
			this.bm = (BookingManagerInterface) registry.lookup("BookingManager");
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	@Override
	public boolean isRoomAvailable(Integer roomNumber, LocalDate date) throws RemoteException {
		Instant startTime = Instant.now();
		boolean result = bm.isRoomAvailable(roomNumber, date);
		Instant endTime = Instant.now();
		long latency = Duration.between(startTime, endTime).toMillis();
		System.out.println("isRoomAvailable latency: " + latency + " milliseconds");
		return result;
	}

	@Override
	public void addBooking(BookingDetail bookingDetail) throws RemoteException {
		Instant startTime = Instant.now();
		bm.addBooking(bookingDetail);
		Instant endTime = Instant.now();
		long latency = Duration.between(startTime, endTime).toMillis();
		System.out.println("addBooking latency: " + latency + " milliseconds");
	}

	@Override
	public Set<Integer> getAvailableRooms(LocalDate date) throws RemoteException {
		Instant startTime = Instant.now();
		Set<Integer> availableRooms = bm.getAvailableRooms(date);
		Instant endTime = Instant.now();
		long latency = Duration.between(startTime, endTime).toMillis();
		System.out.println("getAvailableRooms latency: " + latency + " milliseconds");
		return availableRooms;
	}

	@Override
	public Set<Integer> getAllRooms() throws RemoteException {
		Instant startTime = Instant.now();
		Set<Integer> allRooms = bm.getAllRooms();
		Instant endTime = Instant.now();
		long latency = Duration.between(startTime, endTime).toMillis();
		System.out.println("getAllRooms latency: " + latency + " milliseconds");
		return allRooms;
	}
}
