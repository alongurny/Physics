package physics.graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

import javax.swing.JPanel;

import physics.graphics.drawers.LabelDrawer;
import physics.graphics.drawers.PixelDrawable;
import physics.math.Scalar;
import physics.math.Vector;

@SuppressWarnings("serial")
public class Panel extends JPanel {

	private Vector focus;
	private LabelDrawer labelDrawer;
	private List<PixelDrawable> pixelDrawables;
	private List<Runnable> calculations;
	private Scalar pixel;
	private boolean scrollable;
	private Thread calculationThread;
	private long dt;
	private boolean calculating;

	public Panel(int width, int height, Vector focus, Scalar pixel) {
		this.focus = focus;
		this.pixel = pixel;
		labelDrawer = new LabelDrawer(10, 20);
		calculations = new CopyOnWriteArrayList<>();
		pixelDrawables = new ArrayList<PixelDrawable>();
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
	}

	private void multiplyPixel(double value) {
		pixel = pixel.multiply(value);
	}

	private void calculate() {
		while (calculating) {
			long start = System.currentTimeMillis();
			calculations.forEach(Runnable::run);
			repaint();
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
		calculating = true;
		calculationThread.start();
	}

	public boolean isScrollable() {
		return scrollable;
	}

	public void setScrollable(boolean scrollable) {
		this.scrollable = scrollable;
	}

	public void addDrawable(PixelDrawable pixelDrawable) {
		pixelDrawables.add(pixelDrawable);
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
		int w = getWidth() / 2;
		int h = getHeight() / 2;
		g.translate(w, h);
		PixelGraphics pg = new PixelGraphics(pixel, (Graphics2D) g);
		for (PixelDrawable d : pixelDrawables) {
			pg.translate(focus.negate());
			d.draw(pg);
			pg.translate(focus);
		}
		g.translate(-w, -h);
		labelDrawer.draw(g);
	}
}
