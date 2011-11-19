package de.fhb.defenderTouch.units.notmovable;

import java.util.ArrayList;
import java.util.Date;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.graphics.GraphicTools;
import processing.core.PGraphics;
import processing.core.PVector;

public class Support extends Building {

	public static final int PRICE = 30;
	protected int size = 0;

	private int timeTillNextIncome = 1000;
	private int creditsPerTime = 1;

	private long startingTime = new Date().getTime();
	private long tickerTime;

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
		tickerTime = new Date().getTime();

		if (getNewIncome(startingTime, tickerTime)) {
			if (this.playerID == DefenderControl.PLAYER_ONE) {
				gamelogic.getPlayerOne().addCredits(this.creditsPerTime);
			}

			if (this.playerID == DefenderControl.PLAYER_TWO) {
				gamelogic.getPlayerTwo().addCredits(this.creditsPerTime);
			}
		}

	}

	public boolean getNewIncome(long startingTime, long tickerTime) {
		long helpTime = tickerTime - startingTime;
		if (helpTime >= timeTillNextIncome) {
			this.startingTime = new Date().getTime();
			return true;
		}
		return false;

	}

}
