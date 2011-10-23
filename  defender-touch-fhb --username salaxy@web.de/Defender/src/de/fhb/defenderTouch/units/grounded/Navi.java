package de.fhb.defenderTouch.units.grounded;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import de.fhb.defenderTouch.graphics.GraphicTools;

public class Navi extends Building{

	public Navi(int x, int y, int mode, int playerID, PApplet disp){
		super(x, y, mode, playerID, disp);
		// TODO Auto-generated constructor stub
	}
	
	
	public void drawFigure(){
		

		this.entscheideLineFarbe();
		display.scale(4);
		display.rotate((float)Math.PI);
		ArrayList<PVector> vektoren=new ArrayList<PVector>();
		vektoren.add(new PVector(0, 0));
		vektoren.add(new PVector(0,-8));
		vektoren.add(new PVector(-8,0));
		vektoren.add(new PVector(0, 0));
		vektoren.add(new PVector(0,4));
		vektoren.add(new PVector(4,4));

		GraphicTools.zeicheFigurNachVektoren(vektoren,display);
		
		display.resetMatrix();
		display.stroke(0);
		
	}

}
