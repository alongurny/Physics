package physics.quantity;

import java.util.List;
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

	public static void requireSame(Quantifiable... us) {
		for (Quantifiable u : us) {
			require(u, us[0].getQuantity());
		}
	}

	public static void requireSame(List<? extends Quantifiable> us) {
		for (Quantifiable u : us) {
			require(u, us.get(0).getQuantity());
		}
	}

}
