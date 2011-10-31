package de.fhb.defenderTouch.gamelogic;

import processing.core.PVector;

public class Player {
	
	private float generalAngle;
	
	private float generalScale;
	
	private PVector originPosition;
	
	private float zoomMin=0.1f;
	private float zoomMax=2.0f;
	
	private int side;
	
	private PVector viewPosition= new PVector(0,0);
	
	private float actualZoom=1.0f;
	
	public static final int RIGHT=0;
	public static final int LEFT=1;
	
	
	
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

	public float getGeneralScale() {
		return generalScale;
	}

	public PVector getOriginPosition() {
		return originPosition;
	}

	private DefenderControl gamelogic;

	public Player(DefenderControl gamelogic, float generalAngle, float generalScale, PVector originPosition,int side,PVector viewPosition){
		this.generalAngle=generalAngle;
		this.generalScale=generalScale;
		this.originPosition=originPosition;
		this.gamelogic=gamelogic;			
		this.side=side;
		this.viewPosition=viewPosition;
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

}
