package physics.body;

import physics.math.Scalar;
import physics.math.Vector;

public interface RigidBody {

	Vector getAngularPosition();

	Vector getAngularVelocity();

	Scalar getMomentOfInertia();

}
