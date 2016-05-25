package physics.math.algebra;

public interface MultiplicativeMonoid<M extends MultiplicativeMonoid<M>> {

	@SafeVarargs
	public static <M extends MultiplicativeMonoid<M>> M sum(M... values) {
		if (values.length == 0) {
			throw new IllegalArgumentException("values must not be a zero-length array");
		}
		M product = values[0];
		for (int i = 1; i < values.length; i++) {
			product = product.multiply(values[i]);
		}
		return product;
	}

	M unit();

	M multiply(M other);

}
