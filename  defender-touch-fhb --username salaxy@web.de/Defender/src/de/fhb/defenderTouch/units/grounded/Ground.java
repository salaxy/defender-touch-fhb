package de.fhb.defenderTouch.units.grounded;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import processing.core.PApplet;
import processing.core.PGraphics;

public class Ground extends Building {

	public static final int PRICE = 50;

	public Ground(int x, int y, int mode, int playerID,DefenderControl gamelogic) {
		super(x, y, mode, playerID, gamelogic);
	}

	public void drawFigure(PGraphics graphics) {

		// graphics.stroke(0);
		// graphics.strokeWeight(10);
		// graphics.fill(0);
		graphics.noFill();
		graphics.scale(1.5f);
		graphics.rotate((float) Math.PI);
//		this.entscheideLineFarbe(graphics);
		graphics.triangle(-5, +5, 0, -5, +5, +5);

		graphics.resetMatrix();
		graphics.stroke(0);

	}
	

}
