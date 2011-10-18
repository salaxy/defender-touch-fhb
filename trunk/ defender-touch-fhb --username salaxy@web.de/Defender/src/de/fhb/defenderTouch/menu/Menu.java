package de.fhb.defenderTouch.menu;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Menu {

	/**
	 * Ist das Applet auf dem die Einheiten zugeordnet sind
	 */
	protected PApplet mainPoint;
	protected PApplet mainPoints;

	/**
	 * aktuelle Position der Einheit
	 */
	protected PVector position;

	/**
	 * aktuelle Sichtbarkeit
	 */
	protected boolean menuOpen = false;

	/**
	 * aktuelle Sichtbarkeit
	 */
	protected boolean menuCloseClicked = false;

	/**
	 * Aktivierungsradius eines Menupunkts
	 */
	protected float activateRadiusMenu = 20;

	/**
	 * Aktivierungsradius eines Menupunkts
	 */
	protected float activateRadiusBuildings = 35;
	
	// /**
	// * sagt aus ob ein Menupunkt geklickt wurde
	// */
	// protected boolean menuActive = false;

	/**
	 * Konstruktor
	 */
	public Menu(PApplet display) {
		this.position = new PVector(0, 0);
		this.mainPoint = display;
		this.mainPoints = display;
	}

	/**
	 * Menupunkt anzeigen
	 */
	public void showMenuPoint(PVector position) {
		this.position = position;
		this.menuOpen = true;
	}

	/**
	 * is always been done
	 */
	public void drawMenu() {

		mainPoint.ellipseMode(PConstants.CENTER);
		mainPoint.translate(this.position.x, this.position.y);
		
		if (menuOpen) {

			mainPoint.noFill();
			mainPoint.stroke(100);
			mainPoints.rotate((float) Math.PI / 2);

			// Menupoint
			mainPoint.ellipse(0, 0, 20, 20);
			// Groundunit Point
			mainPoint.ellipse(-35, -35, 30, 30);
			mainPoints.triangle(-40, -30, -35, -40, -30, -30);
			// Supportunit Point
			mainPoint.ellipse(0, -35, 30, 30);
			// Defenceunit Point
			mainPoint.ellipse(35, -35, 30, 30);

			// System.out.println(this.position.x + " und " + this.position.y);
		}

	}

	/**
	 * 
	 * @param clickVector
	 * @return
	 */
	public boolean isInner(PVector clickVector) {

		if (position.dist(clickVector) < this.activateRadiusMenu/2) {
			// Einheit für die Steureung aktivieren
			// setMenuActive(true);
			return true;
		} else {
			// setMenuActive(false);
			return false;
		}
	}

	/**
	 * 
	 * @param clickVector
	 * @return
	 */
	public boolean isInnerGroundUnit(PVector clickVector) {
		PVector helpPosition = new PVector(0, 0);
		helpPosition.x = position.x + 35;
		helpPosition.y = position.y - 35;
		 System.out.println(position.x + " und "+position.y +
		 " oder "+helpPosition.x + " und " + helpPosition.y + " ist "+
		 helpPosition.dist(clickVector));
		if (helpPosition.dist(clickVector) < this.activateRadiusBuildings/2) {
			createGroundUnit();
			return true;
		} else {
			return false;
		}
	}

	public void createGroundUnit() {
		// verweis auf andies klasse
		// TODO
	}

	public void setMenuOpen(boolean menuOpen) {
		this.menuOpen = menuOpen;
	}

	public boolean isMenuOpen() {
		return menuOpen;
	}

}
