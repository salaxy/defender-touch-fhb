package de.fhb.defenderTouch.units.notmovable;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import processing.core.PVector;
import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.graphics.GraphicTools;

public class Navi extends Building {

	public static final int PRICE = 30;

	public Navi(int x, int y, int mode, Player player, DefenderControl gamelogic) {
		super(x, y, mode, player, gamelogic);
	}

	public void drawFigure(Graphics graphics) {

		graphics.scale(1.5f, 1.5f);
		graphics.rotate(0, 0, 180);
		ArrayList<PVector> vektoren = new ArrayList<PVector>();
		vektoren.add(new PVector(0, 0));
		vektoren.add(new PVector(0, -8));
		vektoren.add(new PVector(-8, 0));
		vektoren.add(new PVector(0, 0));
		vektoren.add(new PVector(0, 4));
		vektoren.add(new PVector(4, 4));
		GraphicTools.zeicheFigurNachVektoren(vektoren, graphics);
		graphics.drawOval(-15, -16 , 30 , 30);
		graphics.resetTransform();

	}

}
