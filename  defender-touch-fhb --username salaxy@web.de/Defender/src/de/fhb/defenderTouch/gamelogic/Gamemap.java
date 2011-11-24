package de.fhb.defenderTouch.gamelogic;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import processing.core.PImage;
import processing.core.PVector;
import de.fhb.defenderTouch.graphics.GraphicTools;

public class Gamemap {

	private Image visibleMap;
	private Image informationalMap;
	
	public Gamemap() {

	}	
	
	public void zeichne(Graphics graphics, Player player){
		//Transformationen
		GraphicTools.calcDrawTransformationForSlick(player, graphics, new PVector(0,0));
	
		// für Spieler sichtbare Karte zeichnen
		visibleMap.draw();
		
		//Feldumrandung zeichnen
		//graphics.setColor(new Color(50, 50, 50,55));
		//graphics.fillRect(0f, 0f, 1024, 768);
		
		graphics.resetTransform();		
	}
	
	public boolean isFlyable(PVector position) {
	
		return (position.x > 1f &&
				position.y < informationalMap.getHeight() - 1f &&
				position.y > 1f &&
				position.y < informationalMap.getWidth() - 1f) ?
				true :
				false;
	
	}
	
	public boolean isWalkable(PVector position) {

		return informationalMap.getColor((int)position.x, (int)position.y) == Color.black ? false : true;
	
	}
	
	public boolean isBuildable(PVector upperLeft, PVector lowerRight, int Player) {

		for (int x = (int) upperLeft.x; x < (int) lowerRight.x; x++)
			for (int y = (int) upperLeft.y; y < (int) lowerRight.y; x++) {
		
				if (Player == DefenderControl.PLAYER_ONE_ID)
					if (informationalMap.getColor(x, y).r < 1f) return false;
				if (Player == DefenderControl.PLAYER_TWO_ID)
					if (informationalMap.getColor(x, y).g < 1f) return false;
				if (Player == DefenderControl.PLAYER_SYSTEM_ID)
					if (informationalMap.getColor(x, y).b < 1f) return false;
				
			}				
		
		return true;
	}
}
