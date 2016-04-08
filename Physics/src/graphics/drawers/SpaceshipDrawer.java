package graphics.drawers;

import java.awt.Color;
import java.awt.Graphics;

import bodies.Spaceship;
import graphics.Pixel;

public class SpaceshipDrawer implements Drawable {

	private Spaceship ship;

	public SpaceshipDrawer(Spaceship ship) {
		this.ship = ship;
	}

	@Override
	public void draw(Graphics g, int dx, int dy) {
		g.setColor(Color.RED);
		g.fillOval(Pixel.to(ship.getPosition().get(0)) + dx, Pixel.to(ship.getPosition().get(1)) + dy, 10, 10);
	}

}
