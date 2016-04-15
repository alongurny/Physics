package physics.dimension;

import java.util.Objects;

import physics.exception.DimensionMismatchException;

public class Dimensions {

	public static int require(int dimension, Dimensioned... ds) {
		for (Dimensioned d : ds) {
			Objects.requireNonNull(d);
			if (d.getDimension() != dimension) {
				throw new DimensionMismatchException(d.getDimension(), dimension);
			}
		}
		return dimension;
	}

	public static int requireSame(Dimensioned... vectors) {
		return require(vectors[0].getDimension(), vectors);
	}
}
