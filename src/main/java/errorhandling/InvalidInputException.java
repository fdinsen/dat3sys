package errorhandling;

/**
 *
 * @author Oliver
 */
public class InvalidInputException extends Exception {
    public InvalidInputException() {
        super("Invalid input. Please try again!");
    }
}
