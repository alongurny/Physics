package physics.graphics.drawers;

import java.awt.Color;

import physics.body.geometric.Sphere;
import physics.graphics.PixelGraphics;
import physics.math.Scalar;

public class SphereDrawer implements PixelDrawable {

	private Sphere sphere;
	private Color color;

	public SphereDrawer(Sphere sphere, Color color) {
		this.sphere = sphere;
		this.color = color;
	}

	@Override
	public void draw(PixelGraphics g) {
		g.setColor(color);
		Scalar diameter = sphere.getRadius().multiply(2);
		g.fillOval(sphere.getPosition(), diameter, diameter);
	}

}
