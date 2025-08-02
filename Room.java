public class Room {
    private int roomNumber;
    private String category;
    private boolean isAvailable;
    private double price;

    public Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.isAvailable = true;
    }

    public int getRoomNumber() { return roomNumber; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return roomNumber + "," + category + "," + price + "," + isAvailable;
    }

    public static Room fromString(String data) {
        String[] parts = data.split(",");
        Room r = new Room(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]));
        r.setAvailable(Boolean.parseBoolean(parts[3]));
        return r;
    }
}
