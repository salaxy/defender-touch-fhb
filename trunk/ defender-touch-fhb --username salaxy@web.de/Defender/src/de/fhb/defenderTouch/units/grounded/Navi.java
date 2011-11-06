package de.fhb.defenderTouch.units.grounded;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.graphics.GraphicTools;

public class Navi extends Building{
	
	public static final int PRICE = 30;

	public Navi(int x, int y, int mode, int playerID,DefenderControl gamelogic) {
		super(x, y, mode, playerID, gamelogic);
	}
	
	
	public void drawFigure(PGraphics graphics){
		

//		this.entscheideLineFarbe( graphics);
		graphics.scale(1.5f);
		graphics.rotate((float)Math.PI);
		ArrayList<PVector> vektoren=new ArrayList<PVector>();
		vektoren.add(new PVector(0, 0));
		vektoren.add(new PVector(0,-8));
		vektoren.add(new PVector(-8,0));
		vektoren.add(new PVector(0, 0));
		vektoren.add(new PVector(0,4));
		vektoren.add(new PVector(4,4));

		GraphicTools.zeicheFigurNachVektoren(vektoren,graphics);
		
		graphics.resetMatrix();
		graphics.stroke(0);
		
	}
	


}
