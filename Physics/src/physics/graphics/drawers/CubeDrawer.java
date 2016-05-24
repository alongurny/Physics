package physics.graphics.drawers;

import java.awt.Color;

import physics.body.geometric.Cube;
import physics.graphics.PixelGraphics;
import physics.math.Scalar;

public class CubeDrawer implements PixelDrawable {

	private Cube cube;
	private Color color;

	public CubeDrawer(Cube sphere, Color color) {
		this.cube = sphere;
		this.color = color;
	}

	@Override
	public void draw(PixelGraphics g) {
		g.setColor(color);
		Scalar side = cube.getSide();
		g.fillRect(cube.getPosition(), side, side);
	}

}
