package de.fhb.defenderTouch.units.grounded;

import java.awt.Color;
import java.util.ArrayList;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.graphics.GraphicTools;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class Support extends Building {

	public static final int PRICE = 30;
	protected int size = 0;

	private int frameTimesToRaiseCredits = 50;
	private int framesTemp = 0;

	private int creditsPerTime = 20;

	public Support(int x, int y, int mode, int playerID, DefenderControl gamelogic) {
		super(x, y, mode, playerID, gamelogic);
	}

	public void drawFigure(PGraphics graphics) {
		// farbewechsel bei Aktivierung
		// this.entscheideLineFarbe( graphics);
		// Skalieren
		graphics.scale(1.5f);
		// graphics.strokeWeight(1);
		// Nach norden ausrichten
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

		// Punkte hinzufuegen
		ArrayList<PVector> vektoren = new ArrayList<PVector>();
		vektoren.add(new PVector(0, -size));
		vektoren.add(new PVector(0, size));
		vektoren.add(new PVector(0, 0));
		vektoren.add(new PVector(size, 0));
		vektoren.add(new PVector(-size, 0));

		// zeichnen
		GraphicTools.zeicheFigurNachVektoren(vektoren, graphics);

		// zurücksetzen der Umgebungseinstellungen
		graphics.resetMatrix();
		graphics.stroke(0);
	}

	public void calcNewPosition() {

		framesTemp++;

		if (framesTemp == frameTimesToRaiseCredits) {
			if (this.playerID == DefenderControl.PLAYER_ONE) {
				gamelogic.getPlayerOne().addCredits(this.creditsPerTime);
			}

			if (this.playerID == DefenderControl.PLAYER_TWO) {
				gamelogic.getPlayerTwo().addCredits(this.creditsPerTime);
			}

			framesTemp = 0;
		}

	}

}
