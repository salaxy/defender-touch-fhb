package de.fhb.defenderTouch.gamelogic;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;
import de.fhb.defenderTouch.graphics.GraphicTools;

public class Gamemap {

	private PImage visibleMap;
	private PImage informationalMap;
	
	public Gamemap(PApplet display) {
		visibleMap = display.loadImage("./maps/vtestmap.png");
		informationalMap = display.loadImage("./maps/itestmap.png");
	}
	
	
	
	
	
	
	
	public void zeichne(PGraphics graphics, Player player){
		
		
		//Transformationen
		GraphicTools.calcDrawTransformation(player, graphics, new PVector(0,0));
	
		// für Spieler sichtbare Karte zeichnen
		graphics.image(visibleMap, 0f, 0f);
		
		//Feldumrandung zeichnen
		//graphics.fill(255, 255, 0,55);
		//graphics.stroke(0, 0, 0);
		//graphics.rect(512f, 384f, 1024, 768);
		
		graphics.resetMatrix();
		
	}
	
	
	
	
}
