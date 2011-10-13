package de.fhb.defenderTouch.menu;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Menu {

	/**
	 * Ist das Applet auf dem die einheiten zugeordnet sind
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

	public Menu(PApplet display) {
		this.position = new PVector(0, 0);
		this.display = display;

	}

	/**
	 * Menu anzeigen
	 */
	public void showMenu(PVector position) {
		this.position = position;
		this.visible = true;

	}

	/**
	 * Menu verstecken
	 */
	public void hideMenu() {

		this.visible = false;

	}

	/**
	 * nur Aufruf wenn auch wirklich zeichnen
	 */
	public void drawMenu() {

			display.ellipseMode(PConstants.CENTER);
			display.translate(this.position.x, this.position.y);
//			System.out.println("hier");
		// showMenu();
		// TODO: Kreis machen

		if (visible) {

			display.noFill();
			display.fill(0);
			display.stroke(0);

			display.ellipse(0, 0, 20, 20);
		}

	}

}
