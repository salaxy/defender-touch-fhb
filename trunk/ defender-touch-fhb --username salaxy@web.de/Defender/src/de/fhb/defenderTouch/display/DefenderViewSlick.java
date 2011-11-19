package de.fhb.defenderTouch.display;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.ui.Gestures;
import de.fhb.defenderTouch.units.movable.Fighter;
import de.fhb.defenderTouch.units.root.BaseUnit;

public class DefenderViewSlick extends BasicGame {


    

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

    	
  	  //Testflugstaffel
  	  new Fighter(100,50,BaseUnit.MODE_NORMAL,DefenderControl.PLAYER_TWO,control);
  	  new Fighter(200,50,BaseUnit.MODE_NORMAL,DefenderControl.PLAYER_TWO,control);
  	  new Fighter(300,50,BaseUnit.MODE_NORMAL,DefenderControl.PLAYER_TWO,control);
  	  new Fighter(400,50,BaseUnit.MODE_NORMAL,DefenderControl.PLAYER_TWO,control);
  	  new Fighter(500,50,BaseUnit.MODE_NORMAL,DefenderControl.PLAYER_TWO,control);	  
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

    	g.setColor(Color.red);
    	g.fillRect(0, 0, 20, 20);
    	g.drawRect(0, 0, 20, 20);
    	
    	


    }

   

    public static void main(String[] args) throws SlickException {

          AppGameContainer pong = new AppGameContainer(new DefenderViewSlick());

          pong.setDisplayMode(WIDTH, HEIGHT, false);

          pong.setVSync(true);

          pong.setShowFPS(false);

          pong.start();

    }

	
}
