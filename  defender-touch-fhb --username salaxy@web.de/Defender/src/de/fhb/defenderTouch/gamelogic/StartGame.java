package de.fhb.defenderTouch.gamelogic;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhb.defenderTouch.display.DefenderViewSlick;

public class StartGame extends StateBasedGame {

	public StartGame() {
		super("DEFENDER ALPHA");
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		addState(new DefenderViewSlick());

	}

	/**
	 * @param args
	 * @throws SlickException 
	 */
	public static void main(String[] args) throws SlickException {
		
        AppGameContainer defender = new AppGameContainer(new StartGame());
        defender.setDisplayMode(DefenderViewSlick.WIDTH, DefenderViewSlick.HEIGHT, false);
        defender.setVSync(true);
        defender.setShowFPS(false);
        defender.start();

	}

}
