package physics.math;

import physics.graphics.drawers.VectorCollection;

public class VectorSpace {

	private VectorCollection basis;

	public VectorSpace(VectorCollection vectors) {
		this.basis = vectors;
	}

	public VectorCollection getBasis() {
		return basis;
	}

	public boolean contains(Vector v) {
		if (v.getDimension() != basis.getDimension()) {
			return false;
		}
		if (Vector.isZero(v)) {
			return true;
		}
		Vector d = v.getDirection();
		Matrix m = new Matrix(basis.size() + 1, basis.getDimension(),
				(i, j) -> i == 0 ? d.get(j) : basis.get(i - 1).get(j));
		return Scalar.isZero(m.getDeterminant());
	}

}
