package graphics.drawers;

import java.awt.Graphics;

public class VectorFieldDrawer implements Drawable {

	private double width;
	private double height;

	public VectorFieldDrawer(double width, double height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(Graphics g, int dx, int dy) {
		for (int y = 20; y < height; y += 40) {
			for (int x = 20; x < width; x += 40) {
			}
		}
	}
}
