package physics.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

import javax.swing.JFrame;
import javax.swing.JPanel;

import graphics.drawers.Drawable;
import graphics.drawers.LabelDrawer;
import physics.math.IntVector;
import physics.math.Scalar;
import physics.math.Vector;

@SuppressWarnings("serial")
public class Frame extends JFrame {

	private Vector focus;
	private LabelDrawer labelDrawer;
	private List<Drawable> drawables;
	private List<DrawingListener> drawingListeners;
	private Scalar pixel;
	public static final Dimension DEFAULT_SIZE = new Dimension(640, 640);

	public Vector getVector(int length, Point p) {
		return focus.add(
				Vector.extend(new Vector(p.getX() - getWidth() / 2, p.getY() - getHeight() / 2).multiply(pixel), 3));

	}

	public Scalar getPixel() {
		return pixel;
	}

	public Frame(Vector focus, Color background, Scalar pixel) {
		setBackground(background);
		this.focus = focus;
		this.pixel = pixel;
		labelDrawer = new LabelDrawer(10, 20);
		drawingListeners = new CopyOnWriteArrayList<DrawingListener>();
		drawables = new ArrayList<Drawable>();
		setFocusable(true);
		add(new JPanel() {
			@Override
			public void paint(Graphics g) {
				IntVector i_focus = Pixel.convert(Frame.this.focus, Frame.this.pixel);
				int dx = getWidth() / 2 - i_focus.get(0);
				int dy = getHeight() / 2 - i_focus.get(1);
				for (Drawable d : drawables) {
					g.translate(dx, dy);
					d.draw(g, Frame.this.pixel);
					g.translate(-dx, -dy);
				}
				labelDrawer.draw(g, Frame.this.pixel);
			}
		});
		addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				Frame.this.pixel = Frame.this.pixel.multiply(Math.pow(1.1, e.getPreciseWheelRotation()));
			}
		});
		setSize(DEFAULT_SIZE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		new Thread(() -> {
			long lastMove = System.nanoTime();
			while (true) {
				long now = System.nanoTime();
				long prev = lastMove;
				drawingListeners.forEach(d -> d.onDraw(new DrawingEvent(prev, now)));
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
		this.focus = Objects.requireNonNull(focus, "Focus cannot be null");
	}

	public void addDrawable(Drawable drawable) {
		drawables.add(drawable);
	}

	public void addLabel(String desc, Supplier<?> supp) {
		labelDrawer.add(desc, supp);
	}

	public void addDrawingListener(DrawingListener drawingListener) {
		drawingListeners.add(drawingListener);
	}

	public Vector getFocus() {
		return focus;
	}
}
