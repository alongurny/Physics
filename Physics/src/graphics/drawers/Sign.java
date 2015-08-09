package graphics.drawers;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Sign implements Drawable {

	List<String> strings;
	List<Supplier<?>> suppliers;

	public Sign() {
		strings = new ArrayList<String>();
		suppliers = new ArrayList<Supplier<?>>();
	}

	public void add(String desc, Supplier<?> supp) {
		strings.add(desc);
		suppliers.add(supp);
	}

	@Override
	public void draw(Graphics g, int dx, int dy) {
		g.setColor(Color.WHITE);
		for (int i = 0; i < strings.size(); i++) {
			g.drawString(strings.get(i) + " = " + suppliers.get(i).get(), 10,
					20 + 10 * i);
		}
	}
}
