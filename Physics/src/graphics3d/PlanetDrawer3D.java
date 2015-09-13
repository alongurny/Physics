package graphics3d;

import graphics.Pixel;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import physics.Quantity;
import physics.Scalar;
import physics.Vector;
import bodies.space.Planet;
import bodies.space.Sun;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class PlanetDrawer3D {

	private List<Planet> planets;
	private List<Color> colors;
	private List<BranchGroup> tgs;
	private List<Boolean> brights;
	private SimpleUniverse universe;
	private BranchGroup group;
	private boolean running;

	public PlanetDrawer3D() {
		planets = new CopyOnWriteArrayList<>();
		colors = new CopyOnWriteArrayList<>();
		brights = new CopyOnWriteArrayList<>();
		tgs = new CopyOnWriteArrayList<>();
		universe = new SimpleUniverse();
		DirectionalLight dl = new DirectionalLight(new Color3f(Color.YELLOW),
				new Vector3f(0.3f, 0.8f, -1f));
		dl.setInfluencingBounds(new BoundingSphere(new Point3d(), 200f));
		group = new BranchGroup();
		group.setCapability(Group.ALLOW_CHILDREN_EXTEND);
		group.addChild(dl);
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(group);
	}

	public void addPlanet(Planet planet, Color color, boolean bright) {
		colors.add(color);
		planets.add(planet);
		brights.add(bright);
		Sphere sphere = new Sphere((float) Pixel.to(planet.getRadius()) / 640);
		BranchGroup bg = new BranchGroup();
		if (bright) {
			bg.addChild(new DirectionalLight(new Color3f(color), new Vector3f(
					0, 0, -1f)));
		}
		bg.setCapability(Group.ALLOW_CHILDREN_EXTEND);
		TransformGroup tg = new TransformGroup();
		Transform3D t = new Transform3D();
		Vector pos = planet.getPosition()
				.multiply(universe.getCanvas().getWidth()).divide(Pixel.get());
		t.setTranslation(new Vector3d(pos.getX().convert(Scalar.ONE), pos
				.getY().convert(Scalar.ONE), pos.getZ().convert(Scalar.ONE)));
		tg.setTransform(t);
		tg.addChild(sphere);
		bg.addChild(tg);
		group.addChild(bg);
		tgs.add(bg);
	}

	public void run() {
		running = true;

		while (running) {
			update();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void update() {
		for (int i = 0; i < planets.size(); i++) {
			BranchGroup bg = tgs.get(i);
			Transform3D t = new Transform3D();
			Planet planet = planets.get(i);
			Sphere sphere = new Sphere(
					(float) Pixel.to(planet.getRadius()) / 640);
			System.out.println(sphere.getRadius());
			Vector pos = planet.getPosition()
					.multiply(universe.getCanvas().getWidth())
					.divide(Pixel.get());
			TransformGroup tg = new TransformGroup();
			t.setTranslation(new Vector3d(pos.getX().convert(Scalar.ONE), pos
					.getY().convert(Scalar.ONE), pos.getZ().convert(Scalar.ONE)));
			tg.setTransform(t);
			tg.addChild(sphere);
			BranchGroup g = new BranchGroup();
			g.setCapability(Group.ALLOW_CHILDREN_EXTEND);
			g.addChild(tg);
			bg.addChild(g);
		}
	}

	public static void main(String[] args) {
		PlanetDrawer3D pd = new PlanetDrawer3D();
		pd.addPlanet(
				new Sun(Vector.POSITION_ORIGIN, Vector.zero(Quantity.VELOCITY),
						Vector.zero(Quantity.ANGLE), Vector
								.zero(Quantity.ANGULAR_VELOCITY)),
				Color.YELLOW, true);
		pd.run();
	}
}
