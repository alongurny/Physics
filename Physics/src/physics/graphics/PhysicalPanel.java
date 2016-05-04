package physics.graphics;

import java.util.ArrayList;
import java.util.List;

import physics.Collision;
import physics.PhysicalSystem;
import physics.dimension.Dimensioned;
import physics.graphics.drawers.Drawable;
import physics.graphics.drawers.Elastic;
import physics.math.Scalar;
import physics.math.Vector;

@SuppressWarnings("serial")
public class PhysicalPanel extends Panel implements Dimensioned {

	private PhysicalSystem system;
	private List<Elastic> bodies;

	public PhysicalPanel(int width, int height, Vector focus, Scalar pixel, int dimension, Scalar dt) {
		super(width, height, focus, pixel);
		bodies = new ArrayList<>();
		system = new PhysicalSystem(dimension, dt);
		addDrawingListener(e -> {
			system.progress();
		});
		addDrawingListener(e -> {
			bodies.forEach(a -> bodies.forEach(b -> {
				if (a != b && system.contains(a)) {
					Collision.getFutureIntersection(getPixel(), a, b, system.getDt()).ifPresent(p -> {
						a.addImpulse(Collision.getImpulse(a, b, p));
						a.onCollision(b);
					});
				}
			}));
		});
	}

	public void add(Elastic b, boolean movable) {
		if (b instanceof Drawable) {
			addDrawable((Drawable) b);
		}
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
