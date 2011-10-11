package de.fhb.defenderTouch.units.grounded;

import processing.core.PApplet;
import de.fhb.defenderTouch.units.movable.TestUnit;

public class Building extends TestUnit{

	private float movementSpeed=0.5f;
	
	public Building(int x, int y, int mode) {
		super(x, y, mode);
		// TODO Auto-generated constructor stub
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
