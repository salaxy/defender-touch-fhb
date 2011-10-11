package de.fhb.defenderTouch.units.movable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public class Fighter extends TestUnit {

	private float movementSpeed=5f;
	
	public Fighter(int x, int y, int mode) {
		super(x, y, mode);
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
	
	public void calcNewPosition(PApplet pa){
		//wenn aktuelle position nicht nahe herankommt an ziel, dann weiter bewegen
		if(position.dist(destinationVector)>3){
			//Richtugnsvector berechnen
			direction=position.sub( destinationVector, position);
			//richtungsvector normieren
			direction.normalize();
			//normierten Richtungsvector zur position hinzurechnen
			position.add(direction.mult(direction, movementSpeed));
			//zeichne Schweif
			drawTail(direction, pa);
		}
	}
	

}
