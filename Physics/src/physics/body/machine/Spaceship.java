package physics.body.machine;

import physics.body.RegularRigidBody;
import physics.math.Matrix;
import physics.math.Scalar;
import physics.math.Vector;
import physics.quantity.Quantity;

public class Spaceship extends RegularRigidBody {

	private Container fuel;

	public Spaceship(Scalar mass, Vector center, Vector velocity, Matrix inertiaMoment, Vector angularPosition,
			Vector angularVelocity, Container fuel) {
		super(mass, Scalar.zero(Quantity.CHARGE), center, velocity, inertiaMoment, angularPosition, angularVelocity);
		this.fuel = fuel;
	}

	public void boostForward(Scalar value) {
		Scalar pull = fuel.pull(value);
		Scalar v0 = getVelocity().getMagnitude();
		Scalar v = Scalar.sqrt(pull.multiply(2).divide(getMass()).add(v0.pow(2)));
		addImpulse(getAngularPosition().multiply(v).subtract(getVelocity()).multiply(getMass()));
	}

	public Container getFuel() {
		return fuel;
	}

}
