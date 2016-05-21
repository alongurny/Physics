package physics.graphics;

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

import javax.swing.JPanel;

import physics.graphics.drawers.Drawable;
import physics.graphics.drawers.LabelDrawer;
import physics.math.IntVector;
import physics.math.Scalar;
import physics.math.Vector;

@SuppressWarnings("serial")
public class Panel extends JPanel {

	private Vector focus;
	private LabelDrawer labelDrawer;
	private List<Drawable> drawables;
	private List<Runnable> calculations;
	private Scalar pixel;
	private boolean scrollable;
	private Thread calculationThread;
	private Thread drawingThread;
	private long dt;
	private boolean calculating;

	public Panel(int width, int height, Vector focus, Scalar pixel) {
		this.focus = focus;
		this.pixel = pixel;
		labelDrawer = new LabelDrawer(10, 20);
		calculations = new CopyOnWriteArrayList<>();
		drawables = new ArrayList<Drawable>();
		setFocusable(true);
		addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (scrollable) {
					multiplyPixel(Math.pow(1.1, e.getPreciseWheelRotation()));
				}
			}
		});
		setPreferredSize(new Dimension(width, height));
		calculationThread = new Thread(this::calculate);
		drawingThread = new Thread(() -> {
			while (true) {
				repaint();
			}
		});

	}

	private void multiplyPixel(double value) {
		pixel = pixel.multiply(value);
	}

	private void calculate() {
		calculating = true;
		while (calculating) {
			long start = System.currentTimeMillis();
			calculations.forEach(Runnable::run);
			long diff = System.currentTimeMillis() - start;
			if (diff < dt) {
				try {
					Thread.sleep(dt - diff);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void setCalculating(boolean calculating) {
		this.calculating = calculating;
	}

	public void start() {
		calculationThread.start();
		drawingThread.start();
	}

	public boolean isScrollable() {
		return scrollable;
	}

	public void setScrollable(boolean scrollable) {
		this.scrollable = scrollable;
	}

	public void addDrawable(Drawable drawable) {
		drawables.add(drawable);
	}

	public void addCalculation(Runnable calculation) {
		calculations.add(calculation);
	}

	public void addLabel(String desc, Supplier<?> supp) {
		labelDrawer.add(desc, supp);
	}

	public Vector getFocus() {
		return focus;
	}

	public Scalar getPixel() {
		return pixel;
	}

	public Vector getVector(int length, Point p) {
		return focus.add(Vector
				.extend(new Vector(p.getX() - getWidth() / 2, p.getY() - getHeight() / 2).multiply(pixel), length));

	}

	public void setFocus(Vector focus) {
		this.focus = Objects.requireNonNull(focus, "focus cannot be null");
	}

	public void setFPS(int fps) {
		this.dt = 1000 / fps;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		IntVector i_focus = Pixel.convert(focus, pixel);
		int dx = getWidth() / 2 - i_focus.get(0);
		int dy = getHeight() / 2 - i_focus.get(1);
		for (Drawable d : drawables) {
			g.translate(dx, dy);
			d.draw(g, pixel);
			g.translate(-dx, -dy);
		}
		labelDrawer.draw(g, pixel);
	}
}
