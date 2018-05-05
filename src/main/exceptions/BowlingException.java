package main.exceptions;

/**
 * Groups all exceptions that will need to be signaled to and handled by the player directly.
 * @author leovi.nutakor
 *
 */
public class BowlingException extends Exception {
	
	public BowlingException() {
		super();
	}

	public BowlingException(String s) {
		super(s);
	}
}
