package run;

import physics.Quantity;
import physics.Scalar;
import physics.Vector;

public class Test {
	public static void main(String[] args) {
		Vector v = new Vector(Scalar.ONE, 3, -3, 1);
		Vector u = new Vector(Scalar.ONE, 4, 9, 2);
		System.out.println(v.cross(u));
		System.out.println(Vector.zero(Quantity.ANGULAR_VELOCITY));
	}
}
