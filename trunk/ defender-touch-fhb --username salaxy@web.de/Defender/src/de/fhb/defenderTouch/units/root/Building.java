package de.fhb.defenderTouch.units.root;

import org.newdawn.slick.geom.Vector2f;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;

public class Building extends Unit {

	// private float movementSpeed=0.5f;
	// eindeutige konstanten f�r die levels
	public static final int LEVEL_ONE = 1;
	public static final int LEVEL_TWO = 2;
	public static final int LEVEL_THREE = 3;

	// upgradezeit in millisecs
	public static final int TIME_TO_UPGRADE_LEVEL_ONE = 2000;
	public static final int TIME_TO_UPGRADE_LEVEL_TWO = 4000;
	public static final int TIME_TO_UPGRADE_LEVEL_THREE = 8000;
	// TODO das mit der Zeit machen wir sp�ter noch du l�sst
	// es sofort upgraden ohne warte zeit

	protected int level = 1;

	public int getLevel() {
		return level;
	}

	public void upgrade() {
		if (this.level < LEVEL_THREE) {
			this.level++;
		}
	}

	public Building(int x, int y, int mode, Player player, DefenderControl gamelogic) {
		super(x, y, mode, player, gamelogic);
		this.movementSpeed = 0.5f;
	}

	public void update() {

	}

	public void commandDestination(Vector2f dest) {

	}

}
