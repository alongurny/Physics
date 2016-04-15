package physics.body.geometric;

import physics.body.RigidBody;
import physics.math.Scalar;
import physics.math.Vector;

public class Cube extends RigidBody {

	private Scalar s;

	public Cube(Scalar mass, Scalar charge, Vector center, Vector velocity,
			Vector angularPosition, Vector angularVelocity, Scalar s) {
		super(mass, charge, center, velocity, mass.multiply(s.pow(2))
				.multiply(1.0 / 6).divide(Scalar.RADIAN.pow(2)),
				angularPosition, angularVelocity);
		this.s = s;
	}

	public Scalar getVolume() {
		return s.pow(3);
	}

	public Scalar getSide() {
		return s;
	}

}
