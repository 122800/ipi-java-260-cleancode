package test.internal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.exceptions.InvalidRollException;
import main.micro.Game;

public class GameTest {
	
	/* === TEST MECHA === */
	@Test public void testSpareConsumesOneFrame() throws IllegalArgumentException, InvalidRollException {
		// Given
		Game g = new Game();
		
		// When
		int initialProgress = g.getFramesRolled();
		g.roll(5);
		int progressOneRoll= g.getFramesRolled();
		g.roll(5);
		int progressTwoRolls= g.getFramesRolled();
		
		// Then
		assertEquals(initialProgress, 0);
		assertEquals(progressOneRoll, 0);
		assertEquals(progressTwoRolls, 1);
	}
	@Test public void testStrikeConsumesOneFrame() throws IllegalArgumentException, InvalidRollException {
		// Given
		Game g = new Game();
		
		// When
		int initialProgress = g.getFramesRolled();
		g.roll(10);
		int progressAfterStrike = g.getFramesRolled();
		
		// Then
		assertEquals(initialProgress, 0);
		assertEquals(progressAfterStrike, 1);
	}

	/* === TEST POINTS === */
	@Test public void testOnlyOpenFramePoints() throws IllegalArgumentException, InvalidRollException {
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
		assertEquals(6, iteration1Score);
		assertEquals(6+19, iteration2Score);
		assertEquals(6+19+0, iteration3Score);
		assertEquals(6+19+0+20, iteration4Score);
	}

	/* === consecutive strikes === */
	@Test public void testStrikePoints() throws IllegalArgumentException, InvalidRollException {
		// Given
		Game g = new Game();
		
		// When
		g.roll(10);
		rollMany(g, 2, 5);// two frames in total
		int score = g.getScore();
		
		// Then
		assertEquals(24, score);
	}
	@Test public void testRhinoPoints() throws IllegalArgumentException, InvalidRollException {
		// Given
		Game g = new Game();
		
		// When
		rollMany(g, 10, 10);// two strikes in a row
		rollMany(g, 6, 3);
		int score = g.getScore();
		
		// Then
		assertEquals(54, score);
	}
	@Test public void testTurkeyPoints() throws IllegalArgumentException, InvalidRollException {
		// Given
		Game g = new Game();
		
		// When
		rollMany(g, 10, 10, 10);// three strikes in a row
		rollMany(g, 2, 4);
		int score = g.getScore();
		
		// Then
		assertEquals(74, score);
	}
	@Test public void testLlamaPoints() throws IllegalArgumentException, InvalidRollException {
		// Given
		Game g = new Game();
		
		// When
		rollMany(g, 10, 10, 10, 10);// four strikes in a row
		rollMany(g, 1, 7);
		int score = g.getScore();
		
		// Then
		assertEquals(107, score);
	}
	@Test public void testThanksgivingTurkeyPoints() throws IllegalArgumentException, InvalidRollException {
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
	private void rollMany(Game g, int... rolls) throws IllegalArgumentException, InvalidRollException {
		for(int num : rolls) {
			g.roll(num);
		}
	}

}