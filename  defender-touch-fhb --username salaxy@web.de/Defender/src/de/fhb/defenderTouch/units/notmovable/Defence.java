package de.fhb.defenderTouch.units.notmovable;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import processing.core.PVector;
import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.graphics.GraphicTools;

public class Defence extends Building {

	public static final int PRICE = 40;
	protected int size = 0;

	public Defence(int x, int y, int mode, Player player, DefenderControl gamelogic) {
		super(x, y, mode, player, gamelogic);
	}

	public void drawFigure(Graphics graphics) {

		// this.entscheideLineFarbe(graphics);
		graphics.scale(1.5f,1.5f);
		// graphics.strokeWeight(1);
		graphics.rotate(0,0,180);
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
//		graphics.noFill();
		graphics.drawOval(0, -size, size * 2, size * 2);
		GraphicTools.zeicheFigurNachVektoren(vektoren, graphics);

		graphics.resetTransform();
//		graphics.stroke(0);

	}

}
