package com.kstn.group4.backend.exception;

/**
 * Exception thrown when a required database resource (entity) is not found.
 * Used for GET operations where the resource ID does not exist.
 */
public class ResourceNotFoundException extends RuntimeException {
    private final String resourceName;

    public ResourceNotFoundException(String message) {
        super(message);
        this.resourceName = "Resource";
    }

    public ResourceNotFoundException(String message, String resourceName) {
        super(message);
        this.resourceName = resourceName;
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.resourceName = "Resource";
    }

    public String getResourceName() {
        return resourceName;
    }
}
