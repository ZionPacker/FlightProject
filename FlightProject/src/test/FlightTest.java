import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FlightTest {
    private Flight flight;

    @BeforeEach
    public void setUp() {
        flight = new Flight("Test Flight", "2022-12-31", "12:00", 2);
    }

    @Test
    public void testPurchaseTicket() throws AllTicketsSoldException {
        Ticket ticket = flight.purchaseTicket();
        assertNotNull(ticket, "Ticket should not be null");
        assertEquals(1, flight.getPurchasedTickets().size(), "Purchased tickets should contain 1 ticket");
    }

    @Test
    public void testPurchaseTicketAllSold() {
        assertThrows(AllTicketsSoldException.class, () -> {
            flight.purchaseTicket();
            flight.purchaseTicket();
            flight.purchaseTicket(); // This should throw an exception
        });
    }

    @Test
    public void testReturnTicket() throws AllTicketsSoldException {
        Ticket ticket = flight.purchaseTicket();
        flight.returnTicket(ticket);
        assertEquals(0, flight.getPurchasedTickets().size(), "Purchased tickets should be empty");
    }
}