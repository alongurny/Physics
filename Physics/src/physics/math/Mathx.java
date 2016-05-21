package physics.math;

public class Mathx {

	public static int kroneckerDelta(int i, int j) {
		return i == j ? 1 : 0;
	}

	public static int factorial(int n) {
		int p = 1;
		for (int i = 2; i <= n; i++) {
			p *= i;
		}
		return p;
	}

	public static int choose(int a, int b) {
		return factorial(a) / (factorial(b) * factorial(a - b));
	}

	public static Scalar partition(Scalar x1, Scalar x2, double ratio) {
		return (x1.add(x2.multiply(ratio))).divide(1 + ratio);
	}

	public static Vector partition(Vector x1, Vector x2, double ratio) {
		return (x1.add(x2.multiply(ratio))).divide(1 + ratio);
	}

}
