package main.micro;

import main.exceptions.InvalidRollException;

public class Game {
	
	private int score = 0;
	private int framesRolled = 0;
	private Frame[] frames = new Frame[10];

	public int getFramesRolled() {
		return framesRolled;
	}
	public int getScore() {
		return score;
	}
	
	public Game() {
		
	}
	
	/**
	 * Throw the bowling ball to knock down some pins.
	 * @param fallenPins - the number of pins that have fallen due to this roll.
	 * @throws IllegalArgumentException fallenPins can only be a number from 0 to 10.
	 * @throws InvalidRollException cannot knock over more than 10 pins total.
	 */
	public void roll(int fallenPins) throws IllegalArgumentException, InvalidRollException {
	
		/* === Validate context === */
		
		if(framesRolled > 9) {
			throw new InvalidRollException("Cannot roll more than 10 frames per game.");
		}
		
		/* === Body === */
		
		Frame f = getCurrentFrame();
		
		f.roll(fallenPins);
		
		if(f.isFull()) {
			framesRolled++;
		}
	}
	
	
	/* === INTERNAL === */

	/**
	 * Initialises a new Frame if needed.
	 * @return
	 */
	private Frame getCurrentFrame() {
		if(frames[framesRolled] == null) frames[framesRolled] = new Frame();
		return frames[framesRolled];
	}
	private boolean canAddFrame() {
		return false;
	}
}
