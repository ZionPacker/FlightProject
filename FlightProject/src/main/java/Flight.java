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

    public Ticket purchaseTicket() throws AllTicketsSoldException {
        if (availableTickets.isEmpty()) {
            throw new AllTicketsSoldException("All tickets sold for flight departing at " + this.time + " on " + this.date);
        }
        Ticket t = availableTickets.pop();
        purchasedTickets.add(t);
        return t;
    }

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

    public String getFlightHeader() {
        return name + " @ " + date + " " + time;
    }
}