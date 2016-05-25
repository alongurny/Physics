package physics.math.algebra;

public interface AdditiveMonoid<M extends AdditiveMonoid<M>> {

	@SafeVarargs
	public static <M extends AdditiveMonoid<M>> M sum(M... values) {
		if (values.length == 0) {
			throw new IllegalArgumentException("values must not be a zero-length array");
		}
		M sum = values[0];
		for (int i = 1; i < values.length; i++) {
			sum = sum.add(values[i]);
		}
		return sum;
	}

	M zero();

	M add(M other);

}
