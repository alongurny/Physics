package run;

import java.awt.Color;

import physics.body.atom.Electron;
import physics.graphics.DrawingEvent;
import physics.graphics.DrawingListener;
import physics.graphics.Frame;
import physics.graphics.drawers.SphereDrawer;
import physics.math.Scalar;
import physics.math.Vector;
import physics.quantity.Quantity;

public class Test {
	static int x = 0, y = 0;

	public static void main(String[] args) {
		Scalar pixel = Scalar.METER.multiply(3e3);
		Frame f = new Frame(Vector.Axes3D.ORIGIN, Color.GRAY, pixel);
		Electron e1 = new Electron(Vector.Axes3D.ORIGIN, Vector.zero(Quantity.VELOCITY, 3));
		Electron e2 = new Electron(new Vector(pixel, 100, 0, 0), Vector.zero(Quantity.VELOCITY, 3));
		f.addDrawable(new SphereDrawer(e1, Color.BLUE));
		f.addDrawable(new SphereDrawer(e2, Color.RED));
		f.addLabel("p1", e1::getPosition);
		f.addLabel("p2", e2::getPosition);
		f.addDrawingListener(new DrawingListener() {

			@Override
			public void onDraw(DrawingEvent e) {
				e1.addForce(e2.getElectricalField().get(e1.getPosition()).multiply(e1.getCharge()));
				e2.addForce(e1.getElectricalField().get(e2.getPosition()).multiply(e2.getCharge()));
				e1.move();
				e2.move();
			}
		});
	}
}
