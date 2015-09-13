package bodies.space;

import physics.Scalar;
import physics.Vector;

public class Sun extends Planet {

	public static final Scalar MASS = Scalar.KILOGRAM.multiply(1.99e30);
	public static final Scalar RADIUS = Scalar.METER.multiply(6.96e8);

	public Sun(Vector center, Vector velocity, Vector angularPosition,
			Vector angularVelocity) {
		super(MASS, center, velocity, angularPosition, angularVelocity, RADIUS);
	}

}
