package test.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import main.exceptions.BowlingException;
import main.exceptions.GameMaxedOutException;
import main.micro.Game;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameTest {
	
	/* === TEST MECHA === */
	@Test public void testEveryNonStrikeConsumesOneFrame() throws IllegalArgumentException, BowlingException {
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
	@Test public void testEachStrikeConsumesOneFrame() throws IllegalArgumentException, BowlingException {
		// Given
		Game g = new Game();
		
		// When / Then
		assertEquals(0, g.getFramesCompleted());

		g.roll(10); assertEquals(1, g.getFramesCompleted());
		g.roll(10); assertEquals(2, g.getFramesCompleted());
		g.roll(10); assertEquals(3, g.getFramesCompleted());
		g.roll(10); assertEquals(4, g.getFramesCompleted());
	}

	@Test public void testTooManyRolls() throws IllegalArgumentException, BowlingException {
		// Given
		Game g = new Game();
		rollMany(g, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);// ten frames used up
		
		// When		
		GameMaxedOutException maxedOutException = null;
		try {
			g.roll(0);
			
		} catch (GameMaxedOutException e) {
			maxedOutException = e;
		}
		
		// Then
		assertNotNull("Game should have thrown maxed out exception after more than 10 frames", maxedOutException);
	}

	/* === TEST POINTS === */
	@Test public void testPointsOpenFrames() throws IllegalArgumentException, BowlingException {
		// Given
		Game g = new Game();
		
		// When
		rollMany(g, 2, 4);// frame 1
		int iteration1Score = g.getScore();
		
		rollMany(g, 3, 6, 0, 10);// frame 3
		int iteration2Score = g.getScore();
		
		rollMany(g, 0, 0);// frame 4
		int iteration3Score = g.getScore();
		
		rollMany(g, 1, 1, 9, 0, 2, 7);// frame 7
		int iteration4Score = g.getScore();
		
		// Then
		assertEquals(6, 		iteration1Score);
		assertEquals(6+19, 		iteration2Score);
		assertEquals(6+19+0, 	iteration3Score);
		assertEquals(6+19+0+20, iteration4Score);
	}

	/* === consecutive strikes === */
	@Test public void testPointsStrike() throws IllegalArgumentException, BowlingException {
		// Given
		Game g = new Game();
		
		// When
		g.roll(10);
		rollMany(g, 2, 5);// two frames in total
		int score = g.getScore();
		
		// Then
		assertEquals(24, score);
	}
	@Test public void testPointsRhino() throws IllegalArgumentException, BowlingException {
		// Given
		Game g = new Game();
		
		// When
		rollMany(g, 10, 10);// two strikes in a row
		rollMany(g, 6, 3);
		int score = g.getScore();
		
		// Then
		assertEquals(54, score);
	}
	@Test public void testPointsTurkey() throws IllegalArgumentException, BowlingException {
		// Given
		Game g = new Game();
		
		// When
		rollMany(g, 10, 10, 10);// three strikes in a row
		rollMany(g, 2, 4);
		int score = g.getScore();
		
		// Then
		assertEquals(74, score);
	}
	@Test public void testPointsLlama() throws IllegalArgumentException, BowlingException {
		// Given
		Game g = new Game();
		
		// When
		rollMany(g, 10, 10, 10, 10);// four strikes in a row
		rollMany(g, 1, 7);
		int score = g.getScore();
		
		// Then
		assertEquals(107, score);
	}
	@Test public void testPointsThanksgivingTurkey() throws IllegalArgumentException, BowlingException {
		// Given
		Game g = new Game();
		
		// When
		rollMany(g, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);// ten strikes in a row
		int score = g.getScore();
		
		// Then
		assertEquals(270, score);// TODO unsure of this?
	}

	/* === INTERNAL === */
	
	/**
	 * 
	 * @param g - the bowling game instance
	 * @param rolls - don't forget that a single roll resulting in a strike will automatically consume the entire frame.
	 */
	private void rollMany(Game g, int... rolls) throws IllegalArgumentException, BowlingException {
		for(int num : rolls) {
			g.roll(num);
		}
	}

}