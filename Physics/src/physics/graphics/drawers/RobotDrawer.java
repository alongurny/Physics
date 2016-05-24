package physics.graphics.drawers;

import java.awt.Color;

import physics.body.machine.Robot;
import physics.graphics.PixelGraphics;
import physics.math.Scalar;

public class RobotDrawer implements PixelDrawable {

	private Robot robot;
	private Color color;

	public RobotDrawer(Robot robot, Color color) {
		this.robot = robot;
		this.color = color;
	}

	@Override
	public void draw(PixelGraphics g) {
		Scalar angle = robot.getAngularPosition().get(0);
		g.rotate(angle, robot.getPosition());
		g.setColor(color);
		g.fillRect(robot.getPosition(), robot.getWidth(), robot.getHeight());
		g.setColor(Color.RED);
		g.drawLine(robot.getPosition().subtract(robot.getDimensions().divide(2)),
				robot.getPosition().add(robot.getDimensions().divide(2)));
		g.rotate(angle.negate(), robot.getPosition());

	}

}
