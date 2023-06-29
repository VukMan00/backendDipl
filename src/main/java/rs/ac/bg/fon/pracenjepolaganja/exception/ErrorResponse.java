package rs.ac.bg.fon.pracenjepolaganja.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Represent the class that is sent in JSON form to the client when exception occurs.
 * Contains status, list of messages and timeStamp when exception occurred.
 *
 * @author Vuk Manojlovic
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    /**
     * Value of HTTP status of exception. Can be NOT_FOUND,
     * BAD_REQUEST etc.
     */
    private int status;

    /**
     * Map of message that exception throw.
     */
    private Map<String,String> message;

    /**
     * Time when exception occurred in seconds.
     */
    private long timeStamp;
}
