package exceptions;

public class VectorLengthMismatchException extends RuntimeException {

	public VectorLengthMismatchException() {
		super("Not all vectors are of the same length");
	}
}
