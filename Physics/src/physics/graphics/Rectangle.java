package physics.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import graphics.Pixel;
import graphics.drawers.DrawableBody;
import physics.body.RegularBody;
import physics.math.IntVector;
import physics.math.Scalar;
import physics.math.Vector;
import physics.quantity.Quantity;

public class Rectangle extends RegularBody implements DrawableBody {

	private Color color;
	private Scalar width;
	private Scalar height;

	public Rectangle(Scalar mass, Vector center, Vector velocity, Scalar width, Scalar height, Color color) {
		super(mass, Scalar.zero(Quantity.CHARGE), center, velocity);
		this.width = width;
		this.height = height;
		this.color = color;
	}

	@Override
	public void draw(Graphics g, Scalar pixel) {
		IntVector i_p = Pixel.convert(getPosition(), pixel);
		IntVector i_d = Pixel.convert(getDimensions(), pixel);
		g.setColor(color);
		g.fillRect(i_p.get(0) - i_d.get(0) / 2, i_p.get(1) - i_d.get(1) / 2, i_d.get(0), i_d.get(1));
	}

	public Vector getDimensions() {
		return new Vector(width, height);
	}

	@Override
	public Polygon getBounds(Scalar pixel) {
		IntVector i_p = Pixel.convert(getPosition(), pixel);
		IntVector i_d = Pixel.convert(getDimensions(), pixel);
		return new Polygon(
				new int[] { i_p.get(0) - i_d.get(0) / 2, i_p.get(0) - i_d.get(0) / 2, i_p.get(0) + i_d.get(0) / 2,
						i_p.get(0) + i_d.get(0) / 2 },
				new int[] { i_p.get(1) - i_d.get(1) / 2, i_p.get(1) + i_d.get(1) / 2, i_p.get(1) + i_d.get(1) / 2,
						i_p.get(1) - i_d.get(1) / 2 },
				4);
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
	public Vector getCircleCenter(Vector contactPoint) {
		contactPoint = Vector.extend(contactPoint, getPosition().getDimension());
		Vector d = contactPoint.subtract(getPosition());
		double phase = Scalar.atan2(d.get(1), d.get(0));
		double angle = Scalar.atan2(height, width);
		contactPoint = Vector.extend(contactPoint, 2);
		if (Math.abs(phase) < angle) {
			contactPoint = contactPoint.add(new Vector(-0.5, 0).multiply(width));
		} else if (Math.abs(phase) > Math.PI - angle) {
			contactPoint = contactPoint.add(new Vector(0.5, 0).multiply(width));
		} else if (angle < phase && phase < Math.PI - angle) {
			contactPoint = contactPoint.add(new Vector(0, 0.5).multiply(height));
		} else if (-Math.PI + angle < phase && phase < -angle) {
			contactPoint = contactPoint.add(new Vector(0, -0.5).multiply(height));
		}
		return Vector.extend(contactPoint, getPosition().getDimension());

	}

}