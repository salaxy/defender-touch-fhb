package de.fhb.defenderTouch.units.grounded;

import processing.core.PApplet;


public class Ground extends Building{

	
	public Ground(int x, int y, int mode, PApplet disp){
		super(x, y, mode, disp);
		// TODO Auto-generated constructor stub
	}

	public void drawFigure(){
		
//		display.stroke(0);
//		display.strokeWeight(10);
//		display.fill(0);
		display.noFill();
		display.rotate((float)Math.PI);
		this.entscheideLineFarbe();
		display.scale(4);
		display.triangle(-5,+5, 0, -5, +5, +5);
		
		display.resetMatrix();
		display.stroke(0);
		
	}
	
}
