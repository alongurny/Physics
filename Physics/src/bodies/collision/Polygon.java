package bodies.collision;

import java.util.ArrayList;
import java.util.List;

import physics.Vector;

public class Polygon {
	private List<Vector> verticles;
	private List<Vector> edges;

	public Polygon(List<Vector> verticles) {
		this.verticles = new ArrayList<>(verticles);
		this.edges = new ArrayList<>(verticles.size());
		for (int i = 0; i < verticles.size(); i++) {
			edges.add(verticles.get((i + 1) % verticles.size()).subtract(verticles.get(i)));
		}
	}
}