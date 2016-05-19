package physics.body;

import physics.math.Mathx;
import physics.math.Matrix;
import physics.math.Scalar;

public class MomentsOfInertia {
	public static Matrix ball(int radiusDimension, Scalar mass, Scalar radius) {
		int dimension = Mathx.choose(radiusDimension, 2);
		Scalar i = mass.multiply(0.4).multiply(radius.divide(Scalar.RADIAN).pow(2));
		return Matrix.identity(dimension).multiply(i);
	}
}
