package physics.body;

import physics.math.Scalar;
import physics.math.Vector;

public interface Movable extends Body {

	void addForce(Vector force);

	void addImpulse(Vector impulse, Scalar dt);

	void move(Scalar dt);

}
