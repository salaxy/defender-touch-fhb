package de.fhb.defenderTouch.gamelogic;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import processing.core.PImage;
import processing.core.PVector;
import de.fhb.defenderTouch.graphics.GraphicTools;
import de.fhb.defenderTouch.units.root.Building;

public class Gamemap {

	private Image visibleMap;
	private Image informationalMap;
	
	public Gamemap() {

//		try {
//			//visibleMap = new Image("./maps/vtestmap.png");
//			informationalMap = new Image("./maps/itestmap.png");
//		} catch (SlickException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}	
	
	public void zeichne(Graphics graphics, Player player){
		//Transformationen
		GraphicTools.calcDrawTransformationForSlick(player, graphics, new PVector(0,0));
	
		// für Spieler sichtbare Karte zeichnen
		
		// TODO Frank machen tun
//		visibleMap.draw();
		
		//Feldumrandung zeichnen
		graphics.setColor(new Color(255, 255, 0,55));
		graphics.fillRect(0f, 0f, 1024, 768);
		
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
	
	public boolean isBuildable(float x1, float y1, float x2, float y2, int player) {

		for (int x = (int) x1; x < (int) x2; x++)
			for (int y = (int) y1; y < (int) y2; x++) {
		
				if (player == DefenderControl.PLAYER_ONE_ID)
					if (informationalMap.getColor(x, y).r < 1f) return false;
				if (player == DefenderControl.PLAYER_TWO_ID)
					if (informationalMap.getColor(x, y).g < 1f) return false;
				if (player == DefenderControl.PLAYER_SYSTEM_ID)
					if (informationalMap.getColor(x, y).b < 1f) return false;
				
			}				
		
		return true;
	}
	
	public boolean isBuildable(PVector position, Building unit, int player) {
		return isBuildable(position.x - (1 + unit.LEVEL_THREE), position.y - (1 + unit.LEVEL_THREE), position.x + unit.LEVEL_THREE, position.x + unit.LEVEL_THREE, player);
	}
}
