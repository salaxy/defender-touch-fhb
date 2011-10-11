package de.fhb.defenderTouch.units.grounded;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class Navi extends Building{

	public Navi(int x, int y, int mode) {
		super(x, y, mode);
		// TODO Auto-generated constructor stub
	}
	
	
	public void drawFigure(PApplet pa){
		

		this.entscheideLineFarbe(pa);
		pa.scale(4);
		pa.rotate((float)Math.PI);
		ArrayList<PVector> vektoren=new ArrayList<PVector>();
		vektoren.add(new PVector(0, 0));
//		vektoren.add(new PVector(0, 0));
		vektoren.add(new PVector(0,-8));
		vektoren.add(new PVector(-8,0));
		vektoren.add(new PVector(0, 0));
		vektoren.add(new PVector(0,4));
		vektoren.add(new PVector(4,4));
		
//		vektoren.add(new PVector(8,8));
//		vektoren.add(new PVector(4,6));
//		vektoren.add(new PVector(0,8));
		

		this.zeicheFigurNachVektoren(vektoren, pa);
		
		pa.resetMatrix();
		pa.stroke(0);
		
	}

}
