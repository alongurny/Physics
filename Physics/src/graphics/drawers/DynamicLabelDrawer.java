package graphics.drawers;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import graphics.Pixel;
import physics.RegularBody;
import physics.IntVector;
import physics.Scalar;
import physics.Vector;

public class DynamicLabelDrawer implements Drawable {

	private List<String> strings;
	private List<Supplier<?>> suppliers;
	private RegularBody regularBody;
	private int offsetX, offsetY;

	public DynamicLabelDrawer(RegularBody regularBody, int offsetX, int offsetY) {
		strings = new ArrayList<String>();
		suppliers = new ArrayList<Supplier<?>>();
		this.regularBody = regularBody;
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
		Vector p = regularBody.getPosition();
		IntVector i_p = Pixel.convert(p, pixel);
		for (int i = 0; i < strings.size(); i++) {
			g.drawString(strings.get(i) + " = " + suppliers.get(i).get(), i_p.get(0) + offsetX,
					i_p.get(1) + offsetY + 10 * i);
		}
	}
}
