package graphics;

import graphics.drawers.Drawable;

import java.awt.Color;
import java.awt.Graphics;

import physics.Scalar;
import physics.ScalarField;
import physics.Vector;

public class ScalarFieldDrawer implements Drawable {

	private int width, height;
	private Scalar min, max;
	private ScalarField field;

	public ScalarFieldDrawer(ScalarField field, Scalar min, Scalar max,
			int width, int height) {
		this.width = width;
		this.height = height;
		this.min = min;
		this.max = max;
		this.field = field;
	}

	@Override
	public void draw(Graphics g, int dx, int dy) {
		for (int y = dy; y < dy + height; y++) {
			for (int x = dx; x < dx + width; x++) {
				Scalar value = field.get(new Vector(Pixel.get(), x, y, 0));
				if (value.compareTo(max) > 0) {
					value = max;
				} else if (value.compareTo(min) < 0) {
					value = min;
				}
				float ratio = (float) (value.convert(max.subtract(min)));
				g.setColor(new Color(1, 0, 0, ratio));
				g.drawLine(x, y, x, y);
			}
		}
	}

}
