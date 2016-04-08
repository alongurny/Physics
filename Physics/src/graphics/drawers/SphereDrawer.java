package graphics.drawers;

import java.awt.Color;
import java.awt.Graphics;

import bodies.Sphere;
import graphics.PixelHandler;
import physics.Scalar;
import physics.Vector;

public class SphereDrawer implements Drawable {

	private Sphere sphere;
	private Color color;
	private PixelHandler pixelHandler;

	public SphereDrawer(Sphere sphere, Color color, PixelHandler pixelHandler) {
		this.sphere = sphere;
		this.color = color;
		this.pixelHandler = pixelHandler;
	}

	@Override
	public void draw(Graphics g, int dx, int dy) {
		g.setColor(color);
		Scalar radius = sphere.getRadius();

		Vector p = sphere.getPosition().subtract(new Vector(radius, radius, radius));
		int x = pixelHandler.to(p.get(0));
		int y = pixelHandler.to(p.get(1));
		int i_radius = pixelHandler.to(radius);
		g.fillOval(x + dx, y + dy, Math.max(3, 2 * i_radius), Math.max(3, 2 * i_radius));
	}

}
