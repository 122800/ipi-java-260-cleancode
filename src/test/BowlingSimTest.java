package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.BowlingSim;

public class BowlingSimTest {
	
	@Test
	public void initSimTest() {
		// Given
		BowlingSim sim = new BowlingSim();
		
		// When
		int currentScore = sim.getScore();
		
		// Then
		assertEquals("Starting score should be equal to 0", 0, currentScore);
		
	}
}
