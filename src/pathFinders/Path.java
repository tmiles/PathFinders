package pathFinders;

import java.awt.*;
import java.util.*;

import model.*;

/**
 * A defined structure for accessing the points required to traverse from point
 * a to b
 * 
 * @author Timmy Miles
 * @version PathFinders V1.0
 *
 */
public class Path {
	// Underlying structure for points needed to traverse
	private ArrayDeque<Point> paths;
	private long computationTime;

	/* Default constructor */
	public Path() {
		this.paths = new ArrayDeque<Point>();
	}

	/**
	 * Getter method for the path
	 * 
	 * @deprecated only for copying
	 * @return
	 */
	public ArrayDeque<Point> getPaths() {
		return this.paths;
	}

	/**
	 * Gets the time of the algorithm run time
	 * 
	 * @return
	 */
	public long getComputationTime() {
		return this.computationTime;
	}

	/**
	 * Sets the computation time of the path
	 * 
	 * @param ct
	 */
	public void setComputationTime(long ct) {
		this.computationTime = ct;
	}

	/**
	 * Adds a point to the path Inserted at stack fashion because path algorithm
	 * 
	 * @param point
	 *            to add into array deque
	 */
	public void addPoint(Point p) {
		this.paths.addFirst(p); // add to end
	}

	/**
	 * Determines the next point to move to Goes in Queue fashion
	 * 
	 * @return the next point
	 */
	public Point next() {
		// Empty path means no more points
		if (this.paths.isEmpty()) {
			return null;
		}
		// Gets the next point
		Point tmp = paths.getFirst();
		this.paths.removeFirst();
		// removes from the arraydeque
		return tmp;
	}

	/**
	 * ToString print out of the path
	 * 
	 * @return string version of the path
	 */
	@Override
	public String toString() {
		String s = "";
		if (this.paths.isEmpty()) {
			return s;
		} else {
			for (Point p : paths) {
				s += "(" + p.x + "," + p.y + ") -> ";
			}
			// gets rid of last arrow symbol & space
			s = s.substring(0, s.length() - 3);
			return s;
		}

	}

	/*
	 * Clear block determination
	 */

	/**
	 * Static method
	 * 
	 * @param blocks
	 *            to check if its clear
	 * @param dimension
	 *            (to check blocks on the grid or not
	 * @param point
	 *            to check adjacent
	 * @param parent
	 *            of this point (to set the new bock info)
	 * @return an arraylist of all the blockinfo's that are clear 1 block NESW
	 *         of you (no diagonals)
	 */
	public static ArrayList<BlockInfo> getEmptyAdjacent(
			HashMap<Point, Block> blocks, Dimension dim, Point p,
			BlockInfo parent) {
		ArrayList<BlockInfo> bl = new ArrayList<BlockInfo>();
		Point tmp = new Point(p.x, p.y - 1); // north
		// System.out.println("tmp = " + tmp);
		if (blocks.get(tmp).isClear() && tmp.y > 0) {
			bl.add(new BlockInfo(tmp, parent));
		}
		tmp = new Point(p.x + 1, p.y); // east
		if (blocks.get(tmp).isClear() && (tmp.x < dim.width)) {
			bl.add(new BlockInfo(tmp, parent));
		}
		tmp = new Point(p.x, p.y + 1); // south
		if (blocks.get(tmp).isClear() && tmp.y < dim.height) {
			bl.add(new BlockInfo(tmp, parent));
		}
		tmp = new Point(p.x - 1, p.y); // west
		if (blocks.get(tmp).isClear() && tmp.x > 0) {
			bl.add(new BlockInfo(tmp, parent));
		}
		return bl;
	}

	/**
	 * Determines all the empty blocks on neary to block
	 * 
	 * @param blocks
	 * @param p
	 * @return
	 */
	public static ArrayList<Point> getEmptyAdjacent(
			HashMap<Point, Block> blocks, Dimension dim, Point p) {
		ArrayList<Point> bl = new ArrayList<Point>();
		Point tmp = new Point(p.x, p.y - 1); // north
		// System.out.println("tmp = " + tmp);
		if (blocks.get(tmp).isClear() && tmp.y > 0) {
			bl.add(tmp);
		}
		tmp = new Point(p.x + 1, p.y); // east
		if (blocks.get(tmp).isClear() && (tmp.x < dim.width)) {
			bl.add(tmp);
		}
		tmp = new Point(p.x, p.y + 1); // south
		if (blocks.get(tmp).isClear() && tmp.y < dim.height) {
			bl.add(tmp);
		}
		tmp = new Point(p.x - 1, p.y); // west
		if (blocks.get(tmp).isClear() && tmp.x > 0) {
			bl.add(tmp);
		}
		return bl;

	}

}
