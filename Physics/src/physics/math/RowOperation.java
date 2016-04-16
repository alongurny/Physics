package physics.math;

public interface RowOperation {
	Matrix operate(Matrix m);

	public static RowOperation swap(int a, int b) {
		return m -> new Matrix(m.getRowCount(), m.getColumnCount(), (i, j) -> m.get(i == a ? b : i == b ? a : i, j));
	}

	public static RowOperation multiply(int a, Scalar c) {
		return m -> new Matrix(m.getRowCount(), m.getColumnCount(),
				(i, j) -> i == a ? c.multiply(m.get(a, j)) : m.get(i, j));
	}

	public static RowOperation addRow(int a, int b, Scalar c) {
		return m -> new Matrix(m.getRowCount(), m.getColumnCount(),
				(i, j) -> i == a ? m.get(a, j).add(c.multiply(m.get(b, j))) : m.get(i, j));
	}
}
