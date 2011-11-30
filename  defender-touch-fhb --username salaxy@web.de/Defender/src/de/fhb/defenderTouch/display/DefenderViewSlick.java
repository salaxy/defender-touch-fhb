package de.fhb.defenderTouch.display;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhb.defenderTouch.gamelogic.DefenderControl;

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

    /**
     * What happens when mouse was clicked
     */
    public void mouseClicked(int button, int x, int y, int clickCount){
    	//Klickvektor holen
    	Vector2f clickVector=new Vector2f(x,y);
    	
		//wenn aktion im steuerbarenbereich
		if(isInUsableInputarea(clickVector)){
			
			// Test der Mapeigenschaften:
//						System.out.println("Map Properties:");
//						System.out.println("Coordinates: " + clickVector.toString());
//						System.out.println("isWalkable:  " + control.getMap().isWalkable(clickVector));
//						System.out.println("isFlyable:   " + control.getMap().isFlyable(clickVector));
//						System.out.println("isBuildable: (click at center of building)\n" +
//											   "\t[10x10]   Player 1: " + control.getMap().isBuildable(new Vector2f(x-4, y-4), new Vector2f(x+5, y+5), DefenderControl.PLAYER_ONE_ID) +
//											   "  Player 2: " + control.getMap().isBuildable(new Vector2f(x-4, y-4), new Vector2f(x+5, y+5), DefenderControl.PLAYER_TWO_ID) +
//											   "  System: " + control.getMap().isBuildable(new Vector2f(x-4, y-4), new Vector2f(x+5, y+5), DefenderControl.PLAYER_SYSTEM_ID) + "\n" + 
//											   "\t[25x25]   Player 1: " + control.getMap().isBuildable(new Vector2f(x-12, y-12), new Vector2f(x+13, y+13), DefenderControl.PLAYER_ONE_ID) +
//											   "  Player 2: " + control.getMap().isBuildable(new Vector2f(x-12, y-12), new Vector2f(x+13, y+13), DefenderControl.PLAYER_TWO_ID) +
//											   "  System: " + control.getMap().isBuildable(new Vector2f(x-12, y-12), new Vector2f(x+13, y+13), DefenderControl.PLAYER_SYSTEM_ID) + "\n" + 
//											   "\t[100x100] Player 1: " + control.getMap().isBuildable(new Vector2f(x-49, y-49), new Vector2f(x+50, y+50), DefenderControl.PLAYER_ONE_ID) +
//											   "  Player 2: " + control.getMap().isBuildable(new Vector2f(x-49, y-49), new Vector2f(x+50, y+50), DefenderControl.PLAYER_TWO_ID) +
//											   "  System: " + control.getMap().isBuildable(new Vector2f(x-49, y-49), new Vector2f(x+50, y+50), DefenderControl.PLAYER_SYSTEM_ID) + "\n" 
//										   );
						
			this.control.startUnitControlForMouse(clickVector, button);	
//			this.control.startMenueControlForMouse(clickVector, button);	
			
		}
    }
	
    
    /**
     * What happens when mouse is moving
     */
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
    	
    	//Klickvektor holen
    	Vector2f clickVector=new Vector2f(newx,newy);
    	
		if(isInUsableInputarea(clickVector)){
			
			
			
	//    	if (mouseButton == LEFT) {
	//    		control.schiebeInterface(oldx, oldy, newx, newy);
	//    	}
	   
	//    	if (mouseButton == RIGHT) {
	    		control.zoomInterface(oldx, oldy, newx, newy);
	//    	}			

		}
		
    }
    
    /**
     * What happens when mousewhell is scrolled
     */
    public void mouseWheelMoved(int change){
    	
	}
    
    
	/**
	 * Gibt true zurueck wenn Klickvektor im Steuerbaren bereich
	 * @param clickVector
	 * @return
	 */
	private boolean isInUsableInputarea(Vector2f clickVector){
		
		return clickVector.x > 522|| clickVector.x < 502;
	}


}
