package physics.math;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.gurny.ImmutablePair;
import com.gurny.Lazy;

import physics.quantity.Quantities;
import physics.quantity.Quantity;

public class Matrix {

	public static Matrix column(Vector v) {
		return new Matrix(v.getDimension(), 1, (i, j) -> v.get(i));
	}

	public static Matrix identity(int dimension) {
		return new Matrix(dimension, dimension, (i, j) -> i == j ? Scalar.ONE : Scalar.ZERO);
	}

	public static Matrix rotation(double phi) {
		return new Matrix(new double[][] { { Math.cos(phi), -Math.sin(phi) }, { Math.sin(phi), Math.cos(phi) } });
	}

	private Scalar[][] values;
	private Lazy<ImmutablePair<Matrix, List<RowOperation>>> rref;
	private Lazy<Scalar> determinant;
	private Lazy<Matrix> negate;
	private Lazy<Matrix> inverse;
	private Lazy<Boolean> invertible;

	public Matrix(double[][] values) {
		this(values.length, values[0].length, (i, j) -> new Scalar(values[i][j]));
	}

	public Matrix(int rowCount, int columnCount, BiFunction<Integer, Integer, Scalar> f) {
		rref = new Lazy<>(this::getRREF);
		determinant = new Lazy<>(this::calculateDeterminant);
		inverse = new Lazy<>(this::calculateInverse);
		negate = new Lazy<>(this::calculateNegate);
		invertible = new Lazy<>(this::calculateInvertible);
		this.values = new Scalar[rowCount][columnCount];
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				this.values[i][j] = f.apply(i, j);
				Quantities.requireSame(this.values[i][j], this.values[0][0]);
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

	public Scalar calculateDeterminant() {
		if (!isSquare()) {
			throw new MatrixArithmeticException("Matrix is not square");
		}
		if (!isInvertible()) {
			return Scalar.zero(getQuantity().pow(getRowCount()));
		}
		Scalar s = Scalar.ONE;
		for (RowOperation r : getRowOperations()) {
			s = r.changeDeterminant(s);
		}
		return s.inverse();
	}

	private Matrix calculateInverse() {
		if (!isInvertible()) {
			throw new MatrixArithmeticException("Matrix is not invertible");
		}
		Matrix i = identity(getRowCount());
		for (RowOperation r : getRowOperations()) {
			r.operate(i.values);
		}
		return i;
	}

	private Matrix calculateNegate() {
		return apply(Scalar::negate);
	}

	private boolean calculateInvertible() {
		return isSquare() && !Vector.isZero(getRowCanonicalForm().getRow(getRowCount() - 1));
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

	public Scalar getDeterminant() {
		return determinant.get();
	}

	public Quantity getQuantity() {
		return get(0, 0).getQuantity();
	}

	public Vector getRow(int i) {
		return new Vector(getColumnCount(), j -> values[i][j]);
	}

	public Matrix getRowCanonicalForm() {
		return rref.get().getFirst();
	}

	public int getRowCount() {
		return values.length;
	}

	public List<RowOperation> getRowOperations() {
		return rref.get().getSecond();
	}

	private ImmutablePair<Matrix, List<RowOperation>> getRREF() {
		return null;
	}

	public Matrix inverse() {
		return inverse.get();
	}

	public boolean isInvertible() {
		return invertible.get();
	}

	public boolean isSquare() {
		return getRowCount() == getColumnCount();
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
		return negate.get();
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
