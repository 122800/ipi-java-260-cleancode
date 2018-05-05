package test.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import main.exceptions.BowlingException;
import main.exceptions.GameMaxedOutException;
import main.exceptions.InvalidRollException;
import main.micro.Game;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameTest {
	
	/* === TEST MECHA === */
	@Test public void testEveryNonStrikeConsumesOneFrame()  {
		// Given
		Game g = new Game();
		
		// When / Then
		assertEquals(0, g.getFramesCompleted());

		try {
			g.roll(5); assertEquals(0, g.getFramesCompleted());
			g.roll(5); assertEquals(1, g.getFramesCompleted());
			
			g.roll(5); assertEquals(1, g.getFramesCompleted());
			g.roll(5); assertEquals(2, g.getFramesCompleted());
			
			g.roll(0); assertEquals(2, g.getFramesCompleted());
			g.roll(0); assertEquals(3, g.getFramesCompleted());
			
		} catch (Exception e) {
			throw new RuntimeException("Bad test? " + e.getMessage());
		}
	}
	@Test public void testEachStrikeConsumesOneFrame()  {
		// Given
		Game g = new Game();
		
		// When / Then
		assertEquals(0, g.getFramesCompleted());

		try {
			g.roll(10); assertEquals(1, g.getFramesCompleted());
			g.roll(10); assertEquals(2, g.getFramesCompleted());
			g.roll(10); assertEquals(3, g.getFramesCompleted());
			g.roll(10); assertEquals(4, g.getFramesCompleted());
			
		} catch (Exception e) {
			throw new RuntimeException("Bad test? " + e.getMessage());
		}
	}

	@Test public void testTooManyRolls_Strikes()  {
		// Given
		Game g = new Game();
		try {
			rollMany(g, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);// ten frames used up
			
		} catch (Exception e) {
			throw new RuntimeException("Bad test? " + e.getMessage());
		}
		
		// When		
		GameMaxedOutException maxedOutException = null;
		try {
			g.roll(0);
			
		} catch (GameMaxedOutException e) {
			maxedOutException = e;
			
		} catch (Exception e) {
			throw new RuntimeException("Bad test? " + e.getMessage());
		}
		
		// Then
		assertNotNull("Game should have thrown maxed out exception after more than 10 frames", maxedOutException);
	}
	@Test public void testTooManyRolls_Gutterballs()  {
		// Given
		Game g = new Game();
		try {
			rollMany(g, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			rollMany(g, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			
		} catch(BowlingException e) {
			throw new RuntimeException("Bad test? " + e.getMessage());
		}
		
		// When		
		GameMaxedOutException maxedOutException = null;
		try {
			g.roll(0);
			
		} catch (GameMaxedOutException e) {
			maxedOutException = e;
			
		} catch (Exception e) {
			throw new RuntimeException("Bad test? " + e.getMessage());
		}
		
		// Then
		assertNotNull("Game should have thrown maxed out exception after more than 10 frames", maxedOutException);
	}
	@Test public void testTooManyRolls_Mixed() {
		// Given
		Game g = new Game();
		try {
			rollMany(g, 0, 0);//1
			rollMany(g, 0, 0);
			g.roll(10);
			rollMany(g, 0, 0);
			rollMany(g, 0, 0);//5
			rollMany(g, 0, 0);
			g.roll(10);
			rollMany(g, 0, 0);
			rollMany(g, 0, 0);
			rollMany(g, 0, 0);//10
		} catch(BowlingException e) {
			throw new RuntimeException("Bad test? " + e.getMessage());
		}
		
		// When		
		GameMaxedOutException maxedOutException = null;
		try {
			g.roll(0);
			
		} catch (GameMaxedOutException e) {
			maxedOutException = e;
		} catch (InvalidRollException e) {
			throw new RuntimeException("Bad test?");
		}
		
		// Then
		assertNotNull("Game should have thrown maxed out exception after more than 10 frames", maxedOutException);
	}

	/* === TEST POINTS === */
	@Test public void testPointsOpenFrames()  {
		// Given
		Game g = new Game();
		
		try {
			// When / Then
			rollMany(g, 2, 4);// frame 1
			assertEquals(6, g.getScore());
			 		 
			rollMany(g, 3, 6, 0, 9);// frame 3
			assertEquals(6+18, g.getScore());
			 
			rollMany(g, 0, 0);// frame 4
			assertEquals(6+18+0, g.getScore());
			 
			rollMany(g, 1, 1, 9, 0, 2, 7);// frame 7
			assertEquals(6+18+0+20, g.getScore());
			
		} catch(BowlingException e) {
			throw new RuntimeException("Bad test? " + e.getMessage());
		}
	}

	/* === consecutive strikes === */
	@Test public void testPointsOneStrike()  {
		// Given
		Game g = new Game();
		
		// When
		try {
			g.roll(10);
			rollMany(g, 2, 5);// two frames in total
			int score = g.getScore();
			
			// Then
			assertEquals(24, score);

		} catch(BowlingException e) {
			throw new RuntimeException("Bad test? " + e.getMessage());
		}
	}
	@Test public void testPointsRhino()  {
		// Given
		Game g = new Game();
		
		// When
		try {
			rollMany(g, 10, 10);// two strikes in a row
			rollMany(g, 6, 3);
			int score = g.getScore();
			
			// Then
			assertEquals(54, score);
			
		} catch(BowlingException e) {
			throw new RuntimeException("Bad test? " + e.getMessage());
		}
	}
	@Test public void testPointsTurkey()  {
		// Given
		Game g = new Game();
		
		// When
		try {
			rollMany(g, 10, 10, 10);// three strikes in a row
			rollMany(g, 2, 4);
			int score = g.getScore();
			
			// Then
			assertEquals(74, score);
			
		} catch(BowlingException e) {
			throw new RuntimeException("Bad test? " + e.getMessage());
		}
	}
	@Test public void testPointsLlama()  {
		// Given
		Game g = new Game();
		
		// When
		try {
			rollMany(g, 10, 10, 10, 10);// four strikes in a row
			rollMany(g, 1, 7);
			int score = g.getScore();
			
			// Then
			assertEquals(107, score);
			
		} catch(BowlingException e) {
			throw new RuntimeException("Bad test? " + e.getMessage());
		}
	}
	@Test public void testPointsThanksgivingTurkey()  {
		// Given
		Game g = new Game();
		
		// When
		try {
			rollMany(g, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);// ten strikes in a row
			int score = g.getScore();
			
			// Then
			assertEquals(270, score);// TODO would need to add a bonus frame
		
		} catch(BowlingException e) {
			throw new RuntimeException("Bad test? " + e.getMessage());
		}
	}

	/* === consecutive spares === */
	@Test public void testPointsOneSpare()  {
		// Given
		Game g = new Game();
		
		// When / Then
		try {
			rollMany(g, 5, 5); assertEquals(10, g.getScore());
			rollMany(g, 7, 1); assertEquals(10+14+1, g.getScore());
		
		} catch(BowlingException e) {
			throw new RuntimeException("Bad test? " + e.getMessage());
		}
	}
	@Test public void testPointsTwoSpares()  {
		// Given
		Game g = new Game();
		
		// When / Then
		try {
			rollMany(g, 0, 10); assertEquals(10, g.getScore());
			rollMany(g, 1, 9); assertEquals(10+2+9, g.getScore());
			rollMany(g, 5, 4); assertEquals(10+2+9 + 10+4, g.getScore());
		
		} catch(BowlingException e) {
			throw new RuntimeException("Bad test? " + e.getMessage());
		}
	}
	@Test public void testPointsThreeSpares()  {
		// Given
		Game g = new Game();
		
		// When / Then
		try {
			rollMany(g, 4, 6); assertEquals(10, g.getScore());
			rollMany(g, 0, 10); assertEquals(10+0+10, g.getScore());
			rollMany(g, 0, 10); assertEquals(10+0+10 + 0+10, g.getScore());
			rollMany(g, 0, 0); assertEquals(10+0+10 + 0+10 + 0+0, g.getScore());
			
		} catch(BowlingException e) {
			throw new RuntimeException("Bad test? " + e.getMessage());
		}
	}
	@Test public void testPointsFourSpares()  {
		// Given
		Game g = new Game();
		
		// When / Then
		try {
			rollMany(g, 1, 9); assertEquals(10, g.getScore());
			rollMany(g, 9, 1); assertEquals(10 + 18+1, g.getScore());
			rollMany(g, 8, 2); assertEquals(10 + 18+1 + 16+2, g.getScore());
			rollMany(g, 7, 3); assertEquals(10 + 18+1 + 16+2 + 14+3, g.getScore());
			rollMany(g, 6, 3); assertEquals(10,+ 18+1 + 16+2 + 14+3 + 12+3, g.getScore());
		
		} catch(BowlingException e) {
			throw new RuntimeException("Bad test? " + e.getMessage());
		}
	}
	
	/* === mixed events === */
	@Test public void testPointsMixed() {
		// Given
		Game g = new Game();
		
		try {
			rollMany(g, 3, 0); assertEquals(3, g.getScore());// OPEN FRAME
			rollMany(g, 10);   assertEquals(3 + 10, g.getScore());// STRIKE
			rollMany(g, 4, 6); assertEquals(3 + 10 + 8+12, g.getScore());// SPARE
			rollMany(g, 8, 2); assertEquals(3 + 10 + 8+12 + 16+2, g.getScore());// SPARE
			rollMany(g, 10);   assertEquals(3 + 10 + 8+12 + 16+2 + 20, g.getScore());// STRIKE
			rollMany(g, 0, 0); assertEquals(3 + 10 + 8+12 + 16+2 + 20 + 0+0, g.getScore());// GUTTER BALL
			rollMany(g, 5, 1); assertEquals(3 + 10 + 8+12 + 16+2 + 20 + 0+0 + 5+1, g.getScore());// OPEN FRAME
			rollMany(g, 9, 0); assertEquals(3 + 10 + 8+12 + 16+2 + 20 + 0+0 + 5+1 + 9+0, g.getScore());// OPEN FRAME
			rollMany(g, 1, 1); assertEquals(3 + 10 + 8+12 + 16+2 + 20 + 0+0 + 5+1 + 9+0 + 1+1, g.getScore());// OPEN FRAME
			rollMany(g, 1, 2); assertEquals(3 + 10 + 8+12 + 16+2 + 20 + 0+0 + 5+1 + 9+0 + 1+1 + 1+2, g.getScore());// OPEN FRAME
		
		} catch(BowlingException e) {
			throw new RuntimeException("Bad test? " + e.getMessage());
		}
	}
	
	
	/* === INTERNAL === */
	
	/**
	 * 
	 * @param g - the bowling game instance
	 * @param rolls - don't forget that a single roll resulting in a strike will automatically consume the entire frame.
	 * @throws IllegalArgumentException 
	 * @throws GameMaxedOutException 
	 * @throws InvalidRollException 
	 */
	private void rollMany(Game g, int... rolls) throws IllegalArgumentException, InvalidRollException, GameMaxedOutException  {
		for(int num : rolls) {
			g.roll(num);
		}
	}

}