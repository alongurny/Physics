package physics.math;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Matrix {

	public static Matrix identity(int dimension) {
		return new Matrix(dimension, dimension, (i, j) -> i == j ? Scalar.ONE : Scalar.ZERO);
	}

	public static Matrix column(Vector v) {
		return new Matrix(v.getDimension(), 1, (i, j) -> v.get(i));
	}

	public static Matrix rotation(double phi) {
		return new Matrix(new double[][] { { Math.cos(phi), -Math.sin(phi) }, { Math.sin(phi), Math.cos(phi) } });
	}

	private Scalar[][] values;

	public Matrix(double[][] values) {
		this(values.length, values[0].length, (i, j) -> new Scalar(values[i][j]));
	}

	public Matrix(int rowCount, int columnCount, BiFunction<Integer, Integer, Scalar> f) {
		this.values = new Scalar[rowCount][columnCount];
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				this.values[i][j] = f.apply(i, j);
			}
		}
	}

	public Matrix(Scalar[][] values) {
		this(values.length, values[0].length, (i, j) -> values[i][j]);
	}

	public Matrix add(Matrix other) {
		return new Matrix(getRowCount(), getColumnCount(), (i, j) -> get(i, j).add(other.get(i, j)));
	}

	private Matrix apply(Function<Scalar, Scalar> f) {
		return new Matrix(getRowCount(), getColumnCount(), (i, j) -> f.apply(get(i, j)));
	}

	public Scalar get(int row, int column) {
		return values[row][column];
	}

	public Vector getColumn(int j) {
		return new Vector(getRowCount(), i -> values[i][j]);
	}

	public int getColumnCount() {
		return values[0].length;
	}

	public Vector getRow(int i) {
		return new Vector(getColumnCount(), j -> values[i][j]);
	}

	public int getRowCount() {
		return values.length;
	}

	public Matrix multiply(double c) {
		return apply(s -> s.multiply(c));
	}

	public Matrix multiply(Matrix other) {
		return new Matrix(getRowCount(), other.getColumnCount(), (i, j) -> getRow(i).dot(other.getColumn(j)));
	}

	public Matrix multiply(Scalar c) {
		return apply(s -> s.multiply(c));
	}

	public Vector multiplyColumn(Vector v) {
		return multiply(column(v)).getColumn(0);
	}

	public Matrix negate() {
		return apply(Scalar::negate);
	}

	public boolean isSquare() {
		return getRowCount() == getColumnCount();
	}

	public boolean isInvertible() {
		return isSquare() && !Vector.isZero(getRowCanonicalForm().getFirst().getRow(getRowCount() - 1));
	}

	public Matrix inverse() {
		if (!isInvertible()) {
			throw new MatrixArithmeticException("Matrix is not invertible");
		}
		Matrix i = identity(getRowCount());
		for (RowOperation r : getRowCanonicalForm().getSecond()) {
			i = r.operate(i);
		}
		return i;
	}

	public Scalar getDeterminant() {
		if (!isSquare()) {
			throw new MatrixArithmeticException("Matrix is not square");
		}
		Scalar s = Scalar.ONE;
		for (RowOperation r : getRowCanonicalForm().getSecond()) {
			s = r.changeDeterminant(s);
		}
		return s.inverse();
	}

	public Pair<Matrix, List<RowOperation>> getRowCanonicalForm() {
		Matrix a = this;
		List<RowOperation> ops = new ArrayList<>();
		int i = 0, j = 0;
		int m = getRowCount(), n = getColumnCount();
		while (i < m && j < n) {
			int maxi = i;
			for (int k = i + 1; k < m; k++) {
				if (Scalar.abs(a.get(k, j)).compareTo(Scalar.abs(a.get(maxi, j))) > 0) {
					maxi = k;
				}
			}
			if (!Scalar.isZero(a.get(maxi, j))) {
				if (i != maxi) {
					ops.add(RowOperation.swap(i, maxi));
					a = ops.get(ops.size() - 1).operate(a);
				}
				ops.add(RowOperation.multiply(i, a.get(i, j).inverse()));
				a = ops.get(ops.size() - 1).operate(a);
				for (int u = i + 1; u < m; u++) {
					if (!Scalar.isZero(a.get(u, j))) {
						ops.add(RowOperation.addRow(u, i, a.get(u, j).negate()));
						a = ops.get(ops.size() - 1).operate(a);
					}
				}
				i++;
			}
			j++;
		}
		return new Pair<Matrix, List<RowOperation>>(a, ops);
	}

	public Matrix subtract(Matrix other) {
		return this.add(other.negate());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < getRowCount(); i++) {
			sb.append(getRow(i).toString() + "\n");
		}
		return sb.toString();
	}

}
