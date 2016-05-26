package physics.math.algebra;

import java.util.Iterator;

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

	public static <M extends AdditiveMonoid<M>> M sum(Iterable<M> values) {
		Iterator<M> iter = values.iterator();
		if (!iter.hasNext()) {
			throw new IllegalArgumentException("values must not be a zero-length iterable");
		}
		M sum = iter.next();
		while (iter.hasNext()) {
			sum = sum.add(iter.next());
		}
		return sum;
	}

	M zero();

	M add(M other);

}
