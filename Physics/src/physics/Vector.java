package physics;

import java.util.function.BiFunction;

public final class Vector implements Measurable {

	public static class Axes2D {
		public static final Vector ZERO = new Vector(0, 0);
		public static final Vector ORIGIN = Vector.zero(Quantity.LENGTH, 2);
		public static final Vector X = new Vector(1, 0);
		public static final Vector Y = new Vector(0, 1);
	}

	public static class Axes3D {
		public static final Vector ZERO = new Vector(0, 0, 0);
		public static final Vector ORIGIN = Vector.zero(Quantity.LENGTH, 3);
		public static final Vector X = new Vector(1, 0, 0);
		public static final Vector Y = new Vector(0, 1, 0);
		public static final Vector Z = new Vector(0, 0, 1);
	}

	private static Object[] convertToUnitSystemObjectArray(Scalar[] scalars, UnitSystem system) {
		Object[] arr = new Object[scalars.length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = scalars[i].convert(system);
		}
		return arr;
	}

	private static Scalar[] fromDoubleArray(double[] numbers) {
		Scalar[] arr = new Scalar[numbers.length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new Scalar(numbers[i]);
		}
		return arr;
	}

	private final Scalar[] entries;

	public Vector(double... entries) {
		this(fromDoubleArray(entries));
	}

	public Vector(Scalar s) {
		this.entries = new Scalar[] { s };
	}

	public Vector(Scalar... entries) {
		this.entries = ArrayComprehension.get(entries, x -> x);
	}

	public Vector(Scalar s, double... entries) {
		this(ArrayComprehension.get(entries.length, i -> s.multiply(entries[i])));
	}

	public int getLength() {
		return entries.length;
	}

	public Vector multiply(Scalar s) {
		return new Vector(ArrayComprehension.get(getLength(), i -> get(i).multiply(s)));
	}

	public Vector multiply(double d) {
		return new Vector(ArrayComprehension.get(getLength(), i -> get(i).multiply(d)));
	}

	@Override
	public Quantity getQuantity() {
		return get(0).getQuantity();
	}

	public Scalar getMagnitude() {
		return Scalar.sqrt(this.dot(this));
	}

	public Vector getDirection() {
		return this.divide(getMagnitude());
	}

	private Vector applyBinaryOperator(Vector other, BiFunction<Scalar, Scalar, Scalar> op) {
		Quantities.requireSameLength(this, other);
		return new Vector(ArrayComprehension.get(getLength(), i -> op.apply(get(i), other.get(i))));
	}

	public Vector add(Vector w) {
		return applyBinaryOperator(w, Scalar::add);
	}

	public Vector negate() {
		return new Vector(ArrayComprehension.get(entries, s -> s.negate()));
	}

	public Scalar dot(Vector v) {
		return Scalar.sum(applyBinaryOperator(v, Scalar::multiply).entries);
	}

	public Vector subtract(Vector v) {
		return this.add(v.negate());
	}

	public Vector divide(Scalar s) {
		return this.multiply(s.inverse());
	}

	public Vector divide(double d) {
		return new Vector(ArrayComprehension.get(getLength(), i -> get(i).divide(d)));
	}

	public Scalar get(int i) {
		return entries[i];
	}

	@Override
	public String toString() {
		return toString(UnitSystem.SI);
	}

	public String toString(UnitSystem system) {
		StringBuilder sb = new StringBuilder("(%.4g");
		for (int i = 1; i < entries.length; i++) {
			sb.append(", %.4g");
		}
		return String.format(sb.toString(), convertToUnitSystemObjectArray(entries, system)) + ") "
				+ system.getUnitName(getQuantity());
	}

	public static Vector sum(Vector... vectors) {
		if (vectors.length == 0) {
			throw new IllegalArgumentException("Must have at least one vector");
		}
		Quantities.requireSameLength(vectors);
		Vector res = vectors[0];
		for (int i = 1; i < vectors.length; i++) {
			res = res.add(vectors[i]);
		}
		return res;
	}

	public static Vector zero(Quantity quantity, int length) {
		Scalar zero = Scalar.zero(quantity);
		return new Vector(ArrayComprehension.get(length, i -> zero));
	}

}
