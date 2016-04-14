package physics;

import graphics.drawers.Drawer2D;

public class Util {

	public static Scalar forCircularMovement(Body t, Scalar radius) {
		return Scalar.sqrt(t.getGravitationalField().get(t.getPosition().add(new Vector(radius, 1, 0, 0)))
				.getMagnitude().multiply(radius));
	}

	public static Vector getVelocity(Scalar m1, Scalar m2, Vector v1, Vector v2, double phi) {
		double theta1 = Scalar.atan2(v1.get(1), v1.get(0));
		double theta2 = Scalar.atan2(v2.get(1), v2.get(0));
		Scalar r1 = v1.getMagnitude();
		Scalar r2 = v2.getMagnitude();
		Vector v = new Vector(
				r1.multiply(Math.cos(theta1 - phi)).multiply(m1.subtract(m2))
						.add(m2.multiply(r2).multiply(Math.cos(theta2 - phi)).multiply(2)).divide(m1.add(m2)),
				r1.multiply(Math.sin(theta1 - phi)));
		return Matrix.rotation(phi).multiply(Matrix.column(v)).getColumn(0);
	}

	public static <T extends Body & Drawer2D, S extends Body & Drawer2D> Vector getVelocity(T c1, S c2,
			Vector contactPoint) {
		double phi = c2.getNormalAngle(contactPoint);
		return getVelocity(c1.getMass(), c2.getMass(), c1.getVelocity(), c2.getVelocity(), phi);
	}

	public static Vector getImpulse(Scalar m1, Scalar m2, Vector v1, Vector v2, double phi) {
		return getVelocity(m1, m2, v1, v2, phi).subtract(v1).multiply(m1);
	}

	public static <T extends Body & Drawer2D, S extends Body & Drawer2D> Vector getImpulse(T c1, S c2) {
		Vector r = c2.getPosition().subtract(c1.getPosition());
		double phi = Scalar.atan2(r.get(1), r.get(0));
		return getImpulse(c1.getMass(), c2.getMass(), c1.getVelocity(), c2.getVelocity(), phi);
	}

}
