package physics;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class PhysicalSystem {
	private List<Body> body;

	public PhysicalSystem(Body... things) {
		this.body = new CopyOnWriteArrayList<Body>(things);
	}

	public synchronized void applyGravityForces() {
		for (Body t : body) {
			for (Body s : body) {
				if (t != s) {
					t.addAcceleration(t.getAngleWith(s).multiply(
							s.getGravitationalField(t.distance(s))));
				}
			}
		}
	}

	public synchronized void forEach(Consumer<? super Body> action) {
		body.forEach(action);
	}

	public Vector getTotalMomentum() {
		Vector sum = Vector.zero(Quantity.MOMENTUM);
		for (Body b : body) {
			sum = sum.add(b.getMomentum());
		}
		return sum;
	}
}
