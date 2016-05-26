package pool.bodies;

import java.awt.Color;

import physics.body.AbstractBody;
import physics.graphics.PixelGraphics;
import physics.graphics.drawers.PixelDrawable;
import physics.math.Scalar;
import physics.math.Vector;
import physics.quantity.Quantity;
import physics.quantity.UnitSystem;

public class Stick extends AbstractBody implements PixelDrawable {

	private static final Scalar density = UnitSystem.SI.get(Quantity.MASS.divide(Quantity.LENGTH), 0.02);

	private Vector length;

	public Stick(Vector start, Vector length) {
		super(density.multiply(length.getMagnitude()), Scalar.zero(Quantity.CHARGE), start,
				Vector.zero(Quantity.VELOCITY, length.getDimension()));
		this.length = length;
	}

	@Override
	public void draw(PixelGraphics g) {
		g.setColor(new Color(100, 135, 0));
		g.drawLine(getPosition(), getPosition().add(length));
	}

}
