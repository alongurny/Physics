package run;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import bodies.Robot;
import graphics.Drivetrain;
import graphics.Frame;
import graphics.Joystick;
import graphics.Pixel;
import graphics.drawers.RobotDrawer;
import physics.Quantity;
import physics.Scalar;
import physics.Vector;

public class RunRobot {
	public static void main(String[] args) {
		Pixel.set(Scalar.METER.divide(100));
		Robot robot = new Robot(Scalar.KILOGRAM.multiply(48), Vector.Axes2D.ORIGIN,
				Scalar.KILOGRAM.multiply(Scalar.METER.divide(Scalar.RADIAN).pow(2).multiply(12)),
				Vector.zero(Quantity.ANGLE, 1), Scalar.METER.multiply(0.6), Scalar.METER.multiply(0.8));
		Frame f = new Frame(Vector.Axes2D.ORIGIN, Color.GRAY);
		f.addDrawable(new RobotDrawer(robot, Color.WHITE));
		f.addLabel("Velocity", robot::getVelocity);
		f.addLabel("Position", robot::getPosition);
		f.addLabel("Rotation", () -> robot.getAngularPosition().get(0));
		f.addLabel("Angular velocity", () -> robot.getAngularVelocity().get(0));
		f.addDrawingListener(e -> robot.move());
		f.addDrawingListener(e -> robot.rotate());
		double mu = 0.04;
		f.addDrawingListener(e -> {
			if (!Scalar.isZero(robot.getVelocity().getMagnitude())) {
				Vector friction = robot.getVelocity().getDirection().multiply(robot.getFriction(mu)).negate();
				robot.addForce(friction);
			}
			if (!Scalar.isZero(robot.getAngularVelocity().getMagnitude())) {
				Vector friction = robot.getAngularVelocity().getDirection().multiply(robot.getFriction(mu))
						.multiply(robot.getPivot()).negate();
				robot.addTorque(friction);
			}
		});
		Joystick j = new Joystick();
		Drivetrain d = new Drivetrain(robot);
		Timer t = new Timer();
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				d.arcade(-j.getY(), -j.getX());
			}
		}, 0, 30);

	}
}
