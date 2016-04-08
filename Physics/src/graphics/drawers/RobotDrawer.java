package graphics.drawers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import bodies.Robot;
import graphics.PixelHandler;
import physics.Scalar;

public class RobotDrawer implements Drawable {

	private Robot robot;
	private Color color;
	private PixelHandler pixelHandler;

	public RobotDrawer(Robot robot, Color color, PixelHandler pixelHandler) {
		this.robot = robot;
		this.pixelHandler = pixelHandler;
	}

	@Override
	public void draw(Graphics g, int dx, int dy) {
		Graphics2D g2d = (Graphics2D) g;
		int x = pixelHandler.to(robot.getPosition().get(0));
		int y = pixelHandler.to(robot.getPosition().get(1));
		int width = pixelHandler.to(robot.getWidth());
		int height = pixelHandler.to(robot.getHeight());
		Rectangle rect = new Rectangle(x + dx - width / 2, y + dy - height / 2, width, height);
		double angle = robot.getAngularPosition().get(0).convert(Scalar.RADIAN);
		g2d.rotate(angle, x + dx, y + dy);
		g2d.setColor(color);
		g2d.fill(rect);
		g2d.setColor(Color.RED);
		g2d.drawLine(x + dx - width / 2, y + dy - height / 2, x + dx + width / 2, y + dy - height / 2);

	}

}
