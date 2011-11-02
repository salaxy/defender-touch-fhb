package de.fhb.defenderTouch.units.grounded;

import processing.core.PApplet;
import processing.core.PVector;
import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.units.movable.BaseUnit;

public class Building extends BaseUnit {

	// private float movementSpeed=0.5f;
	// eindeutige konstanten für die levels
	public static final int LEVEL_ONE = 1;
	public static final int LEVEL_TWO = 2;
	public static final int LEVEL_THREE = 3;

	// upgradezeit in millisecs
	public static final int TIME_TO_UPGRADE_LEVEL_ONE = 2000;
	public static final int TIME_TO_UPGRADE_LEVEL_TWO = 4000;
	public static final int TIME_TO_UPGRADE_LEVEL_THREE = 8000;
	// TODO das mit der Zeit machen wir später noch du lässt
	// es sofort upgraden ohne warte zeit

	protected int level = 1;

	public int getLevel() {
		return level;
	}

	public void upgrade() {
		if (this.level < LEVEL_THREE) {
			this.level++;
			// TODO weitere eigenschaften rauf setzen
		}
	}

	public Building(int x, int y, int mode, int playerID, PApplet disp,DefenderControl gamelogic) {
		super(x, y, mode, playerID, disp, gamelogic);
		this.movementSpeed = 0.5f;
	}

	 public void calcNewPosition(){

	 }
	 
	public void commandDestination(PVector dest) {

	}

}
