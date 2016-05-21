package physics.graphics.drawers;

import java.util.Optional;

import physics.Collision;
import physics.body.Movable;
import physics.math.Scalar;
import physics.math.Vector;

public interface Collidable extends Boundable, Movable {

	default Optional<Vector> getFutureIntersection(Scalar pixel, Boundable b, Scalar dt) {
		VectorCollection bounds1 = this.getAbsoluteBounds().map(v -> v.add(this.getVelocity().multiply(dt)));
		VectorCollection bounds2 = b.getAbsoluteBounds().map(v -> v.add(b.getVelocity().multiply(dt)));
		return Collision.getIntersection(pixel, bounds1, bounds2);
	}

	void onCollision(Scalar pixel, Collidable other, Scalar dt);

}
