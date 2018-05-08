package test.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import main.exceptions.FrameMaxedOutException;
import main.exceptions.InvalidRollException;
import main.micro.Frame;
import main.micro.Frame.FrameType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FrameTest implements TestUtilities {
    
    @Test public void testRolledTooManyTimes() {
        // Given
        Frame f = new Frame();
        rollFrame(f, 0);
        rollFrame(f, 0);
        
        
        // When
        FrameMaxedOutException alreadyFullException = rollFrameExpectingFrameMaxedOutException(f, 0);
        
        // Then
        assertNotNull(alreadyFullException);
    }
    @Test public void testRollNegativeNumber() {
        // Given
        Frame f = new Frame();
        
        // When
        IllegalArgumentException invalidRoll = rollFrameExpectingIllegalArgumentException(f, -1);
        
        // Then
        assertNotNull("Should throw exception when number format incorrect", invalidRoll);
    }
    @Test public void testFirstRollTooBig() {
        // Given
        Frame f = new Frame();
        
        // When
        IllegalArgumentException invalidRoll = rollFrameExpectingIllegalArgumentException(f, 11);
        
        // Then
        assertNotNull("Should throw exception when first roll exceeds remaining pins", invalidRoll);
    }
    @Test public void testSecondRollTooBig() {
        // Given
        Frame f = new Frame();
        rollFrame(f, 5);
        
        // When
        InvalidRollException invalidRoll = rollFrameExpectingInvalidRollException(f, 6);
        
        // Then
        assertNotNull("Should throw exception when second roll exceeds remaining pins", invalidRoll);
    }

    @Test public void testStrikeConsumesFrame() {
        // Given
        Frame f = new Frame();
        rollFrame(f, 10);
        
        
        // When
        FrameMaxedOutException alreadyFullException = rollFrameExpectingFrameMaxedOutException(f, 0);
        
        // Then
        assertNotNull(alreadyFullException);
    }

    @Test public void testFrameTypeONGOING() {
        // Given
        Frame f = new Frame();
        rollFrame(f, 9);
        
        // When
        FrameType type = f.getType();
        
        // Then
        assertEquals(FrameType.ONGOING, type);
        assertFalse(f.isFull());
    }
    @Test public void testFrameTypeOPENFRAME() {
        // Given
        Frame f = new Frame();
        rollFrame(f, 5);
        rollFrame(f, 4);
        
        // When
        FrameType type = f.getType();
        
        // Then
        assertEquals(FrameType.OPENFRAME, type);
        assertTrue(f.isFull());
    }
    @Test public void testFrameTypeOPENFRAME_2() {
        // Given
        Frame f = new Frame();
        rollFrame(f, 0);
        rollFrame(f, 9);
        
        // When
        FrameType type = f.getType();
        
        // Then
        assertEquals(FrameType.OPENFRAME, type);
        assertTrue(f.isFull());
    }
    @Test public void testFrameTypeSPARE() {
        // Given
        Frame f = new Frame();
        rollFrame(f, 5);
        rollFrame(f, 5);
        
        // When
        FrameType type = f.getType();

        // Then
        assertEquals(FrameType.SPARE, type);
        assertTrue(f.isFull());
    }
    @Test public void testFrameTypeSPARE_2() {
        // Given
        Frame f = new Frame();
        rollFrame(f, 0);
        rollFrame(f, 10);
        
        // When
        FrameType type = f.getType();

        // Then
        assertEquals(FrameType.SPARE, type);
        assertTrue(f.isFull());
    }
    
    @Test public void testFrameTypeSTRIKE() {
        // Given
        Frame f = new Frame();
        rollFrame(f, 10);
        
        // When
        FrameType type = f.getType();
        
        // Then
        assertEquals(FrameType.STRIKE, type);
        assertTrue(f.isFull());
    }
}