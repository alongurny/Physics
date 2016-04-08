package graphics.drawers;

import java.awt.Color;
import java.awt.Graphics;

import bodies.Sphere;
import graphics.Pixel;
import physics.Scalar;
import physics.Vector;

public class SphereDrawer implements Drawable {

	private Sphere sphere;
	private Color color;

	public SphereDrawer(Sphere sphere, Color color) {
		this.sphere = sphere;
		this.color = color;
	}

	@Override
	public void draw(Graphics g, int dx, int dy) {
		g.setColor(color);
		Scalar radius = sphere.getRadius();

		Vector p = sphere.getPosition().subtract(new Vector(radius, radius, radius));
		int x = Pixel.to(p.get(0));
		int y = Pixel.to(p.get(1));
		int i_radius = Pixel.to(radius);
		g.fillOval(x + dx, y + dy, Math.max(3, 2 * i_radius), Math.max(3, 2 * i_radius));
	}

}
