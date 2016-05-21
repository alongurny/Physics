package physics.util;

import java.util.function.Supplier;

public class Debug {

	public static void println(String line, Object... args) {
		System.err.println(String.format(line, args));
	}

	public static long timeIt(Runnable action) {
		long start = System.nanoTime();
		action.run();
		return System.nanoTime() - start;
	}

	public static <T> T printTimeIt(String prefix, Supplier<T> action) {
		long start = System.nanoTime();
		T t = action.get();
		StackTraceElement e = Thread.currentThread().getStackTrace()[2];
		System.err.println(
				e.getClassName() + ":" + e.getLineNumber() + " (" + prefix + "): " + (System.nanoTime() - start));
		return t;
	}

	public static void printTimeIt(String prefix, Runnable action) {
		long start = System.nanoTime();
		action.run();
		StackTraceElement e = Thread.currentThread().getStackTrace()[2];
		System.err.println(
				e.getClassName() + ":" + e.getLineNumber() + " (" + prefix + "): " + (System.nanoTime() - start));
	}
}
