package graphics.drawers;

import graphics.Pixel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.function.Supplier;

import physics.Scalar;
import physics.ScalarField;
import physics.Vector;

public class ScalarFieldDrawer implements Drawable {

	private double width, height;
	private Scalar min, max;
	private Supplier<ScalarField> fieldSupp;
	private BufferedImage image;

	public ScalarFieldDrawer(Supplier<ScalarField> fieldSupp, Scalar min,
			Scalar max, double width, double height) {
		this.width = width;
		this.height = height;
		this.min = min;
		this.max = max;
		this.fieldSupp = fieldSupp;
		image = new BufferedImage((int) width, (int) height,
				BufferedImage.TYPE_INT_ARGB);
	}

	@Override
	public void draw(Graphics g, int dx, int dy) {
		ScalarField field = fieldSupp.get();
		Scalar pixel = Pixel.get();

		for (int y = 0; y < height; y += 3) {
			for (int x = 0; x < width; x += 3) {

				Scalar value = field.get(new Vector(pixel, x - dx, y - dy, 0));
				if (value.compareTo(max) > 0) {
					value = max;
				} else if (value.compareTo(min) <= 0) {
					continue;
				}
				float ratio = (float) (value.convert(max.subtract(min)));
				image.setRGB(x, y, new Color(1, 0, 0, ratio).getRGB());
			}
		}
		g.drawImage(image, 0, 0, (int) width, (int) height, null);
	}
}
