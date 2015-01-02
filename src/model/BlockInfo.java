package model;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * A defined structure for accessing the values assigned to each block in A-star
 * algorithm analysis
 * 
 * @author Timmy Miles
 * @version PathFinders V1.0
 *
 */
public class BlockInfo {
	private Point point; // location of block
	/*
	 * G value is the cost from origin to block
	 * H value is the heuristic (manhatten distance)
	 * F value is the sum of the g and h value (minimize this)
	 */
	private int gVal, hVal, fVal; 
	// A reference to the parent block
	// Used to traverse backwards once you reach the last block
	private BlockInfo parent;
	/**
	 * Constructor 
	 * @param block's location
	 * @param parent blockInfo
	 */
	public BlockInfo(Point p, BlockInfo parent) {
		this.point = p;
		this.parent = parent;
		this.gVal = this.hVal = this.fVal = 0;
	}
	/**
	 * Gets the location of the block
	 * @return block's location
	 */
	public Point getLocation() {
		return this.point;
	}
	/**
	 * Gets the parent blockinfo
	 * @return parent block info
	 */
	public BlockInfo getParent() {
		return this.parent;
	}
	/**
	 * Sets the parent 
	 * @deprecated testing purposes
	 * @param parent block info
	 */
	public void setParent(BlockInfo p) {
		this.parent = p;
	}
	/**
	 * Getter method for gVal
	 * @return g value
	 */
	public int getgVal() {
		return gVal;
	}
	/**
	 * Setter method for g value
	 * @param g value
	 */
	public void setgVal(int gVal) {
		this.gVal = gVal;
	}
	/**
	 * getter method of h value
	 * @return h value
	 */
	public int gethVal() {
		return hVal;
	}
	/**
	 * Setter method for h value
	 * @param h value
	 */
	public void sethVal(int hVal) {
		this.hVal = hVal;
	}
	/**
	 * Getter method for fvalue
	 * @return f value
	 */
	public int getfVal() {
		return fVal;
	}
	/**
	 * Setter methof for the f value
	 * @param fVal
	 */
	public void setfVal(int fVal) {
		this.fVal = fVal;
	}
	/**
	 * Calculates the manhatten distance
	 * |x_i - x_0|
	 * @param first point
	 * @param second point
	 * @return the sum of the absolute differences for x & y axis
	 */
	public static int calculateMahatten(Point p, Point p1) {
		return Math.abs(p1.x - p.x) + Math.abs(p1.y - p.y);
	}

	/**
	 * @deprecated - used for testing
	 * @param graphics
	 * @param location to draw
	 */
	public void draw(Graphics2D g) {
		if (hVal == 0 && fVal == 0 && gVal == 0) {
			g.setColor(Color.white);
		} else {
			g.setColor(Color.black);
		}
		/*
		 * Top left -> h stat
		 * Top right -> g stat
		 * Bottom right -> f stat
		 */
		g.drawString("" + hVal, (this.point.x) * Model.GRID_SIZE + 4, (this.point.y)
				* Model.GRID_SIZE + 12); // h stat - top left

		g.drawString("" + gVal, (this.point.x + 1) * Model.GRID_SIZE - 15, (this.point.y)
				* Model.GRID_SIZE + 12); // g stat - top right

		g.drawString("" + fVal, (this.point.x + 1) * Model.GRID_SIZE - 15, (this.point.y + 1)
				* Model.GRID_SIZE - 2); // f stat - bottom right

	}
	/**
	 * ToString method of the bock info
	 * @return string representation of the block info
	 */
	@Override
	public String toString() {
		return point + "\n(" + fVal + "," + gVal + "," + hVal + ")\n" + parent;
	}
}
