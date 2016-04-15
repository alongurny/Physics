package bodies;

import physics.RigidBody;
import physics.Scalar;
import physics.Vector;

public class Sphere extends RigidBody {

	private Scalar radius;

	public Sphere(Scalar mass, Scalar charge, Vector center, Vector velocity, Vector angularPosition,
			Vector angularVelocity, Scalar radius) {
		super(mass, charge, center, velocity, mass.multiply(radius.pow(2)).multiply(0.4).divide(Scalar.RADIAN.pow(2)),
				angularPosition, angularVelocity);
		this.radius = radius;
	}

	public Scalar getRadius() {
		return radius;
	}

	public Scalar getVolume() {
		return radius.pow(3).multiply(Math.PI * 4.0 / 3.0);
	}

}
