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
import de.fhb.defenderTouch.ui.Gestures;
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
	 * Gestenerkennung
	 */
	private Gestures gestures=new Gestures();
	
	/**
	 * Controlunit des Gesamtprogramms
	 */
	private DefenderControl control;

   

    public DefenderViewSlick() {

          super("Pong");
          
          
          
          

    }



    public void init(GameContainer gc) throws SlickException {
    	
    //gamelogic initialisieren  
  	  control= new DefenderControl();
  	  
  	  gc.setShowFPS(true);
  	  gc.setTargetFrameRate(25);
//  	  gc.setMaximumLogicUpdateInterval20);

  	  
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

          // Abfangen der Eingabegaräte

          Input input = gc.getInput();

    }



    public void render(GameContainer gc, Graphics g) throws SlickException {
    	
//    	g.setDrawMode(g.Mo)
    	g.setAntiAlias(true);
    	g.setBackground(Color.white);
    	g.setColor(Color.red);
    	g.fillRect(0, 0, 20, 20);
    	g.drawRect(0, 0, 20, 20);
    	
    	this.control.drawAll(g);
    	
    }

   

    public static void main(String[] args) throws SlickException {

          AppGameContainer pong = new AppGameContainer(new DefenderViewSlick());

          pong.setDisplayMode(WIDTH, HEIGHT, false);

          pong.setVSync(true);

          pong.setShowFPS(false);

          pong.start();

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
//    	if (mouseButton == LEFT) {
//    		if (mouseX < 512) {
//    			PVector tempVec = control.getPlayerOne().getViewPosition();
//    			tempVec.y = tempVec.y + pmouseX - mouseX;
//    			tempVec.x = tempVec.x + mouseY - pmouseY;
//    		} else {
//    			PVector tempVec = control.getPlayerTwo().getViewPosition();
//    			tempVec.y = tempVec.y + mouseX  - pmouseX;
//    			tempVec.x = tempVec.x + pmouseY - mouseY ;
//    		}
//    	}
    	
//    	if (mouseButton == RIGHT) {
    		if (newx < 512) {
    			control.getPlayerOne().setActualZoom(control.getPlayerOne().getActualZoom() + (newy -  oldy) * 0.01f);    			
    		} else {
    			control.getPlayerTwo().setActualZoom(control.getPlayerTwo().getActualZoom()  + (newy -  oldy) * 0.01f);    			
    		}
//    	}
    }
    
    public void mouseWheelMoved(int change){
//    	System.out.println("reaction");
//		control.getPlayerOne().setActualZoom(control.getPlayerOne().getActualZoom() + change * 0.0001f);   
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
