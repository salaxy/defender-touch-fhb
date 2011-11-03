package de.fhb.defenderTouch.gamelogic;

import processing.core.PGraphics;
import processing.core.PVector;
import de.fhb.defenderTouch.graphics.GraphicTools;

public class Gamemap {

	
	
	
	
	
	
	
	
	
	public void zeichne(PGraphics graphics, Player player){
		
		
		//Transformationen
		GraphicTools.calcDrawTransformation(player, graphics, new PVector(0,0));
	
		//Feldumrandung zeichnen
		graphics.fill(255, 255, 0,55);
		graphics.stroke(0, 0, 0);
		graphics.rect(512f, 384f, 1024, 768);
		
		graphics.resetMatrix();
		
	}
	
	
	
	public int getType(PVector mapVector){
		
		
		return 1;
	}
	
	
}
