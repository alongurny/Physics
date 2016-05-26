package physics.body;

import physics.dimension.Dimensions;
import physics.math.Matrix;
import physics.math.Scalar;
import physics.math.Vector;
import physics.quantity.Quantities;
import physics.quantity.Quantity;

public abstract class AbstractRigidBody extends AbstractBody implements RigidBody {

	private Matrix momentOfInertia;

	private Vector angularPosition;
	private Vector angularVelocity;
	private Vector angularAcceleration;
	private Vector angularImpulse;

	public AbstractRigidBody(Scalar mass, Scalar charge, Vector position, Vector velocity, Matrix momentOfInertia,
			Vector angularPosition, Vector angularVelocity) {
		super(mass, charge, position, velocity);
		this.momentOfInertia = Quantities.require(momentOfInertia, Quantity.MOMENT_OF_INERTIA);
		this.angularPosition = Quantities.require(angularPosition, Quantity.ANGLE);
		this.angularVelocity = Quantities.require(angularVelocity, Quantity.ANGULAR_VELOCITY);
		int dimension = Dimensions.requireSame(angularPosition, angularVelocity);
		this.angularAcceleration = Vector.zero(Quantity.ANGULAR_ACCELERATION, dimension);
		this.angularImpulse = Vector.zero(Quantity.ANGULAR_MOMENTUM, dimension);
	}

	public final void addAngularAcceleration(Vector angularAcceleration) {
		Quantities.require(angularAcceleration, Quantity.ANGULAR_ACCELERATION);
		this.angularAcceleration = this.angularAcceleration.add(angularAcceleration);
	}

	public final void addAngularImpulse(Vector angularImpulse) {
		Quantities.require(angularImpulse, Quantity.ANGULAR_MOMENTUM);
		this.angularImpulse = this.angularImpulse.add(angularImpulse);
	}

	public final void addTorque(Vector torque) {
		Quantities.require(torque, Quantity.TORQUE);
		this.angularAcceleration = this.angularAcceleration.add(momentOfInertia.inverse().multiplyColumn(torque));
	}

	@Override
	public Vector getAngularPosition() {
		return angularPosition;
	}

	@Override
	public Vector getAngularVelocity() {
		return angularVelocity;
	}

	@Override
	public Matrix getMomentOfInertia() {
		return momentOfInertia;
	}

	@Override
	public final void rotate(Scalar dt) {
		angularVelocity = angularVelocity.add(momentOfInertia.inverse().multiplyColumn(angularImpulse));
		angularPosition = angularPosition.add(angularVelocity.multiply(dt));
		angularVelocity = angularVelocity.add(angularAcceleration.multiply(dt));
		angularAcceleration = Vector.zero(Quantity.ACCELERATION, angularAcceleration.getDimension());
		angularImpulse = Vector.zero(Quantity.ANGULAR_MOMENTUM, angularImpulse.getDimension());
	}

}
