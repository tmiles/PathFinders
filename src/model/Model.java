package model;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

import pathFinders.*;

/**
 * Main interface of the A-star algorithm Right Click to set a building Left
 * click to set waypoint
 * 
 * @author Timmy Miles
 * @version PathFinders V1.0
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
	 * 
	 * @param dimension
	 *            of grid
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
		this.view.setPreferredSize(new Dimension(dim.width * GRID_SIZE,
				dim.height * GRID_SIZE));
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
					ppl.get(0).setDest(p); // sets the citizen's destination

				}
				// placing building
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
	 * 
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
		 * 
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

	private Path path = null;

	/**
	 * Action performed to animate (show movement)
	 * 
	 * @param the
	 *            action event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (ppl.get(0).getMoving()) { // check if moving
			if (path == null) {
				// Determine the path
				path = AStar.determinePath(this.blocks, this.dim, ppl.get(0)
						.getLocation(), ppl.get(0).getDest());
				// path = DepthFirstSearch.determinePath(this.blocks, dim,
				// ppl.get(0).getLocation(), ppl.get(0).getDest());
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

}
