/*
 * Filename: AppException.java
 * Author: Andrew Walker
 * Date Last Modified: 1/30/2020
 */

package edu.baylor.ecs.athleticstorm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An app level exception
 *
 * @author Andrew Walker
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends RuntimeException {

    /**
     * Constructs an AppException with a message
     * @param message a message
     */
    public AppException(String message) {
        super(message);
    }

    /**
     * Constructs an AppException with a message and throwable
     * @param message a message
     * @param cause the cause of the exception
     */
    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
