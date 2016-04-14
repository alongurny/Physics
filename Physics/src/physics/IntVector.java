package physics;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class IntVector {
	private final int[] entries;

	public IntVector(int... entries) {
		this(entries.length, i -> entries[i]);

	}

	private IntVector(int length, Function<Integer, Integer> f) {
		entries = new int[length];
		for (int i = 0; i < entries.length; i++) {
			this.entries[i] = f.apply(i);
		}
	}

	public int getLength() {
		return entries.length;
	}

	public IntVector multiply(int n) {
		return new IntVector(getLength(), i -> get(i) * n);
	}

	private IntVector applyBinaryOperator(IntVector other, BiFunction<Integer, Integer, Integer> op) {
		Quantities.requireSameLength(this, other);
		return new IntVector(getLength(), i -> op.apply(get(i), other.get(i)));
	}

	public IntVector add(IntVector w) {
		return applyBinaryOperator(w, (a, b) -> a + b);
	}

	public IntVector negate() {
		return new IntVector(getLength(), i -> -get(i));
	}

	public IntVector subtract(IntVector v) {
		return this.add(v.negate());
	}

	public int get(int i) {
		return entries[i];
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("(" + get(0));
		for (int i = 1; i < entries.length; i++) {
			sb.append(", " + get(i));
		}
		return sb.toString() + ")";
	}

	public static IntVector sum(IntVector... vectors) {
		if (vectors.length == 0) {
			throw new IllegalArgumentException("Must have at least one vector");
		}
		Quantities.requireSameLength(vectors);
		IntVector res = vectors[0];
		for (int i = 1; i < vectors.length; i++) {
			res = res.add(vectors[i]);
		}
		return res;
	}

	public static IntVector zero(int length) {
		return new IntVector(length, i -> 0);
	}

}
