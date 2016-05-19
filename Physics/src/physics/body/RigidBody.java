package physics.body;

import physics.math.Matrix;
import physics.math.Vector;

public interface RigidBody extends Rotatable {

	Vector getAngularPosition();

	Vector getAngularVelocity();

	Matrix getMomentOfInertia();

}
