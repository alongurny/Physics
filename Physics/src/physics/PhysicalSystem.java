package physics;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import physics.interfaces.Movable;

public class PhysicalSystem {
	private List<Movable> regularBodies;
	private List<Function<Movable, Vector>> forces;
	private List<BiFunction<Movable, Movable, Vector>> biforces;

	private int length;

	public PhysicalSystem(int dimensions, Movable... things) {
		this.regularBodies = new CopyOnWriteArrayList<>(things);
		this.forces = new CopyOnWriteArrayList<>();
		this.biforces = new CopyOnWriteArrayList<>();
		this.length = dimensions;
	}

	public PhysicalSystem(int dimensions, List<? extends Movable> things) {
		this.regularBodies = new CopyOnWriteArrayList<>(things);
		this.forces = new CopyOnWriteArrayList<>();
		this.biforces = new CopyOnWriteArrayList<>();
		this.length = dimensions;
	}

	public void applyForces() {
		regularBodies.forEach(b -> forces.forEach(f -> b.addForce(f.apply(b))));
		biforces.forEach(f -> regularBodies.forEach(b -> regularBodies.forEach(c -> {
			if (b != c) {
				b.addForce(f.apply(b, c));
			}
		})));
	}

	public synchronized void forEach(Consumer<? super Movable> action) {
		regularBodies.forEach(action);
	}

	public Scalar getTotalScalar(Quantity quantity, Function<Movable, Scalar> f) {
		Scalar sum = Scalar.zero(quantity);
		for (Movable b : regularBodies) {
			sum = sum.add(f.apply(b));
		}
		return sum;
	}

	public Vector getTotalVector(Quantity quantity, Function<Movable, Vector> f) {
		Vector sum = Vector.zero(quantity, length);
		for (Movable b : regularBodies) {
			sum = sum.add(f.apply(b));
		}
		return sum;
	}

	public Vector getTotalMomentum() {
		return getTotalVector(Quantity.MOMENTUM, Movable::getMomentum);
	}

	public Vector getTotalForce() {
		return getTotalVector(Quantity.FORCE, Movable::getTotalForce);
	}

	public void addExternalForce(Function<Movable, Vector> force) {
		forces.add(force);
	}

	public void addExternalBiForce(BiFunction<Movable, Movable, Vector> force) {
		biforces.add(force);
	}
}
