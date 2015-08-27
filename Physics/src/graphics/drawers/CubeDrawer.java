package graphics.drawers;

import graphics.Pixel;

import java.awt.Color;
import java.awt.Graphics;

import physics.Scalar;
import physics.Vector;
import bodies.Cube;

public class CubeDrawer implements Drawable {

	private Cube cube;
	private Color color;

	public CubeDrawer(Cube sphere, Color color) {
		this.cube = sphere;
		this.color = color;
	}

	@Override
	public void draw(Graphics g, int dx, int dy) {
		g.setColor(color);
		Scalar side = cube.getSide();
		Vector p = cube.getPosition().subtract(
				new Vector(side, side, side).divide(2));
		int x = Pixel.to(p.getX());
		int y = Pixel.to(p.getY());
		int i_side = Pixel.to(side);
		g.fillRect(x + dx, y + dy, Math.max(3, i_side), Math.max(3, i_side));
	}

}
