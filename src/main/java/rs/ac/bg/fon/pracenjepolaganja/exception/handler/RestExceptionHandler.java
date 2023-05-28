package rs.ac.bg.fon.pracenjepolaganja.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rs.ac.bg.fon.pracenjepolaganja.exception.ErrorResponse;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represent Exception handler that intercepts all errors and exceptions.
 * Exceptions are sent in JSON form using class ErrorResponse to the client.
 *
 * @author Vuk Manojlovic
 */
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Method that process all exceptions that are type of NotFoundException.
     * NotFoundException is caused when some entity is not found in database.
     *
     * @param ex NotFoundException that was thrown.
     * @return object of ErrorResponse, JSON format of error that is sent to client.
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotFoundException ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ErrorResponse errorResponse =
                new ErrorResponse(HttpStatus.NOT_FOUND.value(),errors,System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    /**
     * Method that process all exception that were validated by Spring Validation.
     * MethodArgumentNotValidException is thrown when some property of entity is not valid.
     *
     * @param ex MethodArgumentNotValidException that was thrown. Contains list of messages which properties are not
     *           validated by Spring Validation.
     * @return object of ErrorResponse, JSON format of error that is sent to client.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        ErrorResponse errorResponse =
                new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), errors,System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Method that process all exceptions that are type of UsernameNotFoundException, provided by SpringSecurity.
     * UsernameNotFoundException is caused when member, with given username, doesn't exist in database.
     *
     * @param ex UsernameNotFoundException that was thrown.
     * @return object of ErrorResponse, JSON format of error that is sent to client.
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UsernameNotFoundException ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ErrorResponse errorResponse =
                new ErrorResponse(HttpStatus.NOT_FOUND.value(),errors,System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    /**
     * Method that process all exceptions that are type of SQLException.
     * SQLException is caused by wrong sql query, when constraints of database are not satisfied.
     *
     * @param ex SQLException that was thrown.
     * @return object of ErrorResponse, JSON format of error that is sent to client.
     */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> handleException(SQLException ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ErrorResponse errorResponse =
                new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(),errors,System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Method that process all exceptions that were not processed by the previous methods.
     * Represent global exception.
     *
     * @param ex Exception that was thrown. Parent of all exceptions.
     * @return object of ErrorResponse, JSON format of error that is sent to client.
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ErrorResponse errorResponse =
                new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),errors,System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
