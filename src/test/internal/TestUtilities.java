package test.internal;

import main.exceptions.BowlingException;
import main.exceptions.FrameMaxedOutException;
import main.exceptions.GameMaxedOutException;
import main.exceptions.InvalidRollException;
import main.micro.Frame;
import main.micro.Game;

public interface TestUtilities {
	
	/* === FRAME === */
	
	/**
	 *  Utility to remove reduce the need for try/catch statements all over the place.
	 * Any exception caught indicates a badly-written test and will be rethrown as a
	 * RuntimeException.
	 * @param f - a Frame instance
	 * @param num - the number of pins knocked over.
	 * 
	 */
	public default void rollFrame(Frame f, int num) {
		try {
			f.roll(num);
			
		} catch (IllegalArgumentException | BowlingException e) {
			throwRuntimeException(e);
		}
	}
	
	/**
	 * Utility to remove reduce the need for try/catch statements all over the place.
	 * A caught FrameMaxedOutException will be returned, any other exception indicates
	 * a badly-written test and will be rethrown as a RuntimeException.
	 * @param f - a Frame instance
	 * @param num - the number of pins knocked over.
	 * @return the exception that could have been thrown. Check for null and fail the test accordingly.
	 */
	public default FrameMaxedOutException rollFrameExpectingFrameMaxedOutException(Frame f, int num) {

		FrameMaxedOutException expected = null;

		try {
			f.roll(num);

		} catch (FrameMaxedOutException e) {
			expected = e;

		} catch (IllegalArgumentException | BowlingException e) {
			throwRuntimeException(e);
		}

		return expected;
	}
	
	
	/**
	 * Utility to remove reduce the need for try/catch statements all over the place.
	 * A caught IllegalArgumentException will be returned, any other exception indicates
	 * a badly-written test and will be rethrown as a RuntimeException.
	 * @param f - a Frame instance
	 * @param num - the number of pins knocked over.
	 * @return the exception that could have been thrown. Check for null and fail the test accordingly.
	 */
	public default IllegalArgumentException rollFrameExpectingIllegalArgumentException(Frame f, int num) {

		IllegalArgumentException expected = null;

		try {
			f.roll(num);

		} catch (IllegalArgumentException e) {
			expected = e;

		} catch (BowlingException e) {
			throwRuntimeException(e);
		}

		return expected;
	}

	/**
	 * Utility to remove reduce the need for try/catch statements all over the place.
	 * A caught InvalidRollException will be returned, any other exception indicates
	 * a badly-written test and will be rethrown as a RuntimeException.
	 * @param f - a Frame instance
	 * @param num - the number of pins knocked over.
	 * @return the exception that could have been thrown. Check for null and fail the test accordingly.
	 */
	public default InvalidRollException rollFrameExpectingInvalidRollException(Frame f, int num) {

		InvalidRollException expected = null;

		try {
			f.roll(num);

		} catch (InvalidRollException e) {
			expected = e;

		} catch (BowlingException e) {
			throwRuntimeException(e);
		}

		return expected;
	}
	
	/* === GAME === */
	

	/* === GAME === */

	/**
	 * Utility to remove reduce the need for try/catch statements all over the place.
	 * A caught GameMaxedOutException will be returned, any other exception indicates
	 * a badly-written test and will be rethrown as a RuntimeException.
	 * @param g - a bowling game instance
	 * @param num - the number of pins knocked over.
	 * @return the exception that could have been thrown. Check for null and fail the test accordingly.
	 */
	public default GameMaxedOutException rollExpectingMaxedOutException(Game g, int num) {

		GameMaxedOutException expected = null;

		try {
			g.roll(num);

		} catch (GameMaxedOutException e) {
			expected = e;

		} catch (IllegalArgumentException | BowlingException e) {
			throwRuntimeException(e);
		}

		return expected;
	}

	/**
	 * Utility to remove reduce the need for try/catch statements all over the place.
	 * Any exception caught indicates a badly-written test and will be rethrown as a
	 * RuntimeException.
	 * @param g - a bowling game instance
	 * @param rolls - don't forget that a single roll resulting in a strike will automatically consume the entire frame.
	 */
	public default void rollMany(Game g, int... rolls) {
		try {

			for(int num : rolls) g.roll(num);

		} catch (IllegalArgumentException | BowlingException e) {
			throwRuntimeException(e);
		}
	}
	
	/* === INTERNAL === */
	
	/**
	 * Converts any given exception into a RuntimeException.
	 * @param e - any exception
	 */
	public default void throwRuntimeException(Exception e) {
		RuntimeException failure = new RuntimeException("Bad test - this exception should not have been thrown: " + e.getMessage());
		failure.setStackTrace(e.getStackTrace());
		throw new RuntimeException(failure);
	}

}
