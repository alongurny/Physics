package graphics3d;

import java.awt.Color;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

import bodies.space.Planet;
import bodies.space.Sun;
import physics.Quantity;
import physics.Scalar;
import physics.Vector;

public class PlanetDrawer3D {

	public static void main(String[] args) {
		Planet sun = new Sun(Vector.Axes3D.ORIGIN, Vector.zero(Quantity.VELOCITY, 3), Vector.zero(Quantity.ANGLE, 3),
				Vector.zero(Quantity.ANGULAR_VELOCITY, 3));
		Scalar pixel = Scalar.METER.multiply(1e7);
		SimpleUniverse universe = new SimpleUniverse();
		BranchGroup group = new BranchGroup();
		TransformGroup tg = new TransformGroup();
		Transform3D t = new Transform3D();
		Vector pos = sun.getPosition().divide(pixel.multiply(universe.getCanvas().getWidth() / 2));
		t.setTranslation(new Vector3d(pos.get(0).convert(Scalar.ONE), pos.get(1).convert(Scalar.ONE),
				pos.get(2).convert(Scalar.ONE)));
		tg.setTransform(t);
		Sphere sphere = new Sphere((float) (sun.getRadius().divide(pixel.multiply(universe.getCanvas().getWidth() / 2))
				.convert(Scalar.ONE)));
		DirectionalLight light = new DirectionalLight(new Color3f(Color.YELLOW), new Vector3f(12, 11, -20));
		light.setInfluencingBounds(new BoundingSphere(new Point3d(), Double.POSITIVE_INFINITY));
		tg.addChild(sphere);
		tg.addChild(light);
		group.addChild(tg);
		universe.addBranchGraph(group);
		universe.getViewingPlatform().setNominalViewingTransform();
	}
}
