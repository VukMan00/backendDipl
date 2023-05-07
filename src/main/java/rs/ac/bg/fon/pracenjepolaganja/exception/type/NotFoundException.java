package rs.ac.bg.fon.pracenjepolaganja.exception.type;

/**
 * Represent one of the exception types.
 * Occurs when entity is not found in database.
 *
 * @author Vuk Manojlovic
 */
public class NotFoundException extends Exception{

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
