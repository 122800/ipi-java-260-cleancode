package main.micro;

import main.exceptions.FrameMaxedOutException;
import main.exceptions.GameMaxedOutException;
import main.exceptions.InvalidRollException;

public class Game {
	
	private int score = 0;
	private int currentFrameNum = 0;
	private int bonusPointStack = 0;
	private Frame[] frames = new Frame[12];// 10 frames + 2 bonus frames

	/**
	 * 
	 * @return The current number of frames that cannot accept any more rolls. 
	 */
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
	public Frame getCurrentFrame() {
		return frames[currentFrameNum];
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
		
		Frame f = getCurrentFrame();
		
		try {
			f.roll(fallenPins);
			calculateScore();
			
		} catch (FrameMaxedOutException e) {
			nextFrame();// may throw game maxed out exception
			roll(fallenPins);// single-recursion
		}
	}
	
	/* === INTERNAL === */
	
	private void calculateScore() {
		
		Frame f = getCurrentFrame();
		
		score += f.getLastRollPoints();
		
		if(bonusPointStack > 0) {
			
			if(bonusPointStack > 2) {
				int excessBonus = bonusPointStack - 2;
				score += (f.getLastRollPoints() * excessBonus);
				bonusPointStack -= excessBonus;// i.e. bonusPointStack = 2
			}
			
			score += f.getLastRollPoints();
			bonusPointStack--;
		}
		
		if(currentFrameNum > 9) return;// no more bonus points awarded after game end
		
		switch(f.getType()) {
			case ONGOING:
			case OPENFRAME:
				break;
				
			case SPARE:
				bonusPointStack++;
				break;
				
			case STRIKE:
				bonusPointStack += 2;
				break;
		}	
	}

	private void nextFrame() throws GameMaxedOutException {
		
		/* === Validate context === */
		
		boolean gameFull = (currentFrameNum > 8);
		boolean bonusPoints = (bonusPointStack > 0);
		
		if(gameFull && !bonusPoints)	throw new GameMaxedOutException();
		if(!getCurrentFrame().isFull())	throw new RuntimeException("Something went wrong: switched to next Frame while current Frame was not full");
		
		/* === Body === */
		
		if(gameFull && bonusPoints) {
			frames[++currentFrameNum] = new Frame(bonusPointStack);
		} else {
			frames[++currentFrameNum] = new Frame();			
		}
		
	}
}
