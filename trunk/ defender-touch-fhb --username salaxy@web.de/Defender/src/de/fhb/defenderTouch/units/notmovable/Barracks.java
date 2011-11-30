package de.fhb.defenderTouch.units.notmovable;

import org.newdawn.slick.Graphics;
import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.units.root.Building;

public class Barracks extends Building {

	public static final int PRICE = 30;
	protected int size = 0;

	public Barracks(int x, int y, int mode, Player player, DefenderControl gamelogic) {
		super(x, y, mode, player, gamelogic);
	}

	public void drawFigure(Graphics graphics) {

		graphics.scale(1.5f, 1.5f);
		graphics.rotate(0, 0, 180);

		switch (this.level) {
		case LEVEL_ONE:
			size = 4;
			break;
		case LEVEL_TWO:
			size = 5;
			break;
		case LEVEL_THREE:
			size = 6;
			break;
		}

		int difference = 10;
		
		graphics.drawLine(-size, size, difference, -size + difference);
		graphics.drawLine(0, -size + difference, size, size);
		graphics.drawLine(-size, size, 0, -size + difference * 3);
		graphics.drawLine(0, -size + difference * 3, size, size);

		graphics.drawOval(-15, -16, 30, 30);
		graphics.resetTransform();

	}

}
