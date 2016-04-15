package physics.math;

import java.util.function.BiFunction;
import java.util.function.Function;

import physics.dimension.Dimensioned;
import physics.dimension.Dimensions;
import physics.quantity.Measurable;
import physics.quantity.Quantity;
import physics.quantity.UnitSystem;

public final class Vector implements Measurable, Dimensioned {

	public static class Axes2D {
		public static final Vector ORIGIN = Vector.zero(Quantity.LENGTH, 2);
		public static final Vector X = new Vector(1, 0);
		public static final Vector Y = new Vector(0, 1);
		public static final Vector ZERO = new Vector(0, 0);
	}

	public static class Axes3D {
		public static final Vector ORIGIN = Vector.zero(Quantity.LENGTH, 3);
		public static final Vector X = new Vector(1, 0, 0);
		public static final Vector Y = new Vector(0, 1, 0);
		public static final Vector Z = new Vector(0, 0, 1);
		public static final Vector ZERO = new Vector(0, 0, 0);
	}

	private static Object[] convertToUnitSystemObjectArray(Scalar[] scalars, UnitSystem system) {
		Object[] arr = new Object[scalars.length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = scalars[i].convert(system);
		}
		return arr;
	}

	public static Vector extend(Vector v, int newLength) {
		return new Vector(newLength, i -> i < v.getDimension() ? v.get(i) : Scalar.zero(v.getQuantity()));
	}

	public static Vector fromPolar(Scalar magnitude, double phase) {
		return new Vector(magnitude.multiply(Math.cos(phase)), magnitude.multiply(Math.sin(phase)));
	}

	public static double getPhase(Vector v) {
		return Scalar.atan2(v.get(1), v.get(0));
	}

	public static boolean isZero(Vector v) {
		for (Scalar s : v.entries) {
			if (!Scalar.isZero(s)) {
				return false;
			}
		}
		return true;
	}

	public static Vector sum(Vector... vectors) {
		if (vectors.length == 0) {
			throw new IllegalArgumentException("Must have at least one vector");
		}
		Dimensions.requireSame(vectors);
		Vector res = vectors[0];
		for (int i = 1; i < vectors.length; i++) {
			res = res.add(vectors[i]);
		}
		return res;
	}

	public static Vector zero(Quantity quantity, int length) {
		Scalar zero = Scalar.zero(quantity);
		return new Vector(length, i -> zero);
	}

	private final Scalar[] entries;

	public Vector(double... entries) {
		this(entries.length, i -> new Scalar(entries[i]));
	}

	public Vector(int length, Function<Integer, Scalar> f) {
		entries = new Scalar[length];
		for (int i = 0; i < entries.length; i++) {
			entries[i] = f.apply(i);
		}
	}

	public Vector(Scalar s) {
		this(1, i -> s);
	}

	public Vector(Scalar... entries) {
		this(entries.length, i -> entries[i]);
	}

	public Vector(Scalar s, double... entries) {
		this(entries.length, i -> s.multiply(entries[i]));
	}

	public Vector add(Vector w) {
		return applyBinaryOperator(w, Scalar::add);
	}

	private Vector applyBinaryOperator(Vector other, BiFunction<Scalar, Scalar, Scalar> op) {
		Dimensions.requireSame(this, other);
		return new Vector(getDimension(), i -> op.apply(entries[i], other.entries[i]));
	}

	public Vector cross(Vector v) {
		Dimensions.require(3, this, v);
		return new Vector(get(1).multiply(v.get(2)).subtract(get(2).multiply(v.get(1))),
				get(2).multiply(v.get(0)).subtract(get(0).multiply(v.get(2))),
				get(0).multiply(v.get(1)).subtract(get(1).multiply(v.get(0))));
	}

	public Vector divide(double d) {
		return get(s -> s.divide(d));
	}

	public Vector divide(Scalar s) {
		return this.multiply(s.inverse());
	}

	public Scalar dot(Vector v) {
		return Scalar.sum(applyBinaryOperator(v, Scalar::multiply).entries);
	}

	private Vector get(Function<Scalar, Scalar> f) {
		return new Vector(entries.length, i -> f.apply(entries[i]));
	}

	public Scalar get(int i) {
		return entries[i];
	}

	public Vector getDirection() {
		return this.divide(getMagnitude());
	}

	public int getDimension() {
		return entries.length;
	}

	public Scalar getMagnitude() {
		return Scalar.sqrt(this.dot(this));
	}

	@Override
	public Quantity getQuantity() {
		return get(0).getQuantity();
	}

	public Vector multiply(double d) {
		return get(u -> u.multiply(d));
	}

	public Vector multiply(Scalar s) {
		return get(u -> u.multiply(s));
	}

	public Vector negate() {
		return get(Scalar::negate);
	}

	public Vector subtract(Vector v) {
		return this.add(v.negate());
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

}
