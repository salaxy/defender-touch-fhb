package de.fhb.defenderTouch.oldMapConcept;

import java.util.concurrent.CopyOnWriteArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import de.fhb.defenderTouch.interfaces.Drawable;
import de.fhb.defenderTouch.units.movable.BaseUnit;

public class SplitScreen {

	/**
	 * Linker Splitscreen (Spieler 1)
	 */
	public static final boolean LEFTSIDE = false;
	
	/**
	 * Rechter Splitscreen (Spieler 2)
	 */
	public static final boolean RIGHTSIDE = true;
	
	/**
	 * Karte auf der die Spieler spielen.
	 */
	public static Map karte;
	
	/**
	 * Welche Seite auf dem Bildschirm dem Spieler zugeordnet ist.
	 * LEFTSIDE oder RIGHTSIDE
	 */
	private boolean screenSide;
	
	/**
	 * Wie weit der Spieler in die Karte reingezoomt hat.
	 */
	private float zoomFaktor;
	
	/**
	 * Auf welchen Teil der Karte der Spieler schaut. (Mittelpunkt des Bildschirms)
	 */
	private PVector sichtPosition;
	
	/**
	 * Das Applet, auf das zu zeichnen ist.
	 */
	private static PApplet display;
	
	/**
	 * Halber Bildschirm, auf dem das Spielfeld des Spielers dargestellt wird.
	 */
	private PGraphics splitScreen;
	
	private CopyOnWriteArrayList<BaseUnit> globalUnits;
	
	
	public SplitScreen(PApplet display, PVector sichtPosition, float zoomFaktor, boolean screenSide, CopyOnWriteArrayList<BaseUnit> globalUnits) {
		this.globalUnits=globalUnits;
		splitScreen = display.createGraphics(display.getWidth() / 2, display.getHeight(), PApplet.JAVA2D);
		this.zoomFaktor = zoomFaktor;
		this.screenSide = screenSide;
		this.sichtPosition = sichtPosition;
		
		if (SplitScreen.display == null) SplitScreen.display = display;
		
	}
	
	public void paint() {
		Map.paint(splitScreen, sichtPosition, zoomFaktor, globalUnits);
		display.image(splitScreen, (!screenSide) ? 0 : display.getWidth() / 2 , 0);
//		display.image(splitScreen, 512, 0);
	}

	public boolean getScreenSide() {
		return screenSide;
	}

	public void setScreenSide(boolean screenSide) {
		this.screenSide = screenSide;
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
