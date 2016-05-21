package physics.util;

import java.util.function.Supplier;

public class Debug {
	public static long timeIt(Runnable action) {
		long start = System.nanoTime();
		action.run();
		return System.nanoTime() - start;
	}

	public static <T> T printTimeIt(String prefix, Supplier<T> action) {
		long start = System.nanoTime();
		T t = action.get();
		System.out.println(prefix + ": " + (System.nanoTime() - start));
		return t;
	}

	public static void printTimeIt(String prefix, Runnable action) {
		long start = System.nanoTime();
		action.run();
		System.out.println(prefix + ": " + (System.nanoTime() - start));
	}
}
