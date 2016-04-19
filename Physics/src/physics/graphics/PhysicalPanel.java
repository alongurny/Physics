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
public class PhysicalPanel extends Panel implements Dimensioned {

	private PhysicalSystem system;
	private List<DrawableBody> bodies;

	public PhysicalPanel(int width, int height, Vector focus, Color background, Scalar pixel, int dimension,
			Scalar dt) {
		super(width, height, focus, background, pixel);
		bodies = new ArrayList<>();
		system = new PhysicalSystem(dimension, dt);
		addDrawingListener(e -> {
			system.progress();
		});
		addDrawingListener(e -> {
			bodies.forEach(a -> bodies.forEach(b -> {
				if (a != b && system.contains(a)) {
					DrawableBody.getFutureIntersection(getPixel(), a, b, system.getDt())
							.ifPresent(p -> a.addImpulse(Collision.getImpulse(a, b, p), system.getDt()));
				}
			}));
		});
	}

	public void addDrawableBody(DrawableBody b, boolean movable) {
		addDrawable(b);
		if (movable) {
			system.add(b);
		}
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
