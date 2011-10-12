package de.fhb.defenderTouch.units.grounded;

import processing.core.PApplet;
import de.fhb.defenderTouch.units.movable.BaseUnit;

public class Building extends BaseUnit{

//	private float movementSpeed=0.5f;
	
	public Building(int x, int y, int mode, PApplet disp){
		super(x, y, mode, disp);
		this.movementSpeed=0.5f;
		// TODO Auto-generated constructor stub
	}
	
	
//	public void calcNewPosition(){
//		//wenn aktuelle position nicht nahe herankommt an ziel, dann weiter bewegen
//		if(position.dist(destinationVector)>3){
//			//Richtugnsvector berechnen
//			direction=position.sub( destinationVector, position);
//			//richtungsvector normieren
//			direction.normalize();
//			//normierten Richtungsvector zur position hinzurechnen
//			position.add(direction.mult(direction, movementSpeed));
//			//zeichne Schweif
//			drawTail(direction, pa);
//		}
//	}

}
