package de.fhb.defenderTouch.display;
import java.util.Vector;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PVector;
import TUIO.TuioCursor;
import TUIO.TuioObject;
import TUIO.TuioPoint;
import TUIO.TuioProcessing;
import TUIO.TuioTime;
import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.graphics.GraphicTools;
import de.fhb.defenderTouch.menu.Menu;
import de.fhb.defenderTouch.ui.Gestures;
import de.fhb.defenderTouch.units.movable.Fighter;
import de.fhb.defenderTouch.units.movable.Tank;
import de.fhb.defenderTouch.units.notmovable.Defence;
import de.fhb.defenderTouch.units.notmovable.Ground;
import de.fhb.defenderTouch.units.notmovable.Support;
import de.fhb.defenderTouch.units.root.BaseUnit;

public class DefenderView extends PApplet {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	TuioProcessing tuioClient;
	
	// these are some helper variables which are used
	// to create scalable graphical feedback
	private float cursor_size = 15;
//	private float object_size = 60;
	private int width = 1024;
	private int height = 768;
	private PFont font;
	private BaseUnit test;

	/**
	 * Bildschirmgrapfken links und rechts
	 */
	private PGraphics screenLeft, screenRight;
	
	/**
	 * Gestenerkennung
	 */
	private Gestures gestures=new Gestures();
	
	/**
	 * Controlunit des Gesamtprogramms
	 */
	private DefenderControl control;
	
	public void setup()
	{


		
	//Screens links und rechts initialisieren
	screenLeft = createGraphics(510, 768, JAVA2D);
	screenRight = createGraphics(510, 768, JAVA2D);	
	
	//gamelogic initialisieren  
//	  control= DefenderControl.getInstance(this, screenLeft, screenRight);
	
	  //size(screen.width,screen.height);
	  size(width,height, P2D); //size of window
	  noStroke(); //draw no borders
//	  fill(0); //fill shapes (e.g. rectangles, ellipses) with black
	  
	  loop(); //loop the draw-methode
	  frameRate(25);
	  //noLoop();
	  
	  hint(ENABLE_NATIVE_FONTS); //render fonts faster
	  font = createFont("Arial", 18);
	  
	  // we create an instance of the TuioProcessing client
	  // since we add "this" class as an argument the TuioProcessing class expects
	  // an implementation of the TUIO callback methods (see below)
	  tuioClient  = new TuioProcessing(this); //listens to port 3333
	  
	  // Spieler & Karte initialisieren
//	  karte = new Map(getWidth() / 32, getHeight() / 32); // Vorläufig wird das so initialisiert, später wird hier einfach nur eine vorhandene Karte geladen.
  
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
//	  background(255);
	  textFont(font,18);
	 // float obj_size = object_size; 
	  float cur_size = cursor_size;
	  
	  
//	  this.control.drawAll();
	  


//	  if (mousePressed) {
//		    fill(0);
//		  } else {
//		    fill(255);
//		  }
//	  ellipse(mouseX, mouseY, 80, 80);
	   
	  Vector tuioObjectList = tuioClient.getTuioObjects(); //gets all objects which are currently on the screen
	  for (int i=0;i<tuioObjectList.size();i++) {
	     TuioObject tobj = (TuioObject)tuioObjectList.elementAt(i);
	     stroke(0);
	     fill(0);
	     pushMatrix(); //save old coordinate system (bottom left is 0,0)
	     translate(tobj.getScreenX(width),tobj.getScreenY(height)); //translate coordinate-system that 0,0 is at position of object (easier for drawing)
	     rotate(tobj.getAngle()); //rotate coordinate system in same angle than object is
//	     rect(-obj_size/2,-obj_size/2,obj_size,obj_size); //draw rectangle
	     popMatrix(); //restore old coordinate system
	     fill(255);
	     text(""+tobj.getSymbolID(), tobj.getScreenX(width), tobj.getScreenY(height)); //draw objectID at position of object
	   }
	   
	   Vector tuioCursorList = tuioClient.getTuioCursors(); //gets all cursors (fingers) which are currently on the screen
	   for (int i=0;i<tuioCursorList.size();i++) {
	      TuioCursor tcur = (TuioCursor)tuioCursorList.elementAt(i);
	      Vector pointList = tcur.getPath(); // get path of cursors (the positions they have already been in the past)
	      
	    //if points exist (no points will exists when cursor not moved)
	      if (pointList.size()>0) { //draw path
	        stroke(0,0,255);
	        TuioPoint start_point = (TuioPoint)pointList.firstElement();
	        for (int j=0;j<pointList.size();j++) {
	           TuioPoint end_point = (TuioPoint)pointList.elementAt(j);
	           line(start_point.getScreenX(width),start_point.getScreenY(height),end_point.getScreenX(width),end_point.getScreenY(height));
	           start_point = end_point;
	        }
	        
	        stroke(192,192,192); //border is grey
	        fill(192,192,192); //fill with grey
	        ellipse( tcur.getScreenX(width), tcur.getScreenY(height),cur_size,cur_size); //draw ellipse at (current) position of cursor
	        fill(0);
	        text(""+ tcur.getCursorID(),  tcur.getScreenX(width)-5,  tcur.getScreenY(height)+5); //draw id and position at current position of cursor
	      }
	   }
	   
	   
	   
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
	  
	  	PVector vector=new PVector(tcur.getScreenX(width),tcur.getScreenY(height));
	  	boolean wurdeEbendAktiviert=false;	
	  	boolean warSchonAktiv=false;
	
		for(BaseUnit u: control.getGlobalUnits()){
			
			
			//wenn eine unit aktiviert wird dann die anderen deaktiveren
			if(!wurdeEbendAktiviert){
				
				//wenn bereits aktiv dann deaktivieren
				warSchonAktiv=u.isActive();
				wurdeEbendAktiviert=u.isInner(vector);	
				
				if(wurdeEbendAktiviert&&warSchonAktiv){
					u.deactivate();
				}
				
			}else{
				u.deactivate();
			}
		}
		
		//neues Ziel setzen wenn unit aktiv
		for(BaseUnit u: control.getGlobalUnits()){
			if(u.isActive()){
				u.commandDestination(vector);				
			}
		}
	}

