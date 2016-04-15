package physics.body.space;

import physics.math.Scalar;
import physics.math.Vector;

public class Earth extends Planet {

	public static final Scalar MASS = Scalar.KILOGRAM.multiply(5.974e24);
	public static final Scalar RADIUS = Scalar.METER.multiply(6.38e6);
	public static final Scalar ROUND_RADIUS = Scalar.METER.multiply(149.6e9);

	public Earth(Vector center, Vector velocity) {
		super(MASS, center, velocity, RADIUS);
	}

}
