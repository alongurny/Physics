package physics.graphics;

import java.awt.Polygon;

import physics.graphics.drawers.VectorCollection;
import physics.math.IntVector;
import physics.math.Scalar;
import physics.math.Vector;

public class Pixel {

	public static int convert(Scalar s, Scalar pixel) {
		return (int) s.convert(pixel);
	}

	public static IntVector convert(Vector v, Scalar pixel) {
		int[] arr = new int[v.getDimension()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = convert(v.get(i), pixel);
		}
		return new IntVector(arr);
	}

	public static Polygon convert(VectorCollection bounds, Scalar pixel) {
		int size = bounds.size();
		int[] xs = new int[size];
		int[] ys = new int[size];
		for (int i = 0; i < size; i++) {
			IntVector iv = convert(bounds.get(i), pixel);
			xs[i] = iv.get(0);
			ys[i] = iv.get(1);
		}
		return new Polygon(xs, ys, size);
	}

}
