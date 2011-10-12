package de.fhb.defenderTouch.units.movable;

import java.util.ArrayList;

import de.fhb.defenderTouch.graphics.GraphicTools;

import processing.core.PApplet;
import processing.core.PVector;

public class Fighter extends BaseUnit {

//	private float movementSpeed=5f;
	
	public Fighter(int x, int y, int mode, PApplet disp){
		super(x, y, mode, disp);
		this.movementSpeed=5f;
		// TODO Auto-generated constructor stub
	}

	
	public void drawFigure(){
		
//		display.stroke(0);
//		display.strokeWeight(10);
//		display.fill(0);
		this.entscheideLineFarbe();
		display.scale(2);
//		display.triangle(-20,+20, 0, -20, +20, +20);
		ArrayList<PVector> vektoren=new ArrayList<PVector>();
		vektoren.add(new PVector(0, -8));
		vektoren.add(new PVector(-8, 8));
		vektoren.add(new PVector(-4, 6));
		vektoren.add(new PVector(0,8));
		vektoren.add(new PVector(0, -8));
		vektoren.add(new PVector(8,8));
		vektoren.add(new PVector(4,6));
		vektoren.add(new PVector(0,8));
		

		GraphicTools.zeicheFigurNachVektoren(vektoren,display);
		
		display.resetMatrix();
		display.stroke(0);
		
	}
	
//	public void calcNewPosition(PApplet display){
//		//wenn aktuelle position nicht nahe herankommt an ziel, dann weiter bewegen
//		if(position.dist(destinationVector)>3){
//			//Richtugnsvector berechnen
//			direction=position.sub( destinationVector, position);
//			//richtungsvector normieren
//			direction.normalize();
//			//normierten Richtungsvector zur position hinzurechnen
//			position.add(direction.mult(direction, movementSpeed));
//			//zeichne Schweif
//			drawTail();
//		}
//	}
//	

}
