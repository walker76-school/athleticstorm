/*
 * Filename: BadRequestException.java
 * Author: Andrew Walker
 * Date Last Modified: 1/30/2020
 */

package edu.baylor.ecs.athleticstorm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception for bad requests
 *
 * @author Andrew Walker
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    /**
     * Constructs a BadRequestException with a message
     * @param message a message
     */
    public BadRequestException(String message) {
        super(message);
    }

    /**
     * Constructs a BadRequestException with a message and a cause
     * @param message a message
     * @param cause the cause of the exception
     */
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
