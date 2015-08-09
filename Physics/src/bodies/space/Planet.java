package bodies.space;

import physics.Quantity;
import physics.Scalar;
import physics.Vector;
import bodies.Sphere;

public class Planet extends Sphere {

	public Planet(Scalar mass, Vector center, Vector velocity,
			Vector angularPosition, Vector angularVelocity, Scalar radius) {
		super(mass, Scalar.zero(Quantity.CHARGE), center, velocity,
				angularPosition, angularVelocity, radius);
	}
}
