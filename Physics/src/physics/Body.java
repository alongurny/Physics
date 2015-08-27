package physics;

public abstract class Body {

	private static final Scalar DEFAULT_DT = Scalar.SECOND.multiply(15e-3);

	private final Scalar mass;
	private final Scalar charge;
	private final Scalar dt;
	private Vector position;
	private Vector velocity;
	private Vector totalForce;

	public Body(Scalar mass, Scalar charge, Vector position, Vector velocity) {
		this.mass = mass;
		this.charge = charge;
		this.position = position;
		this.velocity = velocity;
		this.totalForce = Vector.zero(Quantity.FORCE);
		this.dt = DEFAULT_DT;
	}

	public void addAcceleration(Vector acceleration) {
		totalForce = totalForce.add(Quantities.require(acceleration,
				Quantity.ACCELERATION).multiply(mass));
	}

	public void addForce(Vector force) {
		totalForce = totalForce.add(Quantities.require(force, Quantity.FORCE));
	}

	public void addImpulse(Vector impulse) {
		totalForce = totalForce.add(Quantities.require(impulse,
				Quantity.IMPULSE).divide(dt));
	}

	public Direction getAngleWith(Body s) {
		return s.getPosition().subtract(getPosition()).getDirection();
	}

	public Scalar distance(Body b) {
		return position.subtract(b.position).getMagnitude();
	}

	public Vector getAcceleration() {
		return getTotalForce().divide(mass);
	}

	public Scalar getCharge() {
		return charge;
	}

	public VectorField getElectricalField() {
		return v -> {
			Vector diff = v.subtract(position);
			return diff.multiply(Scalar.K.multiply(charge)).divide(
					diff.getMagnitude().pow(2));
		};
	}

	public VectorField getGravitationalField() {
		return v -> {
			Vector diff = position.subtract(v);
			return diff.multiply(Scalar.G.multiply(mass)).divide(
					diff.getMagnitude().pow(3));
		};
	}

	public Scalar getTranslationalKinecticEnergy() {
		return Scalar.product(velocity.getMagnitude().pow(2), mass).multiply(
				0.5);
	}

	public Scalar getMass() {
		return mass;
	}

	public Vector getMomentum() {
		return velocity.multiply(mass);
	}

	public Vector getPosition() {
		return position;
	}

	public Vector getTotalForce() {
		return totalForce;
	}

	public Vector getVelocity() {
		return velocity;
	}

	protected Scalar getTimeSpan() {
		return dt;
	}

	public void move() {
		position = position.add(velocity.multiply(getTimeSpan()));
		velocity = velocity.add(getAcceleration().multiply(dt));
		totalForce = Vector.zero(Quantity.FORCE);
	}

}
