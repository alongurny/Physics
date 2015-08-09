package physics;

public class Util {

	public static Scalar forCircularMovement(Body t, Scalar radius) {
		return Scalar.sqrt(t.getGravitationalField(radius).multiply(radius));
	}

}
