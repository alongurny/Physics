package physics.graphics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.Collision;
import physics.PhysicalSystem;
import physics.dimension.Dimensioned;
import physics.graphics.drawers.DrawableBody;
import physics.math.Scalar;
import physics.math.Vector;

@SuppressWarnings("serial")
public class PhysicalFrame extends Frame implements Dimensioned {

	private PhysicalSystem system;
	private List<DrawableBody> bodies;

	public PhysicalFrame(Vector focus, Color background, Scalar pixel, int dimension, Scalar dt) {
		super(focus, background, pixel);
		bodies = new ArrayList<>();
		system = new PhysicalSystem(dimension, dt);
		addDrawingListener(e -> system.progress());
		addDrawingListener(e -> bodies.forEach(a -> bodies.forEach(b -> {
			if (a != b) {
				DrawableBody.getFutureIntersection(getPixel(), a, b, system.getDt())
						.ifPresent(p -> a.addImpulse(Collision.getImpulse(a, b, p), system.getDt()));
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
