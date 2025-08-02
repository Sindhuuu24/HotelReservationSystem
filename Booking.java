public class Booking {
    private String customerName;
    private int roomNumber;
    private String category;
    private double amountPaid;

    public Booking(String customerName, int roomNumber, String category, double amountPaid) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.category = category;
        this.amountPaid = amountPaid;
    }

    @Override
    public String toString() {
        return customerName + "," + roomNumber + "," + category + "," + amountPaid;
    }

    public static Booking fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid booking line: " + line);
        }
        return new Booking(parts[0], Integer.parseInt(parts[1]), parts[2], Double.parseDouble(parts[3]));
    }

    public int getRoomNumber() {
        return roomNumber;
    }
}
