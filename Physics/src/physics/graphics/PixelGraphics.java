package physics.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import physics.graphics.drawers.VectorCollection;
import physics.math.IntVector;
import physics.math.Scalar;
import physics.math.Vector;

public class PixelGraphics {

	private Scalar pixel;
	private Graphics2D g;

	public PixelGraphics(Scalar pixel, Graphics2D g) {
		this.pixel = pixel;
		this.g = g;
	}

	public void translate(Vector dr) {
		IntVector iv = Pixel.convert(dr, pixel);
		g.translate(iv.get(0), iv.get(1));
	}

	public void setColor(Color c) {
		g.setColor(c);
	}

	public void setFont(Font font) {
		g.setFont(font);
	}

	public void drawLine(Vector start, Vector end) {
		IntVector i_start = Pixel.convert(start, pixel);
		IntVector i_end = Pixel.convert(end, pixel);
		g.drawLine(i_start.get(0), i_start.get(1), i_end.get(0), i_end.get(1));
	}

	public void fillRect(Vector center, Scalar width, Scalar height) {
		IntVector i_corner = Pixel
				.convert(center.subtract(new Vector(width, height).extend(center.getDimension()).divide(2)), pixel);
		int i_width = Pixel.convert(width, pixel);
		int i_height = Pixel.convert(height, pixel);
		g.fillRect(i_corner.get(0), i_corner.get(1), i_width, i_height);
	}

	public void drawRect(Vector center, Scalar width, Scalar height) {
		IntVector i_corner = Pixel
				.convert(center.subtract(new Vector(width, height).extend(center.getDimension()).divide(2)), pixel);
		int i_width = Pixel.convert(width, pixel);
		int i_height = Pixel.convert(height, pixel);
		g.drawRect(i_corner.get(0), i_corner.get(1), i_width, i_height);
	}

	public void fillOval(Vector center, Scalar width, Scalar height) {
		IntVector i_corner = Pixel
				.convert(center.subtract(new Vector(width, height).extend(center.getDimension()).divide(2)), pixel);
		int i_width = Pixel.convert(width, pixel);
		int i_height = Pixel.convert(height, pixel);
		g.fillOval(i_corner.get(0), i_corner.get(1), i_width, i_height);
	}

	public void drawOval(Vector center, Scalar width, Scalar height) {
		IntVector i_corner = Pixel
				.convert(center.subtract(new Vector(width, height).extend(center.getDimension()).divide(2)), pixel);
		int i_width = Pixel.convert(width, pixel);
		int i_height = Pixel.convert(height, pixel);
		g.drawOval(i_corner.get(0), i_corner.get(1), i_width, i_height);
	}

	public void drawPolyline(VectorCollection vs) {
		int size = vs.size();
		int[] xs = new int[size];
		int[] ys = new int[size];
		for (int i = 0; i < size; i++) {
			IntVector iv = Pixel.convert(vs.get(i), pixel);
			xs[i] = iv.get(0);
			ys[i] = iv.get(1);
		}
		g.drawPolyline(xs, ys, size);
	}

	public void drawPolygon(VectorCollection vs) {
		g.drawPolygon(Pixel.convert(vs, pixel));
	}

	public void fillPolygon(VectorCollection vs) {
		g.fillPolygon(Pixel.convert(vs, pixel));
	}

	public void drawString(String str, Vector p) {
		IntVector i_p = Pixel.convert(p, pixel);
		g.drawString(str, i_p.get(0), i_p.get(1));
	}

	public void rawTranslate(IntVector dr) {
		g.translate(dr.get(0), dr.get(1));
	}

	public void rawTranslate(int dx, int dy) {
		g.translate(dx, dy);
	}

	public void rotate(Scalar angle, Vector position) {
		g.rotate(angle.convert(Scalar.RADIAN), position.get(0).convert(pixel), position.get(1).convert(pixel));
	}

}
