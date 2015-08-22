package physics;

public class Debug {
	public static <T extends Measurable> T debugQuantity(T m) {
		System.err.println(m.getQuantity());
		return m;
	}
}
