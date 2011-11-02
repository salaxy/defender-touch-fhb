package de.fhb.defenderTouch.gamelogic;

import de.fhb.defenderTouch.interfaces.Drawable;
import de.fhb.defenderTouch.oldMapConcept.SplitScreen;
import processing.core.PApplet;
import processing.core.PVector;

public class Spieler{
	
	private SplitScreen splitScreen;
	
	private DefenderControl gamelogic;
	
	private float zoomMin=0.8f;
	private float zoomMax=3.0f;
	
	/**
	 * Konstruktor
	 * 
	 * @param display
	 * @param sichtPosition
	 * @param zoomFaktor
	 * @param screenSide
	 * @param gamelogic 
	 */
	public Spieler(PApplet display, PVector sichtPosition, float zoomFaktor, boolean screenSide, DefenderControl gamelogic){
		this.gamelogic=gamelogic;
		splitScreen = new SplitScreen(display, sichtPosition, zoomFaktor, screenSide,null );				
	}

	/**
	 * Gibt zurück, wie weit der Spieler reingezoomt hat.
	 * @return
	 */
	public float getZoomFaktor() {
		return splitScreen.getZoomFaktor();
	}

	/**
	 * Legt den Zoom-Faktor des Spielers fest.
	 * @param zoomFaktor
	 */
	public void setZoomFaktor(float zoomFaktor) {
		//wenn minimum oder maximum nicht überschritten
		if(!(zoomFaktor>zoomMax||zoomFaktor<zoomMin)){
			splitScreen.setZoomFaktor(zoomFaktor);			
		}

	}

	/**
	 * Gibt zurück, welcher Teil der Karte bei diesem Spieler im Zentrum der Ansicht ist. 
	 * @return
	 */
	public PVector getSichtPosition() {
		return splitScreen.getSichtPosition();
	}

	/**
	 * Legt fest, welcher Teil der Karte bei diesem Spieler im Zentrum der Ansicht ist. 
	 * @return
	 */
	public void setSichtPosition(PVector sichtPosition) {
		splitScreen.setSichtPosition(sichtPosition);
	}
	
	/**
	 * Gibt zurück, welcher Bildschirmseite der Spieler zugeordnet ist.
	 * @return
	 */
	public boolean isScreenSide() {
		return splitScreen.getScreenSide();
	}

	/**
	 * Legt fest, welcher Bildschirmseite der Spieler zugeordnet ist. (Dafür brauchen wir aber eigentlich keinen Setter, weil das nur im Konstruktor bestimmt werden sollte.)
	 * @param screenSide
	 */
	public void setScreenSide(boolean screenSide) {
		splitScreen.setScreenSide(screenSide);
	}

	/**
	 * Kartenansicht des Spieler zeichnen.
	 */
	public void paint() {
		splitScreen.paint();		
	}
}
