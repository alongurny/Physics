package physics.graphics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.body.AbstractBody;
import physics.collision.Collidable;
import physics.graphics.drawers.PixelDrawable;
import physics.math.Scalar;
import physics.math.Vector;
import physics.math.VectorCollection;
import physics.util.Lazy;

public class Circle extends AbstractBody implements Collidable, PixelDrawable {

	private static final int ACCURACY = 16;

	private Scalar radius;
	private Color color;
	private Lazy<VectorCollection> relativeBounds;

	public Circle(Scalar mass, Scalar charge, Vector center, Vector velocity, Scalar radius, Color color) {
		super(mass, charge, center, velocity);
		this.radius = radius;
		this.color = color;
		this.relativeBounds = new Lazy<>(this::calculateRelativeBounds);
	}

	private VectorCollection calculateRelativeBounds() {
		List<Vector> vectors = new ArrayList<>();
		for (int i = 0; i < ACCURACY; i++) {
			vectors.add(Vector.extend(Vector.fromPolar(radius, (2 * Math.PI * i) / ACCURACY),
					getPosition().getDimension()));
		}
		return new VectorCollection(vectors);
	}

	@Override
	public void draw(PixelGraphics g) {
		Scalar diameter = getRadius().multiply(2);
		g.setColor(color);
		g.drawOval(getPosition(), diameter, diameter);
	}

	@Override
	public VectorCollection getRelativeBounds() {
		return relativeBounds.get();
	}

	@Override
	public Vector getCollisionCircleCenter(Vector contactPoint) {
		return getPosition();
	}

	public Color getColor() {
		return color;
	}

	public Scalar getRadius() {
		return radius;
	}

	@Override
	public double getElasticity() {
		return 1;
	}

}
