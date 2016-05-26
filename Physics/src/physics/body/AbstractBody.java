package physics.body;

import physics.dimension.Dimensions;
import physics.math.Scalar;
import physics.math.Vector;
import physics.math.field.VectorField;
import physics.quantity.Quantities;
import physics.quantity.Quantity;

public abstract class AbstractBody implements Movable {

	private final Scalar mass;
	private Scalar charge;
	private Vector position;
	private Vector velocity;
	private Vector impulse;
	private Vector acceleration;

	public AbstractBody(Scalar mass, Scalar charge, Vector position, Vector velocity) {
		Dimensions.requireSame(position, velocity);
		this.mass = Quantities.require(mass, Quantity.MASS);
		this.charge = Quantities.require(charge, Quantity.CHARGE);
		this.position = Quantities.require(position, Quantity.LENGTH);
		this.velocity = Quantities.require(velocity, Quantity.VELOCITY);
		this.impulse = Vector.zero(Quantity.MOMENTUM, position.getDimension());
		this.acceleration = Vector.zero(Quantity.ACCELERATION, position.getDimension());
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
		this.impulse = this.impulse.add(impulse);
	}

	public final Vector getAcceleration() {
		return acceleration;
	}

	public final Scalar getCharge() {
		return charge;
	}

	public final VectorField getElectricalField() {
		return v -> {
			Vector r = v.subtract(position);
			return Scalar.K.multiply(charge).multiply(r).divide(r.getMagnitude().pow(3));
		};
	}

	public final VectorField getGravitationalField() {
		return v -> {
			Vector diff = position.subtract(v);
			return diff.multiply(Scalar.G.multiply(mass)).divide(diff.getMagnitude().pow(3));
		};
	}

	public final VectorField getMagneticField() {
		return v -> {
			Vector r = v.subtract(position);
			return Scalar.VACUUM_PERMEABILITY.multiply(charge).divide(4 * Math.PI).multiply(velocity.cross(r))
					.divide(r.getMagnitude().pow(3));
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

	public final Vector getTotalForce() {
		return acceleration.multiply(mass);
	}

	public final Scalar getTranslationalKinecticEnergy() {
		return Scalar.product(velocity.getMagnitude().pow(2), mass).multiply(0.5);
	}

	public final Vector getVelocity() {
		return velocity;
	}

	public final void move(Scalar dt) {
		velocity = velocity.add(impulse.divide(mass));
		position = position.add(velocity.multiply(dt));
		velocity = velocity.add(acceleration.multiply(dt));
		acceleration = Vector.zero(Quantity.ACCELERATION, acceleration.getDimension());
		impulse = Vector.zero(Quantity.MOMENTUM, impulse.getDimension());
	}

	protected void setCharge(Scalar charge) {
		this.charge = Quantities.require(charge, Quantity.CHARGE);
	}

}
