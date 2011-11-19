package de.fhb.defenderTouch.display;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class DefenderViewSlick extends BasicGame {


    

    public static final int WIDTH = 400;

    public static final int HEIGHT = 300;

   

    public DefenderViewSlick() {

          super("Pong");

    }



    public void init(GameContainer gc) throws SlickException {



    }



    public void update(GameContainer gc, int delta) throws SlickException {

          // Abfangen der Eingabegaräte

          Input input = gc.getInput();

    }



    public void render(GameContainer gc, Graphics g) throws SlickException {



    }

   

    public static void main(String[] args) throws SlickException {

          AppGameContainer pong = new AppGameContainer(new DefenderViewSlick());

          pong.setDisplayMode(WIDTH, HEIGHT, false);

          pong.setVSync(true);

          pong.setShowFPS(false);

          pong.start();

    }

	
}
