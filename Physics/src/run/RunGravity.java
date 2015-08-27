package run;

import graphics.Frame;
import graphics.Pixel;
import graphics.drawers.CubeDrawer;
import graphics.drawers.SphereDrawer;

import java.awt.Color;

import physics.Body;
import physics.PhysicalSystem;
import physics.Quantity;
import physics.Scalar;
import physics.Vector;
import bodies.Cube;
import bodies.Sphere;
import bodies.space.Earth;

public class RunGravity {
	public static void main(String[] args) {
		Pixel.set(Scalar.METER.multiply(1000));
		Frame f = new Frame(Color.GRAY);
		Cube earth = new Cube(Earth.MASS, Scalar.zero(Quantity.CHARGE),
				Vector.POSITION_ORIGIN, Vector.zero(Quantity.VELOCITY),
				Vector.zero(Quantity.ANGLE),
				Vector.zero(Quantity.ANGULAR_VELOCITY), Scalar.METER);
		Sphere ball = new Sphere(Scalar.KILOGRAM, Scalar.zero(Quantity.CHARGE),
				new Vector(Scalar.METER, 0, Earth.RADIUS.negate().convert(
						Scalar.METER), 0), Vector.zero(Quantity.VELOCITY),
				Vector.zero(Quantity.ANGLE),
				Vector.zero(Quantity.ANGULAR_VELOCITY), Pixel.get().multiply(5));
		PhysicalSystem ps = new PhysicalSystem(earth, ball);
		f.addDrawable(new CubeDrawer(earth, Color.GREEN));
		f.addDrawable(new SphereDrawer(ball, Color.RED));
		f.addDrawingListener(e -> {
			if (earth.getPosition().getY()
					.compareTo(earth.getPosition().getY()) <= 0) {
				ball.addImpulse(ball.getMomentum().multiply(-2));
				earth.addImpulse(earth.getMomentum().multiply(-2));

			}
			ps.forEach(Body::move);
			ps.applyGravityForces();

		});
		f.addLabel("Force", ball::getTotalForce);
		f.addLabel("mg", () -> ball.getMass().multiply(Scalar.LITTLE_G));
	}
}
