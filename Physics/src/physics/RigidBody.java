package physics;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RigidBody extends Body {

	private Scalar inertiaMomement;
	private Vector angularPosition;
	private Vector angularVelocity;
	private List<Vector> torques;
	private List<Vector> angularImpulses;

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
		this.torques = new CopyOnWriteArrayList<Vector>();
		this.angularImpulses = new CopyOnWriteArrayList<Vector>();
	}

	public void addTorque(Vector torque) {
		torques.add(Quantities.require(torque, Quantity.TORQUE));
	}

	public void addAngularAcceleration(Vector angularAcceleration) {
		torques.add(Quantities.require(angularAcceleration,
				Quantity.ANGULAR_ACCELERATION).multiply(inertiaMomement));
	}

	public Vector getAngularAcceleration() {
		return getTotalTorque().divide(inertiaMomement);
	}

	public Vector getTotalTorque() {
		Vector sum = Vector.zero(Quantity.TORQUE);
		for (Vector t : torques) {
			sum = sum.add(t);
		}
		return sum;
	}

	public Scalar getInertiaMomement() {
		return inertiaMomement;
	}

	public void addAngularImpulse(Vector angularImpulse) {
		angularImpulses.add(Quantities.require(angularImpulse,
				Quantity.ANGULAR_IMPULSE));
	}

	public Vector getTotalAngularImpulse() {
		Vector sum = Vector.zero(Quantity.ANGULAR_IMPULSE);
		for (Vector i : angularImpulses) {
			sum = sum.add(i);
		}
		return sum;
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

	public void rotate(Scalar dt) {
		Quantities.require(dt, Quantity.TIME);
		angularPosition = angularPosition.add(angularVelocity.multiply(dt));
		angularVelocity = angularVelocity.add(
				getAngularAcceleration().multiply(dt)).add(
				getTotalAngularImpulse().divide(inertiaMomement));
		angularImpulses.clear();
		torques.clear();
	}
}
