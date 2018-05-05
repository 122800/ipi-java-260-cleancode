import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


public class BowlingGameTest {

    BowlingGame game = new BowlingGame();

    @Test
    public void queDesGoutieresDonne0Points() {
        rollMany(20, 0);
        assertEquals(0, game.score());
    }

    @Test
    public void uneSeuleQuille() {
        game.roll(1);
        rollMany(19, 0);
        assertEquals(1, game.score());
    }

    @Test
    public void bonusDuSpare() {
        game.roll(8);
        game.roll(2);
        game.roll(1);
        rollMany(17, 0);
        assertEquals(12, game.score());
    }

    @Test
    public void bonusDuStrike() {
        game.roll(10);
        game.roll(1);
        game.roll(1);
        rollMany(16, 0);
        assertEquals(14, game.score());
    }


    @Test
    public void spareSuivitStrike() {
        game.roll(8);
        game.roll(2);//10+10

        game.roll(10);//12

        game.roll(1);
        game.roll(1);//2

        rollMany(14, 0);
        assertEquals(34, game.score());
    }

    @Test
    public void strikeFinal() {
        rollMany(18, 0);

        game.roll(10);

        game.roll(1);
        game.roll(1);

        assertEquals(12, game.score());
    }

    @Test
    @Ignore("Règle à implémenter")
    public void finPartie() {
        rollMany(20, 0);
        try { //ou utilise Assertj
            game.roll(1);
            assertTrue("une exception aurait du etre lancée", false);
        } catch (CoupInvalideException e) {
            // normal
        }
    }

    private void rollMany(int numberRolls, int numberPins) {
        for (int i = 0; i < numberRolls; i++) {
            game.roll(numberPins);
        }
    }
}