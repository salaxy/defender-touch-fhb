package de.fhb.defenderTouch.units.grounded;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import processing.core.PGraphics;

public class Ground extends Building {

	public static final int PRICE = 50;
	protected int size = 0;

	public Ground(int x, int y, int mode, int playerID, DefenderControl gamelogic) {
		super(x, y, mode, playerID, gamelogic);
	}

	public void drawFigure(PGraphics graphics) {

		// graphics.stroke(0);
		// graphics.strokeWeight(10);
		// graphics.fill(0);
		graphics.noFill();
		graphics.scale(1.5f);
		// graphics.strokeWeight(1);
		graphics.rotate((float) Math.PI);

		switch (this.level) {
		case LEVEL_ONE:
			size = 5;
			break;
		case LEVEL_TWO:
			size = 7;
			break;
		case LEVEL_THREE:
			size = 9;
			break;
		}

		// this.entscheideLineFarbe(graphics);
		graphics.triangle(-size, +size, 0, -size, +size, +size);

		graphics.resetMatrix();
		graphics.stroke(0);

	}

}
