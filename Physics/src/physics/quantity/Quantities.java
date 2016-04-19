package physics.quantity;

import java.util.Objects;

public class Quantities {

	public static Quantity require(Quantity found, Quantity expected) {
		Objects.requireNonNull(expected);
		Objects.requireNonNull(found);
		if (!expected.equals(found)) {
			throw new QuantityMismatchException(expected, found);
		}
		return found;
	}

	public static <T extends Quantifiable> T require(T m, Quantity expected) {
		require(m.getQuantity(), expected);
		return m;
	}

	@SafeVarargs
	public static void requireSame(Quantifiable... us) {
		for (Quantifiable u : us) {
			require(u, us[0].getQuantity());
		}
	}

}
