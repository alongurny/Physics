package physics.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import physics.body.RegularBody;
import physics.graphics.drawers.DrawableBody;
import physics.math.IntVector;
import physics.math.Scalar;
import physics.math.Vector;

public class Circle extends RegularBody implements DrawableBody {

	private static final int ACCURACY = 16;

	private Scalar radius;
	private Color color;

	public Circle(Scalar mass, Scalar charge, Vector center, Vector velocity, Scalar radius, Color color) {
		super(mass, charge, center, velocity);
		this.radius = radius;
		this.color = color;
	}

	@Override
	public void draw(Graphics g, Scalar pixel) {
		int i_diameter = Pixel.convert(radius.multiply(2), pixel);
		IntVector i_p = Pixel.convert(getPosition(), pixel);
		g.setColor(color);
		g.fillOval(i_p.get(0) - i_diameter / 2, i_p.get(1) - i_diameter / 2, i_diameter, i_diameter);
		g.setColor(Color.BLACK);
		g.drawPolygon(getBounds(pixel));
	}

	@Override
	public Polygon getBounds(Scalar pixel) {
		int[] x = new int[ACCURACY];
		int[] y = new int[ACCURACY];
		for (int i = 0; i < ACCURACY; i++) {
			Vector v = Vector.extend(Vector.fromPolar(radius, (2 * Math.PI * i) / ACCURACY), getPosition().getDimension());
			IntVector iv = Pixel.convert(getPosition().add(v), pixel);
			x[i] = iv.get(0);
			y[i] = iv.get(1);
		}
		return new Polygon(x, y, ACCURACY);

	}

	@Override
	public IntVector getPosition(Scalar pixel) {
		return Pixel.convert(getPosition(), pixel);
	}

	@Override
	public IntVector getVelocity(Scalar pixel) {
		return Pixel.convert(getVelocity().multiply(RegularBody.DEFAULT_TIME_SPAN), pixel);
	}

	public Color getColor() {
		return color;
	}

	@Override
	public Vector getCollisionCircleCenter(Vector contactPoint) {
		return getPosition();
	}

	public Scalar getRadius() {
		return radius;
	}

}
