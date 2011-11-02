package de.fhb.defenderTouch.gamelogic;

import processing.core.PVector;

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
	
//	private DefenderControl gamelogic;

	public Player(DefenderControl gamelogic, float generalAngle, float actualZoom, PVector originPositionInScreen,int side,PVector viewPosition, PVector screenPosition){
		this.generalAngle=generalAngle;
		this.actualZoom=actualZoom;
		this.originPositionInScreen=originPositionInScreen;
		this.screenPosition=screenPosition;
//		this.gamelogic=gamelogic;			
		this.side=side;
		this.viewPosition=viewPosition;
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


}
