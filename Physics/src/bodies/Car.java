package bodies;

import physics.Quantity;
import physics.RigidBody;
import physics.Scalar;
import physics.Vector;

public class Car extends RigidBody {

	public Car(Scalar mass, Vector center, Scalar inertiaMoment,
			Vector angularPosition) {
		super(mass, Scalar.zero(Quantity.CHARGE), center, Vector
				.zero(Quantity.VELOCITY), inertiaMoment, angularPosition,
				Vector.zero(Quantity.ANGULAR_VELOCITY));
	}

}
