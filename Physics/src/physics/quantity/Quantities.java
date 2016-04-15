package physics.quantity;

import java.util.Objects;

import physics.exception.QuantityMismatchException;

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

	@SafeVarargs
	public static void requireSameQuantity(Measurable... us) {
		for (Measurable u : us) {
			require(u, us[0].getQuantity());
		}
	}

}
