package physics.sound.midi;

import physics.math.Scalar;

public class Frequencies {

	public static final Scalar A4 = Scalar.HERTZ.multiply(440);
	public static final int EXP_A4 = 58;
	public static final double DIFF = Math.pow(2, 1.0 / 12.0);

	public int get(Scalar frequency) {
		double pow = frequency.convert(A4);
		int exp = EXP_A4 + (int) Math.round(Math.log(pow) / Math.log(DIFF));
		if (exp < 0 || exp > 127) {
			throw new IllegalArgumentException("Frequency is out of range");
		}
		return exp;
	}

}
