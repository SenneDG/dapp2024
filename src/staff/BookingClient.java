package staff;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.util.Set;

import hotel.BookingDetail;
import hotel.BookingManager;
import hotel.BookingManagerInterface;

public class BookingClient extends AbstractScriptedSimpleTest {

	BookingManagerInterface bm = null;

	public static void main(String[] args) throws Exception {
		BookingClient client = new BookingClient();
		client.run();
	}

	public BookingClient() {
		String serverHostname = "distrs.japaneast.cloudapp.azure.com";
//		String serverHostname = "localhost";

		try {
			Registry registry = LocateRegistry.getRegistry(serverHostname);
			this.bm = (BookingManagerInterface) registry.lookup("BookingManager");
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	@Override
	public boolean isRoomAvailable(Integer roomNumber, LocalDate date) throws RemoteException {
		return bm.isRoomAvailable(roomNumber, date);
	}

	@Override
	public void addBooking(BookingDetail bookingDetail) throws RemoteException {
		bm.addBooking(bookingDetail);
	}

	@Override
	public Set<Integer> getAvailableRooms(LocalDate date) throws RemoteException {
		return bm.getAvailableRooms(date);
	}

	@Override
	public Set<Integer> getAllRooms() throws RemoteException {
		return bm.getAllRooms();
	}
}
