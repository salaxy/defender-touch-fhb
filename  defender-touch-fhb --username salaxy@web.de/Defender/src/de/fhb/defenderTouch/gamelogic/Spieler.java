package de.fhb.defenderTouch.gamelogic;

import processing.core.PVector;

public class Spieler {
	
	private float zoomFaktor;
	
	private PVector sichtPosition;
	
	public Spieler(int feldHoehe, int feldBreite, float zoomFaktor){
		this.zoomFaktor=zoomFaktor;
		this.sichtPosition=new PVector(feldBreite/2,feldHoehe/2);
		
		
	}

	public float getZoomFaktor() {
		return zoomFaktor;
	}

	public void setZoomFaktor(float zoomFaktor) {
		this.zoomFaktor = zoomFaktor;
	}

	public PVector getSichtPosition() {
		return sichtPosition;
	}

	public void setSichtPosition(PVector sichtPosition) {
		this.sichtPosition = sichtPosition;
	}
	
	

	
}
