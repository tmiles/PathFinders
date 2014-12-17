package AStar;
import java.awt.Point;
import java.util.ArrayDeque;

/**
 * A defined structure for accessing the points required to traverse from point
 * a to b
 * 
 * @author Timmy Miles
 * @version AStar Algorithm V1.0
 *
 */
public class Path {
	// Underlying structure for points needed to traverse
	private ArrayDeque<Point> paths;

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

}
