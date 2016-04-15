package physics.math.field;

import physics.math.Scalar;
import physics.math.Vector;

public interface ScalarField {
	Scalar get(Vector v);
}
