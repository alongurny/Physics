package physics.util;

public class ImmutablePair<T, S> {

	private T first;
	private S second;

	public ImmutablePair(T first, S second) {
		this.first = first;
		this.second = second;
	}

	public T getFirst() {
		return first;
	}

	public S getSecond() {
		return second;
	}

}
