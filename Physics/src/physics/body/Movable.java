package physics.body;

import physics.math.Vector;

public interface Movable extends Body {

	void move();

	void addForce(Vector force);

	void addImpulse(Vector impulse);

}
