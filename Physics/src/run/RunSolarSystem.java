package run;

import graphics.DrawingEvent;
import graphics.DrawingListener;
import graphics.Frame;
import graphics.drawers.DynamicLabelDrawer;
import graphics.drawers.ScalarFieldDrawer;
import graphics.drawers.SphereDrawer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import physics.Body;
import physics.PhysicalSystem;
import physics.Quantity;
import physics.Scalar;
import physics.UnitSystem;
import physics.Util;
import physics.Vector;
import physics.VectorField;
import bodies.space.Earth;
import bodies.space.Planet;
import bodies.space.Sun;

public class RunSolarSystem {

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static Planet sun = new Planet(Sun.MASS, Vector.POSITION_ORIGIN,
			Vector.zero(Quantity.VELOCITY), Vector.zero(Quantity.ANGLE),
			Vector.zero(Quantity.ANGULAR_VELOCITY), Sun.RADIUS);
	static Planet earth = new Planet(Earth.MASS, new Vector(Earth.ROUND_RADIUS,
			Scalar.zero(Quantity.LENGTH), Scalar.zero(Quantity.LENGTH)),
			new Vector(Scalar.zero(Quantity.VELOCITY), Util
					.forCircularMovement(sun, Earth.ROUND_RADIUS), Scalar
					.zero(Quantity.VELOCITY)), Vector.zero(Quantity.ANGLE),
			Vector.zero(Quantity.ANGULAR_VELOCITY), Earth.RADIUS);
	static PhysicalSystem solar = new PhysicalSystem(sun, earth);
	static Body focus = sun;
	static ScalarFieldDrawer fieldDrawer = new ScalarFieldDrawer(
			() -> v -> VectorField
					.sum(sun.getGravitationalField(),
							earth.getGravitationalField()).get(v)
					.getMagnitude(), Scalar.zero(Quantity.ACCELERATION),
			UnitSystem.SI.get(Quantity.ACCELERATION).multiply(1e-1),
			screenSize.getWidth(), screenSize.getHeight());

	public static void main(String[] args) throws InterruptedException {
		Frame f = new Frame(sun.getPosition(), Color.DARK_GRAY);
		f.addDrawable(new SphereDrawer(sun, Color.YELLOW));
		f.addDrawable(new SphereDrawer(earth, Color.GREEN));
		DynamicLabelDrawer sunLabel = new DynamicLabelDrawer(sun, 25, 0);
		sunLabel.add("Velocity", sun::getVelocity);
		sunLabel.add("Total force", sun::getTotalForce);
		DynamicLabelDrawer earthLabel = new DynamicLabelDrawer(earth, 25, 0);
		earthLabel.add("Velocity", earth::getVelocity);
		earthLabel.add("Total force", earth::getTotalForce);
		f.addDrawable(sunLabel);
		f.addDrawable(earthLabel);
		f.addLabel("Total momentum", solar::getTotalMomentum);
		f.addLabel("Total force", solar::getTotalForce);
		f.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				switch (e.getKeyChar()) {
				case '1':
					focus = sun;
					break;
				case '2':
					focus = earth;
					break;
				default:
					return;
				}
			}
		});
		f.addDrawingListener(new DrawingListener() {

			@Override
			public void onDraw(DrawingEvent e) {
				solar.forEach(Body::move);
				solar.applyGravityForces();
				f.setFocus(focus.getPosition());
			}
		});

	}
}
