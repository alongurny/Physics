package physics.graphics.drawers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.UnaryOperator;

import physics.math.Vector;
import physics.quantity.Quantities;

public class VectorCollection implements Iterable<Vector> {
	private List<Vector> vectors;

	public VectorCollection(List<Vector> vectors) {
		Quantities.requireSame(vectors);
		this.vectors = new ArrayList<>(vectors);
	}

	public VectorCollection map(UnaryOperator<Vector> f) {
		List<Vector> newVectors = new ArrayList<>(vectors);
		newVectors.replaceAll(f);
		return new VectorCollection(newVectors);
	}

	@Override
	public Iterator<Vector> iterator() {
		return vectors.iterator();
	}

	public Vector get(int i) {
		return vectors.get(i);
	}

	public int size() {
		return vectors.size();
	}
}
