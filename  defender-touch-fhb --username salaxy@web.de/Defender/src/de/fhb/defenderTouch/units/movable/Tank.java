package de.fhb.defenderTouch.units.movable;

import org.newdawn.slick.Graphics;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.units.amunition.RoundShoot;
import de.fhb.defenderTouch.units.amunition.TankShoot;
import de.fhb.defenderTouch.units.root.BaseUnit;

public class Tank extends BaseUnit {

	public static final int PRICE = 80;
	protected int size = 0;

	public Tank(int x, int y, int mode, Player player, DefenderControl gamelogic) {
		super(x, y, mode, player, gamelogic);
	}

	public void drawFigure(Graphics graphics) {

		graphics.scale(1.0f, 1.0f);
		graphics.rotate(0, 0, 180);
		size = 8;

		// graphics.drawRect(0, size, size * 2, size * 3);
		// graphics.drawOval(0, size, size, size);
		// graphics.drawRect(0, -size/2, size/2, size * 2);

		// ketten
		graphics.drawRect(-12, -12, 2, 25);
		// chassi
		graphics.drawRect(-10, -15, 20, 30);
		// Ketten
		graphics.drawRect(10, -12, 2, 25);

		// Turm
		graphics.drawOval(-5, -10, 10, 10);
		graphics.drawRect(-2, -8, 4, 30);

		graphics.resetTransform();

	}

	protected void startShoot(BaseUnit destinationUnit) {
		new TankShoot((int) this.position.x, (int) this.position.y, BaseUnit.MODE_NORMAL, this.gamelogic.getPlayerSystem(), destinationUnit, this.damagePerHit, gamelogic);

	}

}
