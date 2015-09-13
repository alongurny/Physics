package physics;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class PhysicalSystem {
	private List<Body> bodies;

	public PhysicalSystem(Body... things) {
		this.bodies = new CopyOnWriteArrayList<Body>(things);
	}

	public synchronized void applyGravityForces() {
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
}
