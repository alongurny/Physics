package graphics.drawers;

import java.awt.Color;
import java.awt.Graphics;

import bodies.Spaceship;
import graphics.PixelHandler;

public class SpaceshipDrawer implements Drawable {

	private Spaceship ship;
	private PixelHandler pixelHandler;

	public SpaceshipDrawer(Spaceship ship, PixelHandler pixelHandler) {
		this.ship = ship;
		this.pixelHandler = pixelHandler;
	}

	@Override
	public void draw(Graphics g, int dx, int dy) {
		g.setColor(Color.RED);
		g.fillOval(pixelHandler.to(ship.getPosition().get(0)) + dx, pixelHandler.to(ship.getPosition().get(1)) + dy, 10,
				10);
	}

}
