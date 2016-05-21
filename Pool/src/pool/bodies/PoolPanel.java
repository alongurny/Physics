package pool.bodies;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.Forces;
import physics.body.Movable;
import physics.graphics.PhysicalPanel;
import physics.graphics.Rectangle;
import physics.math.Scalar;
import physics.math.Vector;
import physics.quantity.Quantity;
import physics.quantity.UnitSystem;

@SuppressWarnings("serial")
public class PoolPanel extends PhysicalPanel {

	private static final Color COLOR = new Color(0, 127, 0);

	private static final Scalar BORDER_MASS = Scalar.KILOGRAM.multiply(1e20);

	private static final int WIDTH = 960, HEIGHT = WIDTH / 2;

	private static final Scalar PIXEL = Scalar.METER.multiply(2.84).divide(WIDTH);

	private static Rectangle[] borders = new Rectangle[] {
			new Rectangle(BORDER_MASS, new Vector(PIXEL, -WIDTH / 2, 0, 0), Vector.zero(Quantity.VELOCITY, 3),
					new Scalar(PIXEL, 40), new Scalar(PIXEL, HEIGHT - 40), Color.GRAY),
			new Rectangle(BORDER_MASS, new Vector(PIXEL, WIDTH / 2, 0, 0), Vector.zero(Quantity.VELOCITY, 3),
					new Scalar(PIXEL, 40), new Scalar(PIXEL, HEIGHT - 40), Color.GRAY),
			new Rectangle(BORDER_MASS, new Vector(PIXEL, 0, -HEIGHT / 2, 0), Vector.zero(Quantity.VELOCITY, 3),
					new Scalar(PIXEL, WIDTH - 40), new Scalar(PIXEL, 40), Color.GRAY),
			new Rectangle(BORDER_MASS, new Vector(PIXEL, 0, HEIGHT / 2, 0), Vector.zero(Quantity.VELOCITY, 3),
					new Scalar(PIXEL, WIDTH - 40), new Scalar(PIXEL, 40), Color.GRAY) };

	private List<PoolBall> regular;
	private List<PoolBall> stripe;

	private static final Scalar factor = UnitSystem.SI.get(Quantity.CHARGE.divide(Quantity.VELOCITY), 6e-9);

	public PoolPanel() {
		super(WIDTH, HEIGHT, Vector.Axes2D.ORIGIN, PIXEL, 3, Scalar.SECOND.multiply(15e-3));
		setBackground(COLOR);
		for (Rectangle r : borders) {
			add(r, false);
		}
		PoolBall white = PoolBall.getWhiteBall();
		white.addImpulse(new Vector(UnitSystem.SI.get(Quantity.MOMENTUM), 0.25, 0, 0));
		regular = new ArrayList<>();
		stripe = new ArrayList<>();
		add(white, true);
		for (int i = 1; i <= 15; i++) {
			PoolBall b = PoolBall.get(i);
			if (i < 8) {
				regular.add(b);
			} else if (i > 8) {
				stripe.add(b);
			}
			add(b, true);
		}
		addCalculation(() -> {
			for (PoolBall b : regular) {
				b.addCharge(factor.multiply(b.getVelocity().getMagnitude()).negate());
			}
			for (PoolBall b : stripe) {
				b.addCharge(factor.multiply(b.getVelocity().getMagnitude()));
			}
		});
		addLabel("Q", () -> getPhysicalSystem().getTotalScalar(Quantity.CHARGE, Movable::getCharge));
		getPhysicalSystem().addExternalBiForce(Forces::getLorentzForce);
		getPhysicalSystem().addExternalForce(
				a -> Forces.getFriction(a, Scalar.STANDARD_GRAVITY, 0.01, UnitSystem.SI.get(Quantity.VELOCITY, 1e-8)));
		setFPS(30);
	}

}
