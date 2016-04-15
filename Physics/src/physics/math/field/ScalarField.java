package physics.math.field;

import physics.math.Scalar;
import physics.math.Vector;

public interface ScalarField {

	public static ScalarField constant(Scalar s) {
		return v -> s;
	}

	public static ScalarField sum(ScalarField a, ScalarField b) {
		return v -> Scalar.sum(a.get(v), b.get(v));
	}

	Scalar get(Vector v);
}
