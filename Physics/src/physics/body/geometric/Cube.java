package physics.body.geometric;

import physics.body.RegularBody;
import physics.math.Scalar;
import physics.math.Vector;

public class Cube extends RegularBody {

	private Scalar s;

	public Cube(Scalar mass, Scalar charge, Vector center, Vector velocity, Scalar s) {
		super(mass, charge, center, velocity);
		this.s = s;
	}

	public Scalar getSide() {
		return s;
	}

	public Scalar getVolume() {
		return s.pow(3);
	}

}
