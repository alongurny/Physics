package physics;

public interface VectorField {
	Vector get(Vector v);

	public static VectorField sum(VectorField a, VectorField b) {
		return v -> Vector.sum(a.get(v), b.get(v));
	}

}
