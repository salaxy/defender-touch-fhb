package de.fhb.defenderTouch.units.amunition;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.units.root.BaseUnit;

public class TankShoot extends Shoot {

	
	public TankShoot(int x, int y, int modeNormal, Player playerSystem, BaseUnit destinationUnit, int damagePerHit, DefenderControl gamelogic) {
		super(x, y, modeNormal, playerSystem, destinationUnit, damagePerHit, gamelogic);
	}

	public void drawFigure(Graphics graphics) {

		graphics.setColor(Color.black);
		graphics.scale(0.5f, 0.5f);

		graphics.fillRect(0, 0, 10, 30);

		graphics.resetTransform();

	}
}
