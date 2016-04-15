package graphics.drawers;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import graphics.Pixel;
import physics.body.Body;
import physics.math.IntVector;
import physics.math.Scalar;
import physics.math.Vector;

public class DynamicLabelDrawer implements Drawable {

	private List<String> strings;
	private List<Supplier<?>> suppliers;
	private Body body;
	private int offsetX, offsetY;

	public DynamicLabelDrawer(Body body, int offsetX, int offsetY) {
		strings = new ArrayList<String>();
		suppliers = new ArrayList<Supplier<?>>();
		this.body = body;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	public void add(String desc, Supplier<?> supp) {
		strings.add(desc);
		suppliers.add(supp);
	}

	@Override
	public void draw(Graphics g, Scalar pixel) {
		g.setColor(Color.WHITE);
		Vector p = body.getPosition();
		IntVector i_p = Pixel.convert(p, pixel);
		for (int i = 0; i < strings.size(); i++) {
			g.drawString(strings.get(i) + " = " + suppliers.get(i).get(), i_p.get(0) + offsetX,
					i_p.get(1) + offsetY + 10 * i);
		}
	}
}
