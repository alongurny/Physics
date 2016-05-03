package physics.body.geometric;

import physics.body.Chargeable;
import physics.math.Scalar;
import physics.math.Vector;

public class ChargeableSphere extends Sphere implements Chargeable {

	public ChargeableSphere(Scalar mass, Scalar charge, Vector center, Vector velocity, Scalar radius) {
		super(mass, charge, center, velocity, radius);
	}

	@Override
	public void addCharge(Scalar charge) {
		setCharge(getCharge().add(charge));
	}

}
