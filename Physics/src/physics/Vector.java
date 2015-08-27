package physics;

public final class Vector implements Measurable {

	public static final Vector METER_X = new Vector(Scalar.METER, 1, 0, 0);
	public static final Vector METER_Y = new Vector(Scalar.METER, 0, 1, 0);
	public static final Vector METER_Z = new Vector(Scalar.METER, 0, 0, 1);
	public static final Vector POSITION_ORIGIN = Vector.zero(Quantity.LENGTH);

	private final Scalar x;
	private final Scalar y;
	private final Scalar z;

	public Vector(Scalar x, Scalar y, Scalar z) {
		Quantities.requireSame(x, y);
		Quantities.requireSame(x, z);
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector(Scalar s, double x, double y, double z) {
		this(s.multiply(x), s.multiply(y), s.multiply(z));
	}

	public static Vector zero(Quantity q) {
		Scalar zero = Scalar.zero(q);
		return new Vector(zero, zero, zero);
	}

	public Vector multiply(Scalar s) {
		return new Vector(x.multiply(s), y.multiply(s), z.multiply(s));
	}

	public Vector multiply(double d) {
		return new Vector(x.multiply(d), y.multiply(d), z.multiply(d));
	}

	@Override
	public Quantity getQuantity() {
		return x.getQuantity();
	}

	public Scalar getMagnitude() {
		return Scalar.sqrt(Scalar.sum(x.pow(2), y.pow(2), z.pow(2)));
	}

	public Direction getDirection() {
		return Direction.get(this.divide(getMagnitude()));
	}

	public Vector add(Vector w) {
		return new Vector(x.add(w.x), y.add(w.y), z.add(w.z));
	}

	public Vector negate() {
		return new Vector(x.negate(), y.negate(), z.negate());
	}

	public Scalar dot(Vector v) {
		return Scalar.sum(x.multiply(v.x), y.multiply(v.y), z.multiply(v.z));
	}

	public Vector cross(Vector v) {
		Scalar x = this.getY().multiply(v.getZ())
				.subtract(this.getZ().multiply(v.getY()));
		Scalar y = this.getZ().multiply(v.getX())
				.subtract(this.getX().multiply(v.getZ()));
		Scalar z = this.getX().multiply(v.getY())
				.subtract(this.getY().multiply(v.getX()));
		return new Vector(x, y, z);
	}

	public Vector subtract(Vector v) {
		return this.add(v.negate());
	}

	public Vector divide(Scalar s) {
		return this.multiply(s.inverse());
	}

	public Scalar getX() {
		return x;
	}

	public Scalar getY() {
		return y;
	}

	public Scalar getZ() {
		return z;
	}

	@Override
	public String toString() {
		return String.format("(%s, %s, %s) ", x.convert(UnitSystem.SI),
				y.convert(UnitSystem.SI), z.convert(UnitSystem.SI))
				+ UnitSystem.SI.getUnitName(getQuantity());
	}

	public static Vector sum(Vector... vectors) {
		if (vectors.length == 0) {
			throw new IllegalArgumentException("Must have at least one vector");
		}
		Vector res = vectors[0];
		for (int i = 1; i < vectors.length; i++) {
			res = res.add(vectors[i]);
		}
		return res;
	}
}
