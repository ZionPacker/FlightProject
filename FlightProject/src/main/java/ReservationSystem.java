import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 * A class that represents a flight reservation system. This is the main, injectable section of the program. It can be used to start the reservation system with a set of flights and I/O streams.
 */
public class ReservationSystem {
    /**
     * The flights that are available for reservation.
     */
    Flight[] flights;
    /**
     * The output stream to use for printing to the console (or buffer for testing).
     */
    PrintStream standardOutput;
    /**
     * The input stream to use for reading from the console (or buffer for testing).
     */
    InputStream standardInput;

    private final String SEPERATOR = "-------------------------------------------\n";

    enum Action {
        RESERVE_TICKET,
        RETURN_TICKET,
        VIEW_FLIGHT_INFO,
        EXIT
    }

    /**
     * Constructs a new reservation system with the given flights and I/O streams.
     * @param flights The flights that will be available for reservation.
     * @param out The output stream to use for printing to the console (or buffer for testing).
     * @param in The input stream to use for reading from the console (or buffer for testing).
     */
    public ReservationSystem(Flight[] flights, PrintStream out, InputStream in) {
        this.flights = flights;
        standardOutput = out;
        standardInput = in;
    }

    /**
     * Starts the reservation system. This method will block until the user exits the reservation system.
     */
    public void start() {
        standardOutput.println("Welcome to the Flight Reservation System!");
        Scanner scanner = new Scanner(standardInput);
        try {
            while (true) {
                standardOutput.println(SEPERATOR);
                Action action = promptAction(scanner);
                Flight flight = promptFlight(flights, scanner);
                performAction(action, flight, scanner);
                standardOutput.println();
            }
        } catch (ExitException e) {
            standardOutput.println(e.getMessage());
        }
        
        standardOutput.println("Thank you for using the Flight Reservation System!");
    }

    /**
     * Prompts the user for an action to perform.
     * @param scanner The scanner to use for reading user input.
     * @return The action that the user selected.
     * @throws ExitException If the user selects the exit option.
     */
    private Action promptAction(Scanner scanner) throws ExitException {
        while (true) {
            standardOutput.println(sayMainMenu());
            standardOutput.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());
            standardOutput.println();
            if (choice == 0) throw new ExitException();
            if (choice == 1) return Action.RESERVE_TICKET;
            else if (choice == 2) return Action.RETURN_TICKET; 
            else if (choice == 3) return Action.VIEW_FLIGHT_INFO;

            standardOutput.println("Invalid input, please try again.");
        }
    }

    /**
     * Prompts the user for a flight to perform an action on.
     * @param flights The flights to choose from.
     * @param scanner The scanner to use for reading user input.
     * @return The flight that the user selected.
     * @throws ExitException If the user selects the exit option.
     */
    private Flight promptFlight(Flight[] flights, Scanner scanner) throws ExitException {
        while (true) {
            standardOutput.println("Please select a flight:");
            for (int i = 0; i < flights.length; i++) {
                standardOutput.println((i + 1) + ". " + flights[i].getFlightHeader());
            }
            standardOutput.println("0. Exit");
            standardOutput.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 0) throw new ExitException();
            if (choice > 0 && choice <= flights.length) {
                return flights[choice - 1];
            }
            standardOutput.println("Invalid input, please try again.");
        }
    }

    /**
     * Performs the given action on the given flight.
     * @param action The action to perform.
     * @param flight The flight to perform the action on.
     * @param scanner The scanner to use for reading user input.
     */
    private void performAction(Action action, Flight flight, Scanner scanner) {
        List<Ticket> purchasedTickets = flight.getPurchasedTickets();
        if (action == Action.RESERVE_TICKET) {
            try {
                Ticket ticket = flight.purchaseTicket();
                standardOutput.println("Ticket purchased! Here's your ticket information: ");
                standardOutput.println(ticket.toString());
                standardOutput.println();
            } catch (AllTicketsSoldException e) {
                standardOutput.println(e.getMessage());
            }
        } else if (action == Action.RETURN_TICKET) {
            if (purchasedTickets.isEmpty()) {
                standardOutput.println("There are no tickets to return.");
                return;
            }
            printPurchasedTickets(flight, purchasedTickets);
            standardOutput.print("Select a ticket to return: ");
            int ticketNum = Integer.parseInt(scanner.nextLine());
            Ticket ticket = purchasedTickets.get(ticketNum - 1);
            flight.returnTicket(ticket);
            standardOutput.println("Ticket returned!");
        } else if (action == Action.VIEW_FLIGHT_INFO) {
            standardOutput.println(flight.toString());
            if (purchasedTickets.isEmpty()) {
                standardOutput.println("There are no purchased tickets for this flight.");
                return;
            }
            printPurchasedTickets(flight, purchasedTickets);
        }
    } 

    /**
     * Prints the purchased tickets for the given flight.
     * @param flight The flight to print purchased tickets for.
     * @param purchasedTickets The purchased tickets to print.
     */
    private void printPurchasedTickets(Flight flight, List<Ticket> purchasedTickets) {
        standardOutput.println("Purchased Tickets:");
        for (int i = 0; i < purchasedTickets.size(); i++) {
            standardOutput.println((i + 1) + ". " + purchasedTickets.get(i).getTicketHeader());
        }
    }

    /**
     * Prints the main menu for the reservation system.
     * @return The main menu as a string.
     */
    static String sayMainMenu() {
        return new StringBuilder().append("Please select an option:\n")
                                  .append("1. Reserve a ticket\n")
                                  .append("2. Return a ticket\n")
                                  .append("3. View flight info\n")
                                  .append("0. Exit").toString();
    }

}
