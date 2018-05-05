package main.micro;

import main.exceptions.GameMaxedOutException;
import main.exceptions.InvalidRollException;
import main.exceptions.frame.FrameMaxedOutException;

public class Game {
	
	private int score = 0;
	private int currentFrameNum = 0;
	private Frame[] frames = new Frame[10];

	public int getFramesCompleted() {
		int i = 0;
		while(frames[i] != null && frames[i].isFull()) {
			i++;
		}
		
		return i;
	}
	public int getScore() {
		return score;
	}
	
	public Game() {
		frames[0] = new Frame();
	}
	
	/**
	 * Throw the bowling ball to knock down some pins.
	 * @param fallenPins - the number of pins that have fallen due to this roll.
	 * @throws IllegalArgumentException fallenPins can only be a number from 0 to 10.
	 * @throws InvalidRollException cannot knock over more than 10 pins total.
	 * @throws GameMaxedOutException when this game cannot accept any more frames, for a variety of reasons.
	 */
	public void roll(int fallenPins) throws IllegalArgumentException, InvalidRollException, GameMaxedOutException {
		
		/* === Body === */
		
		try {
			getCurrentFrame().roll(fallenPins);
			
		} catch (FrameMaxedOutException e) {
			nextFrame();// may throw maxed out exception
			roll(fallenPins);// single-recursion
		}
	}
	
	
	/* === INTERNAL === */

	private void nextFrame() throws GameMaxedOutException {
		
		/* === Validate context === */
		
		if(currentFrameNum > 8) 		throw new GameMaxedOutException();
		if(!getCurrentFrame().isFull())	throw new RuntimeException("Something went wrong: switched to next Frame while current Frame was not full");
		
		/* === Body === */
		
		frames[++currentFrameNum] = new Frame();
		
	}
	private Frame getCurrentFrame() {
		return frames[currentFrameNum];
	}
}
