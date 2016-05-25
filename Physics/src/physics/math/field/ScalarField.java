package physics.math.field;

import physics.math.Scalar;
import physics.math.Vector;
import physics.math.algebra.Field;

public interface ScalarField extends Field<ScalarField> {

	public static ScalarField constant(Scalar s) {
		return v -> s;
	}

	Scalar get(Vector v);

	@Override
	default ScalarField add(ScalarField other) {
		return v -> get(v).add(other.get(v));
	}

	@Override
	default ScalarField multiply(ScalarField other) {
		return v -> get(v).multiply(other.get(v));
	}

	@Override
	default ScalarField inverse() {
		return v -> get(v).inverse();
	}

	@Override
	default ScalarField negate() {
		return v -> get(v).negate();
	}

	@Override
	default ScalarField unit() {
		return v -> get(v).unit();
	}

	@Override
	default ScalarField zero() {
		return v -> get(v).zero();
	}
}
