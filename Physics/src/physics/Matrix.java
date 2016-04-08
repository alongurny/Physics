package physics;

import java.util.function.Function;

public class Matrix {

	interface ScalarSupplier {
		Scalar get(int i, int j);
	}

	private Scalar[][] values;

	private Matrix(int rowCount, int columnCount, ScalarSupplier f) {
		this.values = new Scalar[rowCount][columnCount];
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				this.values[i][j] = f.get(i, j);
			}
		}
	}

	private Matrix apply(Function<Scalar, Scalar> f) {
		return new Matrix(getRowCount(), getColumnCount(), (i, j) -> f.apply(get(i, j)));
	}

	public Matrix(Scalar[][] values) {
		this(values.length, values[0].length, (i, j) -> values[i][j]);
	}

	public Vector getRow(int i) {
		return new Vector(ArrayComprehension.get(getColumnCount(), j -> values[i][j]));
	}

	public Vector getColumn(int j) {
		return new Vector(ArrayComprehension.get(getRowCount(), i -> values[i][j]));
	}

	public Scalar get(int row, int column) {
		return values[row][column];
	}

	public Matrix add(Matrix other) {
		return new Matrix(getRowCount(), getColumnCount(), (i, j) -> get(i, j).add(other.get(i, j)));
	}

	public Matrix negate() {
		return apply(Scalar::negate);
	}

	public Matrix subtract(Matrix other) {
		return this.add(other.negate());
	}

	public Matrix multiply(double c) {
		return apply(s -> s.multiply(c));
	}

	public Matrix multiply(Scalar c) {
		return apply(s -> s.multiply(c));
	}

	public Matrix multiply(Matrix other) {
		return new Matrix(getRowCount(), other.getColumnCount(), (i, j) -> getRow(i).dot(other.getColumn(j)));

	}

	public int getRowCount() {
		return values.length;
	}

	public int getColumnCount() {
		return values[0].length;
	}

}
