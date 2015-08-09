package bodies;

import physics.Quantities;
import physics.Quantity;
import physics.RigidBody;
import physics.Scalar;
import physics.Vector;

public class Robot extends RigidBody {

	private Scalar width, height;

	public Robot(Scalar mass, Vector center, Scalar inertiaMomemnt,
			Vector angularPosition, Scalar width, Scalar height) {
		super(mass, Scalar.zero(Quantity.CHARGE), center, Vector
				.zero(Quantity.VELOCITY), inertiaMomemnt, angularPosition,
				Vector.zero(Quantity.ANGULAR_VELOCITY));
		this.width = Quantities.require(width, Quantity.POSITION);
		this.height = Quantities.require(height, Quantity.POSITION);
	}

	public Scalar getPivot() {
		return Scalar.sqrt(width.pow(2).add(height.pow(2))).divide(2)
				.divide(Scalar.RADIAN);
	}

	public Scalar getWidth() {
		return width;
	}

	public Scalar getHeight() {
		return height;
	}

	public Scalar getWeight() {
		return Scalar.LITTLE_G.multiply(getMass());
	}

	public Scalar getFriction(double factor) {
		return getWeight().multiply(factor);
	}

}
