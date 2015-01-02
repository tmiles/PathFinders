package pathFinders;

import java.awt.*;
import java.util.*;

import model.*;

public class AStar{
	
	/**
	 * Gets the shortest path
	 * AStar part
	 * @param origin location
	 * @param destination
	 * @return a path structure of the points including o and dest
	 * 			return null if there isn't a path possible
	 */
	public static Path determinePath(HashMap<Point, Block> blockGrid, Dimension dim, Point o, Point dest) {
		long start = System.currentTimeMillis();
		Path path = new Path(); 
		Point tmp = o; // assign a termporary node to traverse with
		BlockInfo parent; // Keeps track of new parent
		
		/* 
		 * OpenList -> All farthest nodes to check (bredth -first)
		 * ClosedList -> All ndoes you have traveled before 
		 * No overlap between closedlist and openlist
		 */
		ArrayList<BlockInfo> openList = new ArrayList<BlockInfo>();
		ArrayList<BlockInfo> closedList = new ArrayList<BlockInfo>();
		
		closedList.add(new BlockInfo(tmp, null)); // add starting point
		
		while (!tmp.equals(dest)) { // if you haven't arrived yet
			// Parent is the one that you last put into closed list
			parent = closedList.get(closedList.size() - 1);
			// Get all the adjacent blocks of where you are now, also assigns parent blockinfo
			ArrayList<BlockInfo> blocks = Path.getEmptyAdjacent(blockGrid, dim, tmp, parent);
			// Check all those blocks
			for (BlockInfo bi : blocks) {
				// If you can get from your location to the next block in a distance 
				// of one, you have got there 
				// Terminate case
				if (bi.getLocation().equals(dest)) { // only 1 block away
					// traverse backwards from that destination block till start
					while (bi != null) {
						path.addPoint(bi.getLocation()); // add point to the path
						bi = bi.getParent(); // go back to your parent
					}
					//System.out.println("Path: " + path); // testing
					path.setComputationTime(System.currentTimeMillis() - start); // sets the timer
					return path;
				}
				// If adjacent blocks aren't in the openList, then you need to add them
				if (!openList.contains(bi)) {
					// Sets the h value with manhatten distance
					bi.sethVal(BlockInfo.calculateMahatten(bi.getLocation(),
							dest)); 
					// G value is 1 more than its parent
					bi.setgVal(bi.getParent().getgVal() + 1);
					// F value is the sum of the g and h values
					bi.setfVal(bi.getgVal() + bi.gethVal());
					openList.add(bi); // add to open list
				}
			}
			// Gets the closest (lowest f-value) blockinfo
			BlockInfo closest = getClose(openList);
			tmp = closest.getLocation(); // sets this location as new point
			closedList.add(closest); // push into closed list (so we don't consider again)
			openList.remove(closest); // Remove from openlist because you are there now

		}
		// Theoretically never reaches here
		return path;
	}
	/**
	 * 
	 * @param list of all blocks (usually open list)
	 * @return the closest block to go to (the lowest f value)
	 */
	public static BlockInfo getClose(ArrayList<BlockInfo> oo) {
		if (oo.size() == 1) { // only one adjacent square - go to that
			return oo.get(0);
		}
		int closest = 999999999;
		BlockInfo close = null;
		for (BlockInfo bi : oo) {
			if (bi.getfVal() < closest) { // finds first
				closest = bi.getfVal();
				close = bi;
			}
		}
		return close;
	}
	
	
}
