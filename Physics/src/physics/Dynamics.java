package physics;

import physics.body.Body;
import physics.math.Scalar;
import physics.math.Vector;

public class Dynamics {

	public static Scalar getOrbitVelocity(Body b, Scalar radius) {
		return Scalar.sqrt(b.getGravitationalField().get(b.getPosition().add(new Vector(radius, 1, 0, 0)))
				.getMagnitude().multiply(radius));
	}
}
