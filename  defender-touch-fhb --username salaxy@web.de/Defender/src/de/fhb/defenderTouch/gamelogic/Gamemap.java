package de.fhb.defenderTouch.gamelogic;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import processing.core.PImage;
import processing.core.PVector;
import de.fhb.defenderTouch.graphics.GraphicTools;

public class Gamemap {

	private PImage visibleMap;
	private PImage informationalMap;
	
	public Gamemap() {
//		visibleMap = display.loadImage("./maps/vtestmap.png");
//		informationalMap = display.loadImage("./maps/itestmap.png");
	}
	
	
	
	
	
	
	
	public void zeichne(Graphics graphics, Player player){
		
		
		//Transformationen
		GraphicTools.calcDrawTransformationForSlick(player, graphics, new PVector(0,0));
	
		// für Spieler sichtbare Karte zeichnen
		//graphics.image(visibleMap, 0f, 0f);
		
		//Feldumrandung zeichnen
//		graphics.setColor(new Color(255, 255, 0,55));
		graphics.setColor(new Color(50, 50, 50,55));
//		graphics.stroke(0, 0, 0);
//		graphics.fillRect(512f, 384f, 1024, 768);
		graphics.fillRect(0f, 0f, 1024, 768);
		
		graphics.resetTransform();
		
	}
	
	
	
	
}
