package de.fhb.defenderTouch.units.notmovable;

import java.util.Date;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.units.movable.Fighter;
import de.fhb.defenderTouch.units.movable.Tank;
import de.fhb.defenderTouch.units.root.BaseUnit;
import processing.core.PGraphics;

public class Ground extends Building {

	public static final int PRICE = 50;
	protected int size = 0;

	private int timeTillNextSpawn = 2000;

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

		if (createNewGroundUnit(startingTime, tickerTime)) {
			if (this.playerID == DefenderControl.PLAYER_ONE) {
				new Tank(generateRandomNumber((int) this.position.x), generateRandomNumber((int) this.position.y), BaseUnit.MODE_NORMAL,
						DefenderControl.PLAYER_ONE, gamelogic);
			}

			if (this.playerID == DefenderControl.PLAYER_TWO) {
				new Tank(generateRandomNumber((int) this.position.x), generateRandomNumber((int) this.position.y), BaseUnit.MODE_NORMAL,
						DefenderControl.PLAYER_TWO, gamelogic);
			}
		}

	}

	public boolean createNewGroundUnit(long startingTime, long tickerTime) {
		long helpTime = tickerTime - startingTime;
		if (helpTime >= timeTillNextSpawn) {
			this.startingTime = new Date().getTime();
			return true;
		}
		return false;

	}

	public int generateRandomNumber(int x) {

		int help;

		if (Math.random() > 0.5)
			help = (int) (this.activateRadius * 2);
		else
			help = (int) -(this.activateRadius * 2);
		return x + help;
	}

}
