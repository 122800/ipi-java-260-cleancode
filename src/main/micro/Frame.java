package main.micro;

import main.exceptions.InvalidRollException;
import main.exceptions.frame.FrameMaxedOutException;

public class Frame {
	
	private Integer[] quilles = new Integer[2];
	
	public Frame() {}

	public boolean hasRolledFirst() {
		return hasRolled(0);
	}
	public boolean hasRolledSecond() {
		return hasRolled(1);
	}
	
	/**
	 * Throw the bowling ball to knock down some pins.
	 * @param fallenPins - the number of pins that have fallen due to this roll.
	 * @throws IllegalArgumentException fallenPins can only be a number from 0 to 10.
	 * @throws InvalidRollException cannot knock over more than 10 pins total.
	 */
	public void roll(int fallenPins) throws IllegalArgumentException, InvalidRollException {
		
		/* === Validate input === */
		
		boolean validInput = (fallenPins >= 0 && fallenPins <= 10); 
		
		if(!validInput)								throw new IllegalArgumentException();
		if(hasRolledFirst() && hasRolledSecond()) 	throw new FrameMaxedOutException();
		if(hasRolledFirst() &&
				getRoll(0) + fallenPins > 10) 	throw new InvalidRollException("Cannot knock over more than 10 pins per frame.");
		
		/* === Body === */
		
		if(!hasRolledFirst()) {
			quilles[0] = fallenPins;
		} else if(!hasRolledSecond()) {
			quilles[1] = fallenPins;
		}
	}
	
	/* === INTERNAL === */
	
	private int getRoll(int num) {
		return quilles[num];
	};

	private boolean hasRolled(int num) {
		return quilles[num] != null;
	}
}
