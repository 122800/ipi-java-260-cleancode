package main.exceptions;

/**
 * Groups exceptions that will be handled internally I think?
 * @author leovi.nutakor
 *
 */
public class RuntimeBowlingException extends RuntimeException {
	
	public RuntimeBowlingException() {
		super();
	}

	public RuntimeBowlingException(String s) {
		super(s);
	}

}