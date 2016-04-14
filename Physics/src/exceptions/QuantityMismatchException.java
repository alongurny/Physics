package exceptions;

import physics.Quantity;

@SuppressWarnings("serial")
public class QuantityMismatchException extends RuntimeException {

	public QuantityMismatchException(Quantity expected, Quantity found) {
		super("Quantity mismatch: expected " + expected + ", found " + found);
	}
}