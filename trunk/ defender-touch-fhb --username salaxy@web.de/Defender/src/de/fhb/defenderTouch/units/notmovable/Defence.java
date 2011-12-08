package de.fhb.defenderTouch.units.notmovable;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.units.root.Building;

public class Defence extends Building {

	public static final int PRICE = 40;
	protected int size = 18;

	public Defence(int x, int y, int mode, Player player, DefenderControl gamelogic) {
		super(x, y, mode, player, gamelogic);
		damagePerHit = 50;
		maximumHealth = 250;
		actualHealth = maximumHealth;
		attackRange = 350;
	}

	public void drawFigure(Graphics graphics) {
		graphics.scale(1.0f, 1.0f);

		graphics.drawRect(-15, -17, 30, 30);
		Image image = null;
		try {
			image = new Image("data/buildings/Defence.png");
			image = image.getScaledCopy(size, size);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		graphics.drawImage(image, -image.getHeight() / 2, -image.getWidth() / 2, size, size, 0f, 0f);
		
		graphics.resetTransform();
	}

	public void update() {
		// AutoAngriffs Algorithmus aufrufen wenn moeglich
		if (isAutoAttackOn && isAttackPossible()) {
			autoAttack();
		}
	}

	public void upgrade() {
		super.upgrade();

		switch (this.level) {
		case LEVEL_ONE:
			size = 18;
			break;
		case LEVEL_TWO:
			size = 20;
			damagePerHit += 20;
			break;
		case LEVEL_THREE:
			size = 22;
			damagePerHit += 20;
			break;
		}
	}
}
