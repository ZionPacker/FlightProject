/**
 * Exception thrown when all tickets for a flight have been sold.
 */
public class AllTicketsSoldException extends Exception {
    public AllTicketsSoldException(String message) {
        super(message);
    }
}
