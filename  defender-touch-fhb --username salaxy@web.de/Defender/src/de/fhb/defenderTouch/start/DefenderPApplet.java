package de.fhb.defenderTouch.start;
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
import de.fhb.defenderTouch.gamelogic.Spieler;
import de.fhb.defenderTouch.units.grounded.Defence;
import de.fhb.defenderTouch.units.grounded.Ground;
import de.fhb.defenderTouch.units.grounded.Navi;
import de.fhb.defenderTouch.units.grounded.Support;
import de.fhb.defenderTouch.units.movable.BaseUnit;
import de.fhb.defenderTouch.units.movable.Fighter;
import de.fhb.defenderTouch.units.movable.Shoot;

public class DefenderPApplet extends PApplet {

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
	BaseUnit test;
	
	private Spieler spielerOne=new Spieler(height, width, 1f);
	private Spieler spielerTwo=new Spieler(height, width, 1f);
	
	// zum testen
	private ArrayList<BaseUnit> units=new ArrayList<BaseUnit>();
	
	public void setup()
	{
	  //size(screen.width,screen.height);
	  size(width,height, JAVA2D); //size of window
	  noStroke(); //draw no borders
	  fill(0); //fill shapes (e.g. rectangles, ellipses) with black
	  
	  loop(); //loop the draw-methode
	  frameRate(25);
	  //noLoop();
	  
	  hint(ENABLE_NATIVE_FONTS); //render fonts faster
	  font = createFont("Arial", 18);
	  
	  // we create an instance of the TuioProcessing client
	  // since we add "this" class as an argument the TuioProcessing class expects
	  // an implementation of the TUIO callback methods (see below)
	  tuioClient  = new TuioProcessing(this); //listens to port 3333
	  
	  
	  
	  //TestUnitBetas schaffen
	  units.add(test=new BaseUnit(100,200,BaseUnit.MODE_ROTATE,BaseUnit.PLAYER_ONE,this));
	  test.commandDestination(new PVector(1000,700));
	  units.add(new BaseUnit(200,100,BaseUnit.MODE_PULSE,BaseUnit.PLAYER_ONE,this));
	  units.add(new BaseUnit(300,200,BaseUnit.MODE_ROTATE_AND_PULSE,BaseUnit.PLAYER_ONE,this));
	  units.add(new BaseUnit(200,300,BaseUnit.MODE_NORMAL,BaseUnit.PLAYER_ONE,this));
	  units.add(new Fighter(300,400,BaseUnit.MODE_NORMAL,BaseUnit.PLAYER_ONE,this));
	  units.add(new Fighter(500,400,BaseUnit.MODE_PULSE_IF_ACTIVE,BaseUnit.PLAYER_ONE,this));
	  //BuildingTest
	  units.add(new Ground(400,700,BaseUnit.MODE_NORMAL,BaseUnit.PLAYER_ONE,this));
	  units.add(new Navi(500,700,BaseUnit.MODE_NORMAL,BaseUnit.PLAYER_ONE,this));
	  units.add(new Support(600,700,BaseUnit.MODE_NORMAL,BaseUnit.PLAYER_ONE,this));
	  units.add(new Defence(700,700,BaseUnit.MODE_NORMAL,BaseUnit.PLAYER_ONE,this));
	  
	  //Testflugstaffel
	  units.add(new Fighter(100,50,BaseUnit.MODE_NORMAL,BaseUnit.PLAYER_TWO,this));
	  units.add(new Fighter(200,50,BaseUnit.MODE_NORMAL,BaseUnit.PLAYER_TWO,this));
	  units.add(new Fighter(300,50,BaseUnit.MODE_NORMAL,BaseUnit.PLAYER_TWO,this));
	  units.add(new Fighter(400,50,BaseUnit.MODE_NORMAL,BaseUnit.PLAYER_TWO,this));
	  units.add(new Fighter(500,50,BaseUnit.MODE_NORMAL,BaseUnit.PLAYER_TWO,this));
//	  units.add(new Shoot(600,50,BaseUnit.MODE_HALO,BaseUnit.PLAYER_TWO,this));
	  
	  
//	  units.add(test=new BaseUnit(300,400,BaseUnit.MODE_ROTATE_AND_PULSE,this));
//	  test.commandDestination(new PVector(500,500));
	  
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
	  background(255);
	  textFont(font,18);
	  float obj_size = object_size; 
	  float cur_size = cursor_size; 
	  
	  //alle Units zeichnen
	  for(BaseUnit u: BaseUnit.globalUnits){
		  u.paint();	  
	  }
	 
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
	
		for(BaseUnit u: units){
			
			
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
		for(BaseUnit u: units){
			if(u.isActive()){
				u.commandDestination(vector);				
			}
		}
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
	
//    /** Start PApplet as a Java program (can also be run as an applet). */
//    static public void main(String args[]) {
//        PApplet.main(new String[] { "de.fhb.defenderTouch.start.TUIOProcessingTest" });
//    }
    
    //speziell fuer SPIELER 2
    //mausclick ueberschreiben
    public void mouseClicked(){
    	PVector clickVector=new PVector(this.mouseX,this.mouseY);
//	    boolean istSchonEinesAktiv=false;
    	boolean weitereAktivierung=false;
	    boolean istAngriff=false;
    	
//	    BaseUnit activeUnit;
	    BaseUnit destinationUnit=null;
	    
    	if(this.mouseButton==LEFT){
    		
	    	
			for(BaseUnit u: units){
				
				//wenn eine unit aktiviert wird dann die anderen deaktiveren
				if(u.getPlayerID()==1 && u.isInner(clickVector)){	
					u.activate();
				}
				
//				else{
//					u.deactivate();
//				}
			}
			
			
			
			//neues Ziel setzen wenn unit aktiv
			for(BaseUnit u: units){
			
				if(u.isActive()){
					//wenn  klick auf eine gegnerische Unit dann angriff
					for(BaseUnit bu: units){
						
						//auf gegnerische einheit geklickt??
						if(bu.getPlayerID()==0 && bu.isInner(clickVector)){
							istAngriff=true;
							destinationUnit=bu;
							System.out.println("Angriff initiiert!");
						}
						
						//auf weitere eigene einheit geklickt??
						if(bu.getPlayerID()==1 && bu.isInner(clickVector)){
							weitereAktivierung=true;
							bu.activate();
							System.out.println("Weitere Einheit aktiviert!");
						}

					}					
					
					if(!weitereAktivierung){
						//bewegung anweisen wenn kein angriff
						if(!istAngriff){	
							u.commandDestination(clickVector);
						}else{
							//falls angriff dann Angriff anweisen
							u.attack(destinationUnit);
						}
					}

				}
			}	
    	}
    	

		
		if(this.mouseButton==RIGHT){

			
			for(BaseUnit u: units){
				u.deactivate();
			}
		}
		

    }
    
    

    
    
}
