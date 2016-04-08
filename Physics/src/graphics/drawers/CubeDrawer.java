package graphics.drawers;

import java.awt.Color;
import java.awt.Graphics;

import bodies.Cube;
import graphics.PixelHandler;
import physics.Scalar;
import physics.Vector;

public class CubeDrawer implements Drawable {

	private Cube cube;
	private Color color;
	private PixelHandler pixelHandler;

	public CubeDrawer(Cube sphere, Color color, PixelHandler pixelHandler) {
		this.cube = sphere;
		this.color = color;
		this.pixelHandler = pixelHandler;
	}

	@Override
	public void draw(Graphics g, int dx, int dy) {
		g.setColor(color);
		Scalar side = cube.getSide();
		Vector p = cube.getPosition().subtract(new Vector(side, side, side).divide(2));
		int x = pixelHandler.to(p.get(0));
		int y = pixelHandler.to(p.get(1));
		int i_side = pixelHandler.to(side);
		g.fillRect(x + dx, y + dy, Math.max(3, i_side), Math.max(3, i_side));
	}

}
