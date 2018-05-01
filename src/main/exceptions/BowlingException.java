package main.exceptions;

public class BowlingException extends RuntimeException {
	
	public BowlingException() {
		super();
	}

	public BowlingException(String s) {
		super(s);
	}
}
