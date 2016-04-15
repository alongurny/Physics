package physics.body.machine;

import physics.math.Scalar;
import physics.math.Vector;
import physics.quantity.Quantity;
import physics.quantity.UnitSystem;

public class Drivetrain {

	private Scalar maximumSpeed = UnitSystem.SI.get(Quantity.VELOCITY, 0.02);
	private Robot robot;

	public Drivetrain(Robot robot) {
		this.robot = robot;
	}

	public void arcade(double moveValue, double rotateValue) {
		double leftMotorSpeed, rightMotorSpeed;
		if (moveValue > 0.0) {
			if (rotateValue > 0.0) {
				leftMotorSpeed = moveValue - rotateValue;
				rightMotorSpeed = Math.max(moveValue, rotateValue);
			} else {
				leftMotorSpeed = Math.max(moveValue, -rotateValue);
				rightMotorSpeed = moveValue + rotateValue;
			}
		} else {
			if (rotateValue > 0.0) {
				leftMotorSpeed = -Math.max(-moveValue, rotateValue);
				rightMotorSpeed = moveValue + rotateValue;
			} else {
				leftMotorSpeed = moveValue - rotateValue;
				rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
			}
		}
		setTwoSides(leftMotorSpeed, rightMotorSpeed);
	}

	public void setTwoSides(double left, double right) {
		left = limit(left);
		right = limit(right);
		Scalar leftSpeed = maximumSpeed.multiply(left);
		Scalar rightSpeed = maximumSpeed.multiply(right);
		if (leftSpeed.equals(rightSpeed)) {
			robot.addImpulse(Vector.Axes2D.X.multiply(leftSpeed).multiply(robot.getMass()));
		} else {
			Scalar angularVelocity = leftSpeed.subtract(rightSpeed).divide(robot.getWidth());
			Scalar gamma = robot.getWidth().multiply(leftSpeed.add(rightSpeed)).divide(leftSpeed.subtract(rightSpeed))
					.multiply(angularVelocity);
			Vector velocity = new Vector(-Scalar.sin(robot.getAngularPosition().get(0)),
					Scalar.cos(robot.getAngularPosition().get(0))).multiply(gamma);
			robot.addImpulse(velocity.multiply(robot.getMass()));
			robot.addAngularImpulse(
					new Vector(angularVelocity.multiply(Scalar.RADIAN).multiply(robot.getMomentOfInertia())));

		}
	}

	private static double limit(double speed) {
		if (speed > 1) {
			speed = 1;
		} else if (speed < -1) {
			speed = -1;
		}
		return speed;
	}

}
