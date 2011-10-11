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
		pa.noFill();
		pa.rotate((float)Math.PI);
		this.entscheideLineFarbe(pa);
		pa.scale(4);
		pa.triangle(-5,+5, 0, -5, +5, +5);
		
		pa.resetMatrix();
		pa.stroke(0);
		
	}
	
}
