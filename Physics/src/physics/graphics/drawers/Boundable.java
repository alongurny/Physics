package physics.graphics.drawers;

import physics.body.Body;

public interface Boundable extends Body {

	public VectorCollection getBounds();

}
