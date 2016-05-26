package physics.body.geometric;

import physics.body.AbstractBody;
import physics.math.Scalar;
import physics.math.Vector;

public class Sphere extends AbstractBody {

	private Scalar radius;

	public Sphere(Scalar mass, Scalar charge, Vector center, Vector velocity, Scalar radius) {
		super(mass, charge, center, velocity);
		this.radius = radius;
	}

	public Scalar getRadius() {
		return radius;
	}

	public Scalar getVolume() {
		return radius.pow(3).multiply(Math.PI * 4.0 / 3.0);
	}

}
