package physics;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import physics.body.Movable;
import physics.dimension.Dimensioned;
import physics.math.Scalar;
import physics.math.Vector;
import physics.math.algebra.AdditiveMonoid;
import physics.quantity.Quantity;

public class LinearSystem implements Dimensioned {

	private List<Movable> bodies;
	private List<Function<Movable, Vector>> forces;
	private List<BiFunction<Movable, Movable, Vector>> biforces;

	private int dimension;
	private Scalar dt;
	private Scalar totalTime;

	public LinearSystem(int dimension, Scalar dt) {
		this.bodies = new CopyOnWriteArrayList<>();
		this.forces = new CopyOnWriteArrayList<>();
		this.biforces = new CopyOnWriteArrayList<>();
		this.dt = dt;
		this.dimension = dimension;
		this.totalTime = Scalar.zero(Quantity.TIME);
	}

	public void add(Movable b) {
		bodies.add(b);
	}

	public void addExternalBiForce(BiFunction<Movable, Movable, Vector> force) {
		biforces.add(force);
	}

	public void addExternalForce(Function<Movable, Vector> force) {
		forces.add(force);
	}

	@Override
	public int getDimension() {
		return dimension;
	}

	public Scalar getDt() {
		return dt;
	}

	public Vector getTotalForce() {
		return getTotalVector(Movable::getTotalForce);
	}

	public Vector getTotalMomentum() {
		return getTotalVector(Movable::getMomentum);
	}

	public Scalar getTotalScalar(Function<Movable, Scalar> f) {
		return AdditiveMonoid.sum(bodies.stream().map(f).collect(Collectors.toList()));
	}

	public Vector getTotalVector(Function<Movable, Vector> f) {
		return AdditiveMonoid.sum(bodies.stream().map(f).collect(Collectors.toList()));
	}

	protected void forEach(Consumer<? super Movable> action) {
		bodies.forEach(action);
	}

	public void progress() {
		bodies.forEach(b -> forces.forEach(f -> b.addForce(f.apply(b))));
		biforces.forEach(f -> {
			bodies.forEach(b -> bodies.forEach(c -> {
				if (b != c) {
					b.addForce(f.apply(b, c));
				}
			}));
		});
		bodies.forEach(b -> b.move(dt));
		totalTime = totalTime.add(dt);
	}

	public Scalar getTotalTime() {
		return totalTime;
	}

	public boolean contains(Movable b) {
		return bodies.contains(b);
	}
}
