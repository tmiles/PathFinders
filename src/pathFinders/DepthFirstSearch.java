package pathFinders;

import java.awt.*;
import java.io.*;
import java.util.*;

import model.Block;

/**
 * Basic depth first search for a path
 * 
 * @author Timmy Miles
 * @version PathFinders V1.0
 */
public class DepthFirstSearch {
	public static Path determinePath(HashMap<Point, Block> blockGrid,
			Dimension dim, Point o, Point dest) {
		try {
			PrintWriter pw = new PrintWriter("DFSOutput.out");
			Path path = new Path();
			ArrayDeque<Vertex> deque = new ArrayDeque<Vertex>();
			ArrayList<Vertex> temporaryArray = new ArrayList<Vertex>();
			Point tmp = o;
			deque.push(new Vertex(tmp, null, dest, false));
			Vertex tmpVertex;
			while (!deque.isEmpty()) {
				tmpVertex = deque.pop();
				pw.write("Checking: " + tmpVertex.getLocation());
				if (!tmpVertex.getDiscovered()) {
					tmpVertex.setDiscovered(true);
					ArrayList<Point> points = Path.getEmptyAdjacent(blockGrid,
							dim, tmp);
					if (points.contains(dest)) {
						path.addPoint(dest); // adds that destination
						while (tmpVertex.getParent() != null) {
							path.addPoint(tmpVertex.getLocation());
							tmpVertex = tmpVertex.getParent();
						}
						pw.write("Found path:\n" + path);
						// System.out.println("Found path:\n" + path);
						pw.close();
						return path;
					}
					for (Point p : points) {
						temporaryArray
								.add(new Vertex(p, tmpVertex, dest, false));
					}
					// has gone through all adjacent (now add to stack in right
					// order)
					temporaryArray.remove(tmpVertex); // don't add the current
														// one again
					Collections.sort(temporaryArray);
					pw.write("TA: \n " + temporaryArray);
					while (!temporaryArray.isEmpty()) {

						deque.push(temporaryArray.get(temporaryArray.size() - 1)); // pushes
						// last
						// element
						temporaryArray.remove(temporaryArray.size() - 1); // deletes
						// from
						// list
					}
				}
			}
			return path;
		} catch (FileNotFoundException e) {
			System.out.println("Unable find file");
			System.exit(1);
		}

		return null;
	}
}
