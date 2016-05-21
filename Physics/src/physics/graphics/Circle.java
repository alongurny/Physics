package physics.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import physics.body.RegularBody;
import physics.graphics.drawers.Drawable;
import physics.graphics.drawers.Elastic;
import physics.graphics.drawers.VectorCollection;
import physics.math.IntVector;
import physics.math.Scalar;
import physics.math.Vector;
import physics.util.Lazy;

public class Circle extends RegularBody implements Elastic, Drawable {

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
	public void draw(Graphics g, Scalar pixel) {
		int i_diameter = Pixel.convert(radius.multiply(2), pixel);
		IntVector i_p = Pixel.convert(getPosition(), pixel);
		g.setColor(color);
		g.fillOval(i_p.get(0) - i_diameter / 2, i_p.get(1) - i_diameter / 2, i_diameter, i_diameter);
		g.setColor(Color.BLACK);
		g.drawPolygon(Pixel.convert(getAbsoluteBounds(), pixel));
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

}
