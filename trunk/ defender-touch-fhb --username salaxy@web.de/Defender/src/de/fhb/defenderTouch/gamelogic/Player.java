package de.fhb.defenderTouch.gamelogic;

import processing.core.PVector;

public class Player {
	
	private float generalAngle;
	
	private float generalScale;
	
	private PVector originPosition;
	
	
	
	public float getGeneralAngle() {
		return generalAngle;
	}

	public float getGeneralScale() {
		return generalScale;
	}

	public PVector getOriginPosition() {
		return originPosition;
	}

	private DefenderControl gamelogic;

	public Player(DefenderControl gamelogic, float generalAngle, float generalScale, PVector originPosition){
		this.generalAngle=generalAngle;
		this.generalScale=generalScale;
		this.originPosition=originPosition;
		this.gamelogic=gamelogic;			
	}

}
