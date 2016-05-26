package physics.collision;

import physics.body.Body;
import physics.math.VectorCollection;

public interface Boundable extends Body {

	VectorCollection getRelativeBounds();

	default VectorCollection getAbsoluteBounds() {
		return getRelativeBounds().map(getPosition()::add);
	}
}
