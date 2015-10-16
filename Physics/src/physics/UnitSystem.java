package physics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitSystem {

	public static final UnitSystem SI = new UnitSystem(new Scalar[] {
			Scalar.METER, Scalar.SECOND, Scalar.KILOGRAM, Scalar.COULOMB,
			Scalar.RADIAN }, new String[] { "m", "s", "kg", "C", "rad" });

	static {
		SI.bindName(Quantity.FORCE, "N");
		SI.bindName(Quantity.CURRENT, "A");
		SI.bindName(Quantity.RESISTANCE, "ohm");
		SI.bindName(Quantity.CONDUCTION, "mho");
		SI.bindName(Quantity.VOLTAGE, "V");
		SI.bindName(Quantity.POWER, "W");
		SI.bindName(Quantity.ENERGY, "J");
		SI.bindName(Quantity.VELOCITY, "m/s");
		SI.bindName(Quantity.ANGULAR_VELOCITY, "rad/s");
		SI.bindName(Quantity.TORQUE, "J/rad");
		SI.bindName(Quantity.quotinent(Quantity.FORCE, Quantity.CHARGE), "N/C");
		SI.bindName(Quantity.MOMENTUM, "N s");
	}

	private final Scalar[] scalars;

	private Map<Quantity, String> repr = new HashMap<Quantity, String>();

	public UnitSystem(Scalar[] scalars, String[] reprs) {
		if (scalars.length != Quantity.COUNT || reprs.length != Quantity.COUNT) {
			throw new IllegalArgumentException();
		}
		this.scalars = scalars;
		for (int i = 0; i < Quantity.COUNT; i++) {
			bindName(scalars[i].getQuantity(), reprs[i]);
		}

	}

	public void bindName(Quantity q, String r) {
		repr.put(q, r);
	}

	public Scalar get(Quantity q, double factor) {
		return get(q).multiply(factor);
	}

	public Scalar get(Quantity q) {
		Scalar res = Scalar.ONE;
		for (int i = 0; i < Quantity.COUNT; i++) {
			res = res.multiply(scalars[i].pow(q.get(i)));
		}
		return res;
	}

	public String getUnitName(Quantity quantity) {
		if (repr.containsKey(quantity)) {
			return repr.get(quantity);
		}
		List<String> ss = new ArrayList<String>();
		for (int i = 0; i < Quantity.COUNT; i++) {
			int dim = quantity.get(i);
			if (dim != 0) {
				ss.add(dim == 1 ? repr.get(scalars[i].getQuantity()) : repr
						.get(scalars[i].getQuantity()) + "^" + dim);
			}

		}
		return String.join(" ", ss.toArray(new String[ss.size()]));
	}
}
