package graphics.drawers;

import java.awt.Color;
import java.awt.Graphics;

import bodies.Spaceship;
import graphics.Pixel;
import physics.IntVector;
import physics.Scalar;

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
