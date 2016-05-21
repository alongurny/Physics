package pool.bodies;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import physics.body.geometric.ChargeableSphere;
import physics.graphics.Pixel;
import physics.graphics.Rectangle;
import physics.graphics.drawers.Boundable;
import physics.graphics.drawers.Collidable;
import physics.graphics.drawers.Drawable;
import physics.graphics.drawers.Elastic;
import physics.graphics.drawers.VectorCollection;
import physics.math.IntVector;
import physics.math.Scalar;
import physics.math.Vector;
import physics.quantity.Quantity;
import physics.util.Lazy;

public class PoolBall extends ChargeableSphere implements Elastic, Drawable {

	private static final int ACCURACY = 4;

	private static final Scalar MASS = Scalar.KILOGRAM.multiply(0.156);
	private static final Scalar RADIUS = Scalar.METER.multiply(0.05715);

	private static Color[] colors = new Color[] { new Color(216, 134, 0), new Color(1, 52, 99), new Color(253, 7, 58),
			new Color(58, 22, 86), new Color(231, 71, 39), new Color(29, 93, 58), new Color(149, 40, 45),
			new Color(27, 25, 26) };

	private static Vector basicPosition = new Vector(Scalar.METER, 0.7, 0, 0);
	public static final Scalar maxCharge = Scalar.COULOMB.multiply(5e-7);
	private static Vector[] positions = new Vector[15];

	static {
		int count = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j <= i; j++) {
				double x = 2 * i;
				double y = 2 * j - i;
				positions[count] = new Vector(RADIUS.multiply(1.1), x, y, 0);
				count++;
			}
		}
	}

	private Color color;
	private boolean stripe;
	private String text;
	private Lazy<VectorCollection> relativeBounds;

	public PoolBall(Scalar mass, Vector center, Scalar radius, Color color, boolean stripe, String text) {
		super(mass, Scalar.zero(Quantity.CHARGE), center, Vector.zero(Quantity.VELOCITY, center.getDimension()),
				radius);
		this.color = color;
		this.stripe = stripe;
		this.text = text;
		this.relativeBounds = new Lazy<>(this::calculateRelativeBounds);
	}

	@Override
	public void draw(Graphics g, Scalar pixel) {
		int i_diameter = Pixel.convert(getRadius().multiply(2), pixel);
		IntVector i_p = Pixel.convert(getPosition(), pixel);
		Scalar charge = getCharge();
		double ratio = Scalar.abs(charge).convert(maxCharge);
		boolean positive = charge.compareTo(Scalar.zero(Quantity.CHARGE)) > 0;
		int magnitude = ((int) (15 * ratio)) / 2 * 2;
		g.setColor(new Color(positive ? 255 : 0, 0, !positive ? 255 : 0, Math.min(255, magnitude * 12)));
		g.fillOval(i_p.get(0) - i_diameter / 2 - magnitude / 2, i_p.get(1) - i_diameter / 2 - magnitude / 2,
				i_diameter + magnitude, i_diameter + magnitude);
		g.setColor(color);
		g.fillOval(i_p.get(0) - i_diameter / 2, i_p.get(1) - i_diameter / 2, i_diameter, i_diameter);
		if (stripe) {
			g.setColor(Color.WHITE);
			g.fillOval(i_p.get(0) - i_diameter * 7 / 20, i_p.get(1) - i_diameter * 7 / 20, i_diameter * 7 / 10,
					i_diameter * 7 / 10);
		}
		g.setColor(stripe ? Color.BLACK : Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 12));
		g.drawString(text, i_p.get(0) - 2, i_p.get(1) + 4);

	}

	private VectorCollection calculateRelativeBounds() {
		List<Vector> vectors = new ArrayList<>();
		for (int i = 0; i < ACCURACY; i++) {
			vectors.add(Vector.extend(Vector.fromPolar(getRadius(), (2 * Math.PI * i) / ACCURACY),
					getPosition().getDimension()));
		}
		return new VectorCollection(vectors);
	}

	@Override
	public VectorCollection getRelativeBounds() {
		return relativeBounds.get();
	}

	@Override
	public Vector getCollisionCircleCenter(Vector contactPoint) {
		return getPosition();
	}

	public static PoolBall getWhiteBall() {
		return new PoolBall(MASS, new Vector(Scalar.METER, -0.5, 0, 0), RADIUS, Color.WHITE, false, "");
	}

	public static PoolBall get(int i) {
		return new PoolBall(MASS, getPosition(i), RADIUS, getColor(i), i > 8, i + "");
	}

	private static Vector getPosition(int i) {
		return positions[i - 1].add(basicPosition);
	}

	private static Color getColor(int i) {
		return colors[(i - 1) % 8];
	}

	@Override
	public void onCollision(Scalar pixel, Collidable other, Scalar dt) {
		Elastic.super.onCollision(pixel, other, dt);
		if (other instanceof PoolBall) {
			PoolBall c = (PoolBall) other;
			Scalar avg = getCharge().add(c.getCharge()).divide(2);
			addCharge(avg.subtract(getCharge()));
			c.addCharge(avg.subtract(c.getCharge()));
		}
	}

	@Override
	public Optional<Vector> getFutureIntersection(Scalar pixel, Boundable b, Scalar dt) {
		if (b instanceof PoolBall) {
			PoolBall pb = (PoolBall) b;
			Vector futurePosition = getPosition().add(getVelocity().multiply(dt));
			Vector pbFuturePosition = pb.getPosition().add(pb.getVelocity().multiply(dt));
			if (Vector.distance(pbFuturePosition, futurePosition).compareTo(getRadius().add(pb.getRadius())) <= 0) {
				return Optional.of(futurePosition.add(pbFuturePosition).divide(2));
			}
			return Optional.empty();
		}
		if (b instanceof Rectangle) {
			Rectangle r = (Rectangle) b;
			Vector futurePosition = getPosition().add(getVelocity().multiply(dt));
			Vector rFuturePosition = r.getPosition().add(r.getVelocity().multiply(dt));
			Vector diff = rFuturePosition.subtract(futurePosition);
			if (Scalar.abs(diff.get(0)).compareTo(r.getWidth().divide(2)) < 0
					&& Scalar.abs(diff.get(1)).compareTo(r.getHeight()) < 0
					|| Scalar.abs(diff.get(1)).compareTo(r.getHeight().divide(2)) < 0
							&& Scalar.abs(diff.get(0)).compareTo(r.getWidth()) < 0) {
				return Elastic.super.getFutureIntersection(pixel, b, dt);
			}
			return Optional.empty();
		}
		return Elastic.super.getFutureIntersection(pixel, b, dt);
	}

}
