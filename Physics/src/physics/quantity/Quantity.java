package physics.quantity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import physics.math.IntVector;

public class Quantity {

	public enum Basic {
		LENGTH, TIME, MASS, CHARGE, ANGLE
	}

	public static final int COUNT = 5;

	private static final String[] letters = new String[] { "length", "time", "mass", "charge", "angle" };

	private static final Map<Quantity, String> repr = new HashMap<Quantity, String>();

	/*
	 * Basics
	 */
	public static final Quantity NONE = new Quantity(IntVector.zero(COUNT));

	public static final Quantity LENGTH = new Quantity(IntVector.axis(COUNT, 0));
	public static final Quantity TIME = new Quantity(IntVector.axis(COUNT, 1));
	public static final Quantity MASS = new Quantity(IntVector.axis(COUNT, 2));
	public static final Quantity CHARGE = new Quantity(IntVector.axis(COUNT, 3));
	public static final Quantity ANGLE = new Quantity(IntVector.axis(COUNT, 4));
	/*
	 * Mechanics
	 */
	public static final Quantity VELOCITY = quotinent(LENGTH, TIME);

	public static final Quantity ACCELERATION = quotinent(VELOCITY, TIME);
	public static final Quantity FORCE = product(MASS, ACCELERATION);
	public static final Quantity MOMENTUM = product(MASS, VELOCITY);

	/*
	 * Waves
	 */

	public static final Quantity FREQUENCY = TIME.inverse();

	/*
	 * Energy
	 */
	public static final Quantity ENERGY = product(FORCE, LENGTH);
	public static final Quantity POWER = quotinent(ENERGY, TIME);

	/*
	 * Rigid Body Mechanics
	 */
	public static final Quantity ANGULAR_VELOCITY = quotinent(ANGLE, TIME);
	public static final Quantity ANGULAR_ACCELERATION = quotinent(ANGULAR_VELOCITY, TIME);
	public static final Quantity TORQUE = product(FORCE, LENGTH).divide(ANGLE);
	public static final Quantity MOMENT_OF_INERTIA = quotinent(TORQUE, ANGULAR_ACCELERATION);
	public static final Quantity ANGULAR_MOMENTUM = product(MOMENT_OF_INERTIA, ANGULAR_VELOCITY);

	/*
	 * Geometry
	 */
	public static final Quantity AREA = pow(LENGTH, 2);
	public static final Quantity VOLUME = pow(LENGTH, 3);

	/*
	 * Electricity
	 */
	public static final Quantity CURRENT = quotinent(CHARGE, TIME);
	public static final Quantity VOLTAGE = quotinent(ENERGY, CHARGE);
	public static final Quantity RESISTANCE = quotinent(VOLTAGE, CURRENT);
	public static final Quantity CONDUCTION = quotinent(CURRENT, VOLTAGE);

	static {
		for (Field f : Quantity.class.getFields()) {
			if (Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers())) {
				try {
					Object o = f.get(null);
					if (o instanceof Quantity) {
						Quantity q = (Quantity) o;
						bindName(q, f.getName().toLowerCase().replace("_", " "));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

	public static void bindName(Quantity quantity, String name) {
		repr.put(quantity, name);
	}

	public Quantity inverse() {
		return new Quantity(vector.negate());
	}

	public static Quantity pow(Quantity q, int n) {
		return q.pow(n);
	}

	public static Quantity product(Quantity... qs) {
		Quantity res = NONE;
		for (Quantity q : qs) {
			res = res.multiply(q);
		}
		return res;
	}

	public static Quantity quotinent(Quantity p, Quantity q) {
		return product(p, q.inverse());
	}

	public static Quantity root(Quantity q, int n) {
		return new Quantity(q.vector.divide(n));
	}

	public static Quantity sqrt(Quantity q) {
		return root(q, 2);
	}

	private final IntVector vector;

	private Quantity(IntVector vector) {
		this.vector = vector;
	}

	public Quantity divide(Quantity q) {
		return quotinent(this, q);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Quantity && this.equalsQuantity((Quantity) obj);
	}

	private boolean equalsQuantity(Quantity p) {
		return vector.equals(p.vector);
	}

	public int get(int i) {
		return vector.get(i);
	}

	@Override
	public int hashCode() {
		return 0;
	}

	public Quantity multiply(Quantity q) {
		return new Quantity(vector.add(q.vector));
	}

	public Quantity pow(int n) {
		return new Quantity(vector.multiply(n));
	}

	@Override
	public String toString() {
		List<String> ss = new ArrayList<String>();
		for (int i = 0; i < vector.getDimension(); i++) {
			int value = vector.get(i);
			if (value != 0) {
				String letter = letters[i];
				ss.add(value == 1 ? letter : letter + "^" + value);
			}
		}
		String res = "[" + (ss.size() == 0 ? "None" : String.join(" ", ss.toArray(new String[ss.size()]))) + "]";
		if (repr.containsKey(this)) {
			res = repr.get(this) + " " + res;
		}
		return res;
	}
}
