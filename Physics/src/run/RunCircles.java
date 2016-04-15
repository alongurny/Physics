package run;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import graphics.DrawingEvent;
import graphics.DrawingListener;
import graphics.Frame;
import graphics.drawers.DrawableBody;
import physics.Collision;
import physics.Forces;
import physics.PhysicalSystem;
import physics.body.Movable;
import physics.graphics.Circle;
import physics.graphics.Rectangle;
import physics.math.Scalar;
import physics.math.Vector;

public class RunCircles {

	static Supplier<Vector> getFocus = () -> Vector.Axes3D.ORIGIN;

	public static void main(String[] args) {
		Scalar pixel = Scalar.METER.multiply(1);
		Frame f = new Frame(Vector.Axes3D.ORIGIN, Color.GRAY, pixel);
		Color[] cs = new Color[] { Color.BLUE, Color.RED };
		List<Circle> circles = new ArrayList<>();
		Vector[] locs = new Vector[] { new Vector(pixel, -200, 0, 0), new Vector(pixel, 50, 50, 0) };
		Vector[] vels = new Vector[] { new Vector(pixel.divide(Scalar.SECOND), -100, 10, 0),
				new Vector(pixel.divide(Scalar.SECOND), -100, 10, 0) };
		for (int i = 0; i < cs.length; i++) {
			circles.add(new Circle(Scalar.KILOGRAM.multiply(3e4), Scalar.COULOMB.multiply(4), locs[i], vels[i],
					pixel.multiply(35), cs[i]));
			f.addDrawable(circles.get(i));
		}
		Vector[] pos = new Vector[] { new Vector(pixel, 0, -320, 0), new Vector(pixel, 320, 0, 0),
				new Vector(pixel, -0, 320, 0), new Vector(pixel, -320, -0, 0) };
		Vector[] dimensions = new Vector[] { new Vector(pixel, 650, 20, 0), new Vector(pixel, 20, 650, 0) };
		List<Rectangle> rects = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			rects.add(new Rectangle(Scalar.KILOGRAM.multiply(1e30), pos[i],
					new Vector(pixel.divide(Scalar.SECOND), 0, 0, 0), dimensions[i % 2].get(0),
					dimensions[i % 2].get(1), Color.GREEN));
			f.addDrawable(rects.get(i));
		}
		PhysicalSystem ps = new PhysicalSystem(2, circles);
		ps.addExternalBiForce(Forces::getGravity);
		ps.addExternalBiForce(Forces::getLorentzForce);
		ps.addExternalForce(b -> Forces.getFriction(b, Scalar.LITTLE_G, 0.5));
		f.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Vector v = f.getVector(3, e.getPoint());
				for (Circle c : circles) {
					if (v.subtract(c.getPosition()).getMagnitude().compareTo(c.getRadius()) < 0) {
						getFocus = c::getPosition;
						return;
					}
				}
				getFocus = () -> v;
			}
		});
		f.addLabel("r1", circles.get(0)::getPosition);
		f.addLabel("r2", circles.get(1)::getPosition);
		f.addDrawingListener(new DrawingListener() {
			@Override
			public void onDraw(DrawingEvent e) {
				f.setFocus(getFocus.get());
				ps.applyForces();
				ps.forEach(Movable::move);
				circles.forEach(c1 -> {
					circles.forEach(c2 -> {
						if (c1 != c2) {
							DrawableBody.willIntersect(pixel, c1, c2)
									.ifPresent(p -> c1.addImpulse(Collision.getImpulse(c1, c2, p)));
						}
					});
					rects.forEach(r -> DrawableBody.willIntersect(pixel, c1, r)
							.ifPresent(c -> c1.addImpulse(Collision.getImpulse(c1, r, c))));
				});
			}
		});
	}
}
