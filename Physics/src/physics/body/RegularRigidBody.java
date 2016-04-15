package physics.body;

import physics.dimension.Dimensions;
import physics.math.Scalar;
import physics.math.Vector;
import physics.quantity.Quantities;
import physics.quantity.Quantity;

public class RegularRigidBody extends RegularBody implements RigidBody {

	private Scalar momentOfInertia;

	private Vector angularPosition;
	private Vector angularVelocity;
	private Vector angularAcceleration;

	public RegularRigidBody(Scalar mass, Scalar charge, Vector position, Vector velocity, Scalar momentOfInertia,
			Vector angularPosition, Vector angularVelocity) {
		super(mass, charge, position, velocity);
		this.momentOfInertia = Quantities.require(momentOfInertia, Quantity.MOMENT_OF_INERTIA);
		this.angularPosition = Quantities.require(angularPosition, Quantity.ANGLE);
		this.angularVelocity = Quantities.require(angularVelocity, Quantity.ANGULAR_VELOCITY);
		int dimension = Dimensions.requireSame(angularPosition, angularVelocity);
		this.angularAcceleration = Vector.zero(Quantity.ANGULAR_ACCELERATION, dimension);
	}

	@Override
	public Scalar getMomentOfInertia() {
		return momentOfInertia;
	}

	@Override
	public Vector getAngularPosition() {
		return angularPosition;
	}

	@Override
	public Vector getAngularVelocity() {
		return angularVelocity;
	}

	public final void rotate() {
		angularVelocity = angularVelocity.add(angularAcceleration.multiply(getTimeSpan()));
		angularPosition = angularPosition.add(angularVelocity.multiply(getTimeSpan()));
		angularAcceleration = Vector.zero(Quantity.ANGULAR_ACCELERATION, angularAcceleration.getDimension());
	}

	public final void addAngularAcceleration(Vector angularAcceleration) {
		Quantities.require(angularAcceleration, Quantity.ANGULAR_ACCELERATION);
		this.angularAcceleration = this.angularAcceleration.add(angularAcceleration);
	}

	public final void addTorque(Vector torque) {
		Quantities.require(torque, Quantity.TORQUE);
		this.angularAcceleration = this.angularAcceleration.add(torque.divide(momentOfInertia));
	}

	public final void addAngularImpulse(Vector angularImpulse) {
		Quantities.require(angularImpulse, Quantity.ANGULAR_MOMENTUM);
		this.angularAcceleration = this.angularAcceleration
				.add(angularImpulse.divide(momentOfInertia).divide(getTimeSpan()));
	}

}
