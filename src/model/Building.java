package model;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Main obstacle (building) you need to walk around
 * 
 * @author Timmy Miles
 * @version PathFinders V1.0
 *
 */
public class Building {
	// Location of point
	private Point location;
	/**
	 * Default constructor
	 * @param block point
	 */
	public Building(Point p){
		this.location = p;
	}
	/**
	 * Getter method of the building's location
	 * @return the building's location
	 */
	public Point getLocation(){
		return this.location;
	}
	/**
	 * Draws a blue box to represent building 
	 * @param graphics
	 */
	public void draw(Graphics2D g){
		g.setColor(Color.BLUE);
		g.fillRect(location.x * Model.GRID_SIZE, location.y * Model.GRID_SIZE,
				Model.GRID_SIZE, Model.GRID_SIZE);
	}

}
