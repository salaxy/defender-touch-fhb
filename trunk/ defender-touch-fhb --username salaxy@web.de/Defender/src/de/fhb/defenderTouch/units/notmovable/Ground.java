package de.fhb.defenderTouch.units.notmovable;

import java.util.Date;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.units.movable.Tank;
import de.fhb.defenderTouch.units.root.BaseUnit;
/**
 * 
 * @author Curtis Mosters, Andy Klay
 */
public class Ground extends Building {

	public static final int PRICE = 50;
	protected int size = 0;

	private int timeTillNextSpawn = 2000;

	private long startingTime = new Date().getTime();
	private long tickerTime;

	public Ground(int x, int y, int mode, Player player, DefenderControl gamelogic) {
		super(x, y, mode, player, gamelogic);
	}

	public void drawFigure(Graphics graphics) {

		graphics.scale(1.5f,1.5f);
		graphics.rotate(0,0,180);

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

		graphics.drawLine(-size, +size,0, -size);
		graphics.drawLine(0, -size, +size, +size);
		graphics.drawLine(+size, +size,-size, +size);

		graphics.resetTransform();
	}

	public void calcNewPosition() {
		tickerTime = new Date().getTime();

		if (createNewGroundUnit(startingTime, tickerTime)) {
			new Tank(generateRandomNumber((int) this.position.x), generateRandomNumber((int) this.position.y), BaseUnit.MODE_NORMAL,
					this.player, gamelogic);
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
