package physics;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public class PhysicalSystem {
	private List<Body> bodies;
	private List<Function<Body, Vector>> forces;

	public PhysicalSystem(Body... things) {
		this.bodies = new CopyOnWriteArrayList<>(things);
		this.forces = new CopyOnWriteArrayList<>();
	}

	public void applyForces() {
		applyGravityForces();
		bodies.forEach(b -> forces.forEach(f -> b.addForce(f.apply(b))));
	}

	private synchronized void applyGravityForces() {
		for (Body t : bodies) {
			for (Body s : bodies) {
				if (t != s) {
					t.addAcceleration(s.getGravitationalField().get(
							t.getPosition()));
				}
			}
		}
	}

	public synchronized void forEach(Consumer<? super Body> action) {
		bodies.forEach(action);
	}

	public Vector getTotalMomentum() {
		Vector sum = Vector.zero(Quantity.MOMENTUM);
		for (Body b : bodies) {
			sum = sum.add(b.getMomentum());
		}
		return sum;
	}

	public Vector getTotalForce() {
		Vector sum = Vector.zero(Quantity.FORCE);
		for (Body b : bodies) {
			sum = sum.add(b.getTotalForce());
		}
		return sum;
	}

	public void addExternalForce(Function<Body, Vector> force) {
		forces.add(force);
	}
}
