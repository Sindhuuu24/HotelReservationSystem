import java.io.*;
import java.util.*;

public class Hotel {
    private List<Room> rooms = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    private final String roomFile = "rooms.txt";
    private final String bookingFile = "bookings.txt";

    public Hotel() {
        loadRooms();
        loadBookings();
    }

    private void loadRooms() {
        try (BufferedReader br = new BufferedReader(new FileReader(roomFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                rooms.add(Room.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Room file not found, creating default rooms.");
            createDefaultRooms();
        }
    }

    private void createDefaultRooms() {
        rooms.clear();
        for (int i = 1; i <= 3; i++) rooms.add(new Room(i, "Standard", 1000));
        for (int i = 4; i <= 6; i++) rooms.add(new Room(i, "Deluxe", 2000));
        for (int i = 7; i <= 9; i++) rooms.add(new Room(i, "Suite", 3000));
        saveRooms();
    }

    private void saveRooms() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(roomFile))) {
            for (Room r : rooms) {
                bw.write(r.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBookings() {
        try (BufferedReader br = new BufferedReader(new FileReader(bookingFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                try {
                    bookings.add(Booking.fromString(line));
                } catch (Exception e) {
                    System.out.println("Skipping invalid booking line: " + line);
                }
            }
        } catch (IOException e) {
            // File might not exist yet
        }
    }

    private void saveBookings() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(bookingFile))) {
            for (Booking b : bookings) {
                bw.write(b.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAvailableRooms(String category) {
        boolean found = false;
        for (Room r : rooms) {
            if (r.isAvailable() && r.getCategory().equalsIgnoreCase(category)) {
                System.out.println("Room " + r.getRoomNumber() + " - ₹" + r.getPrice());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No rooms available in this category.");
        }
    }

    public boolean bookRoom(String customer, String category) {
        for (Room r : rooms) {
            if (r.isAvailable() && r.getCategory().equalsIgnoreCase(category)) {
                r.setAvailable(false);
                bookings.add(new Booking(customer, r.getRoomNumber(), r.getCategory(), r.getPrice()));
                saveRooms();
                saveBookings();
                System.out.println("Payment of ₹" + r.getPrice() + " successful.");
                System.out.println("Room booked successfully!");
                return true;
            }
        }
        System.out.println("No rooms available in this category.");
        return false;
    }

    public void cancelBooking(String customer) {
        Iterator<Booking> it = bookings.iterator();
        boolean found = false;
        while (it.hasNext()) {
            Booking b = it.next();
            if (b.toString().startsWith(customer + ",")) {
                it.remove();
                for (Room r : rooms) {
                    if (r.getRoomNumber() == b.getRoomNumber()) {
                        r.setAvailable(true);
                        break;
                    }
                }
                found = true;
                break;
            }
        }
        if (found) {
            saveRooms();
            saveBookings();
            System.out.println("Booking cancelled successfully.");
        } else {
            System.out.println("No booking found under this name.");
        }
    }

    public void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }
        for (Booking b : bookings) {
            System.out.println(b);
        }
    }

    public void resetAllData() {
        rooms.clear();
        bookings.clear();
        createDefaultRooms();
        saveBookings();
        System.out.println("Hotel data has been reset to default.");
    }
}
