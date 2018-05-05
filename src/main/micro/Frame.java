package main.micro;

import main.exceptions.InvalidRollException;
import main.exceptions.frame.FrameMaxedOutException;

public class Frame {
	
	public Frame() {}
	
	private Integer[] quilles = new Integer[3];
	private FrameType type = FrameType.ONGOING;
	private int remainingPins = 10;

	public boolean isFull() {
		return hasRolledTwice();
	}
	
	/**
	 * Throw the bowling ball to knock down some pins.
	 * @param fallenPins - the number of pins that have fallen due to this roll.
	 * @throws IllegalArgumentException fallenPins can only be a number from 0 to 10.
	 * @throws InvalidRollException cannot knock over more than 10 pins total.
	 * @throws FrameMaxedOutException
	 */
	public void roll(int fallenPins) throws IllegalArgumentException, InvalidRollException, FrameMaxedOutException {
		
		/* === Validate input === */
		
		boolean validInput = (fallenPins >= 0) && (fallenPins <= 10);
		
		if(!validInput)						throw new IllegalArgumentException();
		if(type != FrameType.ONGOING)		throw new FrameMaxedOutException();
		if(remainingPins - fallenPins < 0)	throw new InvalidRollException("Cannot knock over more than 10 pins per frame.");
		
		/* === Body === */
		
		topplePins(fallenPins);
		
		boolean noMorePins = (remainingPins == 0); 
		if(hasRolledOnce() && noMorePins) type = FrameType.STRIKE;
		if(hasRolledTwice() && noMorePins) type = FrameType.SPARE;
		if(hasRolledTwice() && !noMorePins) type = FrameType.OPENFRAME;
	}
	
	/* === INTERNAL === */
	
	private void topplePins(int fallenPins) {
		if(!hasRolledOnce()) {
			quilles[0] = fallenPins;
		} else {
			quilles[1] = fallenPins;
		}
		// TODO consider final frame allowing three rolls
		remainingPins -= fallenPins; 
	}
	
//	private int getRoll(int num) {
//		return quilles[num];
//	};
	
	private boolean hasRolledOnce() {
		return hasRolled(0) && !hasRolled(1);
	}
	private boolean hasRolledTwice() {
		return hasRolled(1);// && !hasRolled(2);
	}

	private boolean hasRolled(int num) {
		return quilles[num] != null;
	}
}

enum FrameType {
	ONGOING, OPENFRAME, SPARE, STRIKE
}