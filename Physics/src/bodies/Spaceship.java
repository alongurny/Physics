package bodies;

import physics.Quantity;
import physics.RigidBody;
import physics.Scalar;
import physics.Vector;

public class Spaceship extends RigidBody {

	private Container fuel;

	public Spaceship(Scalar mass, Vector center, Vector velocity,
			Scalar inertiaMoment, Vector angularPosition,
			Vector angularVelocity, Container fuel) {
		super(mass, Scalar.zero(Quantity.CHARGE), center, velocity,
				inertiaMoment, angularPosition, angularVelocity);
		this.fuel = fuel;
	}

	public void boostForward(Scalar value, Scalar dt) {
		Scalar pull = fuel.pull(value);
		Scalar v0 = getVelocity().getMagnitude();
		Scalar v = Scalar.sqrt(pull.multiply(2).divide(getMass())
				.add(v0.pow(2)));
		addImpulse(getAngularPosition().multiply(v).subtract(getVelocity())
				.multiply(getMass()));
	}

	public Container getFuel() {
		return fuel;
	}

}
