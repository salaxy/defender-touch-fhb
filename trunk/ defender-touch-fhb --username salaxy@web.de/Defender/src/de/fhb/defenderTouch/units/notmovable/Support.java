package de.fhb.defenderTouch.units.notmovable;

import java.util.Date;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.units.root.Building;

public class Support extends Building {

	public static final int PRICE = 50;
	protected int size = 18;

	private int timeTillNextIncome = 1000;

	private long startingTime = new Date().getTime();
	private long tickerTime;

	private int creditsPerTime = 1;

	public Support(int x, int y, int mode, Player player, DefenderControl gamelogic) {
		super(x, y, mode, player, gamelogic);
		maximumHealth = 150;
		actualHealth = maximumHealth;
	}

	public void drawFigure(Graphics graphics) {
		graphics.scale(1.0f, 1.0f);

		graphics.drawRect(-15, -14, 30, 30);
		Image image = null;
		try {
			image = new Image("data/buildings/Support.png");
			image = image.getScaledCopy(size, size);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		graphics.drawImage(image, -image.getHeight() / 2, -image.getWidth() / 2, size, size, 0f, 0f);
		
		graphics.resetTransform();
	}

	public void update() {
		tickerTime = new Date().getTime();

		if (getNewIncome(startingTime, tickerTime)) {
			if (this.playerID == DefenderControl.PLAYER_ONE_ID) {
				gamelogic.getPlayerOne().addCredits(this.creditsPerTime);
			}
			if (this.playerID == DefenderControl.PLAYER_TWO_ID) {
				gamelogic.getPlayerTwo().addCredits(this.creditsPerTime);
			}
		}
	}

	public boolean getNewIncome(long startingTime, long tickerTime) {
		long helpTime = tickerTime - startingTime;
		if (helpTime >= timeTillNextIncome) {
			this.startingTime = new Date().getTime();
			return true;
		}
		return false;
	}

	public void upgrade() {
		super.upgrade();

		switch (this.level) {
		case LEVEL_ONE:
			size = 18;
			break;
		case LEVEL_TWO:
			size = 20;
			creditsPerTime += 1;
			break;
		case LEVEL_THREE:
			size = 22;
			creditsPerTime += 1;
			break;
		}
	}
}
