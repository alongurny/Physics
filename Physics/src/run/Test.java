package run;

import java.awt.Color;

import graphics.DrawingEvent;
import graphics.DrawingListener;
import graphics.Frame;
import graphics.PixelHandler;
import graphics.drawers.SphereDrawer;
import physics.Electron;
import physics.Quantity;
import physics.Scalar;
import physics.Vector;

public class Test {
	static int x = 0, y = 0;

	public static void main(String[] args) {
		PixelHandler pixelHandler = new PixelHandler(Scalar.METER.multiply(3e3));
		Frame f = new Frame(Vector.Axes3D.ORIGIN, Color.GRAY, pixelHandler);
		Electron e1 = new Electron(Vector.Axes3D.ORIGIN, Vector.zero(Quantity.VELOCITY, 3));
		Electron e2 = new Electron(new Vector(pixelHandler.get(), 100, 0, 0), Vector.zero(Quantity.VELOCITY, 3));
		f.addDrawable(new SphereDrawer(e1, Color.BLUE, pixelHandler));
		f.addDrawable(new SphereDrawer(e2, Color.RED, pixelHandler));
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
