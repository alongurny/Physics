package physics.util;

import java.util.Optional;
import java.util.function.Supplier;

public class LazyCache<T> {

	private Optional<T> value;
	private boolean updated;
	private Supplier<T> initializer;

	public LazyCache(Supplier<T> initializer) {
		this.initializer = initializer;
		this.value = Optional.empty();
		this.updated = false;
	}

	public T get() {
		if (!updated) {
			value = Optional.of(initializer.get());
			this.updated = true;
		}
		return value.get();
	}

	public void setOutdated() {
		this.updated = false;
	}

	public boolean isUpdated() {
		return updated;
	}

}
