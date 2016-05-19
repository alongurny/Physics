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

}
