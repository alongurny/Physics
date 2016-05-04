package physics.math;

import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;

import physics.dimension.Dimensioned;
import physics.dimension.Dimensions;

public final class IntVector implements Dimensioned {
	public static IntVector axis(int length, int loc) {
		return new IntVector(length, i -> Mathx.kroneckerDelta(i, loc));
	}

	public static IntVector sum(IntVector... vectors) {
		if (vectors.length == 0) {
			throw new IllegalArgumentException("Must have at least one vector");
		}
		Dimensions.requireSame(vectors);
		IntVector res = vectors[0];
		for (int i = 1; i < vectors.length; i++) {
			res = res.add(vectors[i]);
		}
		return res;
	}

	public static IntVector zero(int length) {
		return new IntVector(length, i -> 0);
	}

	private final int[] entries;

	public IntVector(int... entries) {
		this(entries.length, i -> entries[i]);

	}

	private IntVector(int length, IntUnaryOperator f) {
		entries = new int[length];
		for (int i = 0; i < entries.length; i++) {
			this.entries[i] = f.applyAsInt(i);
		}
	}

	public IntVector add(IntVector w) {
		return applyBinaryOperator(w, (a, b) -> a + b);
	}

	private IntVector applyBinaryOperator(IntVector other, IntBinaryOperator op) {
		Dimensions.requireSame(this, other);
		return new IntVector(getDimension(), i -> op.applyAsInt(get(i), other.get(i)));
	}

	public IntVector divide(int n) {
		if (!isDivisible(n)) {
			throw new IntVectorArithmeticException("Cannot divide this instance of IntVector by " + n);
		}
		return new IntVector(getDimension(), i -> get(i) / n);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof IntVector && equalsIntVector((IntVector) obj);
	}

	private boolean equalsIntVector(IntVector p) {
		for (int i = 0; i < getDimension(); i++) {
			if (entries[i] != p.entries[i]) {
				return false;
			}
		}
		return true;
	}

	public int get(int i) {
		return entries[i];
	}

	public int getDimension() {
		return entries.length;
	}

	public boolean isDivisible(int n) {
		for (int entry : entries) {
			if (entry % n != 0) {
				return false;
			}
		}
		return true;
	}

	public IntVector multiply(int n) {
		return new IntVector(getDimension(), i -> get(i) * n);
	}

	public IntVector negate() {
		return new IntVector(getDimension(), i -> -get(i));
	}

	public IntVector subtract(IntVector v) {
		return this.add(v.negate());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("(" + get(0));
		for (int i = 1; i < entries.length; i++) {
			sb.append(", " + get(i));
		}
		return sb.toString() + ")";
	}
}
