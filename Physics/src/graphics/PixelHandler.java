package graphics;

import physics.Quantities;
import physics.Quantity;
import physics.Scalar;

public class PixelHandler {

	private Scalar pixel;

	public PixelHandler(Scalar pixel) {
		this.pixel = pixel;
	}

	public synchronized void scroll(double c) {
		if (c <= 0) {
			throw new IllegalArgumentException("must be positive");
		}
		pixel = pixel.multiply(c);
	}

	public synchronized void set(Scalar s) {
		pixel = Quantities.require(s, Quantity.LENGTH);
	}

	public static synchronized int to(Scalar distancePerPixel, Scalar s) {
		return (int) s.convert(distancePerPixel);
	}

	public static synchronized Scalar from(Scalar distancePerPixel, int n) {
		return distancePerPixel.multiply(n);
	}

	public synchronized int to(Scalar s) {
		return (int) s.convert(pixel);
	}

	public synchronized Scalar from(int n) {
		return pixel.multiply(n);
	}

	public Scalar get() {
		return pixel;
	}

}
