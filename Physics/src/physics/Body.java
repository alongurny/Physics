package physics;

public abstract class Body {

	public static final Scalar DEFAULT_TIME_SPAN = Scalar.SECOND
			.multiply(15e-3);

	private final Scalar mass;
	private final Scalar charge;
	private final Scalar timeSpan;
	private Vector position;
	private Vector velocity;
	private Vector totalForce;

	public Body(Scalar mass, Scalar charge, Vector position, Vector velocity) {
		this.mass = mass;
		this.charge = charge;
		this.position = position;
		this.velocity = velocity;
		this.totalForce = Vector.zero(Quantity.FORCE);
		this.timeSpan = DEFAULT_TIME_SPAN;
	}

	public final void addAcceleration(Vector acceleration) {
		totalForce = totalForce.add(Quantities.require(acceleration,
				Quantity.ACCELERATION).multiply(mass));
	}

	public final void addForce(Vector force) {
		totalForce = totalForce.add(Quantities.require(force, Quantity.FORCE));
	}

	public final void addImpulse(Vector impulse) {
		totalForce = totalForce.add(Quantities.require(impulse,
				Quantity.MOMENTUM).divide(getTimeSpan()));
	}

	public final Vector getAcceleration() {
		return getTotalForce().divide(mass);
	}

	public final Scalar getCharge() {
		return charge;
	}

	public final VectorField getElectricalField() {
		return v -> {
			Vector diff = v.subtract(position);
			return diff.multiply(Scalar.K.multiply(charge)).divide(
					diff.getMagnitude().pow(2));
		};
	}

	public final VectorField getGravitationalField() {
		return v -> {
			Vector diff = position.subtract(v);
			return diff.multiply(Scalar.G.multiply(mass)).divide(
					diff.getMagnitude().pow(3));
		};
	}

	public final Scalar getMass() {
		return mass;
	}

	public final Vector getMomentum() {
		return velocity.multiply(mass);
	}

	public final Vector getPosition() {
		return position;
	}

	protected final Scalar getTimeSpan() {
		return timeSpan;
	}

	public final Vector getTotalForce() {
		return totalForce;
	}

	public final Scalar getTranslationalKinecticEnergy() {
		return Scalar.product(velocity.getMagnitude().pow(2), mass).multiply(
				0.5);
	}

	public final Vector getVelocity() {
		return velocity;
	}

	public final void move() {
		position = position.add(velocity.multiply(getTimeSpan()));
		velocity = velocity.add(getAcceleration().multiply(getTimeSpan()));
		totalForce = Vector.zero(Quantity.FORCE);
	}

}
