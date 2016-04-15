package physics.interfaces;

import physics.Scalar;
import physics.Vector;
import physics.VectorField;

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
