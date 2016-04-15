package physics.body.space;

import physics.math.Scalar;
import physics.math.Vector;

public class Sun extends Planet {

	public static final Scalar MASS = Scalar.KILOGRAM.multiply(1.99e30);
	public static final Scalar RADIUS = Scalar.METER.multiply(6.96e8);

	public Sun(Vector center, Vector velocity) {
		super(MASS, center, velocity, RADIUS);
	}

}
