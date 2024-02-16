package staff;

import java.time.LocalDate;
import java.util.Set;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import hotel.BookingDetail;
import hotel.BookingManager;

public class BookingClient extends AbstractScriptedSimpleTest {

	private BookingManager bm = null;

	public static void main(String[] args) throws Exception {
		BookingClient client = new BookingClient();
		client.run();
	}

	/***************
	 * CONSTRUCTOR *
	 ***************/
	public BookingClient() {
		try {
			//Look up the registered remote instance
			Registry registry = LocateRegistry.getRegistry("localhost", 0);
			Room room = (Room) registry.lookup("Room");
			bm = new BookingManager();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	@Override
	public boolean isRoomAvailable(Integer roomNumber, LocalDate date) {
		//Implement this method
		return bm.isRoomAvailable(roomNumber, date);
	}

	@Override
	public void addBooking(BookingDetail bookingDetail) throws Exception {
		//Implement this method
		bm.addBooking();
	}

	@Override
	public Set<Integer> getAvailableRooms(LocalDate date) {
		//Implement this method
		return bm.getAvailableRooms();
	}

	@Override
	public Set<Integer> getAllRooms() {
		return bm.getAllRooms();
	}
}
