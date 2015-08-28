package run;

import graphics.Frame;
import graphics.Pixel;
import graphics.drawers.RobotDrawer;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import physics.Quantity;
import physics.Scalar;
import physics.Vector;
import bodies.Robot;

public class RunRobot {
	public static void main(String[] args) {
		Pixel.set(Scalar.METER.divide(100));
		Robot robot = new Robot(Scalar.KILOGRAM.multiply(48),
				Vector.POSITION_ORIGIN, Scalar.KILOGRAM.multiply(Scalar.METER
						.divide(Scalar.RADIAN).pow(2).multiply(12)),
				Vector.zero(Quantity.ANGLE), Scalar.METER.multiply(0.6),
				Scalar.METER.multiply(0.8));
		Frame f = new Frame(Vector.POSITION_ORIGIN, Color.GRAY);
		f.addDrawable(new RobotDrawer(robot, Color.WHITE));
		f.addLabel("Velocity", robot::getVelocity);
		f.addLabel("Position", robot::getPosition);
		f.addLabel("Rotation", () -> robot.getAngularPosition().getZ());
		f.addLabel("Angular velocity", () -> robot.getAngularVelocity().getZ());
		f.addDrawingListener(e -> robot.move());
		f.addDrawingListener(e -> robot.rotate());
		double mu = 0.2;
		Scalar force = Scalar.NEWTONE.multiply(32).multiply(4);
		f.addDrawingListener(e -> {
			if (!Scalar.isZero(robot.getVelocity().getMagnitude())) {
				Vector friction = robot.getVelocity().getDirection()
						.multiply(robot.getFriction(mu)).negate();
				robot.addForce(friction);
			}
			if (!Scalar.isZero(robot.getAngularVelocity().getMagnitude())) {
				Vector friction = robot.getAngularVelocity().getDirection()
						.multiply(robot.getFriction(mu))
						.multiply(robot.getPivot()).negate();
				robot.addTorque(friction);
			}
		});
		f.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				double rotation = robot.getAngularPosition().getZ()
						.convert(Scalar.RADIAN);
				switch (e.getKeyChar()) {
				case 'w':
					robot.addForce(new Vector(force, Math.sin(rotation), -Math
							.cos(rotation), 0));
					break;
				case 's':
					robot.addForce(new Vector(force, -Math.sin(rotation), Math
							.cos(rotation), 0));
					break;
				case 'a':
					robot.addTorque(new Vector(
							force.multiply(robot.getPivot()), 0, 0, -1));
					break;
				case 'd':
					robot.addTorque(new Vector(
							force.multiply(robot.getPivot()), 0, 0, 1));
					break;
				default:
					break;
				}
			}
		});

	}
}
