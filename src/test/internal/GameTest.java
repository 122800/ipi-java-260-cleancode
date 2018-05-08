package test.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import main.exceptions.GameMaxedOutException;
import main.micro.Game;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameTest implements TestUtilities {

	/* = TEST MECHA === */
	@Test public void testEveryNonStrikeConsumesOneFrame()  {
		// Given
		Game g = new Game();

		// When / Then
		assertEquals(0, g.getFramesCompleted());

		rollMany(g, 5); assertEquals(0, g.getFramesCompleted());
		rollMany(g, 5); assertEquals(1, g.getFramesCompleted());

		rollMany(g, 5); assertEquals(1, g.getFramesCompleted());
		rollMany(g, 5); assertEquals(2, g.getFramesCompleted());

		rollMany(g, 0); assertEquals(2, g.getFramesCompleted());
		rollMany(g, 0); assertEquals(3, g.getFramesCompleted());

	}
	@Test public void testEachStrikeConsumesOneFrame()  {
		// Given
		Game g = new Game();

		// When / Then
		assertEquals(0, g.getFramesCompleted());

		rollMany(g, 10); assertEquals(1, g.getFramesCompleted());
		rollMany(g, 10); assertEquals(2, g.getFramesCompleted());
		rollMany(g, 10); assertEquals(3, g.getFramesCompleted());
		rollMany(g, 10); assertEquals(4, g.getFramesCompleted());

	}

	@Test public void testTooManyRolls_Strikes()  {
		// Given
		Game g = new Game();
		rollMany(g, 10, 10, 10, 10, 10, 10, 10, 10, 10, 0, 9);// ten frames used up


		// When
		GameMaxedOutException expectedException = rollExpectingMaxedOutException(g, 0);

		// Then
		assertNotNull("Game should have thrown maxed out exception after more than 10 frames", expectedException);
	}
	@Test public void testTooManyRolls_Gutterballs()  {
		// Given
		Game g = new Game();
		rollMany(g, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		rollMany(g, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

		// When
		GameMaxedOutException expectedException = rollExpectingMaxedOutException(g, 0);

		// Then
		assertNotNull("Game should have thrown maxed out exception after more than 10 frames", expectedException);
	}
	@Test public void testTooManyRolls_Mixed() {
		// Given
		Game g = new Game();
		rollMany(g, 0, 0);//1
		rollMany(g, 0, 0);
		rollMany(g, 10);
		rollMany(g, 0, 0);
		rollMany(g, 0, 0);//5
		rollMany(g, 0, 0);
		rollMany(g, 10);
		rollMany(g, 0, 0);
		rollMany(g, 0, 0);
		rollMany(g, 0, 0);//10

		// When
		GameMaxedOutException expectedException = rollExpectingMaxedOutException(g, 0);

		// Then
		assertNotNull("Game should have thrown maxed out exception after more than 10 frames", expectedException);
	}

	/* === TEST POINTS === */
	@Test public void testPointsOpenFrames()  {
		// Given
		Game g = new Game();

		// When / Then
		rollMany(g, 2, 4);// frame 1
		assertEquals(6, g.getScore());

		rollMany(g, 3, 6, 0, 9);// frame 3
		assertEquals(6+18, g.getScore());

		rollMany(g, 0, 0);// frame 4
		assertEquals(6+18+0, g.getScore());

		rollMany(g, 1, 1, 9, 0, 2, 7);// frame 7
		assertEquals(6+18+0+20, g.getScore());

	}

	/* === consecutive strikes === */
	@Test public void testPointsOneStrike()  {
		// Given
		Game g = new Game();

		// When
		rollMany(g, 10);
		rollMany(g, 2, 5);// two frames in total
		int score = g.getScore();

		// Then
		assertEquals(24, score);

	}
	@Test public void testPointsRhino()  {
		// Given
		Game g = new Game();

		// When
		rollMany(g, 10, 10);// two strikes in a row
		rollMany(g, 6, 3);
		int score = g.getScore();

		// Then
		assertEquals(54, score);

	}
	@Test public void testPointsTurkey()  {
		// Given
		Game g = new Game();

		// When
		rollMany(g, 10, 10, 10);// three strikes in a row
		rollMany(g, 2, 4);
		int score = g.getScore();

		// Then
		assertEquals(74, score);

	}
	@Test public void testPointsLlama()  {
		// Given
		Game g = new Game();

		// When
		rollMany(g, 10, 10, 10, 10);// four strikes in a row
		rollMany(g, 1, 7);
		int score = g.getScore();

		// Then
		assertEquals(107, score);

	}
	@Test public void testPointsThanksgivingTurkey()  {
		// Given
		Game g = new Game();

		// When
		rollMany(g, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);// ten strikes in a row
		int score = g.getScore();

		// Then
		assertEquals(270, score);// TODO would need to add a bonus frame

	}

	/* === consecutive spares === */
	@Test public void testPointsOneSpare()  {
		// Given
		Game g = new Game();

		// When / Then
		rollMany(g, 5, 5); assertEquals(10, g.getScore());
		rollMany(g, 7, 1); assertEquals(10+14+1, g.getScore());

	}
	@Test public void testPointsTwoSpares()  {
		// Given
		Game g = new Game();

		// When / Then
		rollMany(g, 0, 10); assertEquals(10, g.getScore());
		rollMany(g, 1, 9); assertEquals(10+2+9, g.getScore());
		rollMany(g, 5, 4); assertEquals(10+2+9 + 10+4, g.getScore());

	}
	@Test public void testPointsThreeSpares()  {
		// Given
		Game g = new Game();

		// When / Then
		rollMany(g, 4, 6); assertEquals(10, g.getScore());
		rollMany(g, 0, 10); assertEquals(10+0+10, g.getScore());
		rollMany(g, 0, 10); assertEquals(10+0+10 + 0+10, g.getScore());
		rollMany(g, 0, 0); assertEquals(10+0+10 + 0+10 + 0+0, g.getScore());

	}
	@Test public void testPointsFourSpares()  {
		// Given
		Game g = new Game();

		// When / Then
		rollMany(g, 1, 9); assertEquals(10, g.getScore());
		rollMany(g, 9, 1); assertEquals(10 + 18+1, g.getScore());
		rollMany(g, 8, 2); assertEquals(10 + 18+1 + 16+2, g.getScore());
		rollMany(g, 7, 3); assertEquals(10 + 18+1 + 16+2 + 14+3, g.getScore());
		rollMany(g, 6, 3); assertEquals(10,+ 18+1 + 16+2 + 14+3 + 12+3, g.getScore());

	}

	/* === MIXED TESTS === */
	@Test public void testPointsMixed() {
		// Given
		Game g = new Game();

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

	}

	@Test public void testFinalRollSpareBonus() {
		// Given
		Game g = new Game();

		rollMany(g, 10, 10, 10, 10, 10, 10, 10, 10, 10);// 9 frames
		rollMany(g, 5, 5);// final roll is a spare
		
		GameMaxedOutException shouldNotBeThrown = rollExpectingMaxedOutException(g, 5);
		assertNull("Game should not end if last roll granted a bonus", shouldNotBeThrown);

		assertEquals(275, g.getScore());
		
		GameMaxedOutException expectedException = rollExpectingMaxedOutException(g, 5);
		assertNotNull("Game should only provide one bonus roll after a spare", expectedException);

	}
	
	@Test public void testFinalRollStrikeBonus() {
		// Given
		Game g = new Game();

		rollMany(g, 10, 10, 10, 10, 10, 10, 10, 10, 10);// 9 frames
		rollMany(g, 10);// final roll is a strike
		
		GameMaxedOutException shouldNotBeThrown = rollExpectingMaxedOutException(g, 5);
		assertNull("Game should not end if last roll granted a bonus", shouldNotBeThrown);

		assertEquals(285, g.getScore());
		
		GameMaxedOutException shouldNotBeThrown_2 = rollExpectingMaxedOutException(g, 5);
		assertNull("Game should provide two bonus rolls after a strike", shouldNotBeThrown_2);
		
		assertEquals(295, g.getScore());
		
		GameMaxedOutException expectedException = rollExpectingMaxedOutException(g, 5);
		assertNotNull("Game should only provide two bonus roll after a strike", expectedException);

	}
	
	@Test public void testFinalRollStrikeBonusStrikeAgain() {
		// Given
		Game g = new Game();

		rollMany(g, 10, 10, 10, 10, 10, 10, 10, 10, 10);// 9 frames
		rollMany(g, 10);// final roll is a strike
		
		GameMaxedOutException shouldNotBeThrown = rollExpectingMaxedOutException(g, 10);
		assertNull("Game should not end if last roll granted a bonus", shouldNotBeThrown);

		assertEquals(300, g.getScore());
	}

	@Test public void testPetrillo() {
		
		// Given / When / Then
		
		Game queDesGoutieresDonne0Points = new Game();
		rollMany(queDesGoutieresDonne0Points, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		rollMany(queDesGoutieresDonne0Points, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		assertEquals(0, queDesGoutieresDonne0Points.getScore());

		Game uneSeuleQuille = new Game();
		rollMany(uneSeuleQuille, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		rollMany(uneSeuleQuille, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		assertEquals(1, uneSeuleQuille.getScore());

		Game bonusDuSpare = new Game();
		rollMany(bonusDuSpare, 8, 2, 1, 0, 0, 0, 0, 0, 0, 0);
		rollMany(bonusDuSpare, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		assertEquals(12, bonusDuSpare.getScore());

		Game bonusDuStrike = new Game();
		rollMany(bonusDuStrike, 10, 1, 1, 0, 0, 0, 0, 0, 0);
		rollMany(bonusDuStrike, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		assertEquals(14, bonusDuStrike.getScore());

		Game spareSuivitStrike = new Game();
		rollMany(spareSuivitStrike, 8, 2, 10, 1, 1, 0, 0, 0, 0);
		rollMany(spareSuivitStrike, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		assertEquals(34, spareSuivitStrike.getScore());

		Game strikeFinal = new Game();
		rollMany(strikeFinal, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		rollMany(strikeFinal, 0, 0, 0, 0, 0, 0, 0, 0, 10);
		rollMany(strikeFinal, 1, 1);
		assertEquals(14, strikeFinal.getScore());// CHANGED FROM 12 TO 14

		Game finPartie = new Game();
		rollMany(finPartie, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		rollMany(finPartie, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		GameMaxedOutException expectedException = rollExpectingMaxedOutException(finPartie, 1);
		assertNotNull("Game should have thrown maxed out exception after more than 10 frames", expectedException);
	}

}