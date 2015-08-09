package graphics.drawers;

import graphics.Pixel;

import java.awt.Color;
import java.awt.Graphics;

import bodies.Spaceship;

public class SpaceshipDrawer implements Drawable {

	private Spaceship ship;

	public SpaceshipDrawer(Spaceship ship) {
		this.ship = ship;
	}

	@Override
	public void draw(Graphics g, int dx, int dy) {
		g.setColor(Color.RED);
		g.fillOval(Pixel.to(ship.getPosition().getX()) + dx,
				Pixel.to(ship.getPosition().getY()) + dy, 10, 10);
	}

}
