package pool.bodies;

import java.awt.Color;
import java.awt.Graphics;

import physics.body.RegularBody;
import physics.graphics.Pixel;
import physics.graphics.drawers.Drawable;
import physics.math.IntVector;
import physics.math.Scalar;
import physics.math.Vector;
import physics.quantity.Quantity;
import physics.quantity.UnitSystem;

public class Stick extends RegularBody implements Drawable {

	private static final Scalar density = UnitSystem.SI.get(Quantity.MASS.divide(Quantity.LENGTH), 0.02);

	private Vector length;

	public Stick(Vector start, Vector length) {
		super(density.multiply(length.getMagnitude()), Scalar.zero(Quantity.CHARGE), start,
				Vector.zero(Quantity.VELOCITY, length.getDimension()));
		this.length = length;
	}

	@Override
	public void draw(Graphics g, Scalar pixel) {
		g.setColor(new Color(100, 135, 0));
		IntVector i_start = Pixel.convert(getPosition(), pixel);
		IntVector i_end = Pixel.convert(getPosition().add(length), pixel);
		g.drawLine(i_start.get(0), i_start.get(1), i_end.get(0), i_end.get(1));
	}

}
