package test.internal;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import main.exceptions.BowlingException;
import main.micro.Game;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameMechaTest implements TestUtilities {
	
	@Test public void testGetFramesCompleted() {
		// Given
		Game g = new Game();
		
		// When / Then
		assertEquals(0, g.getFramesCompleted());
		
		int i = 0;

		rollMany(g, 5); assertEquals(i, g.getFramesCompleted());
		rollMany(g, 5); assertEquals(++i, g.getFramesCompleted());
		
		rollMany(g, 10); assertEquals(++i, g.getFramesCompleted());

		rollMany(g, 5); assertEquals(i, g.getFramesCompleted());
		rollMany(g, 5); assertEquals(++i, g.getFramesCompleted());

		rollMany(g, 10); assertEquals(++i, g.getFramesCompleted());
		
		rollMany(g, 0); assertEquals(i, g.getFramesCompleted());
		rollMany(g, 0); assertEquals(++i, g.getFramesCompleted());

	}

}