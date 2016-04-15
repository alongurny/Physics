package physics;

public class RigidBody extends RegularBody {

	private Scalar momentOfInertia;
	private Vector angularPosition;
	private Vector angularVelocity;
	private Vector totalTorque;

	public RigidBody(Scalar mass, Scalar charge, Vector center, Vector velocity, Scalar inertiaMoment,
			Vector angularPosition, Vector angularVelocity) {
		super(mass, charge, center, velocity);
		Dimensions.requireSame(angularPosition, angularVelocity);
		this.momentOfInertia = Quantities.require(inertiaMoment, Quantity.MOMENT_OF_INERTIA);
		this.angularPosition = Quantities.require(angularPosition, Quantity.ANGLE);
		this.angularVelocity = Quantities.require(angularVelocity, Quantity.ANGULAR_VELOCITY);
		this.totalTorque = Vector.zero(Quantity.TORQUE, angularPosition.getDimension());
	}

	public void addTorque(Vector torque) {
		totalTorque = totalTorque.add(Quantities.require(torque, Quantity.TORQUE));
	}

	public void addAngularAcceleration(Vector angularAcceleration) {
		totalTorque = totalTorque
				.add(Quantities.require(angularAcceleration, Quantity.TORQUE).multiply(momentOfInertia));
	}

	public Vector getAngularAcceleration() {
		return getTotalTorque().divide(momentOfInertia);
	}

	public Vector getTotalTorque() {
		return totalTorque;
	}

	public Scalar getMomentOfInertia() {
		return momentOfInertia;
	}

	public void addAngularImpulse(Vector angularImpulse) {
		totalTorque = totalTorque
				.add(Quantities.require(angularImpulse, Quantity.ANGULAR_MOMENTUM).divide(getTimeSpan()));

	}

	public Scalar getKineticRotationalEnergy() {
		return momentOfInertia.multiply(angularVelocity.getMagnitude().pow(2)).divide(2);
	}

	public Vector getAngularPosition() {
		return angularPosition;
	}

	public Vector getAngularVelocity() {
		return angularVelocity;
	}

	public Vector getAngularMomentum() {
		return angularVelocity.multiply(momentOfInertia);
	}

	public void rotate() {
		angularPosition = angularPosition.add(angularVelocity.multiply(getTimeSpan()));
		angularVelocity = angularVelocity.add(getAngularAcceleration().multiply(getTimeSpan()));
		totalTorque = Vector.zero(Quantity.TORQUE, totalTorque.getDimension());
	}
}
