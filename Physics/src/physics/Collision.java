package physics;

import graphics.drawers.DrawableBody;
import physics.interfaces.Body;

public class Collision {

	public static Vector getVelocity(Scalar m1, Scalar m2, Vector x1, Vector x2, Vector v1, Vector v2) {
		Vector dx = x2.subtract(x1);
		Vector dv = v2.subtract(v1);
		return v1.add(dx.multiply(m2.divide(m1.add(m2)).multiply(2).multiply(dv.dot(dx)).divide(dx.dot(dx))));
	}

	public static <T extends Body & DrawableBody, S extends RegularBody & DrawableBody> Vector getVelocity(T c1, S c2,
			Vector contactPoint) {
		Vector ce1 = c1.getCircleCenter(contactPoint);
		Vector ce2 = c2.getCircleCenter(contactPoint);
		return getVelocity(c1.getMass(), c2.getMass(), ce1, ce2, c1.getVelocity(), c2.getVelocity());
	}

	public static <T extends Body & DrawableBody, S extends RegularBody & DrawableBody> Vector getImpulse(T c1, S c2,
			Vector contactPoint) {
		return getVelocity(c1, c2, contactPoint).multiply(c1.getMass()).subtract(c1.getMomentum());
	}
}
