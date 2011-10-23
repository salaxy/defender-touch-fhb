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

	// list with all elements of one player
	private ArrayList<BaseUnit> buildings = new ArrayList<BaseUnit>();

	public void setup() {
		size(width, height); // size of window
		noStroke(); // draw no borders

		loop(); // loop the draw-methode
		frameRate(25);

		// render fonts faster
		hint(ENABLE_NATIVE_FONTS);
		font = createFont("Arial", 18);

		// listens to port 3333
		tuioClient = new TuioProcessing(this);

		menue = new Menu(this);

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
		textFont(font, 15);

		textAlign(CENTER);
		text("Dein aktuelles Gold: 200", width / 2, 15);

		// create menue for building options
		menue.drawMenu();

		// shows all buildings of the players
		for (BaseUnit u : buildings) {
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

	// override mouseclick
	public void mouseClicked() {
		// create Point where mouse was clicked in
		PVector clickVector = new PVector(this.mouseX, this.mouseY);

		if (this.mouseButton == LEFT) {

			// watching if menu is open and click is into a building element
			if (menue.isMenuOpen()) {
				// Choosing which Building should be placed
				if (menue.isInnerMenuElement(clickVector)) {

					switch (menue.getNumber()) {
					case 0:
						buildings.add(new Ground((int) menue.getPositionX(), (int) menue.getPositionY(), BaseUnit.MODE_NORMAL, 0, this));
						menue.setMenuOpen(false);
						break;

					case 1:
						buildings.add(new Defence((int) menue.getPositionX(), (int) menue.getPositionY(), BaseUnit.MODE_NORMAL, 0, this));
						menue.setMenuOpen(false);
						break;

					case 2:
						buildings.add(new Support((int) menue.getPositionX(), (int) menue.getPositionY(), BaseUnit.MODE_NORMAL, 0, this));
						menue.setMenuOpen(false);
						break;
					// TODO
					default:
						System.out.println();
					}
				}
			}else{
				// if menu is not already open, just open it
				// if its open, don't do anything
				menue.showMenuPoint(clickVector);
				System.out.println("menu offen");
			}
		}

		if (this.mouseButton == RIGHT) {

			// if menu is open und click in menu point -> close it
			if (menue.isMenuOpen() && menue.isInnerMainElement(clickVector)) {
				menue.setMenuOpen(false);
				System.out.println("menu geschlossen");
			}

		}

	}
}
