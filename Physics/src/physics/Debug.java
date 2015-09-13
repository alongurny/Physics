package physics;

public class Debug {
	public static <T extends Measurable> T debugQuantity(T m) {
		System.err.println("Debug: " + m.getQuantity());
		return m;
	}
}
