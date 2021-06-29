package learn.capstone.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleException(DataAccessException ex) {

        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse("Something went wrong in our database."),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException ex) {

        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse(ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpClientErrorException.Conflict.class)
    public ResponseEntity<ErrorResponse> handleException(HttpClientErrorException.Conflict ex) {

        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse("Miss-match in provided information."), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<ErrorResponse> handleException(HttpClientErrorException.BadRequest ex) {

        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse("Bad request provided."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<ErrorResponse> handleException(HttpClientErrorException.NotFound ex) {

        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse("Information provided not found."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpClientErrorException.UnsupportedMediaType.class)
    public ResponseEntity<ErrorResponse> handleException(HttpClientErrorException.UnsupportedMediaType ex) {

        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse("Information provided not correct media type."), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {

        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse("Sorry, not sorry."),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
