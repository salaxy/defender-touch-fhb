package de.fhb.defenderTouch.display;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import processing.core.PVector;
import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.units.movable.Fighter;
import de.fhb.defenderTouch.units.movable.Tank;
import de.fhb.defenderTouch.units.notmovable.Defence;
import de.fhb.defenderTouch.units.notmovable.Ground;
import de.fhb.defenderTouch.units.notmovable.Navi;
import de.fhb.defenderTouch.units.notmovable.Support;
import de.fhb.defenderTouch.units.root.BaseUnit;

public class DefenderViewSlick extends BasicGame{

    public static final int WIDTH = 1024;

    public static final int HEIGHT = 768;
    

	/**
	 * Control des Gesamtprogramms
	 */
	private DefenderControl control;

	/**
	 * Konstruktor
	 */
    public DefenderViewSlick() {
          super("DEFENDER ALPHA");
    }


    /**
     * initialisierung
     */
    public void init(GameContainer gc) throws SlickException {
    	
    //gamelogic initialisieren  
  	  control= new DefenderControl();
  	  
  	  gc.setShowFPS(true);
  	  gc.setTargetFrameRate(25);
  	  
	  //TestUnitBetas schaffen
  	BaseUnit test=new BaseUnit(100,200,BaseUnit.MODE_ROTATE,DefenderControl.PLAYER_ONE,control);
	  test.commandDestination(new PVector(1000,700));
    	
  	  //Testflugstaffel
  	  new Tank(100,50,BaseUnit.MODE_NORMAL,DefenderControl.PLAYER_TWO,control);
  	  new Defence(200,50,BaseUnit.MODE_NORMAL,DefenderControl.PLAYER_TWO,control);
  	  new Ground(300,50,BaseUnit.MODE_NORMAL,DefenderControl.PLAYER_TWO,control);
  	  new Navi(400,50,BaseUnit.MODE_NORMAL,DefenderControl.PLAYER_TWO,control);
  	  new Support(500,50,BaseUnit.MODE_NORMAL,DefenderControl.PLAYER_TWO,control);	  
  	  new Fighter(600,50,BaseUnit.MODE_NORMAL,DefenderControl.PLAYER_TWO,control);
  	  new Fighter(700,50,BaseUnit.MODE_PULSE_IF_ACTIVE,DefenderControl.PLAYER_TWO,control);

  	  //Testflugstaffel playerOne
  	  new Fighter(100,700,BaseUnit.MODE_NORMAL,DefenderControl.PLAYER_ONE,control);
  	  new Fighter(200,700,BaseUnit.MODE_NORMAL,DefenderControl.PLAYER_ONE,control);
  	  new Fighter(300,700,BaseUnit.MODE_NORMAL,DefenderControl.PLAYER_ONE,control);
  	  new Fighter(400,700,BaseUnit.MODE_NORMAL,DefenderControl.PLAYER_ONE,control);
  	  new Fighter(500,700,BaseUnit.MODE_NORMAL,DefenderControl.PLAYER_ONE,control);
  	  new BaseUnit(600,700,BaseUnit.MODE_PULSE,DefenderControl.PLAYER_ONE,control);
  	  new BaseUnit(700,700,BaseUnit.MODE_ROTATE_AND_PULSE,DefenderControl.PLAYER_ONE,control);
  	  new BaseUnit(800,700,BaseUnit.MODE_NORMAL,DefenderControl.PLAYER_ONE,control);

    }



    public void update(GameContainer gc, int delta) throws SlickException {
          // Abfangen der Eingabegarät
          Input input = gc.getInput();
    }



    public void render(GameContainer gc, Graphics g) throws SlickException {
    	
    	g.setAntiAlias(true);
    	g.setBackground(Color.white);

    	//alles zeichnen
    	this.control.drawAll(g);
    	
    }

   

    public static void main(String[] args) throws SlickException {

          AppGameContainer defender = new AppGameContainer(new DefenderViewSlick());
          defender.setDisplayMode(WIDTH, HEIGHT, false);
          defender.setVSync(true);
          defender.setShowFPS(false);
          defender.start();

    }

    
    public void mouseClicked(int button, int x, int y, int clickCount){
    	//Klickvektor holen
    	PVector clickVector=new PVector(x,y);
    	
		//wenn aktion im steuerbarenbereich
		if(isInUsableInputarea(clickVector)){
			
//			this.control.startUnitControlForMouse(clickVector, button);	
			this.control.startMenueControlForMouse(clickVector, button);	
			
		}
    }
	
    
    
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
    	
    	//Klickvektor holen
    	PVector clickVector=new PVector(newx,newy);
    	
		if(isInUsableInputarea(clickVector)){
			
	//    	if (mouseButton == LEFT) {
	//    		control.schiebeInterface(oldx, oldy, newx, newy);
	//    	}
	   
	//    	if (mouseButton == RIGHT) {
	    		control.zoomInterface(oldx, oldy, newx, newy);
	//    	}			

		}
		
    }
    
    public void mouseWheelMoved(int change){
    	
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
