package physics.body;

import physics.math.Scalar;
import physics.math.Vector;

public interface RigidBody {

	Scalar getMomentOfInertia();

	Vector getAngularPosition();

	Vector getAngularVelocity();

}
