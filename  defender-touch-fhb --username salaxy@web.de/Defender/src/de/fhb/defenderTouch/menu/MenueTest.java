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
import de.fhb.defenderTouch.units.movable.Fighter;

public class MenueTest extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	TuioProcessing tuioClient;

	// these are some helper variables which are used
	// to create scalable graphical feedback
	float cursor_size = 15;
	float object_size = 60;
	int width = 1024;
	int height = 600;
	PFont font;
	BaseUnit test;
	Menu menue;

	// zum testen
	private ArrayList<BaseUnit> units = new ArrayList<BaseUnit>();

	public void setup() {
		size(width, height); // size of window
		noStroke(); // draw no borders

		loop(); // loop the draw-methode
		frameRate(60);

		hint(ENABLE_NATIVE_FONTS); // render fonts faster
		font = createFont("Arial", 18);

		// we create an instance of the TuioProcessing client
		// since we add "this" class as an argument the TuioProcessing class
		// expects
		// an implementation of the TUIO callback methods (see below)
		tuioClient = new TuioProcessing(this); // listens to port 3333

		// //TestUnitBetas schaffen
		// units.add(new BaseUnit(100,200,BaseUnit.MODE_ROTATE,this));
		// units.add(new BaseUnit(200,100,BaseUnit.MODE_PULSE,this));
		// units.add(new BaseUnit(300,200,BaseUnit.MODE_ROTATE_AND_PULSE,this));
		// units.add(new BaseUnit(200,300,BaseUnit.MODE_NORMAL,this));
		// units.add(new Fighter(300,400,BaseUnit.MODE_NORMAL,this));
		// units.add(new Fighter(500,400,BaseUnit.MODE_PULSE_IF_ACTIVE,this));
		// //BuildingTest
		// units.add(new Ground(400,700,BaseUnit.MODE_NORMAL,this));
		// units.add(new Navi(500,700,BaseUnit.MODE_NORMAL,this));
		// units.add(new Support(600,700,BaseUnit.MODE_NORMAL,this));
		// units.add(new Defence(700,700,BaseUnit.MODE_NORMAL,this));
		//
		// //Testflugstaffel
		// units.add(new Fighter(100,50,BaseUnit.MODE_NORMAL,this));
		// units.add(new Fighter(200,50,BaseUnit.MODE_NORMAL,this));
		// units.add(new Fighter(300,50,BaseUnit.MODE_NORMAL,this));
		// units.add(new Fighter(400,50,BaseUnit.MODE_NORMAL,this));
		// units.add(new Fighter(500,50,BaseUnit.MODE_NORMAL,this));
		// units.add(new Fighter(600,50,BaseUnit.MODE_HALO,this));

		// units.add(test=new
		// BaseUnit(300,400,BaseUnit.MODE_ROTATE_AND_PULSE,this));
		// test.commandDestination(new PVector(500,500));

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
		textFont(font, 18);


		// create menue for building options
		menue.drawMenu();

		

	}

	// these callback methods are called whenever a TUIO event occurs

	// called when an object is added to the scene
	public void addTuioObject(TuioObject tobj) {
		println("add object " + tobj.getSymbolID() + " (" + tobj.getSessionID()
				+ ") " + tobj.getX() + " " + tobj.getY() + " "
				+ tobj.getAngle());

	}

	// called when an object is removed from the scene
	public void removeTuioObject(TuioObject tobj) {
		println("remove object " + tobj.getSymbolID() + " ("
				+ tobj.getSessionID() + ")");
	}

	// called when an object is moved
	public void updateTuioObject(TuioObject tobj) {
		println("update object " + tobj.getSymbolID() + " ("
				+ tobj.getSessionID() + ") " + tobj.getX() + " " + tobj.getY()
				+ " " + tobj.getAngle() + " " + tobj.getMotionSpeed() + " "
				+ tobj.getRotationSpeed() + " " + tobj.getMotionAccel() + " "
				+ tobj.getRotationAccel());
	}

	// called when a cursor is added to the scene
	public void addTuioCursor(TuioCursor tcur) {
		println("add cursor " + tcur.getCursorID() + " (" + tcur.getSessionID()
				+ ") " + tcur.getX() + " " + tcur.getY());

		PVector vector = new PVector(tcur.getScreenX(width),
				tcur.getScreenY(height));
		boolean wurdeEbendAktiviert = false;
		boolean warSchonAktiv = false;

		for (BaseUnit u : units) {

			// wenn eine unit aktiviert wird dann die anderen deaktiveren
			if (!wurdeEbendAktiviert) {

				// wenn bereits aktiv dann deaktivieren
				warSchonAktiv = u.isActive();
				wurdeEbendAktiviert = u.isInner(vector);

				if (wurdeEbendAktiviert && warSchonAktiv) {
					u.deactivate();
				}

			} else {
				u.deactivate();
			}
		}

		// neues Ziel setzen wenn unit aktiv
		for (BaseUnit u : units) {
			if (u.isActive()) {
				u.commandDestination(vector);
			}
		}
	}

	// called when a cursor is moved
	public void updateTuioCursor(TuioCursor tcur) {
		println("update cursor " + tcur.getCursorID() + " ("
				+ tcur.getSessionID() + ") " + tcur.getX() + " " + tcur.getY()
				+ " " + tcur.getMotionSpeed() + " " + tcur.getMotionAccel());
	}

	// called when a cursor is removed from the scene
	public void removeTuioCursor(TuioCursor tcur) {
		println("remove cursor " + tcur.getCursorID() + " ("
				+ tcur.getSessionID() + ")");
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

			menue.showMenuPoint(clickVector);

			
		}

	}

}
