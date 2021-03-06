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
import physics.quantity.Quantity;
import physics.util.Lazy;

public class Rectangle extends AbstractBody implements Collidable, PixelDrawable {

	private Color color;
	private Scalar width;
	private Scalar height;
	private Lazy<VectorCollection> relativeBounds;

	public Rectangle(Scalar mass, Vector center, Vector velocity, Scalar width, Scalar height, Color color) {
		super(mass, Scalar.zero(Quantity.CHARGE), center, velocity);
		this.width = width;
		this.height = height;
		this.color = color;
		this.relativeBounds = new Lazy<>(this::calculateRelativeBounds);
	}

	@Override
	public void draw(PixelGraphics g) {
		g.setColor(color);
		g.fillRect(getPosition(), width, height);
	}

	private VectorCollection calculateRelativeBounds() {
		List<Vector> vectors = new ArrayList<>();
		int dimension = getPosition().getDimension();
		vectors.add(new Vector(width.negate(), height.negate()).multiply(0.5).extend(dimension));
		vectors.add(new Vector(width, height.negate()).multiply(0.5).extend(dimension));
		vectors.add(new Vector(width, height).multiply(0.5).extend(dimension));
		vectors.add(new Vector(width.negate(), height).multiply(0.5).extend(dimension));
		return new VectorCollection(vectors);
	}

	@Override
	public VectorCollection getRelativeBounds() {
		return relativeBounds.get();
	}

	@Override
	public Vector getCollisionCircleCenter(Vector contactPoint) {
		contactPoint = Vector.extend(contactPoint, getPosition().getDimension());
		Vector d = contactPoint.subtract(getPosition());
		double phase = Vector.getPhase(d);
		double angle = Scalar.atan2(height, width);
		contactPoint = Vector.extend(contactPoint, 2);
		if (Math.abs(phase) < angle) {
			contactPoint = contactPoint.add(new Vector(-0.5, 0).multiply(width));
		} else if (Math.abs(phase) > Math.PI - angle) {
			contactPoint = contactPoint.add(new Vector(0.5, 0).multiply(width));
		} else if (angle < phase && phase < Math.PI - angle) {
			contactPoint = contactPoint.add(new Vector(0, -0.5).multiply(height));
		} else if (-Math.PI + angle < phase && phase < -angle) {
			contactPoint = contactPoint.add(new Vector(0, 0.5).multiply(height));
		}
		return Vector.extend(contactPoint, getPosition().getDimension());

	}

	public Color getColor() {
		return color;
	}

	public Vector getDimensions() {
		return new Vector(width, height);
	}

	public Scalar getWidth() {
		return width;
	}

	public Scalar getHeight() {
		return height;
	}

	@Override
	public double getElasticity() {
		return 1;
	}

}
