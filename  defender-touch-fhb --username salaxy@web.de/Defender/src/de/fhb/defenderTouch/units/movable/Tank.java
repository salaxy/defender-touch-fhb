package de.fhb.defenderTouch.units.movable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.units.amunition.ShootWithRange;
import de.fhb.defenderTouch.units.root.Unit;

public class Tank extends Unit {

	protected int size = 20;

	public Tank(int x, int y, int mode, Player player, DefenderControl gamelogic) {
		super(x, y, mode, player, gamelogic);
		damagePerHit = 30;
		attackRange = 250;
		maximumHealth = 400;
		actualHealth = maximumHealth;
		movementSpeed = 1.5f;
	}

	public void drawFigure(Graphics graphics) {

		graphics.scale(1.0f, 1.0f);
		graphics.drawOval(-15, -17, 30, 30);
		
		Image image = null;
		try {
			image = new Image("data/units/Tank.png");
			image = image.getScaledCopy(size, size);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		graphics.drawImage(image, -image.getHeight() / 2, -image.getWidth() / 2, size, size, 0f, 0f);
		
		int help = (int) ((255f * (float) actualHealth) / (float) maximumHealth);
		graphics.setColor(new Color(255 - help, help, 0));
		graphics.fillRect(20, -25, -40, 3);

		graphics.resetTransform();
	}

	protected void startShoot(Unit destinationUnit) {
		new ShootWithRange((int) this.position.x, (int) this.position.y, Unit.MODE_NORMAL, this.gamelogic.getPlayerSystem(),
				destinationUnit, this.damagePerHit, gamelogic);
	}

	protected void drawTail(Player player, Graphics graphics) {
		// tail aus
	}
}
