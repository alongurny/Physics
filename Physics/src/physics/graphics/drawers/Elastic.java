package physics.graphics.drawers;

import physics.Collision;
import physics.math.Scalar;
import physics.math.Vector;

public interface Elastic extends Collidable {

	Vector getCollisionCircleCenter(Vector contactPoint);

	@Override
	default void onCollision(Scalar pixel, Collidable other, Scalar dt) {
		if (other instanceof Elastic) {
			Elastic e = (Elastic) other;
			getFutureIntersection(pixel, e, dt).ifPresent(p -> addImpulse(Collision.getImpulse(this, e, p)));
		}
	}

}
