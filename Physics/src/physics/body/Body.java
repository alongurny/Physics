package physics.body;

import physics.math.Scalar;
import physics.math.Vector;
import physics.math.field.VectorField;

public interface Body {

	Vector getAcceleration();

	Scalar getCharge();

	VectorField getElectricalField();

	VectorField getGravitationalField();

	VectorField getMagneticField();

	Scalar getMass();

	Vector getMomentum();

	Vector getPosition();

	Vector getTotalForce();

	Vector getVelocity();

}
