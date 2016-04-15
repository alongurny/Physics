package physics.body.atom;

import physics.body.geometric.Sphere;
import physics.math.Scalar;
import physics.math.Vector;

public class Electron extends Sphere {

	public static final Scalar MASS = Scalar.KILOGRAM.multiply(9.11e-31);
	public static final Scalar CHARGE = Scalar.E_CHARGE;
	public static final Scalar RADIUS = Scalar.METER.multiply(2.81794e-15);

	public Electron(Vector position, Vector velocity) {
		super(MASS, CHARGE, position, velocity, RADIUS);
	}

}
