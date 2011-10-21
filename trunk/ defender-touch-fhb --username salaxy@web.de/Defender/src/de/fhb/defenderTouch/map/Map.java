package de.fhb.defenderTouch.map;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;
import de.fhb.defenderTouch.interfaces.Drawable;

public class Map implements  Drawable{
	/**
	 *  Das Applet, auf dem diese Karte anzeigt werden soll.
	 */
	protected static PApplet display;
	
	/**
	 * Blickrichtung
	 */
	protected static float angle = 0;
	
	/**
	 * Position der linken oberen Kartenecke.
	 */
	protected static PVector position = new PVector(16, 16);

	/**
	 * Array mit allen Components (Kartenbestandteile)
	 */
	protected static ArrayList<MapComponent> karte = new ArrayList<MapComponent>();
	
	/**
	 * Konstante des linken Spielers, um seinen Teil des Bildschirms zu identifizieren.
	 */
	protected static boolean PLAYERONE = false;

	/**
	 * Konstante des rechten Spielers, um seinen Teil des Bildschirms zu identifizieren.
	 */
	protected static boolean PLAYERTWO = true;
	
	/**
	 * Konstruktor
	 * Wenn die Karte bereits existiert, wird sie nicht(!) neu erzeugt, stattdessen bekommt man ein weiteres Handle für die aktuelle Karte.
	 * @param width Breite (32 für 1024x768)
	 * @param height Höhe (24 für 1024x768)
	 */
	public Map(PApplet display, int width, int height) {
		Map.display = display;
		if (karte.isEmpty())
			for (int x = 0; x < width; x++)
				for (int y = 0; y < height; y++)
					karte.add(new MapComponent(new PVector(x * 32 + 16, y * 32 + 16)));
	}
	
	/**
	 * Karte mit allen Components zeichnen.
	 */
	public void paint() {
		for (MapComponent element : karte) {
			element.paint();
		}
		
		paintView(PLAYERONE);
		paintView(PLAYERTWO);
	}
	
	private void paintView(boolean whichOne) {
		// passendes Spielerobjekt abfragen, um seinen Sichtbereich und Zoommodus zu erfahren
		// dann zeichnen
	}
}
