package physics;

public class Util {

	public static Scalar forCircularMovement(RegularBody t, Scalar radius) {
		return Scalar.sqrt(t.getGravitationalField().get(t.getPosition().add(new Vector(radius, 1, 0, 0)))
				.getMagnitude().multiply(radius));
	}
}
