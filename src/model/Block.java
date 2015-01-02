package model;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Basic block implementation
 * 
 * @author Timmy Miles
 * @version PathFinders V1.0
 */
public class Block {
	private Point location;
	private Building building;
	
	/**
	 * Constructor 
	 * @param point of block
	 */
	public Block(Point p) {
		this.location = p;
		this.building = null;
	}

	/**
	 * Adds a building (obstacle) to the block
	 * @param b
	 * @return
	 */
	public boolean addBuilding(Building b) {
		this.building = b;
		return true;
	}
	
	/**
	 * Gets the location of the block
	 * @return
	 */
	public Point getLocation() {
		return this.location;
	}
	
	/**
	 * Draws a block
	 * @param graphics context
	 */
	public void draw(Graphics2D g) {		
		if (building != null) {
			building.draw(g);
		}
	}
	/**
	 * Determine if block can be passed
	 * Currently only requirement is no building
	 * @return if block can be walked on or not
	 */
	public boolean isClear() {
		return (building == null);
	}
}
