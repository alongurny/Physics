package physics.math.field;

import physics.math.Vector;
import physics.math.algebra.AbelianAdditiveGroup;

public interface VectorField extends AbelianAdditiveGroup<VectorField> {

	public static VectorField constant(Vector s) {
		return v -> s;
	}

	Vector get(Vector v);

	@Override
	default VectorField add(VectorField other) {
		return v -> get(v).add(other.get(v));
	}

	@Override
	default VectorField negate() {
		return v -> get(v).negate();
	}

	@Override
	default VectorField zero() {
		return v -> get(v).zero();
	}
}
