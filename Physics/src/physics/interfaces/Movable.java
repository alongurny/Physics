package physics.interfaces;

import physics.Vector;

public interface Movable extends Body {

	void move();

	void addForce(Vector force);

	void addImpulse(Vector impulse);

}
