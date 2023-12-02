public class Ticket {
    private Flight flight;
    private int seatNumber;

    public Ticket(Flight flight, int seatNumber) {
        this.flight = flight;
        this.seatNumber = seatNumber;
    }

    public String getTicketHeader() {
        return "Ticket at seat " + seatNumber + " for " + flight.getFlightHeader();
    }

    @Override
    public String toString() {
        return flight.toString() + "\nSeat Number: " + seatNumber;
    }
}
