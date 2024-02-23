package hotel;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.HashSet;
import java.util.Set;

public class BookingManager extends UnicastRemoteObject implements BookingManagerInterface {
	private Set<BookingDetail> bookings;
	private Room[] rooms;

	public BookingManager() throws RemoteException {
		this.bookings = new HashSet<>();
		this.rooms = initializeRooms();
	}

	public Set<Integer> getAllRooms() throws RemoteException {
		Set<Integer> allRooms = new HashSet<>();
		for (Room room : rooms) {
			allRooms.add(room.getRoomNumber());
		}
		return allRooms;
	}

	public boolean isRoomAvailable(Integer roomNumber, LocalDate date) throws RemoteException {
		for (BookingDetail booking : bookings) {
			if (booking.getRoomNumber() == roomNumber && booking.getDate().equals(date)) {
				return false;
			}
		}
		return true;
	}

	public void addBooking(BookingDetail bookingDetail) throws RemoteException {
		bookings.add(bookingDetail);
	}

	public Set<Integer> getAvailableRooms(LocalDate date) throws RemoteException {
		Set<Integer> bookedRooms = new HashSet<>();
		for (BookingDetail booking : bookings) {
			if (booking.getDate().equals(date)) {
				bookedRooms.add(booking.getRoomNumber());
			}
		}

		Set<Integer> availableRooms = new HashSet<>();
		for (Room room : rooms) {
			if (!bookedRooms.contains(room.getRoomNumber())) {
				availableRooms.add(room.getRoomNumber());
			}
		}
		return availableRooms;
	}


	private Room[] initializeRooms() throws RemoteException {
		Room[] rooms = new Room[4];
		rooms[0] = new Room(101);
		rooms[1] = new Room(102);
		rooms[2] = new Room(201);
		rooms[3] = new Room(203);
		return rooms;
	}
}
