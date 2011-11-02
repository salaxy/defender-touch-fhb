package de.fhb.defenderTouch.units.grounded;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.graphics.GraphicTools;

public class Defence extends Building{

	public static final int PRICE = 40;
	
	public Defence(int x, int y, int mode, int playerID, PApplet disp,DefenderControl gamelogic) {
		super(x, y, mode, playerID, disp, gamelogic);
	}

	
	
	public void drawFigure( PGraphics graphics){
		
		switch(this.level){
			case LEVEL_ONE:	//zeichne sooo;
				;
			break;
			case LEVEL_TWO: //dann zeichne soo;
				;
			break;
			case LEVEL_THREE://oder soooo;
				;
			break;
		}

//		this.entscheideLineFarbe(graphics);
		graphics.scale(2);
		graphics.rotate((float)Math.PI);
		ArrayList<PVector> vektoren=new ArrayList<PVector>();
		vektoren.add(new PVector(-4, -4));
		vektoren.add(new PVector(4, -4));
		vektoren.add(new PVector(0,-4));
		vektoren.add(new PVector(0,-8));
		vektoren.add(new PVector(0, 4));
		vektoren.add(new PVector(4,4));
		vektoren.add(new PVector(-4,4));
		
		
		graphics.noFill();
		graphics.ellipse(0,-4 , 8, 8);

		GraphicTools.zeicheFigurNachVektoren(vektoren,graphics);
		
		graphics.resetMatrix();
		graphics.stroke(0);
		
	}
	

}
