package de.fhb.defenderTouch.units.grounded;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class Ground extends Building{

	
	public Ground(int x, int y, int mode) {
		super(x, y, mode);
		// TODO Auto-generated constructor stub
	}

	public void drawFigure(PApplet pa){
		
//		pa.stroke(0);
//		pa.strokeWeight(10);
//		pa.fill(0);
		this.entscheideLineFarbe(pa);
		pa.scale(2);
//		pa.triangle(-20,+20, 0, -20, +20, +20);
		ArrayList<PVector> vektoren=new ArrayList<PVector>();
		vektoren.add(new PVector(0, -8));
		vektoren.add(new PVector(-8, 8));
		vektoren.add(new PVector(-4, 6));
		vektoren.add(new PVector(0,8));
		vektoren.add(new PVector(0, -8));
		vektoren.add(new PVector(8,8));
		vektoren.add(new PVector(4,6));
		vektoren.add(new PVector(0,8));
		

		this.zeicheFigurNachVektoren(vektoren, pa);
		
		pa.resetMatrix();
		pa.stroke(0);
		
	}
	
	
	
	
	
	
	
	
}
