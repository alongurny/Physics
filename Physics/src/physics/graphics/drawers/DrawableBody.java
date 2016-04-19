package physics.graphics.drawers;

import java.awt.Polygon;
import java.awt.geom.Area;
import java.util.Optional;

import physics.body.Movable;
import physics.graphics.Pixel;
import physics.math.IntVector;
import physics.math.Scalar;
import physics.math.Vector;

public interface DrawableBody extends Drawable, Movable {

	public static Optional<Vector> getFutureIntersection(Scalar pixel, DrawableBody a, DrawableBody b, Scalar dt) {
		IntVector va = Pixel.convert(a.getVelocity().multiply(dt), pixel);
		IntVector vb = Pixel.convert(b.getVelocity().multiply(dt), pixel);
		a.getBounds(pixel).translate(va.get(0), va.get(1));
		b.getBounds(pixel).translate(vb.get(0), vb.get(1));
		Optional<Vector> result = getIntersection(pixel, a, b);
		a.getBounds(pixel).translate(-va.get(0), -va.get(1));
		b.getBounds(pixel).translate(-vb.get(0), -vb.get(1));
		return result;
	}

	public static Optional<Vector> getIntersection(Scalar pixel, DrawableBody a, DrawableBody b) {
		Area sa = new Area(a.getBounds(pixel));
		sa.intersect(new Area(b.getBounds(pixel)));
		return sa.isEmpty() ? Optional.empty()
				: Optional.of(new Vector(sa.getBounds().getCenterX(), sa.getBounds().getCenterY()).multiply(pixel));
	}

	Polygon getBounds(Scalar pixel);

	Vector getCollisionCircleCenter(Vector contactPoint);

}
