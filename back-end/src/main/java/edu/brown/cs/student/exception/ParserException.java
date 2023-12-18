package edu.brown.cs.student.exception;

/**
 * This method throws an edu.brown.cs.student.exception if there is an error in the parser
 */
public class ParserException extends Exception{
    private final Throwable cause;

    public ParserException(String message) {
        super(message);
        this.cause = null;
    }
    public ParserException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }
}