package graphics;

import physics.Quantities;
import physics.Quantity;
import physics.Scalar;

public class Pixel {
	private static Scalar pixel = Scalar.METER.multiply(1e9);

	public static synchronized void scroll(double c) {
		if (c <= 0) {
			throw new IllegalArgumentException("must be positive");
		}
		pixel = pixel.multiply(c);
	}

	public static synchronized void set(Scalar s) {
		pixel = Quantities.require(s, Quantity.POSITION);
	}

	public static synchronized int to(Scalar s) {
		return (int) s.convert(pixel);
	}

	public static synchronized Scalar from(int n) {
		return pixel.multiply(n);
	}

	public static synchronized Scalar get() {
		return pixel;
	}
}
