package graphics;

import graphics.drawers.Drawable;
import graphics.drawers.Sign;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

import javax.swing.JFrame;
import javax.swing.JPanel;

import physics.Quantity;
import physics.Vector;

public class Frame extends JFrame {

	private Vector focus;
	private Sign sign;
	private List<Drawable> drawables;
	private List<DrawingListener> drawingListeners;

	public Frame(Dimension size, Color background) {
		setBackground(background);
		focus = Vector.zero(Quantity.LENGTH);
		sign = new Sign();
		drawingListeners = new CopyOnWriteArrayList<DrawingListener>();
		drawables = new ArrayList<Drawable>();
		add(new JPanel() {
			@Override
			public void paint(Graphics g) {
				int dx = getWidth() / 2 - Pixel.to(focus.getX());
				int dy = getHeight() / 2 - Pixel.to(focus.getY());
				sign.draw(g, dx, dy);
				for (Drawable d : drawables) {
					d.draw(g, dx, dy);
				}
			}
		});
		addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				// Vector newFocus = new Vector(Pixel.get(), e.getX() -
				// getWidth()
				// / 2, e.getY() - getHeight() / 2, 0).add(focus);
				Pixel.scroll(Math.pow(1.1, e.getPreciseWheelRotation()));
				// focus = newFocus;
			}
		});
		setSize(size);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		new Thread(() -> {
			long lastMove = System.nanoTime();
			while (true) {
				long now = System.nanoTime();
				long prev = lastMove;
				drawingListeners.forEach(d -> d.onDraw(new DrawingEvent(prev,
						now)));
				lastMove = now;
				repaint();
				try {
					Thread.sleep(15);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		}).start();

	}

	public void setFocus(Vector focus) {
		this.focus = focus;
	}

	public void addDrawable(Drawable drawable) {
		drawables.add(drawable);
	}

	public void addSign(String desc, Supplier<?> supp) {
		sign.add(desc, supp);
	}

	public void addDrawingListener(DrawingListener drawingListener) {
		drawingListeners.add(drawingListener);
	}
}
