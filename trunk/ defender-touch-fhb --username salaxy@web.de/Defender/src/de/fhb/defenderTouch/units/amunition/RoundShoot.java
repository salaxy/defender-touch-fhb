package de.fhb.defenderTouch.units.amunition;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.units.root.BaseUnit;

public class RoundShoot extends ShootWithRange{

	public RoundShoot(int x, int y, int mode, Player player,BaseUnit destinationUnit, int damage, DefenderControl gamelogic) {
		super(x, y, mode, player, destinationUnit, damage, gamelogic);
	}

	public void drawFigure(Graphics graphics){
		
		
		graphics.setColor(Color.black);
		graphics.scale(0.5f,0.5f);
	
		graphics.fillOval(-10, -10, 20, 20);
		
		graphics.resetTransform();
		
	}
	
	protected void drawTail(Player player,Graphics graphics){
		
	}
}
