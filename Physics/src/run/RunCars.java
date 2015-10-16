package run;

import physics.Quantity;
import physics.Scalar;
import physics.UnitSystem;
import physics.Vector;
import bodies.Car;

public class RunCars {
	public static void main(String[] args) {
		Car c = new Car(new Scalar(Scalar.KILOGRAM, 1000),
				Vector.POSITION_ORIGIN, UnitSystem.SI.get(
						Quantity.MOMENT_OF_INERTIA).multiply(1000),
				Vector.zero(Quantity.ANGLE));

	}
}
