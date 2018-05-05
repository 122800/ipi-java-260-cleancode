package main.micro;

import main.exceptions.InvalidRollException;
import main.exceptions.frame.FrameMaxedOutException;

public class Frame {
	
	public enum FrameType {
		ONGOING, OPENFRAME, SPARE, STRIKE
	}
	
	public Frame() {}
	
	private final int MAX_PINS = 10;
	
	private Integer[] quilles = new Integer[2];
	private FrameType type = FrameType.ONGOING;
	private int remainingPins = MAX_PINS;
	private int lastRollPoints;

	public FrameType getType() {
		return type;
	}
	public boolean isFull() {
		return type != FrameType.ONGOING;
	}
	
	public int getLastRollPoints() {
		return lastRollPoints;
	}
	
	/**
	 * Throw the bowling ball to knock down some pins.
	 * @param fallenPins - the number of pins that have fallen due to this roll.
	 * @throws IllegalArgumentException fallenPins can only be a number from 0 to 10.
	 * @throws InvalidRollException cannot knock over more than 10 pins total.
	 * @throws FrameMaxedOutException when this frame cannot accept any more rolls, for a variety of reasons.
	 */
	public void roll(int fallenPins) throws IllegalArgumentException, InvalidRollException, FrameMaxedOutException {
		
		/* === Validate input === */
		
		boolean validInput = (fallenPins >= 0) && (fallenPins <= 10);
		
		if(!validInput)						throw new IllegalArgumentException();
		if(isFull())						throw new FrameMaxedOutException();
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
		remainingPins -= fallenPins;
		lastRollPoints = fallenPins;
	}
	
	private boolean hasRolledOnce() {
		return hasRolled(0) && !hasRolled(1);
	}
	private boolean hasRolledTwice() {
		return hasRolled(1);
	}

	private boolean hasRolled(int num) {
		return quilles[num] != null;
	}
}