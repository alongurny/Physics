package run;

import physics.Quantity;
import physics.Scalar;
import physics.Vector;

public class Test {
	public static void main(String[] args) {
		System.out.println(new Vector(Scalar.RADIAN.divide(Scalar.SECOND), 0,
				0, 1).multiply(Scalar.zero(Quantity.MOMENT_OF_INERTIA))
				.getQuantity());
	}
}
