package physics.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Joystick {

	private static final int C_RADIUS = 25;
	private static final int C_WIDTH = 480;
	private static final int C_HEIGHT = 480;
	private double x, y;
	private JPanel panel;
	private JFrame frame;
	private boolean dragging = false;

	public Joystick() {
		x = 0;
		y = 0;
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				int side = Math.min(panel.getWidth(), panel.getHeight());
				super.paintComponent(g);
				g.setColor(Color.RED);
				g.fillOval((int) ((x + 1) * side / 2) - C_RADIUS, (int) ((y + 1) * side / 2) - C_RADIUS, 2 * C_RADIUS,
						2 * C_RADIUS);
				g.setColor(Color.BLACK);
				g.drawOval(0, 0, side, side);
			}
		};
		frame = new JFrame();
		frame.setSize(C_WIDTH, C_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setFocusable(true);
		MouseAdapter a = new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (dragging) {
					int side = Math.min(panel.getWidth(), panel.getHeight());

					x = 2.0 * e.getX() / side - 1;
					y = 2.0 * e.getY() / side - 1;
					double length = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
					if (length > 1) {
						x = x / length;
						y = y / length;
					}
					panel.repaint();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				int side = Math.min(panel.getWidth(), panel.getHeight());
				int ex = e.getX(), ey = e.getY();
				if (ex > (int) ((x + 1) * side / 2) - C_RADIUS && ex < (int) ((x + 1) * side / 2) + C_RADIUS
						&& ey > (int) ((y + 1) * side / 2) - C_RADIUS && ey < (int) ((y + 1) * side / 2) + C_RADIUS) {
					dragging = true;
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				dragging = false;
				x = 0;
				y = 0;
				panel.repaint();
			}
		};
		panel.addMouseListener(a);
		panel.addMouseMotionListener(a);

		frame.add(panel);
		frame.setVisible(true);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return -y;
	}

}
