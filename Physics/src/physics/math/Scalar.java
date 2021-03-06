package physics.math;

import java.util.Objects;

import physics.dimension.Dimensioned;
import physics.math.algebra.Field;
import physics.quantity.Quantifiable;
import physics.quantity.Quantities;
import physics.quantity.Quantity;
import physics.quantity.UnitSystem;

public final class Scalar implements Comparable<Scalar>, Quantifiable, Dimensioned, Field<Scalar> {

	public static final Scalar ONE = new Scalar(Quantity.NONE, 1);
	public static final Scalar ZERO = new Scalar(Quantity.NONE, 0);
	public static final Scalar METER = new Scalar(Quantity.LENGTH, 1);
	public static final Scalar SECOND = new Scalar(Quantity.TIME, 1);
	public static final Scalar MINUTE = new Scalar(SECOND, 60);
	public static final Scalar HOUR = new Scalar(MINUTE, 60);
	public static final Scalar DAY = new Scalar(HOUR, 24);
	public static final Scalar KILOGRAM = new Scalar(Quantity.MASS, 1);
	public static final Scalar NEWTON = new Scalar(Quantity.FORCE, 1);
	public static final Scalar JOULE = new Scalar(Quantity.ENERGY, 1);
	public static final Scalar COULOMB = new Scalar(Quantity.CHARGE, 1);
	public static final Scalar RADIAN = new Scalar(Quantity.ANGLE, 1);
	public static final Scalar AMPERE = new Scalar(Quantity.CURRENT, 1);
	public static final Scalar TESLA = new Scalar(Quantity.MAGNETIC_FIELD, 1);
	public static final Scalar VOLT = new Scalar(Quantity.VOLTAGE, 1);
	public static final Scalar HERTZ = new Scalar(Quantity.FREQUENCY, 1);

	public static final Scalar SPEED_OF_LIGHT = new Scalar(Quantity.VELOCITY, 299792458);
	public static final Scalar ELEMENTARY_CHARGE = COULOMB.multiply(1.6e-19);
	public static final Scalar G = METER.pow(3).divide(product(SECOND, SECOND, KILOGRAM)).multiply(6.67384e-11);
	public static final Scalar K = NEWTON.multiply(METER.pow(2)).divide(COULOMB.pow(2)).multiply(8.98755e9);
	public static final Scalar VACUUM_PERMITTIVITY = K.multiply(4 * Math.PI).inverse();
	public static final Scalar VACUUM_PERMEABILITY = product(SPEED_OF_LIGHT.pow(2), VACUUM_PERMITTIVITY).inverse();
	public static final Scalar STANDARD_GRAVITY = new Scalar(Quantity.ACCELERATION, 9.80665);

	public static Scalar abs(Scalar scalar) {
		if (scalar.value < 0) {
			return new Scalar(scalar.quantity, -scalar.value);
		}
		return scalar;
	}

	public static double atan2(Scalar y, Scalar x) {
		Quantities.requireSame(y, x);
		return Math.atan2(y.value, x.value);
	}

	public static int compare(Scalar s, Scalar t) {
		Quantities.requireSame(s, t);
		return Double.compare(s.value, t.value);
	}

	public static double cos(Scalar s) {
		Quantities.require(s, Quantity.ANGLE);
		return Math.cos(s.convert(Scalar.RADIAN));
	}

	public static boolean isInfinite(Scalar s) {
		return Double.isInfinite(s.value);
	}

	public static boolean isZero(Scalar s) {
		return s.value == 0;
	}

	public static Scalar negativeInfinity(Quantity q) {
		return new Scalar(q, Double.NEGATIVE_INFINITY);
	}

	public static Scalar positiveInfinity(Quantity q) {
		return new Scalar(q, Double.POSITIVE_INFINITY);
	}

	public static Scalar product(Scalar... us) {
		Scalar res = ONE;
		for (Scalar u : us) {
			res = res.multiply(u);
		}
		return res;
	}

	public static double sin(Scalar s) {
		Quantities.require(s, Quantity.ANGLE);
		return Math.sin(s.convert(Scalar.RADIAN));
	}

	public static Scalar sqrt(Scalar u) {
		return new Scalar(Quantity.sqrt(u.quantity), Math.sqrt(u.value));
	}

	public static Scalar sum(Scalar... us) {
		if (us.length == 0) {
			throw new IllegalArgumentException("Must have at least one scalar to compute sum");
		}
		Scalar res = us[0];
		for (int i = 1; i < us.length; i++) {
			res = res.add(us[i]);
		}
		return res;
	}

	public static Scalar zero(Quantity q) {
		return new Scalar(q, 0.0);
	}

	private final Quantity quantity;

	private final double value;

	private Scalar(Quantity quantity, double value) {
		this.quantity = Objects.requireNonNull(quantity);
		if (Double.isNaN(value)) {
			throw new IllegalArgumentException("value must be a finite or infinite number");
		}
		this.value = value;
	}

	public Scalar(Scalar s, double factor) {
		this(Objects.requireNonNull(s.quantity), s.value * factor);
	}

	public Scalar(double value) {
		this(Quantity.NONE, value);
	}

	public Scalar add(Scalar v) {
		Quantities.requireSame(this, v);
		return new Scalar(quantity, value + v.value);
	}

	@Override
	public int compareTo(Scalar o) {
		Quantities.requireSame(this, o);
		return Double.compare(value, o.value);
	}

	public double convert(Scalar v) {
		Quantities.requireSame(this, v);
		return value / v.value;
	}

	public double convert(UnitSystem s) {
		return convert(s.get(quantity));
	}

	public Scalar divide(double c) {
		return new Scalar(quantity, value / c);
	}

	public Scalar divide(Scalar v) {
		return multiply(v.inverse());
	}

	public boolean equals(Object other) {
		if (!(other instanceof Scalar)) {
			return false;
		}
		Scalar s = (Scalar) other;
		return quantity.equals(s.getQuantity()) && value == s.value;
	}

	@Override
	public int getDimension() {
		return 1;
	}

	@Override
	public Quantity getQuantity() {
		return quantity;
	}

	public Scalar inverse() {
		return new Scalar(quantity.inverse(), 1 / value);
	}

	public Scalar multiply(double c) {
		return new Scalar(quantity, value * c);
	}

	public Scalar multiply(Scalar s) {
		return new Scalar(Quantity.product(quantity, s.quantity), value * s.value);
	}

	public Vector multiply(Vector v) {
		return v.multiply(this);
	}

	public Scalar negate() {
		return new Scalar(quantity, -value);
	}

	public Scalar pow(int n) {
		return new Scalar(Quantity.pow(quantity, n), Math.pow(value, n));
	}

	public Scalar subtract(Scalar v) {
		return add(v.negate());
	}

	@Override
	public String toString() {
		return toString(4);
	}

	public String toString(int n) {
		return String.format("%." + n + "g", value) + " " + UnitSystem.SI.getUnitName(quantity);
	}

	@Override
	public Scalar unit() {
		return Scalar.ONE;
	}

	@Override
	public Scalar zero() {
		return zero(quantity);
	}
}
