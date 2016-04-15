package graphics.drawers;

import java.awt.Color;
import java.awt.Graphics;

import physics.body.geometric.Sphere;
import physics.graphics.Pixel;
import physics.math.IntVector;
import physics.math.Scalar;
import physics.math.Vector;

public class SphereDrawer implements Drawable {

	private Sphere sphere;
	private Color color;

	public SphereDrawer(Sphere sphere, Color color) {
		this.sphere = sphere;
		this.color = color;
	}

	@Override
	public void draw(Graphics g, Scalar pixel) {
		g.setColor(color);
		Scalar radius = sphere.getRadius();
		Vector p = sphere.getPosition().subtract(new Vector(radius, radius, radius));
		IntVector i_p = Pixel.convert(p, pixel);
		int i_radius = Pixel.convert(radius, pixel);
		g.fillOval(i_p.get(0), i_p.get(1), Math.max(3, 2 * i_radius), Math.max(3, 2 * i_radius));
	}

}
