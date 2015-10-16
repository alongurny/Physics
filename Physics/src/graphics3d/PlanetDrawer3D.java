package graphics3d;

import graphics.Pixel;

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

import physics.Quantity;
import physics.Scalar;
import physics.Vector;
import bodies.space.Planet;
import bodies.space.Sun;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class PlanetDrawer3D {

	public static void main(String[] args) {
		Planet sun = new Sun(Vector.POSITION_ORIGIN,
				Vector.zero(Quantity.VELOCITY), Vector.zero(Quantity.ANGLE),
				Vector.zero(Quantity.ANGULAR_VELOCITY));
		SimpleUniverse universe = new SimpleUniverse();
		BranchGroup group = new BranchGroup();
		TransformGroup tg = new TransformGroup();
		Transform3D t = new Transform3D();
		Vector pos = sun.getPosition().divide(
				Pixel.get().multiply(universe.getCanvas().getWidth() / 2));
		t.setTranslation(new Vector3d(pos.getX().convert(Scalar.ONE), pos
				.getY().convert(Scalar.ONE), pos.getZ().convert(Scalar.ONE)));
		tg.setTransform(t);
		Sphere sphere = new Sphere(
				(float) (sun.getRadius().divide(
						Pixel.get().multiply(
								universe.getCanvas().getWidth() / 2))
						.convert(Scalar.ONE)));
		DirectionalLight light = new DirectionalLight(
				new Color3f(Color.YELLOW), new Vector3f(12, 11, -20));
		light.setInfluencingBounds(new BoundingSphere(new Point3d(),
				Double.POSITIVE_INFINITY));
		tg.addChild(sphere);
		tg.addChild(light);
		group.addChild(tg);
		universe.addBranchGraph(group);
		universe.getViewingPlatform().setNominalViewingTransform();
	}
}
