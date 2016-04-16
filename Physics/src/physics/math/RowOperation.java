package physics.math;

public interface RowOperation {
	Matrix operate(Matrix m);

	Scalar changeDeterminant(Scalar s);

	public static RowOperation swap(int a, int b) {
		return new RowOperation() {

			@Override
			public Matrix operate(Matrix m) {
				return new Matrix(m.getRowCount(), m.getColumnCount(), (i, j) -> m.get(i == a ? b : i == b ? a : i, j));
			}

			@Override
			public Scalar changeDeterminant(Scalar s) {
				return s.negate();
			}

			@Override
			public String toString() {
				return String.format("R%d <-> R%d", a, b);
			}
		};
	}

	public static RowOperation multiply(int a, Scalar c) {
		return new RowOperation() {

			@Override
			public Matrix operate(Matrix m) {
				return new Matrix(m.getRowCount(), m.getColumnCount(),
						(i, j) -> i == a ? c.multiply(m.get(a, j)) : m.get(i, j));
			}

			@Override
			public Scalar changeDeterminant(Scalar s) {
				return s.multiply(c);
			}

			@Override
			public String toString() {
				return String.format("R%d <- R%d * %s", a, a, c);
			}
		};
	}

	public static RowOperation addRow(int a, int b, Scalar c) {
		return new RowOperation() {

			@Override
			public Matrix operate(Matrix m) {
				return new Matrix(m.getRowCount(), m.getColumnCount(),
						(i, j) -> i == a ? m.get(a, j).add(c.multiply(m.get(b, j))) : m.get(i, j));
			}

			@Override
			public Scalar changeDeterminant(Scalar s) {
				return s;
			}

			@Override
			public String toString() {
				return String.format("R%d <- R%d + R%d * %s", a, a, b, c);
			}
		};
	}
}
