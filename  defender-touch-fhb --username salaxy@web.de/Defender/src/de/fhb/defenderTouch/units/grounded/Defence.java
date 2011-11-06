package de.fhb.defenderTouch.units.grounded;

import java.util.ArrayList;

import processing.core.PGraphics;
import processing.core.PVector;
import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.graphics.GraphicTools;

public class Defence extends Building {

	public static final int PRICE = 40;
	protected int size = 0;

	public Defence(int x, int y, int mode, int playerID, DefenderControl gamelogic) {
		super(x, y, mode, playerID, gamelogic);
	}

	public void drawFigure(PGraphics graphics) {

		// this.entscheideLineFarbe(graphics);
		graphics.scale(1.5f);
		// graphics.strokeWeight(1);
		graphics.rotate((float) Math.PI);
		switch (this.level) {
		case LEVEL_ONE:
			size = 4;
			break;
		case LEVEL_TWO:
			size = 6;
			break;
		case LEVEL_THREE:
			size = 8;
			break;
		}

		ArrayList<PVector> vektoren = new ArrayList<PVector>();
		vektoren.add(new PVector(-size, -size));
		vektoren.add(new PVector(size, -size));
		vektoren.add(new PVector(0, -size));
		vektoren.add(new PVector(0, -size * 2));
		vektoren.add(new PVector(0, size));
		vektoren.add(new PVector(size, size));
		vektoren.add(new PVector(-size, size));
		graphics.noFill();
		graphics.ellipse(0, -size, size * 2, size * 2);
		GraphicTools.zeicheFigurNachVektoren(vektoren, graphics);

		graphics.resetMatrix();
		graphics.stroke(0);

	}

}
