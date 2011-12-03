package de.fhb.defenderTouch.units.notmovable;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.units.root.Building;

public class Defence extends Building {


	
	public static final int PRICE = 40;
	protected int size = 0;

	public Defence(int x, int y, int mode, Player player, DefenderControl gamelogic) {
		super(x, y, mode, player, gamelogic);
		healthpointsMax = 250;
		healthpointsStat = 250;
		damagePerHit = 80;
		attackRange = 350;
	}

	public void drawFigure(Graphics graphics) {

		graphics.scale(1.0f, 1.0f);
		// graphics.rotate(0, 0, 180);

		switch (this.level) {
		case LEVEL_ONE:
			size = 18;
			break;
		case LEVEL_TWO:
			size = 20;
			break;
		case LEVEL_THREE:
			size = 22;
			break;
		}

		// ArrayList<Vector2f> vektoren = new ArrayList<Vector2f>();
		// vektoren.add(new Vector2f(-size, -size));
		// vektoren.add(new Vector2f(size, -size));
		// vektoren.add(new Vector2f(0, -size));
		// vektoren.add(new Vector2f(0, -size * 2));
		// vektoren.add(new Vector2f(0, size));
		// vektoren.add(new Vector2f(size, size));
		// vektoren.add(new Vector2f(-size, size));
		// graphics.drawOval(-size, -size * 2, size * 2, size * 2);
		// GraphicTools.zeicheFigurNachVektoren(vektoren, graphics);

		graphics.drawRect(-15, -17, 30, 30);

		Image image = null;
		try {
			image = new Image("data/buildings/Defence.png");
			image = image.getScaledCopy(size, size);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		graphics.drawImage(image, -image.getHeight() / 2, -image.getWidth() / 2, size, size, 0f, 0f);
		graphics.resetTransform();

	}

}
