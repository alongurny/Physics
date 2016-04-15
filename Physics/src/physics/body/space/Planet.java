package physics.body.space;

import physics.body.geometric.Sphere;
import physics.math.Scalar;
import physics.math.Vector;
import physics.quantity.Quantity;

public class Planet extends Sphere {

	public Planet(Scalar mass, Vector center, Vector velocity, Scalar radius) {
		super(mass, Scalar.zero(Quantity.CHARGE), center, velocity, radius);
	}
}
