package carThing;

import physics.Quantity;
import physics.RigidBody;
import physics.Scalar;
import physics.Vector;

public class Car extends RigidBody {

	public Car(Scalar mass, Vector center, Vector velocity, Scalar inertiaMoment) {
		super(mass, Scalar.zero(Quantity.CHARGE), center, Vector
				.zero(Quantity.VELOCITY), inertiaMoment, Vector
				.zero(Quantity.ANGLE), Vector.zero(Quantity.ANGULAR_VELOCITY));
	}

}
