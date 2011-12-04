package de.fhb.defenderTouch.start;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhb.defenderTouch.display.DefenderViewSlick;

public class StartGame extends StateBasedGame {

	/**
	 *  Konstruktor
	 */
	public StartGame() {
		super("Defender Touch Deluxe ALPHA v.0.0.1");
	}

	/**
	 * Hier werden die GameStates bekannt gemacht.
	 */
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		addState(new DefenderViewSlick());

	}

	/**
	 * Main-Methode
	 * 
	 * @param args
	 * @throws SlickException 
	 */
	public static void main(String[] args) throws SlickException {
		
        AppGameContainer defender = new AppGameContainer(new StartGame());
        defender.setDisplayMode(DefenderViewSlick.WIDTH, DefenderViewSlick.HEIGHT, false);
        defender.setVSync(true);
        defender.setShowFPS(false);
        defender.setAlwaysRender(true);
        defender.setIcon("data/logos/DefenderTouchDeluxe32.png");
        defender.start();

	}

}
