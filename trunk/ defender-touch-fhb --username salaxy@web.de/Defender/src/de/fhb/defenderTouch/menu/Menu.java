package de.fhb.defenderTouch.menu;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Menu {

	/**
	 * Ist das Applet auf dem die Einheiten zugeordnet sind
	 */
	protected PApplet display;

	/**
	 * aktuelle Position der Einheit
	 */
	protected PVector position;

	/**
	 * aktuelle Sichtbarkeit
	 */
	protected boolean visible = false;

	/**
	 * Konstruktor
	 */
	public Menu(PApplet display) {
		this.position = new PVector(0, 0);
		this.display = display;

	}

	/**
	 * Menupunkt anzeigen
	 */
	public void showMenuPoint(PVector position) {
		this.position = position;
		this.visible = true;
		// showMenu();
	}

	/**
	 * Menu anzeigen
	 */
	public void showMenu() {

		display.ellipseMode(PConstants.CENTER);
		display.translate(this.position.x - 30, this.position.y);

		display.ellipse(0, 0, 20, 20);

		display.translate(this.position.x - 30, this.position.y);
	}

	// /**
	// * Menu verstecken
	// */
	// public void hideMenu() {
	//
	// this.visible = false;
	//
	// }

	/**
	 * nur Aufruf wenn auch wirklich zeichnen
	 */
	public void drawMenu() {

		display.ellipseMode(PConstants.CENTER);
		display.translate(this.position.x, this.position.y);

		if (visible) {

			display.noFill();
			display.stroke(100);

			display.ellipse(0, 0, 20, 20);
			display.ellipse(30, -35, 30, 30);
			display.ellipse(30, 0, 30, 30);
			display.ellipse(30, 35, 30, 30);
		}

		

	}

}
