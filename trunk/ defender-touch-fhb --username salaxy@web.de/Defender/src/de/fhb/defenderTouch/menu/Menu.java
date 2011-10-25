package de.fhb.defenderTouch.menu;

import java.util.ArrayList;
import de.fhb.defenderTouch.graphics.GraphicTools;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Menu {

	/**
	 * applet where the buildings are placed
	 */
	protected PApplet mainPoint;

	/**
	 * recent position of the click
	 */
	protected PVector position;

	/**
	 * recent position of the building
	 */
	protected PVector positionBuilding;

	/**
	 * menu points, actual 6 points, 3 in use
	 */
	protected PVector menu[] = new PVector[6];

	/**
	 * menu points, actual 6 points, 3 in use
	 */
	protected PVector menuBuildings[] = new PVector[2];

	/**
	 * Vector with all saved building that palyed has built
	 */
	protected ArrayList<PVector> bBuildings = new ArrayList<PVector>();

	/**
	 * saves if the menu is open
	 */
	protected boolean menuOpen = false;

	/**
	 * saves if the building options is open
	 */
	protected boolean buildingOpen = false;

	/**
	 * Radius of the circle Is always half its diameter
	 */
	protected final int RADIUSCIRCLEMENU = 19;

	/**
	 * Aktivierungsradius eines Menupunkts
	 */
	protected final int DIAMETERCIRCLEMENU = 38;

	/**
	 * Aktivierungsradius eines Menupunkts
	 */
	protected final int DISTANCE = -40;

	/**
	 * Nummer des gebäudes
	 */
	protected int numberBuilding = 0;

	/**
	 * Konstruktor
	 */
	public Menu(PApplet display) {
		this.position = new PVector(0, 0);
		this.mainPoint = display;
		// PFont font;
		// font = createFont("Arial", 18);

		for (int i = 0; i < menu.length; i++) {
			menu[i] = new PVector(-100, -100);
		}
		for (int i = 0; i < menuBuildings.length; i++) {
			menuBuildings[i] = new PVector(-100, -100);
		}
	}

	/**
	 * Menupunkt anzeigen
	 */
	public void showMenuPoint(PVector position) {
		this.position = position;
	}

	/**
	 * is always been done
	 */
	public void drawMenu() {

		mainPoint.ellipseMode(PConstants.CENTER);

		if (menuOpen) {

			mainPoint.stroke(255);

			float drehung = 0f;
			float drehungProUntermenue = PApplet.TWO_PI / 6;

			mainPoint.translate(this.position.x, this.position.y);
			mainPoint.rotate(drehung);
			menu[0] = new PVector(0, DISTANCE);
			menu[0].rotate(drehung);
			menu[0].add(position);
			mainPoint.fill(20, 50, 20);
			mainPoint.ellipse(0, DISTANCE, DIAMETERCIRCLEMENU, DIAMETERCIRCLEMENU);
			mainPoint.triangle(-5, DISTANCE + 5, 0, DISTANCE - 5, +5, DISTANCE + 5);
			// mainPoint.ellipse(0, DISTANCE +(DISTANCE/2), RADIUSCIRCLEMENU,
			// RADIUSCIRCLEMENU);
			// mainPoint.createFont(font, arg1);
			drehung = drehung + drehungProUntermenue;
			mainPoint.resetMatrix();

			mainPoint.translate(this.position.x, this.position.y);
			mainPoint.rotate(drehung);
			menu[1] = new PVector(0, DISTANCE);
			menu[1].rotate(drehung);
			menu[1].add(position);
			mainPoint.fill(20, 20, 50);
			mainPoint.ellipse(0, DISTANCE, DIAMETERCIRCLEMENU, DIAMETERCIRCLEMENU);
			ArrayList<PVector> vektoren1 = new ArrayList<PVector>();
			vektoren1.add(new PVector(-4, DISTANCE - 4));
			vektoren1.add(new PVector(4, DISTANCE - 4));
			vektoren1.add(new PVector(0, DISTANCE - 4));
			vektoren1.add(new PVector(0, DISTANCE - 8));
			vektoren1.add(new PVector(0, DISTANCE + 4));
			vektoren1.add(new PVector(4, DISTANCE + 4));
			vektoren1.add(new PVector(-4, DISTANCE + 4));
			mainPoint.ellipse(0, DISTANCE - 4, 8, 8);
			GraphicTools.zeicheFigurNachVektoren(vektoren1, mainPoint);
			drehung = drehung + drehungProUntermenue;
			mainPoint.resetMatrix();

			mainPoint.translate(this.position.x, this.position.y);
			mainPoint.rotate(drehung);
			menu[2] = new PVector(0, DISTANCE);
			menu[2].rotate(drehung);
			menu[2].add(position);
			mainPoint.fill(50, 20, 20);
			mainPoint.ellipse(0, DISTANCE, DIAMETERCIRCLEMENU, DIAMETERCIRCLEMENU);
			ArrayList<PVector> vektoren2 = new ArrayList<PVector>();
			vektoren2.add(new PVector(0, DISTANCE - 8));
			vektoren2.add(new PVector(0, DISTANCE + 8));
			vektoren2.add(new PVector(0, DISTANCE));
			vektoren2.add(new PVector(8, DISTANCE));
			vektoren2.add(new PVector(-8, DISTANCE));
			GraphicTools.zeicheFigurNachVektoren(vektoren2, mainPoint);
			drehung = drehung + drehungProUntermenue;
			mainPoint.resetMatrix();

			mainPoint.translate(this.position.x, this.position.y);
			mainPoint.rotate(drehung);
			menu[3] = new PVector(0, DISTANCE);
			menu[3].rotate(drehung);
			menu[3].add(position);
			mainPoint.noFill();
			mainPoint.stroke(150);
			mainPoint.ellipse(0, DISTANCE, RADIUSCIRCLEMENU, RADIUSCIRCLEMENU);
			drehung = drehung + drehungProUntermenue;
			mainPoint.resetMatrix();

			mainPoint.translate(this.position.x, this.position.y);
			mainPoint.rotate(drehung);
			menu[4] = new PVector(0, DISTANCE);
			menu[4].rotate(drehung);
			menu[4].add(position);
			mainPoint.ellipse(0, DISTANCE, RADIUSCIRCLEMENU, RADIUSCIRCLEMENU);
			drehung = drehung + drehungProUntermenue;
			mainPoint.resetMatrix();

			mainPoint.translate(this.position.x, this.position.y);
			mainPoint.rotate(drehung);
			menu[5] = new PVector(0, DISTANCE);
			menu[5].rotate(drehung);
			menu[5].add(position);
			mainPoint.ellipse(0, DISTANCE, RADIUSCIRCLEMENU, RADIUSCIRCLEMENU);
			drehung = drehung + drehungProUntermenue;
			mainPoint.resetMatrix();
		}

		// here is the complete menu for a specific building
		if (buildingOpen && !menuOpen) {
			mainPoint.noFill();
			mainPoint.stroke(100);
			float drehung = 0f;
			float drehungProUntermenue = PApplet.TWO_PI / 4;

			mainPoint.translate(this.positionBuilding.x, this.positionBuilding.y);
			mainPoint.rotate(drehung);
			menuBuildings[0] = new PVector(0, DISTANCE);
			menuBuildings[0].rotate(drehung);
			menuBuildings[0].add(position);
			mainPoint.ellipse(0, DISTANCE, DIAMETERCIRCLEMENU, DIAMETERCIRCLEMENU);
			mainPoint.triangle(-5, DISTANCE, 0, DISTANCE - 10, +5, DISTANCE);
			ArrayList<PVector> vektoren1 = new ArrayList<PVector>();
			vektoren1.add(new PVector(0, DISTANCE));
			vektoren1.add(new PVector(0, DISTANCE + 8));
			GraphicTools.zeicheFigurNachVektoren(vektoren1, mainPoint);
			drehung = drehung + drehungProUntermenue;
			mainPoint.resetMatrix();

			mainPoint.translate(this.positionBuilding.x, this.positionBuilding.y);
			mainPoint.rotate(drehung);
			menuBuildings[1] = new PVector(0, DISTANCE);
			menuBuildings[1].rotate(drehung);
			menuBuildings[1].add(position);
			mainPoint.ellipse(0, DISTANCE, DIAMETERCIRCLEMENU, DIAMETERCIRCLEMENU);
			ArrayList<PVector> vektoren2 = new ArrayList<PVector>();
			vektoren2.add(new PVector(5, DISTANCE - 5));
			vektoren2.add(new PVector(-5, DISTANCE + 5));
			vektoren2.add(new PVector(0, DISTANCE));
			vektoren2.add(new PVector(5, DISTANCE + 5));
			vektoren2.add(new PVector(-5, DISTANCE - 5));
			GraphicTools.zeicheFigurNachVektoren(vektoren2, mainPoint);
			drehung = drehung + drehungProUntermenue;
			mainPoint.resetMatrix();

			mainPoint.translate(this.positionBuilding.x, this.positionBuilding.y);
			mainPoint.rotate(drehung);
			mainPoint.ellipse(0, DISTANCE, RADIUSCIRCLEMENU, RADIUSCIRCLEMENU);
			// mainPoint.createFont(font, arg1);
			drehung = drehung + drehungProUntermenue;
			mainPoint.resetMatrix();
			// System.out.println(this.positionBuilding.x + " " +
			// this.positionBuilding.y + " : " + this.position.x + " " +
			// this.position.y);

			mainPoint.translate(this.positionBuilding.x, this.positionBuilding.y);
			mainPoint.rotate(drehung);
			mainPoint.ellipse(0, DISTANCE, RADIUSCIRCLEMENU, RADIUSCIRCLEMENU);
			// mainPoint.createFont(font, arg1);
			drehung = drehung + drehungProUntermenue;
			mainPoint.resetMatrix();
		}

	}

	/**
	 * 
	 * @param clickVector
	 * @return false - if click is not inner circle : true - if click is inner
	 *         circle
	 */
	public boolean isInnerMainElement(PVector clickVector) {

		if (this.position.dist(clickVector) < this.RADIUSCIRCLEMENU) {
			System.out.println("Menu close choosed");
			numberBuilding = 0;
			setMenuOpen(false);
			return true;
		}
		return false;

	}

	/**
	 * 
	 * @param clickVector
	 * @return false - if
	 */
	public boolean isInnerMenuElement(PVector clickVector) {

		// look if a menu point was clicked
		if (this.menu[0].dist(clickVector) < this.RADIUSCIRCLEMENU) {
			System.out.println("Menue 1");
			setNumberBuilding(0);
			setMenuOpen(false);
			setBuildingOpen(false);
			return true;
		}
		if (this.menu[1].dist(clickVector) < this.RADIUSCIRCLEMENU) {
			System.out.println("Menue 2");
			setNumberBuilding(1);
			setMenuOpen(false);
			setBuildingOpen(false);
			return true;
		}
		if (this.menu[2].dist(clickVector) < this.RADIUSCIRCLEMENU) {
			System.out.println("Menue 3");
			setNumberBuilding(2);
			setMenuOpen(false);
			setBuildingOpen(false);
			return true;
		}
		if (this.menu[3].dist(clickVector) < this.RADIUSCIRCLEMENU) {
			System.out.println("Menue 4");
		}
		if (this.menu[4].dist(clickVector) < this.RADIUSCIRCLEMENU) {
			System.out.println("Menue 5");
		}
		if (this.menu[5].dist(clickVector) < this.RADIUSCIRCLEMENU) {
			System.out.println("Menue 6");
		}

		return false;

	}

	public boolean isInnerBuildingElement(PVector clickVector) {
		// look if a specific building menu point was clicked
		if (this.menuBuildings[0].dist(clickVector) < this.RADIUSCIRCLEMENU) {
			System.out.println("Building upgrade");
		}
		if (this.menuBuildings[1].dist(clickVector) < this.RADIUSCIRCLEMENU) {
			System.out.println("Building destroyed");
		}

		return false;
	}

	// public void paintBuildingLevel(PVector clickVector, int level) {
	// // TODO
	// mainPoint.noFill();
	// mainPoint.stroke(100);
	// System.out.println(clickVector.x + " :: " + clickVector.y);
	// mainPoint.translate(this.position.x, this.position.y);
	// mainPoint.rotate(0);
	// menu[0] = new PVector(0, DISTANCE);
	// menu[0].rotate(0);
	// menu[0].add(position);
	// mainPoint.ellipse(0, -DISTANCE / 2, DIAMETERCIRCLEMENU / 2,
	// DIAMETERCIRCLEMENU / 2);
	// // mainPoint.createFont(font, arg1);
	// mainPoint.resetMatrix();
	//
	// }

	public boolean isBuildingOpen() {
		return buildingOpen;
	}

	public void setNumberBuilding(int numberBuilding) {
		this.numberBuilding = numberBuilding;
	}

	public void setMenuOpen(boolean menuOpen) {
		this.menuOpen = menuOpen;
	}

	public boolean isMenuOpen() {
		return menuOpen;
	}

	public int getNumberBuilding() {
		return numberBuilding;
	}

	public float getPositionX() {
		return position.x;
	}

	public float getPositionY() {
		return position.y;
	}

	public PVector getPosition() {
		return position;
	}

	public void setPosition(PVector position) {
		this.position = position;
	}

	public void setBuildingOpen(boolean buildingOpen) {
		this.buildingOpen = buildingOpen;
	}

	public void setPositionBuilding(PVector positionBuilding) {
		this.positionBuilding = positionBuilding;
	}

}
