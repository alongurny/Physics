package physics.math.algebra;

public interface MultiplicativeGroup<G extends MultiplicativeGroup<G>> extends MultiplicativeMonoid<G> {

	G inverse();

	default G divide(G other) {
		return multiply(other.inverse());
	}

}
