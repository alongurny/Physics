package physics;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class PhysicalSystem {
	private List<Body> bodies;
	private List<Function<Body, Vector>> forces;
	private List<BiFunction<Body, Body, Vector>> biforces;

	private int length;

	public PhysicalSystem(int dimensions, Body... things) {
		this.bodies = new CopyOnWriteArrayList<>(things);
		this.forces = new CopyOnWriteArrayList<>();
		this.biforces = new CopyOnWriteArrayList<>();
		this.length = dimensions;
	}

	public void applyForces() {
		bodies.forEach(b -> forces.forEach(f -> b.addForce(f.apply(b))));
		biforces.forEach(f -> bodies.forEach(b -> bodies.forEach(c -> {
			if (b != c) {
				b.addForce(f.apply(b, c));
			}
		})));
	}

	public synchronized void forEach(Consumer<? super Body> action) {
		bodies.forEach(action);
	}

	public Vector getTotal(Quantity quantity, Function<Body, Vector> f) {
		Vector sum = Vector.zero(quantity, length);
		for (Body b : bodies) {
			sum = sum.add(f.apply(b));
		}
		return sum;
	}

	public Vector getTotalMomentum() {
		return getTotal(Quantity.MOMENTUM, Body::getMomentum);
	}

	public Vector getTotalForce() {
		return getTotal(Quantity.FORCE, Body::getTotalForce);
	}

	public void addExternalForce(Function<Body, Vector> force) {
		forces.add(force);
	}

	public void addExternalBiForce(BiFunction<Body, Body, Vector> force) {
		biforces.add(force);
	}
}
