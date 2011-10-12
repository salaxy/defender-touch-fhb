package de.fhb.defenderTouch.units.grounded;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class Defence extends Building{

	public Defence(int x, int y, int mode, PApplet disp){
		super(x, y, mode, disp);
		// TODO Auto-generated constructor stub
	}

	
	
	public void drawFigure(){
		

		this.entscheideLineFarbe();
		display.scale(4);
		display.rotate((float)Math.PI);
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

		this.zeicheFigurNachVektoren(vektoren);
		
		display.resetMatrix();
		display.stroke(0);
		
	}

}
