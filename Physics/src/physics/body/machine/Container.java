package physics.body.machine;

import physics.math.Scalar;
import physics.quantity.Quantities;
import physics.quantity.Quantity;

public class Container {

	private Scalar maximum;
	private Scalar value;
	private Quantity quantity;

	public Container(Scalar capacity) {
		this(capacity, capacity);
	}

	public Container(Scalar maximum, Scalar value) {
		Quantities.requireSame(maximum, value);
		this.maximum = maximum;
		this.value = value;
		this.quantity = maximum.getQuantity();
	}

	public void fill(Scalar toFill) {
		Quantities.require(toFill, quantity);
		Scalar sum = toFill.add(value);
		if (sum.compareTo(maximum) < 0) {
			value = sum;
		} else {
			value = maximum;
		}
	}

	public Scalar getMaximum() {
		return maximum;
	}

	public double getPercentage() {
		return value.convert(maximum) * 100;
	}

	public Quantity getQuantity() {
		return quantity;
	}

	public Scalar getValue() {
		return value;
	}

	public boolean isEmpty() {
		return Scalar.isZero(value);
	}

	public Scalar pull(Scalar toPull) {
		Quantities.require(toPull, quantity);
		if (toPull.compareTo(value) < 0) {
			value = value.subtract(toPull);
			return toPull;
		}
		Scalar oldValue = value;
		value = Scalar.zero(quantity);
		return oldValue;
	}
}
