package model;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import AStar.Path;
/**
 * Main interface of the A-star algorithm
 * Right Click to set a building
 * Left click to set waypoint
 * 
 * @author Timmy Miles
 * @version AStar Algorithm V1.0
 *
 */
public class Model extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1774841853735613153L;
	/**
	 * Main method to run
	 */
	public static void main(String[] args) {
		new Model(new Dimension(20, 20)); // makes 20 x 20 grid
	}
	// Dimension of the grid
	public Dimension dim;
	// Size of the grid
	public static int GRID_SIZE = 40;
	private View view; // view panel
	private Timer timer; // timer to show the movement
	private HashMap<Point, Block> blocks; // All the blocks to draws
	private ArrayList<Person> ppl; // all the actors (only one)
	/**
	 * Constructor to set up grid
	 * @param dimension of grid
	 */
	public Model(Dimension dim) {
		this.dim = dim; // sets the dimension
		// setting up initial grid
		this.blocks = new HashMap<Point, Block>();
		for (int i = 0; i < this.dim.height; i++) { // does up & down
			for (int j = 0; j < this.dim.width; j++) { // goes left -> right
				this.blocks.put(new Point(i, j), new Block(new Point(i, j)));
			}
		}
		
		/* Adds first actor (person) */
		this.ppl = new ArrayList<Person>();
		Person p = new Person(new Point(5, 5));
		this.ppl.add(p);
		
		/* View panel */
		this.view = new View();
		// set jpanel size
		this.view.setPreferredSize(new Dimension(dim.width * GRID_SIZE, dim.height * GRID_SIZE));
		this.setSize(dim.width * GRID_SIZE, dim.height * GRID_SIZE); 
		// sets size of jframe to 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(view);
		
		// 1/2 of a second
		timer = new Timer(500, this);
		timer.start();
		
		this.setVisible(true);
		// Mouse listener for interactiveness
		view.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// setting waypoint
				if (SwingUtilities.isLeftMouseButton(e)) {
					Point p = new Point(e.getX() / GRID_SIZE, e.getY()
							/ GRID_SIZE);
					System.out.println("Set waypoint: " + p);
					ppl.get(0).setDest(p); // sets the citizen's wdestination

				}
				// placing buidlding
				if (SwingUtilities.isRightMouseButton(e)) {
					System.out.println("Putting building!");
					// puts in new building
					Point p = new Point(e.getX() / GRID_SIZE, e.getY()
							/ GRID_SIZE);
					Block b = blocks.get(p);
					b.addBuilding(new Building(p));
					blocks.put(p, b);
					repaint();
				}
			}
		});
	}
	/**
	 * View panel
	 * @author Timmy Miles
	 * @version AStar Algorithm v1.0
	 */
	private class View extends JPanel {
		private static final long serialVersionUID = 6477103506638801178L;
		/**
		 * Constructor
		 */
		public View() {
			this.setVisible(true);
		}
		/**
		 * Drawing everything
		 * @param graphics
		 */
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;

			// draws all the blocks
			for (int i = 0; i < dim.height; i++) {
				for (int j = 0; j < dim.width; j++) {
					blocks.get(new Point(i, j)).draw(g2);
				}
			}
			// Colors all the grid lines
			g2.setColor(Color.blue);
			for (int i = GRID_SIZE; i < dim.width * GRID_SIZE; i += GRID_SIZE) {
				g2.drawLine(i, 0, i, this.getHeight());
			}
			for (int i = GRID_SIZE; i < dim.height * GRID_SIZE; i += GRID_SIZE) {
				g2.drawLine(0, i, this.getWidth(), i);
			}
			// Draws the people in red
			g2.setColor(Color.red);
			for (Person p : ppl) {
				p.draw(g2); // calls the people to draw themselves
			}
		}
	}
	/**
	 * Gets the shortest path
	 * AStar part
	 * @param origin location
	 * @param destination
	 * @return a path structure of the points including o and dest
	 * 			return null if there isn't a path possible
	 */
	public static Path getDeterminedPath(HashMap<Point, Block> blockGrid, Dimension dim, Point o, Point dest) {
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
			ArrayList<BlockInfo> blocks = Model.getEmptyAdjacent(blockGrid, dim, tmp, parent);
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

	private Path path = null;
	/**
	 * Action performed to animate (show movement)
	 * @param the action event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (ppl.get(0).getMoving()) { // check if moving
			if (path == null) {
				// Determine the path
				path = Model.getDeterminedPath(this.blocks, this.dim, ppl.get(0).getLocation(), ppl
						.get(0).getDest());
			} else {
				Point move = path.next(); // get next point
				if (move == null) { // no more paths
					path = null; // sets the path back to null
					ppl.get(0).setMoving(false); // stop moving
					System.exit(1); // exits once done
				} else {
					ppl.get(0).move(move);
					repaint();
				}
			}
		}
	}

	/**
	 * Static method
	 * @param blocks to check if its clear
	 * @param dimension (to check blocks on the grid or not
	 * @param point to check adjacent
	 * @param parent of this point (to set the new bock info)
	 * @return an arraylist of all the blockinfo's that are clear 1 block NESW of you (no diagonals)
	 */
	public static ArrayList<BlockInfo> getEmptyAdjacent(HashMap<Point, Block> blocks, Dimension dim, Point p, BlockInfo parent) {
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

}
