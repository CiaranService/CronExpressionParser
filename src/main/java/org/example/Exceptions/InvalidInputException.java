package org.example.Exceptions;

/**
 * An invalid input exception should be thrown whenever a user
 * inputs an invalid cron expression
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String errorMessage) {
        super(errorMessage);
    }
}