package exceptions;

@SuppressWarnings("serial")
public class VectorLengthMismatchException extends RuntimeException {

	public VectorLengthMismatchException() {
		super("Not all vectors are of the same length");
	}
}
