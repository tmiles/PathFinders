package pathFinders;

import java.awt.Point;

/**
 * Vertex in a graph
 * @author Timmy Miles
 * @version PathFinders V1.0
 *
 */
public class Vertex implements Comparable<Vertex> {
	private Point location, compare;
	private Vertex parent;
	private boolean discovered;

	Vertex(Point p, Vertex par, Point compare, boolean d) {
		this.location = p;
		this.parent = par;
		this.compare = compare;
		this.discovered = d;
	}

	@Override
	public int compareTo(Vertex arg0) {
		double dist1 = this.location.distance(compare.x, compare.y);
		double dist2 = arg0.location.distance(compare.x, compare.y);
		if (dist1 == dist2) {
			return 0;
		} else if (dist1 < dist2) {
			return -1;
		}
		return 1;
	}

	/* Getters */
	public Point getLocation() {
		return this.location;
	}

	public Vertex getParent() {
		return this.parent;
	}

	public boolean getDiscovered() {
		return this.discovered;
	}

	/* Setters */

	public void setDiscovered(boolean d) {
		this.discovered = d;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString(){
		return "" + this.parent.getLocation() + " -> " + this.location + " -> " + this.discovered;
	}

}