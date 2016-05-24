package physics.graphics.drawers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import physics.body.Body;
import physics.graphics.PixelGraphics;
import physics.math.IntVector;

public class DynamicLabelDrawer implements PixelDrawable {

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
	public void draw(PixelGraphics g) {
		g.setColor(Color.WHITE);
		for (int i = 0; i < strings.size(); i++) {
			IntVector offset = new IntVector(offsetX, offsetY + 10 * i);
			g.rawTranslate(offset);
			g.drawString(strings.get(i) + " = " + suppliers.get(i).get(), body.getPosition());
			g.rawTranslate(offset.negate());
		}
	}
}
