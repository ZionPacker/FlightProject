/**
 * An exception that is thrown when the user wants to exit the program.
 */
public class ExitException extends Exception {
    public ExitException() {
        super("Exiting...");
    }
}
