package physics;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Body {

	private static final Scalar DEFAULT_DT = Scalar.SECOND.multiply(15e-3);

	private final Scalar mass;
	private final Scalar charge;
	private final Scalar dt;
	private Vector position;
	private Vector velocity;
	private List<Vector> forces;
	private List<Vector> impulses;

	public Body(Scalar mass, Scalar charge, Vector position, Vector velocity) {
		this.mass = mass;
		this.charge = charge;
		this.position = position;
		this.velocity = velocity;
		this.forces = new CopyOnWriteArrayList<Vector>();
		this.impulses = new CopyOnWriteArrayList<Vector>();
		this.dt = DEFAULT_DT;
	}

	public void addAcceleration(Vector acceleration) {
		forces.add(Quantities.require(acceleration, Quantity.ACCELERATION)
				.multiply(mass));
	}

	public void addForce(Vector force) {
		forces.add(Quantities.require(force, Quantity.FORCE));
	}

	public void addImpulse(Vector impulse) {
		impulses.add(Quantities.require(impulse, Quantity.IMPULSE));
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

	public Scalar getElectricalField(Scalar radius) {
		Quantities.require(radius, Quantity.POSITION);
		return Scalar.K.multiply(charge).divide(radius.pow(2));
	}

	public Scalar getGravitationalEnergy(Body b) {
		return Scalar.product(Scalar.G, b.getMass(), getMass())
				.divide(distance(b)).negate();
	}

	public Scalar getGravitationalField(Scalar radius) {
		Quantities.require(radius, Quantity.POSITION);
		return Scalar.G.multiply(mass).divide(radius.pow(2));
	}

	public Scalar getTranslationalKinecticEnergy() {
		return Scalar.product(velocity.getMagnitude(), velocity.getMagnitude(),
				mass).multiply(0.5);
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
		Vector sum = Vector.zero(Quantity.FORCE);
		for (Vector f : forces) {
			sum = sum.add(f);
		}
		return sum;
	}

	public Vector getTotalImpulse() {
		Vector sum = Vector.zero(Quantity.IMPULSE);
		for (Vector j : impulses) {
			sum = sum.add(j);
		}
		return sum;
	}

	public Vector getVelocity() {
		return velocity;
	}

	protected Scalar getTimeSpan() {
		return dt;
	}

	public void move() {
		position = position.add(velocity.multiply(getTimeSpan()));
		velocity = velocity.add(getAcceleration().multiply(dt)).add(
				getTotalImpulse().divide(mass));
		impulses.clear();
		forces.clear();
	}

}
