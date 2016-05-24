package physics.graphics.drawers;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class LabelDrawer implements RawDrawable {

	private List<String> strings;
	private List<Supplier<?>> suppliers;
	private int x, y;

	public LabelDrawer(int x, int y) {
		strings = new ArrayList<String>();
		suppliers = new ArrayList<Supplier<?>>();
		this.x = x;
		this.y = y;
	}

	public void add(String desc, Supplier<?> supp) {
		strings.add(desc);
		suppliers.add(supp);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		for (int i = 0; i < strings.size(); i++) {
			g.drawString(strings.get(i) + " = " + suppliers.get(i).get(), x, y + 10 * i);
		}
	}
}
