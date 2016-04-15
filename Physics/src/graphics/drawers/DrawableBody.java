package graphics.drawers;

import java.awt.Polygon;
import java.awt.geom.Area;
import java.util.Optional;

import physics.body.Movable;
import physics.math.IntVector;
import physics.math.Scalar;
import physics.math.Vector;

public interface DrawableBody extends Drawable, Movable {

	public static Optional<Vector> getIntersection(Scalar pixel, DrawableBody a, DrawableBody b) {
		Area sa = new Area(a.getBounds(pixel));
		sa.intersect(new Area(b.getBounds(pixel)));
		return sa.isEmpty() ? Optional.empty()
				: Optional.of(new Vector(sa.getBounds().getCenterX(), sa.getBounds().getCenterY()).multiply(pixel));
	}

	public static Optional<Vector> getFutureIntersection(Scalar pixel, DrawableBody a, DrawableBody b) {
		IntVector va = a.getVelocity(pixel);
		IntVector vb = b.getVelocity(pixel);
		a.getBounds(pixel).translate(va.get(0), va.get(1));
		b.getBounds(pixel).translate(vb.get(0), vb.get(1));
		Optional<Vector> result = getIntersection(pixel, a, b);
		a.getBounds(pixel).translate(-va.get(0), -va.get(1));
		b.getBounds(pixel).translate(-vb.get(0), -vb.get(1));
		return result;
	}

	Polygon getBounds(Scalar pixel);

	IntVector getPosition(Scalar pixel);

	IntVector getVelocity(Scalar pixel);

	Vector getCollisionCircleCenter(Vector contactPoint);

}
