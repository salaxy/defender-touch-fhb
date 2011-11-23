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
  	  control.createTestUnits();
  	  
  	  gc.setShowFPS(true);
  	  gc.setTargetFrameRate(25);
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
			
			this.control.startUnitControlForMouse(clickVector, button);	
//			this.control.startMenueControlForMouse(clickVector, button);	
			
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
