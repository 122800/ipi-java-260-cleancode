package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import main.exceptions.frame.FrameException;
import main.exceptions.frame.FrameMaxedOutException;
import main.exceptions.frame.InvalidRollException;
import main.exceptions.frame.NotYetRolledException;
import main.micro.Frame;

public class FrameTest {
	
	@Test public void testFrameEmpty() {
		// Given
		Frame f = new Frame();
		
		// When
		FrameException firstRollException = null;
		try {
			int firstRollQuilles = f.getFirstRoll();
			
		} catch (NotYetRolledException e) {
			firstRollException = e;
		}
		
		FrameException secondRollException = null;
		try {
			int secondRollQuilles = f.getSecondRoll();
			
		} catch (NotYetRolledException e) {
			secondRollException = e;
		}
		
		// Then
		assertNotNull(firstRollException);
		assertNotNull(secondRollException);
	}
	@Test public void testFrameHalfFull() {
		// Given
		Frame f = new Frame();
		
		// When
		f.roll(0);
		
		FrameException firstRollException = null;
		try {
			int firstRollQuilles = f.getFirstRoll();
			
		} catch (NotYetRolledException e) {
			firstRollException = e;
		}
		
		FrameException secondRollException = null;
		try {
			int secondRollQuilles = f.getSecondRoll();
			
		} catch (NotYetRolledException e) {
			secondRollException = e;
		}
		
		// Then
		assertNull(firstRollException);
		assertNotNull(secondRollException);
	}
	@Test public void testFrameFull() {
		// Given
		Frame f = new Frame();
		
		// When
		f.roll(0);
		f.roll(0);
		
		FrameException firstRollException = null;
		try {
			int firstRollQuilles = f.getFirstRoll();
			
		} catch (NotYetRolledException e) {
			firstRollException = e;
		}
		
		FrameException secondRollException = null;
		try {
			int secondRollQuilles = f.getSecondRoll();
			
		} catch (NotYetRolledException e) {
			secondRollException = e;
		}
		
		// Then
		assertNull(firstRollException);
		assertNull(secondRollException);
	}
	
	@Test public void testFrameRolledTooManyTimes() {
		// Given
		Frame f = new Frame();
		f.roll(0);
		f.roll(0);
		
		// When
		FrameException alreadyFullException = null;
		try {
			f.roll(0);
			
		} catch (FrameMaxedOutException e) {
			alreadyFullException = e;
		}
		
		// Then
		assertNotNull(alreadyFullException);
	}
	@Test public void invalidRollNegativeNumber() {
		// Given
		Frame f = new Frame();
		
		// When
		IllegalArgumentException invalidRoll = null;
		try {
			f.roll(-1);
		} catch (IllegalArgumentException e) {
			invalidRoll = e;
		}
		
		// Then
		assertNotNull("Should throw exception when number format incorrect", invalidRoll);
	}
	@Test public void invalidRollFirstRollTooBig() {
		// Given
		Frame f = new Frame();
		
		// When
		IllegalArgumentException invalidRoll = null;
		try {
			f.roll(11);
		} catch (IllegalArgumentException e) {
			invalidRoll = e;
		}
		
		// Then
		assertNotNull("Should throw exception when first roll exceeds remaining pins", invalidRoll);
	}
	@Test public void invalidRollSecondRollTooBig() {
		// Given
		Frame f = new Frame();
		
		// When
		FrameException invalidRoll = null;
		f.roll(5);
		try {
			f.roll(6);
		} catch (InvalidRollException e) {
			invalidRoll = e;
		}
		
		// Then
		assertNotNull("Should throw exception when second roll exceeds remaining pins", invalidRoll);
	}

}