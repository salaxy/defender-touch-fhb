package de.fhb.defenderTouch.units.amunition;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.units.root.Unit;

public class LinearShoot extends ShootWithRange{

	public LinearShoot(int x, int y, int mode, Player player,
			Unit destinationUnit, int damage, DefenderControl gamelogic) {
		super(x, y, mode, player, destinationUnit, damage, gamelogic);
		// TODO Auto-generated constructor stub
	}
}
