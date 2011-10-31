package de.fhb.defenderTouch.units.grounded;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import de.fhb.defenderTouch.graphics.GraphicTools;

public class Defence extends Building{

	public static final int PRICE = 40;
	
	public Defence(int x, int y, int mode, int playerID, PApplet disp){
		super(x, y, mode, playerID, disp);
		// TODO Auto-generated constructor stub
	}

	
	
	public void drawFigure(){
		
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

		this.entscheideLineFarbe();
		display.scale(2);
//		display.rotate((float)Math.PI);
		ArrayList<PVector> vektoren=new ArrayList<PVector>();
		vektoren.add(new PVector(-4, -4));
		vektoren.add(new PVector(4, -4));
		vektoren.add(new PVector(0,-4));
		vektoren.add(new PVector(0,-8));
		vektoren.add(new PVector(0, 4));
		vektoren.add(new PVector(4,4));
		vektoren.add(new PVector(-4,4));
		
		
		display.noFill();
		display.ellipse(0,-4 , 8, 8);

		GraphicTools.zeicheFigurNachVektoren(vektoren,display);
		
		display.resetMatrix();
		display.stroke(0);
		
	}

}
