package de.fhb.defenderTouch.menu;

import java.util.ArrayList;
import java.util.HashMap;

import de.fhb.defenderTouch.graphics.GraphicTools;
import de.fhb.defenderTouch.units.movable.BaseUnit;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PMatrix;
import processing.core.PVector;

public class Menu {

	/**
	 * applet where the buildings are placed
	 */
	protected PApplet mainPoint;

	/**
	 * recent position of the building
	 */
	protected PVector position;

	/**
	 * menu points, actual 6 points, 3 in use
	 */
	protected PVector menu[] = new PVector[6];

	/**
	 * Vector with all saved building that palyed has built
	 */
	protected ArrayList<PVector> bBuildings = new ArrayList<PVector>();

	/**
	 * saves if the menu is open
	 */
	protected boolean menuOpen = false;

	/**
	 * Aktivierungsradius eines Menupunkts
	 */
	protected final int ACTIVATECIRCLEMENU = 19;

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

		menu[0] = new PVector(-100, -100);
		menu[1] = new PVector(-100, -100);
		menu[2] = new PVector(-100, -100);
		menu[3] = new PVector(-100, -100);
		menu[4] = new PVector(-100, -100);
		menu[5] = new PVector(-100, -100);

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
		// mainPoint.translate(this.position.x, this.position.y);

		if (menuOpen) {

			mainPoint.noFill();
			mainPoint.stroke(100);

			float drehung = 0f;
			float drehungProUntermenue = PApplet.TWO_PI / 6;

			// for(;drehung<PApplet.TWO_PI;){
			// mainPoint.translate(this.position.x, this.position.y);
			// System.out.println(drehung+"");
			// mainPoint.rotate(drehung);
			// PVector v=new PVector(0,-40);
			// v.rotate(drehung);
			// v.add(position);
			//
			// System.out.println(v+"");
			// mainPoint.ellipse(0, -40, 38, 38);
			// drehung=drehung+drehungProUntermenue;
			// mainPoint.resetMatrix();
			//
			// }

			mainPoint.translate(this.position.x, this.position.y);
			mainPoint.rotate(0);
			menu[0] = new PVector(0, DISTANCE);
			menu[0].rotate(0);
			menu[0].add(position);
			mainPoint.ellipse(0, DISTANCE, 38, 38);
			mainPoint.triangle(-5, DISTANCE + 5, 0, DISTANCE - 5, +5, DISTANCE + 5);
			drehung = drehung + drehungProUntermenue;
			mainPoint.resetMatrix();

			mainPoint.translate(this.position.x, this.position.y);
			mainPoint.rotate(PApplet.TWO_PI / 6);
			menu[1] = new PVector(0, DISTANCE);
			menu[1].rotate(PApplet.TWO_PI / 6);
			menu[1].add(position);
			mainPoint.ellipse(0, DISTANCE, 38, 38);
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
			mainPoint.rotate(PApplet.TWO_PI / 6 * 2);
			menu[2] = new PVector(0, DISTANCE);
			menu[2].rotate(PApplet.TWO_PI / 6 * 2);
			menu[2].add(position);
			mainPoint.ellipse(0, DISTANCE, 38, 38);
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
			mainPoint.rotate(PApplet.TWO_PI / 6 * 3);
			menu[3] = new PVector(0, DISTANCE);
			menu[3].rotate(PApplet.TWO_PI / 6 * 3);
			menu[3].add(position);
			mainPoint.ellipse(0, DISTANCE, 38, 38);
			drehung = drehung + drehungProUntermenue;
			mainPoint.resetMatrix();

			mainPoint.translate(this.position.x, this.position.y);
			mainPoint.rotate(PApplet.TWO_PI / 6 * 4);
			menu[4] = new PVector(0, DISTANCE);
			menu[4].rotate(PApplet.TWO_PI / 6 * 4);
			menu[4].add(position);
			mainPoint.ellipse(0, DISTANCE, 38, 38);
			drehung = drehung + drehungProUntermenue;
			mainPoint.resetMatrix();

			mainPoint.translate(this.position.x, this.position.y);
			mainPoint.rotate(PApplet.TWO_PI / 6 * 5);
			menu[5] = new PVector(0, DISTANCE);
			menu[5].rotate(PApplet.TWO_PI / 6 * 5);
			menu[5].add(position);
			mainPoint.ellipse(0, DISTANCE, 38, 38);
			drehung = drehung + drehungProUntermenue;
			mainPoint.resetMatrix();

			// mainPoint.rotate((float) Math.PI);
			//
			// // Menupoint
			// mainPoint.ellipse(0, 0, 20, 20);
			// // Groundunit Point
			// mainPoint.translate(-35, -15);
			// mainPoint.triangle(-5,+5, 0, -5, +5, +5);
			// mainPoint.ellipse(0, 0, 30, 30);
			//
			//
			// //mainPoints.triangle(-20, -10, -15, -20, -10, -10);
			// // Supportunit Point
			// mainPoint.translate(35, -20);
			// //Punkte hinzufuegen
			// ArrayList<PVector> vektoren=new ArrayList<PVector>();
			// vektoren.add(new PVector(0, -8));
			// vektoren.add(new PVector(0, 8));
			// vektoren.add(new PVector(0, 0));
			// vektoren.add(new PVector(8,0));
			// vektoren.add(new PVector(-8, 0));
			//
			// //zeichnen
			// //GraphicTools.zeicheFigurNachVektoren(vektoren,display);
			// mainPoint.ellipse(0, 0, 30, 30);
			// // Defenceunit Point
			// mainPoint.translate(35, 20);
			// mainPoint.ellipse(0, 0, 30, 30);

			// mainPoint.translate(-35, -15);

			// System.out.println(this.position.x + " und " + this.position.y);
		} else {
			// bBuildings.translate(this.position.x, this.position.y);
			// mainPoint.translate(this.position.x, this.position.y);
			// builtBuildings.show();

			// bBuildings.add(new PVector(-4,4));
			// mainPoint.ellipse(0,DISTANCE-4 , 8, 8);
			// GraphicTools.zeicheFigurNachVektoren(bBuildings,mainPoint);
			// // builtBuildings.triangle(-5,+5, 0, -5, +5, +5);

			// TODO
		}

	}

	/**
	 * 
	 * @param clickVector
	 * @return false - if click is not inner circle true - if click is inner
	 *         circle
	 */
	public boolean isInnerMainElement(PVector clickVector) {

		if (this.position.dist(clickVector) < this.ACTIVATECIRCLEMENU) {
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

		if (this.menu[0].dist(clickVector) < this.ACTIVATECIRCLEMENU) {
			System.out.println("Menue 1");
			setNumberBuilding(0);
			setMenuOpen(false);
			return true;
		}

		if (this.menu[1].dist(clickVector) < this.ACTIVATECIRCLEMENU) {
			System.out.println("Menue 2");
			setNumberBuilding(1);
			setMenuOpen(false);
			return true;
		}

		if (this.menu[2].dist(clickVector) < this.ACTIVATECIRCLEMENU) {
			System.out.println("Menue 3");
			setNumberBuilding(2);
			setMenuOpen(false);
			return true;
		}

		if (this.menu[3].dist(clickVector) < this.ACTIVATECIRCLEMENU) {
			System.out.println("Menue 4");
		}

		if (this.menu[4].dist(clickVector) < this.ACTIVATECIRCLEMENU) {
			System.out.println("Menue 5");
		}

		if (this.menu[5].dist(clickVector) < this.ACTIVATECIRCLEMENU) {
			System.out.println("Menue 6");
		}

		return false;

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

}
