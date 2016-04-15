package physics.body.machine;

import physics.body.RigidBody;
import physics.math.Scalar;
import physics.math.Vector;
import physics.quantity.Quantities;
import physics.quantity.Quantity;

public class Robot extends RigidBody {

	private Scalar width, height;

	public Robot(Scalar mass, Vector center, Scalar inertiaMomemnt, Vector angularPosition, Scalar width,
			Scalar height) {
		super(mass, Scalar.zero(Quantity.CHARGE), center, Vector.zero(Quantity.VELOCITY, center.getDimension()),
				inertiaMomemnt, angularPosition, Vector.zero(Quantity.ANGULAR_VELOCITY, angularPosition.getDimension()));
		this.width = Quantities.require(width, Quantity.LENGTH);
		this.height = Quantities.require(height, Quantity.LENGTH);
	}

	public Scalar getPivot() {
		return Scalar.sqrt(width.pow(2).add(height.pow(2))).divide(2).divide(Scalar.RADIAN);
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

	public Vector getDimensions() {
		return new Vector(width, height);
	}

	public Scalar getFriction(double factor) {
		return getWeight().multiply(factor);
	}

}
