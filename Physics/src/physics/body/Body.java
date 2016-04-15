package physics.body;

import physics.math.Scalar;
import physics.math.Vector;
import physics.math.field.VectorField;

public interface Body {

	Vector getPosition();

	Vector getVelocity();

	Vector getAcceleration();

	Scalar getMass();

	Scalar getCharge();

	Vector getMomentum();

	Vector getTotalForce();

	VectorField getGravitationalField();

	VectorField getElectricalField();

	VectorField getMagneticField();

}
