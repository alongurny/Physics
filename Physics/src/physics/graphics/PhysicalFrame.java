package physics.graphics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import graphics.drawers.DrawableBody;
import physics.Collision;
import physics.PhysicalSystem;
import physics.body.Movable;
import physics.dimension.Dimensioned;
import physics.math.Scalar;
import physics.math.Vector;

@SuppressWarnings("serial")
public class PhysicalFrame extends Frame implements Dimensioned {

	private PhysicalSystem system;
	private List<DrawableBody> bodies;

	public PhysicalFrame(Vector focus, Color background, Scalar pixel, int dimension) {
		super(focus, background, pixel);
		bodies = new ArrayList<>();
		system = new PhysicalSystem(dimension);
		addDrawingListener(e -> system.applyForces());
		addDrawingListener(e -> system.forEach(Movable::move));
		addDrawingListener(e -> bodies.forEach(a -> bodies.forEach(b -> {
			if (a != b) {
				DrawableBody.getFutureIntersection(getPixel(), a, b)
						.ifPresent(p -> a.addImpulse(Collision.getImpulse(a, b, p)));
			}
		})));
	}

	public void addDrawableBody(DrawableBody b) {
		addDrawable(b);
		system.add(b);
		bodies.add(b);
	}

	@Override
	public int getDimension() {
		return system.getDimension();
	}

	public PhysicalSystem getPhysicalSystem() {
		return system;
	}

}
