package test.internal;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import main.exceptions.BowlingException;
import main.micro.Game;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameMechaTest {
	
	@Test public void testGetFramesCompleted0() throws IllegalArgumentException, BowlingException {
		// Given
		Game g = new Game();
		
		// When / Then
		assertEquals(0, g.getFramesCompleted());

		g.roll(5); assertEquals(0, g.getFramesCompleted());
		g.roll(5); assertEquals(1, g.getFramesCompleted());
		
		g.roll(5); assertEquals(1, g.getFramesCompleted());
		g.roll(5); assertEquals(2, g.getFramesCompleted());
		
		g.roll(0); assertEquals(2, g.getFramesCompleted());
		g.roll(0); assertEquals(3, g.getFramesCompleted());
	}

}
