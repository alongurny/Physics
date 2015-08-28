package physics;

public class Electron extends Body {

	public static final Scalar MASS = Scalar.KILOGRAM.multiply(9.11e-31);
	public static final Scalar CHARGE = Scalar.E_CHARGE;

	public Electron(Vector position, Vector velocity) {
		super(MASS, CHARGE, position, velocity);
	}

}
