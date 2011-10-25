package de.fhb.defenderTouch.units.grounded;

import processing.core.PApplet;
import de.fhb.defenderTouch.units.movable.BaseUnit;

public class Building extends BaseUnit{

//	private float movementSpeed=0.5f;
	//eindeutige konstanten für die levels
	public static final int LEVEL_ONE=1;
	public static final int LEVEL_TWO=2;
	public static final int LEVEL_THREE=3;
	
	//upgradezeit in millisecs
	public static final int TIME_TO_UPGRADE_LEVEL_ONE=2000;
	public static final int TIME_TO_UPGRADE_LEVEL_TWO=4000;
	public static final int TIME_TO_UPGRADE_LEVEL_THREE=8000;
	//TODO das mit der Zeit machen wir später noch du lässt
	//es sofort upgraden ohne warte zeit
	
	protected int level=1;
	
	public int getLevel() {
		return level;
	}

	public void upgrade() {
		if(this.level<LEVEL_THREE){
			this.level++;		
			//TODO weitere eigenschaften rauf setzen
		}
	}

	public Building(int x, int y, int mode, int playerID, PApplet disp){
		super(x, y, mode, playerID, disp);
		this.movementSpeed=0.5f;
		// TODO Auto-generated constructor stub
	}
	
	
//	public void calcNewPosition(){
//		//wenn aktuelle position nicht nahe herankommt an ziel, dann weiter bewegen
//		if(position.dist(destinationVector)>3){
//			//Richtugnsvector berechnen
//			direction=position.sub( destinationVector, position);
//			//richtungsvector normieren
//			direction.normalize();
//			//normierten Richtungsvector zur position hinzurechnen
//			position.add(direction.mult(direction, movementSpeed));
//			//zeichne Schweif
//			drawTail(direction, pa);
//		}
//	}

}
