import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationSystemTest {
    private ReservationSystem rs;
    private ByteArrayOutputStream stdOut;
    private Flight flight;

    @BeforeEach
    // This method is run before each test
    public void setUp() {
        flight = new Flight("Test Flight", "2022-12-31", "12:00", 2);
        Flight[] flights = new Flight[] { flight };
        stdOut = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(stdOut);
        rs = new ReservationSystem(flights, printStream, null);
    }

    @Test
    public void testStartExit() {
        String input = "0"; // Simulate user input: Reserve a ticket for the first flight
        rs.standardInput = new ByteArrayInputStream(input.getBytes());
        rs.start();
        String output = stdOut.toString();
        // Print out what was printed to Console for testing only.
        System.out.println(output);
        assertTrue(output.contains("Welcome to the Flight Reservation System!"), "Output should contain welcome message");
        assertTrue(output.contains(ReservationSystem.sayMainMenu()), "Output should contain menu menu options");
        assertTrue(output.contains("Thank you for using the Flight Reservation System!"), "Output should contain thank you message");
    }

    @Test
    public void testStartReserveOne() {
        String input = "1\n1\n0"; // Simulate user input: Reserve a ticket for the first flight
        rs.standardInput = new ByteArrayInputStream(input.getBytes());
        rs.start();
        String output = stdOut.toString();
        // Print out what was printed to Console for testing only.
        System.out.println(output);
        // Test that the options displayed correctly.
        assertTrue(output.contains("1. " + flight.getFlightHeader()));

        // Test that we showed we purchased a ticket and displayed it.
        assertEquals(1, flight.getPurchasedTickets().size());
        String ticketInfo = flight.getPurchasedTickets().get(0).toString();
        int i = output.indexOf(ticketInfo);
        assertNotEquals(-1, i);

        // Then test that we went back to the main menu again.
        assertNotEquals(-1, output.indexOf("Please select an option:", i));
    }

    @Test
    public void testStartReserveNotAvailable() {
        flight = new Flight("Test Flight", "2022-12-31", "12:00", 0);
        rs.flights[0] = flight;
        String input = "1\n1\n0"; // Simulate user input: Reserve a ticket for the first flight
        rs.standardInput = new ByteArrayInputStream(input.getBytes());
        rs.start();
        String output = stdOut.toString();
        // Print out what was printed to Console for testing only.
        System.out.println(output);
        String exceptionFormat = "All tickets sold for flight departing at %s on %s";
        String exceptionMessage = String.format(exceptionFormat, flight.time, flight.date);

        // Test that the options displayed correctly.
        assertTrue(output.contains("1. " + flight.getFlightHeader()));

        // Test that we failed to purchase a ticket and we showed it on screen.
        assertEquals(0, flight.getPurchasedTickets().size());
        int i = output.indexOf(exceptionMessage);
        assertNotEquals(-1, i, "Output should contain exception message.");

        // Then test that we went back to the main menu again.
        assertNotEquals(-1, output.indexOf("Please select an option:", i));
    }

    @Test
    public void testStartReturnOne() throws AllTicketsSoldException {
        Ticket ticket = flight.purchaseTicket();
        String input = "2\n1\n1\n0"; // Simulate user input: Reserve a ticket for the first flight
        rs.standardInput = new ByteArrayInputStream(input.getBytes());
        rs.start();
        String output = stdOut.toString();
        // Print out what was printed to Console for testing only.
        System.out.println(output);
        // Test that the options displayed correctly.
        assertTrue(output.contains("1. " + flight.getFlightHeader()));

        // Test that the return ticket options displayed correctly.
        assertTrue(output.contains("1. " + ticket.getTicketHeader()));

        // Test that we removed the ticket from purchased tickets list.
        assertEquals(0, flight.getPurchasedTickets().size());

        // Then test that we went back to the main menu again.
        int i = output.indexOf("Ticket returned!");
        assertNotEquals(-1, i);
        assertNotEquals(-1, output.indexOf("Please select an option:", i));
    }

    @Test
    public void testStartReturnNotAvailable() throws AllTicketsSoldException {
        String input = "2\n1\n0"; // Simulate user input: Reserve a ticket for the first flight
        rs.standardInput = new ByteArrayInputStream(input.getBytes());
        rs.start();
        String output = stdOut.toString();
        // Print out what was printed to Console for testing only.
        System.out.println(output);
        // Test that the options displayed correctly.
        assertTrue(output.contains("1. " + flight.getFlightHeader()));

        // Test that we tell user there are no tickets to return.
        int i = output.indexOf("There are no tickets to return.");
        assertNotEquals(-1, i);

        // Then test that we went back to the main menu again.
        assertNotEquals(-1, output.indexOf("Please select an option:", i));
    }

    @Test
    public void testStartViewFlightInfo() throws AllTicketsSoldException {
        Ticket[] purchasedTickets = new Ticket[] { flight.purchaseTicket(), flight.purchaseTicket() };
        String input = "3\n1\n0"; // Simulate user input: Reserve a ticket for the first flight
        rs.standardInput = new ByteArrayInputStream(input.getBytes());
        rs.start();
        String output = stdOut.toString();
        // Print out what was printed to Console for testing only.
        System.out.println(output);
        // Test that the options displayed correctly.
        assertTrue(output.contains("1. " + flight.getFlightHeader()));

        // Test that the view flight info is displaying the purchased tickets correctly.
        int idx = 0;
        for (int i = 0; i < purchasedTickets.length; i++) {
            String msg = String.format("%d. %s", i + 1, purchasedTickets[i].getTicketHeader());
            idx = output.indexOf(msg, idx);
            assertNotEquals(-1, idx);
        }
        assertTrue(output.contains(flight.toString()));

        // Then test that we went back to the main menu again.
        assertNotEquals(-1, idx);
        assertNotEquals(-1, output.indexOf("Please select an option:", idx));
    }

    @Test
    public void testStartViewFlightInfoNoPurchasedTickets() throws AllTicketsSoldException {
        String input = "3\n1\n0"; // Simulate user input: Reserve a ticket for the first flight
        rs.standardInput = new ByteArrayInputStream(input.getBytes());
        rs.start();
        String output = stdOut.toString();
        // Print out what was printed to Console for testing only.
        System.out.println(output);
        // Test that the options displayed correctly.
        assertTrue(output.contains("1. " + flight.getFlightHeader()));

        // Test that the view flight info is displaying the purchased tickets correctly.
        assertTrue(output.contains(flight.toString()));

        // Then test that we went back to the main menu again.
        int i = output.indexOf("There are no purchased tickets for this flight.");
        assertNotEquals(-1, i);
        assertNotEquals(-1, output.indexOf("Please select an option:", i));
    }
}