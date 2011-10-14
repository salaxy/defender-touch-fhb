package de.fhb.defenderTouch.map;

import processing.core.PApplet;
import processing.core.PVector;
import de.fhb.defenderTouch.interfaces.Drawable;

public class Map implements  Drawable{
	/**
	 *  Das Applet, auf dem diese Karte anzeigt werden soll.
	 */
	protected PApplet display;
	
	/**
	 * Blickrichtung
	 */
	protected float angle = 0;
	
	/**
	 * Position der linken oberen Kartenecke.
	 */
	protected PVector position = new PVector(15, 15);

	/**
	 * Array mit allen Components (Kartenbestandteile)
	 */
	protected MapComponent[][] karte;
	
	/**
	 * Konstruktor
	 * @param width Breite (32 für 1024x768)
	 * @param height Höhe (24 für 1024x768)
	 */
	public Map(int width, int height) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				karte[x][y] = new MapComponent(display, this);
			}
		}
	}
	
	/**
	 * Karte mit allen Components zeichnen.
	 */
	public void paint() {
		for (int x = 0; x < karte.length; x++) {
			for (int y = 0; y < karte[0].length; y++) {
				karte[x][y].paint(new PVector(position.x + x * 32, position.y + y * 32));
			}		
		}
	}
	
}
