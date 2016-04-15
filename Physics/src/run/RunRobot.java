package run;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import physics.PhysicalSystem;
import physics.body.machine.Drivetrain;
import physics.body.machine.Robot;
import physics.graphics.Frame;
import physics.graphics.Joystick;
import physics.graphics.drawers.RobotDrawer;
import physics.math.Scalar;
import physics.math.Vector;
import physics.quantity.Quantity;

public class RunRobot {
	public static void main(String[] args) {
		Robot robot = new Robot(Scalar.KILOGRAM.multiply(48), Vector.Axes2D.ORIGIN,
				Scalar.KILOGRAM.multiply(Scalar.METER.divide(Scalar.RADIAN).pow(2).multiply(12)),
				Vector.zero(Quantity.ANGLE, 1), Scalar.METER.multiply(0.6), Scalar.METER.multiply(0.8));
		Frame f = new Frame(Vector.Axes2D.ORIGIN, Color.GRAY, Scalar.METER.divide(100));
		f.addDrawable(new RobotDrawer(robot, Color.WHITE));
		f.addLabel("Velocity", robot::getVelocity);
		f.addLabel("Position", robot::getPosition);
		f.addLabel("Rotation", () -> robot.getAngularPosition().get(0));
		f.addLabel("Angular velocity", () -> robot.getAngularVelocity().get(0));
		f.addDrawingListener(e -> robot.move(PhysicalSystem.DEFAULT_TIME_SPAN));
		f.addDrawingListener(e -> robot.move(PhysicalSystem.DEFAULT_TIME_SPAN));
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
		Drivetrain d = new Drivetrain(robot, PhysicalSystem.DEFAULT_TIME_SPAN);
		Timer t = new Timer();
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				d.arcade(-j.getY(), -j.getX());
			}
		}, 0, 30);

	}
}
