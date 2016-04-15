package graphics.drawers;

import java.awt.Color;
import java.awt.Graphics;

import graphics.Pixel;
import physics.body.geometric.Cube;
import physics.math.IntVector;
import physics.math.Scalar;
import physics.math.Vector;

public class CubeDrawer implements Drawable {

	private Cube cube;
	private Color color;

	public CubeDrawer(Cube sphere, Color color) {
		this.cube = sphere;
		this.color = color;
	}

	@Override
	public void draw(Graphics g, Scalar pixel) {
		g.setColor(color);
		Scalar side = cube.getSide();
		Vector p = cube.getPosition().subtract(new Vector(side, side, side).divide(2));
		IntVector i_p = Pixel.convert(p, pixel);
		int i_side = Pixel.convert(side, pixel);
		g.fillRect(i_p.get(0), i_p.get(1), Math.max(3, i_side), Math.max(3, i_side));
	}

}
