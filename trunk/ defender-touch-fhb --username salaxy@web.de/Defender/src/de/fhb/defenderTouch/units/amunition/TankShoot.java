package de.fhb.defenderTouch.units.amunition;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.units.root.BaseUnit;

public class TankShoot extends Shoot {

	public TankShoot(int x, int y, int modeNormal, Player playerSystem, BaseUnit destinationUnit, int damagePerHit, DefenderControl gamelogic) {
		super(x, y, modeNormal, playerSystem, destinationUnit, damagePerHit, gamelogic);
	}

	public void drawFigure(Graphics graphics) {

		graphics.setColor(Color.black);
		Image image = null;
		int size = 5;
		try {
			image = new Image("data/shots/Tank.png");
			// image = image.getScaledCopy(size, size);
			image.setRotation(actualAngle + 90);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		graphics.drawImage(image, -image.getHeight() - 20, -image.getWidth() / 2, size, size, 0f, 0f);
		graphics.resetTransform();

	}
}
