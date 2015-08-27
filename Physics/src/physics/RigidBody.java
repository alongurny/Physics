package physics;

public class RigidBody extends Body {

	private Scalar inertiaMomement;
	private Vector angularPosition;
	private Vector angularVelocity;
	private Vector totalTorque;

	public RigidBody(Scalar mass, Scalar charge, Vector center,
			Vector velocity, Scalar inertiaMoment, Vector angularPosition,
			Vector angularVelocity) {
		super(mass, charge, center, velocity);
		this.inertiaMomement = Quantities.require(inertiaMoment,
				Quantity.MOMENT_OF_INERTIA);
		this.angularPosition = Quantities.require(angularPosition,
				Quantity.ANGLE);
		this.angularVelocity = Quantities.require(angularVelocity,
				Quantity.ANGULAR_VELOCITY);
		this.totalTorque = Vector.zero(Quantity.TORQUE);
	}

	public void addTorque(Vector torque) {
		totalTorque = totalTorque.add(Quantities.require(torque,
				Quantity.TORQUE));
	}

	public void addAngularAcceleration(Vector angularAcceleration) {
		totalTorque = totalTorque.add(Quantities.require(angularAcceleration,
				Quantity.TORQUE).multiply(inertiaMomement));
	}

	public Vector getAngularAcceleration() {
		return getTotalTorque().divide(inertiaMomement);
	}

	public Vector getTotalTorque() {
		return totalTorque;
	}

	public Scalar getInertiaMomement() {
		return inertiaMomement;
	}

	public void addAngularImpulse(Vector angularImpulse) {
		totalTorque.add(Quantities.require(angularImpulse,
				Quantity.ANGULAR_IMPULSE).divide(getTimeSpan()));
	}

	public Scalar getKineticRotationalEnergy() {
		return inertiaMomement.multiply(angularVelocity.getMagnitude().pow(2))
				.divide(2);
	}

	public Vector getAngularPosition() {
		return angularPosition;
	}

	public Vector getAngularVelocity() {
		return angularVelocity;
	}

	public Vector getAngularMomentum() {
		return angularVelocity.multiply(inertiaMomement);
	}

	public void rotate() {
		angularPosition = angularPosition.add(angularVelocity
				.multiply(getTimeSpan()));
		angularVelocity = angularVelocity.add(getAngularAcceleration()
				.multiply(getTimeSpan()));
		totalTorque = Vector.zero(Quantity.TORQUE);
	}
}
