package de.fhb.defenderTouch.map;

import TUIO.*;
import processing.core.*;

public class MapTest extends PApplet {

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
	int height = 768;
	PFont font;
	
	private Map karte = new Map(this, 32, 24);
	
	public void setup()
	{
	  size(width,height, JAVA2D); //size of window
	  
	  loop(); //loop the draw-methode
	  frameRate(25);
	  //noLoop();
	  
	  hint(ENABLE_NATIVE_FONTS); //render fonts faster
	  font = createFont("Arial", 18);
	  
	  // we create an instance of the TuioProcessing client
	  // since we add "this" class as an argument the TuioProcessing class expects
	  // an implementation of the TUIO callback methods (see below)
	  tuioClient  = new TuioProcessing(this); //listens to port 3333
	  
	  //kantenglättung aktivieren
	  this.smooth();
	  this.rectMode(CENTER);
	}

	/** within the draw method we retrieve a Vector (List) of TuioObject and TuioCursor (polling)
	 *  from the TuioProcessing client and then loop over both lists to draw the graphical feedback.
	 *  
	 */
	public void draw()
	{
	  background(128);
	  textFont(font,18);
	  
	  karte.paint();
	}

	// these callback methods are called whenever a TUIO event occurs

	// called when an object is added to the scene
	public void addTuioObject(TuioObject tobj) {
	  println("add object "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle());
	  
	}

	// called when an object is removed from the scene
	public void removeTuioObject(TuioObject tobj) {
	  println("remove object "+tobj.getSymbolID()+" ("+tobj.getSessionID()+")");
	}

	// called when an object is moved
	public void updateTuioObject (TuioObject tobj) {
		println("update object "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle()
	          +" "+tobj.getMotionSpeed()+" "+tobj.getRotationSpeed()+" "+tobj.getMotionAccel()+" "+tobj.getRotationAccel());
	}

	// called when a cursor is added to the scene
	public void addTuioCursor(TuioCursor tcur) {
		println("add cursor "+tcur.getCursorID()+" ("+tcur.getSessionID()+ ") " +tcur.getX()+" "+tcur.getY());
	}

	// called when a cursor is moved
	public void updateTuioCursor (TuioCursor tcur) {
		println("update cursor "+tcur.getCursorID()+" ("+tcur.getSessionID()+ ") " +tcur.getX()+" "+tcur.getY()
	          +" "+tcur.getMotionSpeed()+" "+tcur.getMotionAccel());
	}

	// called when a cursor is removed from the scene
	public void removeTuioCursor(TuioCursor tcur) {
		println("remove cursor "+tcur.getCursorID()+" ("+tcur.getSessionID()+")");
	}

	// called after each message bundle
	// representing the end of an image frame
	public void refresh(TuioTime bundleTime) { 
		redraw();
	}
	
    /** Start PApplet as a Java program (can also be run as an applet). */
    static public void main(String args[]) {
        PApplet.main(new String[] { "de.fhb.defenderTouch.map.MapTest" });
    }
    
    //mausclick ueberschreiben
    public void mouseClicked(){
    	PVector clickVector=new PVector(this.mouseX,this.mouseY);
    }
}
