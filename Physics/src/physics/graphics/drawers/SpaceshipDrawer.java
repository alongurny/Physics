package physics.graphics.drawers;

import java.awt.Color;
import java.awt.Graphics;

import physics.body.machine.Spaceship;
import physics.graphics.Pixel;
import physics.math.IntVector;
import physics.math.Scalar;

public class SpaceshipDrawer implements Drawable {

	private Spaceship ship;

	public SpaceshipDrawer(Spaceship ship) {
		this.ship = ship;
	}

	@Override
	public void draw(Graphics g, Scalar pixel) {
		g.setColor(Color.RED);
		IntVector i_p = Pixel.convert(ship.getPosition(), pixel);
		g.fillOval(i_p.get(0), i_p.get(1), 10, 10);
	}

}
