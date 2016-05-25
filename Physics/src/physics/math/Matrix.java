package physics.math;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import physics.graphics.drawers.VectorCollection;
import physics.math.algebra.Ring;
import physics.quantity.Quantifiable;
import physics.quantity.Quantities;
import physics.quantity.Quantity;
import physics.util.ImmutablePair;
import physics.util.Lazy;

public class Matrix implements Quantifiable, Ring<Matrix> {

	public static Matrix column(Vector v) {
		return new Matrix(v.getDimension(), 1, (i, j) -> v.get(i));
	}

	public static Matrix zero(int rowCount, int columnCount) {
		return new Matrix(rowCount, columnCount, (i, j) -> Scalar.ZERO);
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
	private Lazy<Matrix> transpose;
	private Lazy<VectorSpace> nullspace;

	public Matrix(double[][] values) {
		this(values.length, values[0].length, (i, j) -> new Scalar(values[i][j]));
	}

	public Matrix(int rowCount, int columnCount, BiFunction<Integer, Integer, Scalar> f) {
		rref = new Lazy<>(this::getRREF);
		determinant = new Lazy<>(this::calculateDeterminant);
		inverse = new Lazy<>(this::calculateInverse);
		negate = new Lazy<>(this::calculateNegate);
		invertible = new Lazy<>(this::calculateInvertible);
		transpose = new Lazy<>(this::calculateTranspose);
		nullspace = new Lazy<>(this::calculateNullspace);
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

	private Scalar calculateDeterminant() {
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

	private Matrix calculateTranspose() {
		return new Matrix(getColumnCount(), getRowCount(), (i, j) -> get(j, i));
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

	@Override
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

	public int getRank() {
		Matrix rref = getRowCanonicalForm();
		int rank = 0;
		for (int i = 0; i < rref.getRowCount(); i++) {
			if (Vector.isZero(rref.getRow(i))) {
				break;
			}
			rank++;
		}
		return rank;
	}

	public VectorSpace getNullspace() {
		return nullspace.get();
	}

	/**
	 * FIXME: doesn't work
	 */
	private VectorSpace calculateNullspace() {
		Scalar[][] well = zero(getRowCount(), getColumnCount()).values;
		Matrix rref = getRowCanonicalForm();
		List<Vector> vectors = new ArrayList<>();
		int count = 0;
		for (int i = 0; i < getRowCount() && count < getColumnCount(); i++) {
			while (Scalar.isZero(rref.get(i, count)) && count < getColumnCount()) {
				well[count][i] = Scalar.ONE;
				System.out.println(count + ", " + i);
				count++;
			}
			for (int j = count + 1; j < getColumnCount(); j++) {
				well[j][i] = rref.get(i, j).negate();
			}
			count++;
		}
		for (Scalar[] row : well) {
			Vector v = new Vector(row);
			if (!Vector.isZero(v)) {
				vectors.add(v);
			}
		}
		return new VectorSpace(new VectorCollection(vectors));
	}

	private ImmutablePair<Matrix, List<RowOperation>> getRREF() {
		Matrix m = apply(Function.identity());
		Scalar[][] rref = m.values;
		List<RowOperation> operations = new ArrayList<>();
		int r = 0;
		for (int c = 0; c < rref[0].length && r < rref.length; c++) {
			int j = r;
			for (int i = r + 1; i < rref.length; i++)
				if (Scalar.abs(rref[i][c]).compareTo(Scalar.abs(rref[j][c])) > 0)
					j = i;
			if (Scalar.isZero(rref[j][c]))
				continue;

			RowOperation swap = RowOperation.swap(j, r);
			operations.add(swap);
			swap.operate(rref);

			RowOperation multiply = RowOperation.multiply(r, rref[r][c].inverse());
			operations.add(multiply);
			multiply.operate(rref);
			for (int i = 0; i < rref.length; i++) {
				if (i != r) {
					RowOperation addRow = RowOperation.addRow(i, r, rref[i][c].negate());
					operations.add(addRow);
					addRow.operate(rref);
				}
			}
			r++;
		}
		return new ImmutablePair<>(m, operations);
	}

	public Matrix inverse() {
		return inverse.get();
	}

	public Matrix transpose() {
		return transpose.get();
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < getRowCount(); i++) {
			sb.append(getRow(i).toString() + "\n");
		}
		return sb.toString();
	}

	@Override
	public Matrix zero() {
		return zero(getRowCount(), getColumnCount());
	}

	@Override
	public Matrix unit() {
		if (!isSquare()) {
			throw new MatrixArithmeticException("Matrix is not square");
		}
		return identity(getRowCount());
	}

}
