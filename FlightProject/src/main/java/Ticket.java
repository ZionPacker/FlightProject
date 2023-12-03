public class Ticket {
    private Flight flight;
    private int seatNumber;

    /**
     * Creates a new ticket for the given flight at the given seat number.
     * @param flight The flight this ticket is for.
     * @param seatNumber The seat number for this ticket.
     */
    public Ticket(Flight flight, int seatNumber) {
        this.flight = flight;
        this.seatNumber = seatNumber;
    }

    /**
     * Returns a string that can be used as a header for tickets (for selection).
     * @return The ticket header formatted as `Ticket at seat [seat] for [flight header]`.
     */
    public String getTicketHeader() {
        return "Ticket at seat " + seatNumber + " for " + flight.getFlightHeader();
    }

    @Override
    public String toString() {
        return flight.toString() + "\nSeat Number: " + seatNumber;
    }
}
