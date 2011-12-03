package de.fhb.defenderTouch.units.amunition;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.units.root.Unit;

public class RoundShoot extends ShootWithRange{

	public RoundShoot(int x, int y, int mode, Player player,Unit destinationUnit, int damage, DefenderControl gamelogic) {
		super(x, y, mode, player, destinationUnit, damage, gamelogic);
	}

	public void zeichneKugel(Graphics graphics){
		
		
		graphics.setColor(Color.black);
		graphics.scale(0.5f,0.5f);
	
		graphics.fillOval(-10, -10, 20, 20);
		
		graphics.resetTransform();
		
	}
	
	
	public void drawFigure(Graphics graphics){

		if(!shouldBeDelete){
			zeichneKugel(graphics);
		}
		
		if(shouldBeDelete&&nochXFramesZeichnen>0){
			
			smallExplosion.draw((-smallExplosion.getHeight() / 2) * player.getActualZoom(), (-smallExplosion.getWidth() / 2) * player.getActualZoom(), smallExplosion.getHeight()
					* player.getActualZoom(), smallExplosion.getWidth() * player.getActualZoom());
			graphics.resetTransform();
			System.out.println(1234);
			if (smallExplosion.getFrame() == gl.getNumberPictures() - 1) {
//				smallExplosionPlaying = false;
				
//				smallExplosion.stop();
			}
			
			
			graphics.resetTransform();
			this.nochXFramesZeichnen--;
		}
		
		if(shouldBeDelete&&nochXFramesZeichnen==0){
			this.deleteWirklich();
		}

	}	
	
	protected void drawTail(Player player,Graphics graphics){
		
	}
}
