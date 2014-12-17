package model;
import java.awt.Graphics2D;
import java.awt.Point;

public class Block {
	private Point location;
	private Building building;

	public Block(Point p) {
		this.location = p;
		this.building = null;
	}

	public boolean addBuilding(Building b) {

		this.building = b;
		return true;
	}

	public Point getLocation() {
		return this.location;
	}

	public void draw(Graphics2D g) {
		if (building != null) {
			// only draws one part of it
			building.draw(g);
		}
	}

	public boolean isClear() {
		return (building == null);
	}
}
