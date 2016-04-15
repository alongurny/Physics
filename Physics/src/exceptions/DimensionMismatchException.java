package exceptions;

@SuppressWarnings("serial")
public class DimensionMismatchException extends RuntimeException {

	public DimensionMismatchException(int expected, int found) {
		super("Dimension mismatch: expected " + expected + ", found " + found);
	}
}
