package physics;

public abstract class Body {

	public static final Scalar DEFAULT_TIME_SPAN = Scalar.SECOND.multiply(15e-3);

	private final Scalar mass;
	private final Scalar charge;
	private final Scalar timeSpan;
	private Vector position;
	private Vector velocity;
	private Vector acceleration;

	public Body(Scalar mass, Scalar charge, Vector position, Vector velocity) {
		Quantities.requireSameLength(position, velocity);
		this.mass = Quantities.require(mass, Quantity.MASS);
		this.charge = Quantities.require(charge, Quantity.CHARGE);
		this.position = Quantities.require(position, Quantity.LENGTH);
		this.velocity = Quantities.require(velocity, Quantity.VELOCITY);
		this.acceleration = Vector.zero(Quantity.ACCELERATION, position.getLength());
		this.timeSpan = DEFAULT_TIME_SPAN;
	}

	public final void addAcceleration(Vector acceleration) {
		Quantities.require(acceleration, Quantity.ACCELERATION);
		this.acceleration = this.acceleration.add(acceleration);
	}

	public final void addForce(Vector force) {
		Quantities.require(force, Quantity.FORCE);
		this.acceleration = this.acceleration.add(force.divide(mass));
	}

	public final void addImpulse(Vector impulse) {
		Quantities.require(impulse, Quantity.MOMENTUM);
		this.acceleration = this.acceleration.add(impulse.divide(mass).divide(getTimeSpan()));
	}

	public final Vector getAcceleration() {
		return acceleration;
	}

	public final Vector getTotalForce() {
		return acceleration.multiply(mass);
	}

	public final Scalar getCharge() {
		return charge;
	}

	public final VectorField getElectricalField() {
		return v -> {
			Vector diff = v.subtract(position);
			return diff.multiply(Scalar.K.multiply(charge)).divide(diff.getMagnitude().pow(3));
		};
	}

	public final VectorField getGravitationalField() {
		return v -> {
			Vector diff = position.subtract(v);
			return diff.multiply(Scalar.G.multiply(mass)).divide(diff.getMagnitude().pow(3));
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

	public final Scalar getTranslationalKinecticEnergy() {
		return Scalar.product(velocity.getMagnitude().pow(2), mass).multiply(0.5);
	}

	public void setVelocity(Vector v) {
		this.velocity = v;
	}

	public final Vector getVelocity() {
		return velocity;
	}

	public final void move() {
		velocity = velocity.add(acceleration.multiply(getTimeSpan()));
		position = position.add(velocity.multiply(getTimeSpan()));
		acceleration = Vector.zero(Quantity.ACCELERATION, acceleration.getLength());
	}

}
