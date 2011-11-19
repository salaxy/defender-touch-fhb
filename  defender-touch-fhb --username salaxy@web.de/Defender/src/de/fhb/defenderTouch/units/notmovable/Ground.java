package de.fhb.defenderTouch.units.notmovable;

import java.util.Date;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import processing.core.PGraphics;

public class Ground extends Building {

	public static final int PRICE = 50;
	protected int size = 0;
	
	private int timeTillNextIncome = 5000;

	private long startingTime = new Date().getTime();
	private long tickerTime;

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
	
	public void calcNewPosition() {
		tickerTime = new Date().getTime();

		if (getNewGroundUnit(startingTime, tickerTime)) {
			if (this.playerID == DefenderControl.PLAYER_ONE) {
				// TODO
			}

			if (this.playerID == DefenderControl.PLAYER_TWO) {
				//gamelogic.getPlayerTwo().addCredits(this.creditsPerTime);
			}
		}

	}

	public boolean getNewGroundUnit(long startingTime, long tickerTime) {
		long helpTime = tickerTime - startingTime;
		if (helpTime >= timeTillNextIncome) {
			this.startingTime = new Date().getTime();
			return true;
		}
		return false;

	}

}
