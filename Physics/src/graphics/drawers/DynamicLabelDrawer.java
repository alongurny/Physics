package graphics.drawers;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import graphics.PixelHandler;
import physics.Body;
import physics.Vector;

public class DynamicLabelDrawer implements Drawable {

	private List<String> strings;
	private List<Supplier<?>> suppliers;
	private Body body;
	private int offsetX, offsetY;
	private PixelHandler pixelHandler;

	public DynamicLabelDrawer(Body body, int offsetX, int offsetY, PixelHandler pixelHandler) {
		strings = new ArrayList<String>();
		suppliers = new ArrayList<Supplier<?>>();
		this.body = body;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.pixelHandler = pixelHandler;
	}

	public void add(String desc, Supplier<?> supp) {
		strings.add(desc);
		suppliers.add(supp);
	}

	@Override
	public void draw(Graphics g, int dx, int dy) {
		g.setColor(Color.WHITE);
		Vector p = body.getPosition();
		for (int i = 0; i < strings.size(); i++) {
			g.drawString(strings.get(i) + " = " + suppliers.get(i).get(), pixelHandler.to(p.get(0)) + dx + offsetX,
					pixelHandler.to(p.get(1)) + dy + offsetY + 10 * i);
		}
	}
}
