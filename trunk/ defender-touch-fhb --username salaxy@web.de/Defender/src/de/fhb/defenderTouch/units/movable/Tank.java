package de.fhb.defenderTouch.units.movable;

import org.newdawn.slick.Graphics;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.units.notmovable.Building;

public class Tank extends Building {

	public static final int PRICE = 80;
	protected int size = 0;

	public Tank(int x, int y, int mode, int playerID, DefenderControl gamelogic) {
		super(x, y, mode, playerID, gamelogic);
	}

	public void drawFigure(Graphics graphics) {

		// this.entscheideLineFarbe(graphics);
		graphics.scale(1.5f,1.5f);
//		graphics.noFill();
		// graphics.strokeWeight(1);
		graphics.rotate(0,0,180);
		switch (this.level) {
		case LEVEL_ONE:
			size = 8;
			break;
		case LEVEL_TWO:
			size = 10;
			break;
		case LEVEL_THREE:
			size = 12;
			break;
		}

		graphics.drawRect(0, size, size * 2, size * 3);
		graphics.drawOval(0, size, size, size);
		graphics.drawRect(0, -size/2, size/2, size * 2);

		graphics.resetTransform();
//		graphics.stroke(0);

	}

}
