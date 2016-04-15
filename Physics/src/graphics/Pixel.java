package graphics;

import physics.IntVector;
import physics.Scalar;
import physics.Vector;

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

}
