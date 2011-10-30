package de.fhb.defenderTouch.map;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import de.fhb.defenderTouch.units.movable.BaseUnit;
import processing.core.PGraphics;
import processing.core.PVector;

public class Map {
	
	/**
	 * Blickrichtung
	 */
	protected static float angle = 0;
	
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
	public Map(int width, int height) {		
		if (karte.isEmpty())
			for (int x = 0; x < width; x++)
				for (int y = 0; y < height; y++)
					karte.add(new MapComponent(new PVector(x * 32, y * 32)));
	}
	
	/**
	 * Karte mit allen Components zeichnen.
	 * @param globalUnits 
	 */
	public static void paint(PGraphics display, PVector position, float zoom, CopyOnWriteArrayList<BaseUnit> globalUnits) {
		display.beginDraw();
		display.background(0f);
		
		for (MapComponent element : karte){
			element.paint(display, position, zoom);
		}
		
//		for(BaseUnit bu : globalUnits){
//			bu.paint();
//		}

		display.endDraw();
	}

	public static void saveMap() {
		
		
	}
}