	// called when a cursor is moved
	public void updateTuioCursor (TuioCursor tcur) {
	  println("update cursor "+tcur.getCursorID()+" ("+tcur.getSessionID()+ ") " +tcur.getX()+" "+tcur.getY()
	          +" "+tcur.getMotionSpeed()+" "+tcur.getMotionAccel());
	  
	  //XXX erster Test .....anbiden von Gestures , NOTIZ funktioniert nicht
		if(this.gestures.twoFingersInRange(this.tuioClient.getTuioCursors(), 2)){
			
			System.out.println("zwei finger erkannt");
		}
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
        PApplet.main(new String[] { "de.fhb.defenderTouch.display.DefenderView" });
    }
    
    
    public void mouseClicked(){
    	//Klickvektor holen
    	PVector clickVector=new PVector(this.mouseX,this.mouseY);
    	
		//wenn aktion im steuerbarenbereich
		if(isInUsableInputarea(clickVector)){
			
//			this.control.startUnitControlForMouse(clickVector, this.mouseButton);	
			
			this.control.startMenueControlForMouse(clickVector, this.mouseButton);	
		}
    	
    }
    
    
    
    


    
    public void mouseDragged() {
    	if (mouseButton == LEFT) {
    		if (mouseX < 512) {
    			PVector tempVec = control.getPlayerOne().getViewPosition();
    			tempVec.y = tempVec.y + pmouseX - mouseX;
    			tempVec.x = tempVec.x + mouseY - pmouseY;
    		} else {
    			PVector tempVec = control.getPlayerTwo().getViewPosition();
    			tempVec.y = tempVec.y + mouseX  - pmouseX;
    			tempVec.x = tempVec.x + pmouseY - mouseY ;
    		}
    	}
    	
    	if (mouseButton == RIGHT) {
    		if (mouseX < 512) {
    			control.getPlayerOne().setActualZoom(control.getPlayerOne().getActualZoom() + (mouseY - pmouseY) * 0.01f);    			
    		} else {
    			control.getPlayerTwo().setActualZoom(control.getPlayerTwo().getActualZoom()  + (mouseY - pmouseY) * 0.01f);    			
    		}
    	}
    }
    
    
	/**
	 * Gibt true zurueck wenn Klickvektor im Steuerbaren bereich
	 * @param clickVector
	 * @return
	 */
	private boolean isInUsableInputarea(PVector clickVector){
		
		return clickVector.x > 522|| clickVector.x < 502;
	}

    
}
