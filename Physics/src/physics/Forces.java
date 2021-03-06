package physics;

import physics.body.Body;
import physics.math.Scalar;
import physics.math.Vector;
import physics.quantity.Quantity;

public class Forces {

	public static Vector getFriction(Body a, Scalar gravity, double co, Scalar threshold) {
		if (a.getVelocity().getMagnitude().compareTo(threshold) <= 0) {
			return Vector.zero(Quantity.FORCE, a.getPosition().getDimension());
		}
		return a.getVelocity().getDirection().multiply(1).multiply(gravity).multiply(a.getMass().multiply(-co));
	}

	public static Vector getGravity(Body a, Body b) {
		return b.getGravitationalField().get(a.getPosition()).multiply(a.getMass());
	}

	public static Vector getLorentzForce(Body a, Body b) {
		return a.getCharge().multiply(Vector.sum(b.getElectricalField().get(a.getPosition()),
				a.getVelocity().cross(b.getMagneticField().get(a.getPosition()))));
	}

}
