package run;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import physics.Dynamics;
import physics.Forces;
import physics.PhysicalSystem;
import physics.body.Body;
import physics.body.space.Earth;
import physics.body.space.Planet;
import physics.body.space.Sun;
import physics.graphics.DrawingEvent;
import physics.graphics.DrawingListener;
import physics.graphics.Frame;
import physics.graphics.drawers.DynamicLabelDrawer;
import physics.graphics.drawers.SphereDrawer;
import physics.math.Scalar;
import physics.math.Vector;
import physics.quantity.Quantity;

public class RunSolarSystem {

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static Planet sun = new Sun(Vector.Axes3D.ORIGIN, Vector.zero(Quantity.VELOCITY, 3));
	static Planet earth = new Earth(
			new Vector(Earth.ROUND_RADIUS, Scalar.zero(Quantity.LENGTH), Scalar.zero(Quantity.LENGTH)),
			new Vector(0, 1, 0).multiply(Dynamics.getOrbitVelocity(sun, Earth.ROUND_RADIUS)));
	static PhysicalSystem solar = new PhysicalSystem(3, PhysicalSystem.DEFAULT_TIME_SPAN.multiply(1e6));
	static Body focus = sun;

	public static void main(String[] args) throws InterruptedException {
		solar.addExternalBiForce(Forces::getGravity);
		solar.add(sun);
		solar.add(earth);
		Frame f = new Frame(sun.getPosition(), Color.DARK_GRAY, Scalar.METER.multiply(1e7));
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
				solar.progress();
				f.setFocus(focus.getPosition());
			}
		});

	}
}
