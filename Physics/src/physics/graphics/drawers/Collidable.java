package physics.graphics.drawers;

import physics.body.Movable;

public interface Collidable extends Boundable, Movable {

	void onCollision(Collidable other);

}
