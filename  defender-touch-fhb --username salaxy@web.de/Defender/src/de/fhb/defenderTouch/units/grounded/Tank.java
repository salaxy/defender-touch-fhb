package de.fhb.defenderTouch.units.grounded;

import processing.core.PGraphics;
import de.fhb.defenderTouch.gamelogic.DefenderControl;

public class Tank extends Building {

	public static final int PRICE = 80;
	protected int size = 0;

	public Tank(int x, int y, int mode, int playerID, DefenderControl gamelogic) {
		super(x, y, mode, playerID, gamelogic);
	}

	public void drawFigure(PGraphics graphics) {

		// this.entscheideLineFarbe(graphics);
		graphics.scale(1.5f);
		graphics.noFill();
		// graphics.strokeWeight(1);
		graphics.rotate((float) Math.PI);
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

		graphics.rect(0, size, size * 2, size * 3);
		graphics.ellipse(0, size, size, size);
		graphics.rect(0, -size/2, size/2, size * 2);

		graphics.resetMatrix();
		graphics.stroke(0);

	}

}
