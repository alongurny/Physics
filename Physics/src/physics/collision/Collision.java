package physics.collision;

import java.awt.geom.Area;
import java.util.Optional;

import physics.graphics.Pixel;
import physics.math.Scalar;
import physics.math.Vector;
import physics.math.VectorCollection;

public class Collision {

	public static Optional<Vector> getIntersection(Scalar pixel, VectorCollection bounds1, VectorCollection bounds2) {
		Area area = new Area(Pixel.convert(bounds1, pixel));
		area.intersect(new Area(Pixel.convert(bounds2, pixel)));
		return area.isEmpty() ? Optional.empty()
				: Optional.of(new Vector(area.getBounds().getCenterX(), area.getBounds().getCenterY()).multiply(pixel));
	}

	public static Vector getImpulse(Collidable c1, Collidable c2, Vector contactPoint) {
		return getVelocity(c1, c2, contactPoint).multiply(c1.getMass()).subtract(c1.getMomentum());
	}

	public static Vector getVelocity(Collidable a, Collidable b, Vector contactPoint) {
		Vector ce1 = a.getCollisionCircleCenter(contactPoint);
		Vector ce2 = b.getCollisionCircleCenter(contactPoint);
		return getVelocity(a.getMass(), b.getMass(), ce1, ce2, a.getVelocity(), b.getVelocity())
				.multiply(a.getElasticity());
	}

	private static Vector getVelocity(Scalar m1, Scalar m2, Vector x1, Vector x2, Vector v1, Vector v2) {
		Vector dx = x2.subtract(x1);
		Vector dv = v2.subtract(v1);
		return v1.add(dx.multiply(m2.divide(m1.add(m2)).multiply(2).multiply(dv.dot(dx)).divide(dx.dot(dx))));
	}
}
