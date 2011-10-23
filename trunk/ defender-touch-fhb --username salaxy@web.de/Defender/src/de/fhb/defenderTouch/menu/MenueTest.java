package de.fhb.defenderTouch.menu;

import java.util.ArrayList;
import java.util.Vector;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import TUIO.TuioCursor;
import TUIO.TuioObject;
import TUIO.TuioPoint;
import TUIO.TuioProcessing;
import TUIO.TuioTime;
import de.fhb.defenderTouch.units.grounded.Defence;
import de.fhb.defenderTouch.units.grounded.Ground;
import de.fhb.defenderTouch.units.grounded.Navi;
import de.fhb.defenderTouch.units.grounded.Support;
import de.fhb.defenderTouch.units.movable.BaseUnit;

public class MenueTest extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	TuioProcessing tuioClient;

	// these are some helper variables which are used
	// to create scalable graphical feedback
	float object_size = 60;
	int width = 1024;
	int height = 600;
	PFont font;
	BaseUnit test;
	Menu menue;

	// TODO
	// private ArrayList<BaseUnit> buildings = new ArrayList<BaseUnit>();
	private ArrayList<BaseUnit> units = new ArrayList<BaseUnit>();

	public void setup() {
		size(width, height); // size of window
		noStroke(); // draw no borders

		loop(); // loop the draw-methode
		frameRate(25);

		hint(ENABLE_NATIVE_FONTS); // render fonts faster
		font = createFont("Arial", 18);

		tuioClient = new TuioProcessing(this); // listens to port 3333

		menue = new Menu(this);

		// kantenglättung aktivieren
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
		textFont(font, 15);

		textAlign(CENTER);
		text("Dein aktuelles Gold: 200", width / 2, 15);

		// create menue for building options
		menue.drawMenu();

		for (BaseUnit u : units) {
			u.paint();
		}

	}

	// these callback methods are called whenever a TUIO event occurs

	// called when an object is added to the scene
	public void addTuioObject(TuioObject tobj) {
		println("add object " + tobj.getSymbolID() + " (" + tobj.getSessionID() + ") " + tobj.getX() + " " + tobj.getY() + " "
				+ tobj.getAngle());

	}

	// called when an object is removed from the scene
	public void removeTuioObject(TuioObject tobj) {
		println("remove object " + tobj.getSymbolID() + " (" + tobj.getSessionID() + ")");
	}

	// called when an object is moved
	public void updateTuioObject(TuioObject tobj) {
		println("update object " + tobj.getSymbolID() + " (" + tobj.getSessionID() + ") " + tobj.getX() + " " + tobj.getY() + " "
				+ tobj.getAngle() + " " + tobj.getMotionSpeed() + " " + tobj.getRotationSpeed() + " " + tobj.getMotionAccel() + " "
				+ tobj.getRotationAccel());
	}

	// called when a cursor is added to the scene
	public void addTuioCursor(TuioCursor tcur) {
		println("add cursor " + tcur.getCursorID() + " (" + tcur.getSessionID() + ") " + tcur.getX() + " " + tcur.getY());

		PVector vector = new PVector(tcur.getScreenX(width), tcur.getScreenY(height));
		boolean wurdeEbendAktiviert = false;
		boolean warSchonAktiv = false;

	}

	// called when a cursor is moved
	public void updateTuioCursor(TuioCursor tcur) {
		println("update cursor " + tcur.getCursorID() + " (" + tcur.getSessionID() + ") " + tcur.getX() + " " + tcur.getY() + " "
				+ tcur.getMotionSpeed() + " " + tcur.getMotionAccel());
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
		PApplet.main(new String[] { "de.fhb.defenderTouch.menu.MenueTest" });
	}

	// mausclick ueberschreiben
	public void mouseClicked() {
		PVector clickVector = new PVector(this.mouseX, this.mouseY);

		if (this.mouseButton == LEFT) {

			// if menu is not already open, just open it
			// if its open, dont do anything
			if (menue.isMenuOpen() == false) {
				menue.showMenuPoint(clickVector);
				System.out.println("menu offen");
			}

			// building a Building
			if (menue.isMenuOpen() && menue.isInner(clickVector)) {
				// System.out.println("Building a Ground Unit if enough Gold");
				// menue.createGroundUnit();
				// buildings.add(new MakeBuilding(100,200,this));
				System.out.println("test");

				if (menue.isInnerMenuElement(clickVector)) {

					switch (menue.getNumber()) {
					case 0:
						units.add(new Ground((int) menue.getPositionX(), (int) menue.getPositionY(), BaseUnit.MODE_NORMAL, 0, this));
						break;

					case 1:
						units.add(new Defence((int) menue.getPositionX(), (int) menue.getPositionY(), BaseUnit.MODE_NORMAL, 0, this));
						break;

					case 2:
						units.add(new Support((int) menue.getPositionX(), (int) menue.getPositionY(), BaseUnit.MODE_NORMAL, 0, this));
						break;
					// TODO
					default:
						System.out.println();
					}
				}

			}

		}

		if (this.mouseButton == RIGHT) {

			// if menu is open und click in menu point -> close it
			if (menue.isMenuOpen() == true && menue.isInner(clickVector)) {
				menue.setMenuOpen(false);
				System.out.println("menu geschlossen");
			}

		}

	}
}
