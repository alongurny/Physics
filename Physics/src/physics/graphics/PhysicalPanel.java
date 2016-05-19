package physics.graphics;

import java.util.ArrayList;
import java.util.List;

import physics.Collision;
import physics.LinearSystem;
import physics.dimension.Dimensioned;
import physics.graphics.drawers.Drawable;
import physics.graphics.drawers.Elastic;
import physics.math.Scalar;
import physics.math.Vector;

@SuppressWarnings("serial")
public class PhysicalPanel extends Panel implements Dimensioned {

	public static final Scalar DEFAULT_TIME_SPAN = Scalar.SECOND.multiply(15e-3);
	private LinearSystem system;
	private List<Elastic> bodies;

	public PhysicalPanel(int width, int height, Vector focus, Scalar pixel, int dimension, Scalar dt) {
		super(width, height, focus, pixel);
		bodies = new ArrayList<>();
		system = new LinearSystem(dimension, dt);
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

	public LinearSystem getPhysicalSystem() {
		return system;
	}

	public Scalar getDt() {
		return system.getDt();
	}

}
