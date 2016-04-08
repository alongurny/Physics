package physics;

import java.util.Objects;

import exceptions.QuantityMismatchException;
import exceptions.VectorLengthMismatchException;

public class Quantities {

	public static Quantity require(Quantity found, Quantity expected) {
		Objects.requireNonNull(expected, "Not null!");
		Objects.requireNonNull(found, "Not null!");
		if (!expected.equals(found)) {
			throw new QuantityMismatchException(expected, found);
		}
		return found;
	}

	public static <T extends Measurable> T require(T m, Quantity expected) {
		require(m.getQuantity(), expected);
		return m;
	}

	public static int requireSameLength(Vector... vectors) {
		for (Vector v : vectors) {
			if (v.getLength() != vectors[0].getLength()) {
				throw new VectorLengthMismatchException();
			}
		}
		return vectors[0].getLength();
	}

	@SafeVarargs
	public static void requireSameQuantity(Measurable... us) {
		for (Measurable u : us) {
			require(u, us[0].getQuantity());
		}
	}

}
