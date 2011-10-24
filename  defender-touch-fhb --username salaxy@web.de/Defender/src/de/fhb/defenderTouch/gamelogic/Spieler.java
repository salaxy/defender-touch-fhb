package de.fhb.defenderTouch.gamelogic;

import de.fhb.defenderTouch.interfaces.Drawable;
import de.fhb.defenderTouch.map.Map;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class Spieler implements Drawable{
	
	/**
	 * Linker Splitscreen (Spieler 1)
	 */
	public static boolean LEFTSIDE = false;
	
	/**
	 * Rechter Splitscreen (Spieler 2)
	 */
	public static boolean RIGHTSIDE = true;
	
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
	private PApplet display;
	
	/**
	 * Halber Bildschirm, auf dem das Spielfeld des Spielers dargestellt wird.
	 */
	private PGraphics splitScreen;
	
	/**
	 * Konstruktor
	 * 
	 * @param display
	 * @param zoomFaktor - initialer Zoomfaktor
	 * @param screenSide - Welche Bildschirmseite dem Spieler zugeordnet ist (LEFTSIDE oder RIGHTSIDE)
	 */
	public Spieler(PApplet display, Map karte, float zoomFaktor, boolean screenSide){
		this.zoomFaktor=zoomFaktor;
		this.sichtPosition=new PVector(display.getWidth() / 2, display.getHeight() / 2);
		this.display = display;
		splitScreen = this.display.createGraphics(display.getWidth() / 2, display.getHeight(), PApplet.JAVA2D);
		this.screenSide = screenSide;
		if (Spieler.karte != null) Spieler.karte = karte;
	}

	/**
	 * Gibt zurück, wie weit der Spieler reingezoomt hat.
	 * @return
	 */
	public float getZoomFaktor() {
		return zoomFaktor;
	}

	/**
	 * Legt den Zoom-Faktor des Spielers fest.
	 * @param zoomFaktor
	 */
	public void setZoomFaktor(float zoomFaktor) {
		this.zoomFaktor = zoomFaktor;
	}

	/**
	 * Gibt zurück, welcher Teil der Karte bei diesem Spieler im Zentrum der Ansicht ist. 
	 * @return
	 */
	public PVector getSichtPosition() {
		return sichtPosition;
	}

	/**
	 * Legt fest, welcher Teil der Karte bei diesem Spieler im Zentrum der Ansicht ist. 
	 * @return
	 */
	public void setSichtPosition(PVector sichtPosition) {
		this.sichtPosition = sichtPosition;
	}
	
	/**
	 * Gibt zurück, welcher Bildschirmseite der Spieler zugeordnet ist.
	 * @return
	 */
	public boolean isScreenSide() {
		return screenSide;
	}

	/**
	 * Legt fest, welcher Bildschirmseite der Spieler zugeordnet ist. (Dafür brauchen wir aber eigentlich keinen Setter, weil das nur im Konstruktor bestimmt werden sollte.)
	 * @param screenSide
	 */
	public void setScreenSide(boolean screenSide) {
		this.screenSide = screenSide;
	}

	/**
	 * Kartenansicht des Spieler zeichnen.
	 */
	public void paint() {
		try {
			karte.paint(splitScreen, sichtPosition, zoomFaktor);
			display.image(splitScreen, (screenSide) ? 0 : display.getWidth() / 2 , 0);
		} catch (Exception ex) {
			//if (ex != null) System.out.println(ex.getMessage());
			System.out.println("splitScreen: " + splitScreen.toString());
			System.out.println("sichtPosition: " + sichtPosition.toString());
			System.out.println("zoomFaktor: " + zoomFaktor);
		}
	}
}
