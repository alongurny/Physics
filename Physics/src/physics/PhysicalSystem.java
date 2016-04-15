package physics;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import physics.body.Movable;
import physics.dimension.Dimensioned;
import physics.math.Scalar;
import physics.math.Vector;
import physics.quantity.Quantity;

public class PhysicalSystem implements Dimensioned {
	private List<Movable> bodies;
	private List<Function<Movable, Vector>> forces;
	private List<BiFunction<Movable, Movable, Vector>> biforces;

	private int dimension;

	public PhysicalSystem(int dimension, Movable... things) {
		this.bodies = new CopyOnWriteArrayList<>(things);
		this.forces = new CopyOnWriteArrayList<>();
		this.biforces = new CopyOnWriteArrayList<>();
		this.dimension = dimension;
	}

	public PhysicalSystem(int dimension, List<? extends Movable> things) {
		this.bodies = new CopyOnWriteArrayList<>(things);
		this.forces = new CopyOnWriteArrayList<>();
		this.biforces = new CopyOnWriteArrayList<>();
		this.dimension = dimension;
	}

	public void applyForces() {
		bodies.forEach(b -> forces.forEach(f -> b.addForce(f.apply(b))));
		biforces.forEach(f -> bodies.forEach(b -> bodies.forEach(c -> {
			if (b != c) {
				b.addForce(f.apply(b, c));
			}
		})));
	}

	public void forEach(Consumer<? super Movable> action) {
		bodies.forEach(action);
	}

	public Scalar getTotalScalar(Quantity quantity, Function<Movable, Scalar> f) {
		Scalar sum = Scalar.zero(quantity);
		for (Movable b : bodies) {
			sum = sum.add(f.apply(b));
		}
		return sum;
	}

	public Vector getTotalVector(Quantity quantity, Function<Movable, Vector> f) {
		Vector sum = Vector.zero(quantity, dimension);
		for (Movable b : bodies) {
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

	public void add(Movable b) {
		bodies.add(b);
	}

	@Override
	public int getDimension() {
		return dimension;
	}
}
