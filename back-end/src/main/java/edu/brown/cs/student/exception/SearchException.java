package edu.brown.cs.student.exception;

/**
 * This method throws an edu.brown.cs.student.exception if there is a problem with the searcher
 */
public class SearchException extends Exception{
    private final Throwable cause;

    public SearchException(String message) {
        super(message);
        this.cause = null;
    }
    public SearchException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }
}

