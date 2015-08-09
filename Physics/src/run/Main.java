package run;

import graphics.DrawingEvent;
import graphics.DrawingListener;
import graphics.Frame;
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
import physics.Util;
import physics.Vector;
import bodies.space.Earth;
import bodies.space.Planet;
import bodies.space.Sun;

public class Main {

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static Planet sun = new Planet(Sun.MASS, Vector.POSITION_ORIGIN,
			Vector.zero(Quantity.VELOCITY), Vector.zero(Quantity.ANGLE),
			Vector.zero(Quantity.ANGULAR_VELOCITY), Sun.RADIUS);
	static Planet earth = new Planet(Earth.MASS, new Vector(Earth.ROUND_RADIUS,
			Scalar.zero(Quantity.POSITION), Scalar.zero(Quantity.POSITION)),
			new Vector(Scalar.zero(Quantity.VELOCITY), Util
					.forCircularMovement(sun, Earth.ROUND_RADIUS), Scalar
					.zero(Quantity.VELOCITY)), Vector.zero(Quantity.ANGLE),
			Vector.zero(Quantity.ANGULAR_VELOCITY), Earth.RADIUS);
	static PhysicalSystem solar = new PhysicalSystem(sun, earth);
	static Body focus = null;

	public static void main(String[] args) throws InterruptedException {
		Frame f = new Frame(screenSize, Color.DARK_GRAY, new SphereDrawer(sun,
				Color.YELLOW), new SphereDrawer(earth, Color.GREEN));
		f.addSign("Total momentum", () -> solar.getTotalMomentum());
		f.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				switch (e.getKeyChar()) {
				case '0':
					focus = null;
					break;
				case '1':
					focus = sun;
					break;
				case '2':
					focus = earth;
					break;
				case '3':
					focus = sun;
					break;
				default:
					return;
				}
			}
		});
		f.addDrawingListener(new DrawingListener() {

			@Override
			public void onDraw(DrawingEvent e) {
				solar.forEach(t -> t.move(Scalar.NANOSECOND.multiply(e
						.getNanoTimePassed() * 1e6)));
				solar.applyGravityForces();
				if (focus != null) {
					f.setFocus(focus.getPosition());
				}
			}
		});

	}
}
