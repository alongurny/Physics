package physics;

import physics.graphics.drawers.DrawableBody;
import physics.math.Scalar;
import physics.math.Vector;

public class Collision {

	public static Vector getImpulse(DrawableBody c1, DrawableBody c2, Vector contactPoint) {
		return getVelocity(c1, c2, contactPoint).multiply(c1.getMass()).subtract(c1.getMomentum());
	}

	public static Vector getVelocity(DrawableBody a, DrawableBody b, Vector contactPoint) {
		Vector ce1 = a.getCollisionCircleCenter(contactPoint);
		Vector ce2 = b.getCollisionCircleCenter(contactPoint);
		return getVelocity(a.getMass(), b.getMass(), ce1, ce2, a.getVelocity(), b.getVelocity());
	}

	public static Vector getVelocity(Scalar m1, Scalar m2, Vector x1, Vector x2, Vector v1, Vector v2) {
		Vector dx = x2.subtract(x1);
		Vector dv = v2.subtract(v1);
		return v1.add(dx.multiply(m2.divide(m1.add(m2)).multiply(2).multiply(dv.dot(dx)).divide(dx.dot(dx))));
	}
}
