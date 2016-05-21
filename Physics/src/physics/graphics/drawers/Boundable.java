package physics.graphics.drawers;

import physics.body.Body;

public interface Boundable extends Body {

	VectorCollection getRelativeBounds();

	default VectorCollection getAbsoluteBounds() {
		return getRelativeBounds().map(getPosition()::add);
	}
}
