import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * The actors (movers) on the path
 * 
 * @author Timmy Miles
 * @version AStar Algorithm V1.0
 *
 **/
public class Person {
	// Location of the citizen
	private Point location;
	// Intended location
	private Point dest;
	// if moving or not
	private boolean moving;
	/**
	 * Constructor
	 * @param location of citizen
	 */
	public Person(Point p) {
		this.location = p;
	}
	/**
	 * Sets the destination
	 * @param p
	 */
	public void setDest(Point p){
		this.dest = p;
		// says in moving already
		this.moving = true; 
	}
	/**
	 * Moves to the next location
	 * Teleports
	 * @param point to mvoe to
	 */
	public void move(Point p){
		this.location = p;
	}
	/**
	 * Gets the destination
	 * @return the destination location
	 */
	public Point getDest(){
		return this.dest;
	}
	/**
	 * Gets the current location
	 * @return current location
	 */
	public Point getLocation(){
		return this.location;
	}
	/**
	 * Gets if moving or not
	 * @return if moving or not
	 */
	public boolean getMoving(){
		return this.moving;
	}
	/**
	 * Lets you set if you are moving or not
	 * @param boolean if moving or not
	 */
	public void setMoving(boolean b){
		this.moving = b;
	}
	/**
	 * How to draw a citizen
	 * @param graphics
	 */
	public void draw(Graphics2D g) {
		g.setColor(Color.red);
		g.fillRect(location.x * Model.GRID_SIZE, location.y * Model.GRID_SIZE,
				Model.GRID_SIZE, Model.GRID_SIZE);
	}
}
