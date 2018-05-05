package test.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import main.exceptions.BowlingException;
import main.exceptions.InvalidRollException;
import main.exceptions.frame.FrameMaxedOutException;
import main.micro.Frame;
import main.micro.Frame.FrameType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FrameTest {
	
	@Test public void testRolledTooManyTimes() throws IllegalArgumentException, InvalidRollException, FrameMaxedOutException {
		// Given
		Frame f = new Frame();
		f.roll(0);
		f.roll(0);
		
		
		// When
		FrameMaxedOutException alreadyFullException = null;
		try {
			f.roll(0);
			
		} catch (FrameMaxedOutException e) {
			alreadyFullException = e;
		}
		
		// Then
		assertNotNull(alreadyFullException);
	}
	@Test public void testRollNegativeNumber() throws InvalidRollException, FrameMaxedOutException {
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
	@Test public void testFirstRollTooBig() throws InvalidRollException, FrameMaxedOutException {
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
	@Test public void testSecondRollTooBig() throws IllegalArgumentException, InvalidRollException, FrameMaxedOutException {
		// Given
		Frame f = new Frame();
		
		// When
		InvalidRollException invalidRoll = null;
		f.roll(5);
		try {
			f.roll(6);
		} catch (InvalidRollException e) {
			invalidRoll = e;
		}
		
		// Then
		assertNotNull("Should throw exception when second roll exceeds remaining pins", invalidRoll);
	}

	@Test public void testStrikeConsumesFrame() throws IllegalArgumentException, InvalidRollException, FrameMaxedOutException {
		// Given
		Frame f = new Frame();
		f.roll(10);
		
		
		// When
		FrameMaxedOutException alreadyFullException = null;
		try {
			f.roll(0);
			
		} catch (FrameMaxedOutException e) {
			alreadyFullException = e;
		}
		
		// Then
		assertNotNull(alreadyFullException);
	}

	@Test public void testFrameTypeONGOING() throws IllegalArgumentException, BowlingException {
		// Given
		Frame f = new Frame();
		f.roll(9);
		
		// When
		FrameType type = f.getType();
		
		// Then
		assertEquals(FrameType.ONGOING, type);
	}
	@Test public void testFrameTypeOPENFRAME() throws IllegalArgumentException, BowlingException {
		// Given
		Frame f = new Frame();
		f.roll(5);
		f.roll(4);
		
		
		// When
		FrameType type = f.getType();
		
		// Then
		assertEquals(FrameType.OPENFRAME, type);
	}
	@Test public void testFrameTypeSPARE() throws IllegalArgumentException, BowlingException {
		// Given
		Frame f = new Frame();
		f.roll(5);
		f.roll(5);
		
		Frame f2 = new Frame();
		f2.roll(0);
		f2.roll(10);
		
		
		// When
		FrameType type = f.getType();
		FrameType type2 = f2.getType();
		
		// Then
		assertEquals(FrameType.SPARE, type);
		assertEquals(FrameType.SPARE, type2);
	}
	@Test public void testFrameTypeSTRIKE() throws IllegalArgumentException, BowlingException {
		// Given
		Frame f = new Frame();
		f.roll(10);
		
		// When
		FrameType type = f.getType();
		
		// Then
		assertEquals(FrameType.STRIKE, type);
	}
}