package physics.body;

import physics.math.Scalar;

public interface Rotatable extends Body {

	void rotate(Scalar dt);

}
