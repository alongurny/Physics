package physics;

public class Direction {

	private double x, y, z;

	private Direction(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector multiply(Scalar s) {
		return new Vector(s.multiply(x), s.multiply(y), s.multiply(z));
	}

	public static Direction get(Vector v) {
		Vector d = v.divide(v.getMagnitude());
		return new Direction(d.getX().convert(Scalar.ONE), d.getY().convert(
				Scalar.ONE), d.getZ().convert(Scalar.ONE));
	}

	public static Direction getOnXY(double angle) {
		return new Direction(Math.cos(angle), Math.sin(angle), 0);
	}

	@Override
	public String toString() {
		return "Direction(" + x + ", " + y + ", " + z + ")";
	}
}
