package physics;

public class Forces {

	public static Vector getGravity(Body a, Body b) {
		return b.getGravitationalField().get(a.getPosition()).multiply(a.getMass());
	}

}
