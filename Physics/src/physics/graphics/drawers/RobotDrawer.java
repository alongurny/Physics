package physics.graphics.drawers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import physics.body.machine.Robot;
import physics.graphics.Pixel;
import physics.math.IntVector;
import physics.math.Scalar;

public class RobotDrawer implements Drawable {

	private Robot robot;
	private Color color;

	public RobotDrawer(Robot robot, Color color) {
		this.robot = robot;
	}

	@Override
	public void draw(Graphics g, Scalar pixel) {
		Graphics2D g2d = (Graphics2D) g;
		IntVector i_p = Pixel.convert(robot.getPosition(), pixel);
		IntVector i_d = Pixel.convert(robot.getDimensions(), pixel);
		int x = i_p.get(0);
		int y = i_p.get(1);
		int width = i_d.get(0);
		int height = i_d.get(1);
		Rectangle rect = new Rectangle(x - width / 2, y - height / 2, width, height);
		double angle = robot.getAngularPosition().get(0).convert(Scalar.RADIAN);
		g2d.rotate(angle, x, y);
		g2d.setColor(color);
		g2d.fill(rect);
		g2d.setColor(Color.RED);
		g2d.drawLine(x - width / 2, y - height / 2, x + width / 2, y - height / 2);
		g2d.rotate(-angle, x, y);

	}

}
