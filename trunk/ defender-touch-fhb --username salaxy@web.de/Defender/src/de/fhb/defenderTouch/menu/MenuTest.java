package de.fhb.defenderTouch.menu;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import TUIO.TuioCursor;
import TUIO.TuioObject;
import TUIO.TuioProcessing;
import TUIO.TuioTime;
import de.fhb.defenderTouch.units.grounded.Defence;
import de.fhb.defenderTouch.units.grounded.Ground;
import de.fhb.defenderTouch.units.grounded.Support;
import de.fhb.defenderTouch.units.movable.BaseUnit;

public class MenuTest extends PApplet {

	private static final long serialVersionUID = 1L;

	TuioProcessing tuioClient;

	int width = 1024;
	int height = 600;
	PFont font;
	Menu menu;

	// list with all elements of one player
	private ArrayList<BaseUnit> buildings = new ArrayList<BaseUnit>();

	public void setup() {
		size(width, height); // size of window
		noStroke(); // draw no borders

		loop(); // loop the draw-methode
		frameRate(20);

		// render fonts faster
		hint(ENABLE_NATIVE_FONTS);
		font = createFont("Arial", 18);

		// listens to port 3333
		tuioClient = new TuioProcessing(this);

		menu = new Menu(this);

		// activate anti aliaising
		this.smooth();
		this.rectMode(CENTER);
	}

	/**
	 * within the draw method we retrieve a Vector (List) of TuioObject and
	 * TuioCursor (polling) from the TuioProcessing client and then loop over
	 * both lists to draw the graphical feedback.
	 * 
	 */
	public void draw() {
		background(200);
		fill(100);
		textFont(font, 15);
		textAlign(CENTER);
		text("Dein aktuelles Gold: " + menu.creditsPlayer, width / 2, 15);

		// create menue for building options
		menu.drawMenu();

		// shows all buildings of the players
		for (BaseUnit u : buildings) {
			u.paint();
		}

	}

	// these callback methods are called whenever a TUIO event occurs

	// called when an object is added to the scene
	public void addTuioObject(TuioObject tobj) {
		println("add object " + tobj.getSymbolID() + " (" + tobj.getSessionID() + ") " + tobj.getX() + " " + tobj.getY() + " " + tobj.getAngle());
	}

	// called when an object is removed from the scene
	public void removeTuioObject(TuioObject tobj) {
		println("remove object " + tobj.getSymbolID() + " (" + tobj.getSessionID() + ")");
	}

	// called when an object is moved
	public void updateTuioObject(TuioObject tobj) {
		println("update object " + tobj.getSymbolID() + " (" + tobj.getSessionID() + ") " + tobj.getX() + " " + tobj.getY() + " " + tobj.getAngle() + " " + tobj.getMotionSpeed()
				+ " " + tobj.getRotationSpeed() + " " + tobj.getMotionAccel() + " " + tobj.getRotationAccel());
	}

	// called when a cursor is added to the scene
	public void addTuioCursor(TuioCursor tcur) {
		println("add cursor " + tcur.getCursorID() + " (" + tcur.getSessionID() + ") " + tcur.getX() + " " + tcur.getY());
	}

	// called when a cursor is moved
	public void updateTuioCursor(TuioCursor tcur) {
		println("update cursor " + tcur.getCursorID() + " (" + tcur.getSessionID() + ") " + tcur.getX() + " " + tcur.getY() + " " + tcur.getMotionSpeed() + " "
				+ tcur.getMotionAccel());
	}

	// called when a cursor is removed from the scene
	public void removeTuioCursor(TuioCursor tcur) {
		println("remove cursor " + tcur.getCursorID() + " (" + tcur.getSessionID() + ")");
	}

	// called after each message bundle
	// representing the end of an image frame
	public void refresh(TuioTime bundleTime) {
		redraw();
	}

	/** Start PApplet as a Java program (can also be run as an applet). */
	static public void main(String args[]) {
		PApplet.main(new String[] { "de.fhb.defenderTouch.menu.MenuTest" });
	}

	// override mouseclick
	public void mouseClicked() {
		// create Point where mouse was clicked in
		PVector clickVector = new PVector(this.mouseX, this.mouseY);
		System.out.print(this.mouseX + "," + this.mouseY +" ... ");
		System.out.print(menu.isMenuOpen() + " menu...");
		System.out.println(menu.isBuildingOpen() + " building...");

		if (this.mouseButton == LEFT) {
			// watching if both menus are closed
			if (!menu.isMenuOpen() && !menu.isBuildingOpen()) {

				if (isTaken(clickVector)) {
					// look if a building was clicked
					System.out.println("show building options");
					menu.setBuildingOpen(true);

				} else {
					// if menu is not already open, just open it
					// if its open, don't do anything
					System.out.println("show menu open");
					menu.showMenuPoint(clickVector);
				}

			}

			else if (menu.isBuildingOpen()) {
				// Choosing which Building menu point was clicked
				if (menu.isInnerBuildingElement(clickVector)) {
					switch (menu.getActualNumber()) {
					case 0: // TODO : hier muss level up rein
						System.out.println("do Building upgrade");
						break;
					case 1: // TODO : hier muss gebäude zerstören rein
						System.out.println("do Building destroyed");
						buildings.remove(buildings.get(0));
						break;
					default:
					}
				}
			}

			// watching if menu is open and click is into a building element
			else if (menu.isMenuOpen() ) {
				// menu.setBuildingOpen(false);

				// Choosing which Building should be placed
				if (menu.isInnerMenuElement(clickVector)) {
					switch (menu.getActualNumber()) {
					case 0:
						System.out.println("building a Ground unit");
						buildings.add(new Ground((int) menu.getPositionX(), (int) menu.getPositionY(), BaseUnit.MODE_NORMAL, 0, this));
						break;
					case 1:
						System.out.println("building a Defence unit");
						buildings.add(new Defence((int) menu.getPositionX(), (int) menu.getPositionY(), BaseUnit.MODE_NORMAL, 0, this));
						break;
					case 2:
						System.out.println("building a Support unit");
						buildings.add(new Support((int) menu.getPositionX(), (int) menu.getPositionY(), BaseUnit.MODE_NORMAL, 0, this));
						break;
					default:
						System.out.println();
					}
				}
			}
		}

		if (this.mouseButton == RIGHT) {

			// if menu is open und click in menu point -> close it
			if (menu.isMenuOpen() && menu.isInnerMainElement(clickVector)) {
				menu.setMenuOpen(false);
				System.out.println("close building menu");
			}
			// closes the menu of a specific building
			if (isTaken(clickVector)) {
				System.out.println("close specific building menu");
				menu.setBuildingOpen(false);
			}
		}

	}

	/**
	 * 
	 * @param clickVector
	 * @return if place for the new building is free
	 */
	public boolean isTaken(PVector clickVector) {
		for (BaseUnit building : buildings) {
			// if a building is clicked
			if (building.getPosition().dist(clickVector) < (building.getCollisionRadius())) {
				menu.setPositionBuilding(building.getPosition());
				return true;
			}
		}
		return false;
	}
}
