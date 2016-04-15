package physics.math.field;

import physics.math.Vector;

public interface VectorField {

	public static VectorField constant(Vector u) {
		return v -> u;
	}

	public static VectorField sum(VectorField a, VectorField b) {
		return v -> Vector.sum(a.get(v), b.get(v));
	}

	Vector get(Vector v);

}
