package de.fhb.defenderTouch.gamelogic;

import java.awt.Color;
import java.util.concurrent.CopyOnWriteArrayList;

import processing.core.PVector;
import de.fhb.defenderTouch.units.movable.BaseUnit;

public class Player {
	
	private float generalAngle;
	
	private PVector originPositionInScreen;
	private PVector screenPosition;
	
	private float zoomMin=0.3f;
	private float zoomMax=3.0f;
	
	private int side;
	
	private PVector viewPosition= new PVector(0,0);
	
	private float actualZoom=0.5f;
	
	public static final int RIGHT=0;
	public static final int LEFT=1;
	
	private Color unitColor;
	
	private int credits=500;
	
	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	/**
	 * Liste der aktivierten Units
	 */
	private CopyOnWriteArrayList<BaseUnit> activeUnits;
	
//	private DefenderControl gamelogic;

	public Player(DefenderControl gamelogic, float generalAngle, float actualZoom, PVector originPositionInScreen,int side,PVector viewPosition, PVector screenPosition,Color unitColor){
		this.generalAngle=generalAngle;
		this.actualZoom=actualZoom;
		this.originPositionInScreen=originPositionInScreen;
		this.screenPosition=screenPosition;
//		this.gamelogic=gamelogic;			
		this.side=side;
		this.viewPosition=viewPosition;
		activeUnits= new CopyOnWriteArrayList<BaseUnit>();
		this.unitColor=unitColor;
	}

	public Color getUnitColor() {
		return unitColor;
	}

	public PVector getScreenPosition() {
		return screenPosition;
	}

	public boolean isRight() {
		if(this.side==Player.RIGHT){
			return true;
		}else{
			return false;	
		}
	}

	public CopyOnWriteArrayList<BaseUnit> getActiveUnits() {
		return activeUnits;
	}

	public boolean isLeft() {
		if(this.side==Player.LEFT){
			return true;
		}else{
			return false;	
		}
	}
	
	
	
	public PVector getOriginPositionInScreen() {
		return originPositionInScreen;
	}
	
	public PVector getViewPosition() {
		return viewPosition;
	}

	public void setViewPosition(PVector viewPosition) {
		this.viewPosition = viewPosition;
	}

	public float getActualZoom() {
		return actualZoom;
	}

	public void setActualZoom(float actualZoom) {
		
		//wenn minimum oder maximum nicht überschritten
		if(!(actualZoom>zoomMax||actualZoom<zoomMin)){
			this.actualZoom = actualZoom;			
		}

	}

	public float getGeneralAngle() {
		return generalAngle;
	}
	
//	public void addCredits(int credits){
//		if(this.credits>0){
//			
//		}
//	}


}
