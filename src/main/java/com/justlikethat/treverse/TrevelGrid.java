package com.justlikethat.treverse;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */

enum Direction {
	NORTH, EAST, SOUTH, WEST
}

public class TrevelGrid {
	
	static int MAX = 4;
	static int moveCount = 0;
	
	private final static Logger LOG = LoggerFactory.getLogger(TrevelGrid.class);	 
	
	
	
	public static void main(String[] args) {
		
		List<PitStop> path = new ArrayList<PitStop>();
		path.add(new PitStop(0, 0, 0));
		path.add(new PitStop(0, 1, 2));
		PitStop start = new PitStop(0, 2, 4);
		path.add(start);

		int loopDepth = 0;
		startNavigation(start, path, loopDepth);

	}
	
	private static void startNavigation(PitStop startFrom, List<PitStop> path, int loopDepth) {

		if (startFrom.getX() == MAX 
				&& startFrom.getY() == MAX) {
			LOG.debug("REACHED END: {}, {}, {}, path size: {}, path: {}", moveCount, loopDepth, startFrom , path.size(), path);
			if(startFrom.getToll() == 0.0) {
				LOG.debug("HURRAY... MISSION ACCOMPLISHED!!");
				System.exit(0);
			}
		} 
		
		loopDepth++;
		for (Direction direction : Direction.values()) {
			if(canMoveTo(startFrom, direction, path)) {
				
				moveCount++;
				
				PitStop next;
				List<PitStop> newPath = new ArrayList<PitStop>();
				newPath.addAll(path);
				
				next = startFrom.clone();
				next.move(direction);				
				newPath.add(next);
				LOG.debug("{} > {}     Moved: [{}], from: {}, current: {}, path: {}", 
						moveCount, loopDepth, direction, startFrom, next, newPath);
				startNavigation(next, newPath, loopDepth);
			} else {
				LOG.debug("{} > {} Cant Move: [{}], from: {}", moveCount, loopDepth, direction, startFrom);
			}
			
		}
		
	}

	static boolean canMoveTo(PitStop from, Direction dir, List<PitStop> travelledPath) {
		
		boolean flag = false;
		PitStop to = null;
		
		switch(dir) {
		case EAST: 
			flag = from.getY() < MAX ? true : false;
			to = new PitStop(from.getX(), from.getY() + 1);
			break;
		case WEST:
			flag = from.getY() > 0 ? true : false;
			to = new PitStop(from.getX(), from.getY() - 1);
			break;
		case SOUTH:
			flag = from.getX() < MAX ? true : false;
			to = new PitStop(from.getX() + 1, from.getY());
			break;
		case NORTH:
			flag = from.getX() > 0 ? true : false;
			to = new PitStop(from.getX() - 1, from.getY());
			break;
		}
		
		if(!flag) {
			return false;
		}
		// target location is a valid location
		
		if(wasTravelled(from, to, travelledPath)) {
			return false;
		}
		// target location has not been traversed before
		
		return true;
		
	}
	
	static boolean wasTravelled(PitStop from, PitStop to, List<PitStop> travelledPath) {
		
		if(travelledPath.size() < 2) {
			return false;
		}
		
		for(int i = 0; i < travelledPath.size(); i++) {
			if(travelledPath.get(i).equals(from)) {
				
				if(i == 0) {
					if((travelledPath.get(i + 1).equals(to))) {
						return true;
					} else {
						continue;
					}
				}
				
				if(i == travelledPath.size()-1) {
					if(travelledPath.get(i-1).equals(to)) {
						return true;
					} else {
						continue;
					}
				}
				
				if ((travelledPath.get(i - 1).equals(to)
								|| travelledPath.get(i + 1).equals(to))) {
					return true;
				}
			}
		}
		return false;
	}
	
}

class PitStop {
	private int x;
	private int y;
	private double toll;
	
	PitStop(int i, int j) {
		x = i;
		y = j;
	}
	
	PitStop(int i, int j, double amt) {
		x = i;
		y = j;
		toll = amt;
	}
	
	public void move(Direction dir) {
		switch(dir) {
		case EAST: 
			moveEast();
			break;
		case WEST:
			moveWest();
			break;
		case SOUTH:
			moveSouth();
			break;
		case NORTH:
			moveNorth();
			break;
		}
	}
	
	private void moveEast() {		
		y++;
		toll += 2;
	}
	
	private void moveWest() {
		y--;
		toll -= 2;
	}
	
	private void moveSouth() {
		x++;
		toll *= 2;
	}
	
	private void moveNorth() {
		x--;
		toll /= 2;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public double getToll() {
		return toll;
	}
	
	@Override
	protected PitStop clone() {
		return new PitStop(x, y, toll);
	}
	
	@Override
	public String toString() {
		return String.format("(%d, %d)", x, y, toll);
	}
	
	public boolean equals(PitStop obj) {
		if(this.x == obj.getX()
				&& this.y == obj.getY()) {
			return true;
		} else {
			return false;
		}
	}
}
