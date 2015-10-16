package run;

import physics.Quantity;
import physics.Scalar;
import physics.Vector;
import bodies.Sphere;

public class Test {
	public static void main(String[] args) {
		Sphere sp = new Sphere(Scalar.KILOGRAM, Scalar.COULOMB.multiply(1e-9),
				Vector.POSITION_ORIGIN, Vector.zero(Quantity.VELOCITY),
				Vector.zero(Quantity.ANGLE),
				Vector.zero(Quantity.ANGULAR_VELOCITY), Scalar.METER);
		System.out.println(sp.getGravitationalField().get(
				Vector.UNIT_X.multiply(Scalar.METER)));
		System.out.println(sp.getElectricalField().get(
				Vector.UNIT_X.multiply(Scalar.METER)));
	}
}
