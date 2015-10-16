package graphics.drawers;

import graphics.Pixel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import physics.Scalar;
import bodies.Robot;

public class RobotDrawer implements Drawable {

	private Robot robot;
	private Color color;

	public RobotDrawer(Robot robot, Color color) {
		this.robot = robot;
	}

	@Override
	public void draw(Graphics g, int dx, int dy) {
		Graphics2D g2d = (Graphics2D) g;
		int x = Pixel.to(robot.getPosition().getX());
		int y = Pixel.to(robot.getPosition().getY());
		int width = Pixel.to(robot.getWidth());
		int height = Pixel.to(robot.getHeight());
		Rectangle rect = new Rectangle(x + dx - width / 2, y + dy - height / 2,
				width, height);
		double angle = robot.getAngularPosition().getZ().convert(Scalar.RADIAN);
		g2d.rotate(angle, x + dx, y + dy);
		g2d.setColor(color);
		g2d.fill(rect);
		g2d.setColor(Color.RED);
		g2d.drawLine(x + dx - width / 2, y + dy - height / 2, x + dx + width
				/ 2, y + dy - height / 2);

	}

}
