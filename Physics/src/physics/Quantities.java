package physics;

import java.util.Objects;

import exceptions.QuantityMismatchException;

public class Quantities {

	public static Quantity require(Quantity found, Quantity expected) {
		Objects.requireNonNull(expected, "Not null!");
		Objects.requireNonNull(found, "Not null!");
		if (!expected.equals(found)) {
			throw new QuantityMismatchException(expected, found);
		}
		return found;
	}

	public static Scalar require(Scalar s, Quantity expected) {
		require(s.getQuantity(), expected);
		return s;
	}

	public static Vector require(Vector v, Quantity expected) {
		require(v.getQuantity(), expected);
		return v;
	}

	public static void requireSame(Scalar... us) {
		for (Scalar u : us) {
			Objects.requireNonNull(u);
			require(u, us[0].getQuantity());
		}
	}

	public static void requireSame(Vector... vs) {
		for (Vector v : vs) {
			Objects.requireNonNull(v);
			require(v, vs[0].getQuantity());
		}
	}

}
