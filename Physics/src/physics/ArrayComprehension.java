package physics;

import java.util.function.Function;

public class ArrayComprehension {

	@SuppressWarnings("unchecked")
	public static <T> Scalar[] get(T[] arr, Function<T, Scalar> f) {
		Scalar[] ss = new Scalar[arr.length];
		for (int i = 0; i < ss.length; i++) {
			ss[i] = f.apply(arr[i]);
		}
		return ss;
	}

	@SuppressWarnings("unchecked")
	public static <S> S[] get(Vector arr, Function<Scalar, S> f) {
		Object[] ss = new Object[arr.getLength()];
		for (int i = 0; i < ss.length; i++) {
			ss[i] = f.apply(arr.get(i));
		}
		return (S[]) ss;
	}

	public static Scalar[] get(int length, Function<Integer, Scalar> f) {
		Integer[] arr = new Integer[length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i;
		}
		return get(arr, f);
	}
}
