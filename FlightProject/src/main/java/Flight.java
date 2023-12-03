import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class Flight {
    final String name;
    final String date;
    final String time;
    private final LinkedList<Ticket> availableTickets;
    private final ArrayList<Ticket> purchasedTickets;

    /**
     * Creates a new flight with the given name, date, time, and number of seats.
     * @param flightName The name of the flight.
     * @param date The date of the flight.
     * @param time The time of the flight.
     * @param numSeats The number of seats available for this flight.
     */

    public Flight(String flightName, String date, String time, int numSeats) {
        this.name = flightName;
        this.date = date;
        this.time = time;
        availableTickets = new LinkedList<Ticket>();
        purchasedTickets = new ArrayList<Ticket>();
        
        for (int i = 0; i < numSeats; i++) {
            availableTickets.add(new Ticket(this, i + 1));
        }
    }

    /**
     * Purchases a ticket for this flight.
     * @return A ticket for this flight.
     * @throws AllTicketsSoldException If there are no more tickets available for this flight.
     */
    public Ticket purchaseTicket() throws AllTicketsSoldException {
        if (availableTickets.isEmpty()) {
            throw new AllTicketsSoldException("All tickets sold for flight departing at " + this.time + " on " + this.date);
        }
        Ticket t = availableTickets.pop();
        purchasedTickets.add(t);
        return t;
    }

    /**
     * Returns a ticket for this flight.
     * @param ticket The ticket to return.
     */
    public void returnTicket(Ticket ticket) {
        purchasedTickets.remove(ticket);
        availableTickets.addFirst(ticket);
    } 

    public List<Ticket> getPurchasedTickets() {
        return Collections.unmodifiableList(purchasedTickets);
    }

    @Override
    public String toString() {
        return name + " Information:\n" +
               "Date: " + date + "\n" +
               "Time: " + time + "\n" +
               "Available Seats: " + availableTickets.size();
    }

    /**
     * Returns a string that can be used as a header for the flight (for selection).
     * @return The flight header formatted as `name at date time`.
     */
    public String getFlightHeader() {
        return name + " @ " + date + " " + time;
    }
}