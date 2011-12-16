package de.fhb.defenderTouch.display;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.graphics.GraphicTools;

public class DefenderViewSlick extends BasicGameState{

	/**
	 * Bildschirmbreite
	 */
    public static final int WIDTH = 1024;

    /**
     * Bildschirmhöhe
     */
    public static final int HEIGHT = 768;
    
    /**
     * GameState ID (nicht verändern!)
     */
    public static final int ID = 0; 
    
    private boolean isMouseUsingOn=true;
    

	/**
	 * Control des Gesamtprogramms
	 */
	private DefenderControl control;
	
	
	private int mouseButton=0; 

    /**
     * initialisierung
     */
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
    	 
    //gamelogic initialisieren  
  	  control = new DefenderControl();
  	  control.createTestUnits();
  	  control.createPraesentationUnits();
  	  
  	  gc.setShowFPS(true);
  	  gc.setTargetFrameRate(25);
  	  
    }

    /**
     * Berechnung, die jeden Frame gemacht werden müssen.
     */
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
    	
	 // Abfangen der Eingabegarät
//    	Input input = gc.getInput();
    }

    /**
     * Rendern eines Frames
     */
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
    	
    	g.setAntiAlias(true);
    	g.setBackground(Color.blue);

    	//alles zeichnen
    	this.control.drawAll(g);  
    	this.control.updateGame();
    }

    /**
     * What happens when mouse was clicked
     */
    public void mouseClicked(int button, int x, int y, int clickCount){

    	
    	//Klickvektor holen
    	Vector2f clickVector=new Vector2f(x,y);
    	Vector2f mapCoords = GraphicTools.calcInputVector(clickVector, control.getPlayerOne());
    	
		//wenn aktion im steuerbarenbereich
		if(isInUsableInputarea(clickVector)){
			
			// Test der Mapeigenschaften:
						System.out.println("Map Properties:");
						System.out.println("Coordinates: " + mapCoords.toString());
						System.out.println("isWalkable:  " + control.getMap().isWalkable(mapCoords));
						System.out.println("isFlyable:   " + control.getMap().isFlyable(mapCoords));
						System.out.println("isBuildable: (click at center of building)\n" +
											   "[50x50]   Player 1: " + control.getMap().isBuildable(mapCoords, DefenderControl.PLAYER_ONE_ID) +
											   "  Player 2: " +           control.getMap().isBuildable(mapCoords, DefenderControl.PLAYER_TWO_ID) +
											   "  System: " +             control.getMap().isBuildable(mapCoords, DefenderControl.PLAYER_SYSTEM_ID) + "\n" 
										   );
						
	    	if(isMouseUsingOn){
//				this.control.startUnitControlForMouse(clickVector, button);	
				this.control.startMenueControlForMouse(clickVector, button);		    		
	    	}	
		}
    }
	
    
    /**
     * What happens when mouse is moving
     */
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
    	
    	if(isMouseUsingOn)
		if(isInUsableInputarea(new Vector2f(newx,newy))){
			
	    	if (mouseButton == 0) {
	    		control.schiebeInterface(oldx, oldy,newx, newy);		
	    	}
	   
	    	if (mouseButton == 1) {		
	    		control.zoomInterface(oldx, oldy, newx, newy);
	    	}			
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
	
	
	public void mousePressed(int button, int x, int y){	
    	mouseButton=button;
	}
	
	@Override
	public int getID() {
		return ID;
	}


}
