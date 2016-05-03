package physics.graphics.drawers;

import physics.math.Vector;

public interface Elastic extends Collidable {

	Vector getCollisionCircleCenter(Vector contactPoint);

}
