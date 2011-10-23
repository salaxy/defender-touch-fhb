package de.fhb.defenderTouch.units.movable;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import de.fhb.defenderTouch.graphics.GraphicTools;

/**
 * Stellt eine Einfache Animation eines Schusses einer Einheit dar
 * Ein Schuss wird auch erstmal wie eine Unit behandelt!!
 * eine spezielle Unit
 * 
 * @author Salaxy
 *
 */
public class Shoot extends BaseUnit {
	
	
	//	private float movementSpeed=5f;
	private  BaseUnit destinationUnit;
	
	public Shoot(int x, int y, int mode, int playerID, PApplet disp, BaseUnit destinationUnit){
		super(x, y, mode, playerID, disp);
		
		//Einheit ist sehr schnell
		this.movementSpeed=5f;
		this.destinationUnit=destinationUnit;
		this.commandDestination(destinationUnit.position);
		
	}
	
	
	public void drawFigure(){
		
	//	display.stroke(0);
	//	display.strokeWeight(10);
	//	display.fill(0);
		this.entscheideLineFarbe();
		display.scale(0.5f);
	
		display.rect(0, 0, 10, 30);
	
	//	GraphicTools.zeicheFigurNachVektoren(vektoren,display);
		
		display.resetMatrix();
		display.stroke(0);
		
	}
	
	/**
	 * Berechnen des neuen Position, wenn in Bewegung
	 */
	protected void calcNewPosition(){
		
		this.commandDestination(destinationUnit.position);


		PVector newPosition;
		
		//wenn aktuelle position noch weit weg vom ziel, dann weiter bewegen
		if(position.dist(destinationVector)>3){
			
			//neue Position erechnen, normierten Richtungsvector zur position hinzurechnen
			newPosition=PVector.add(this.position, PVector.mult(direction, movementSpeed));
			
//			//wenn keien kollision dann bewegen
//			if(!isCollision(newPosition)){
//
				//neue Position setzen
				this.position=newPosition;
				//zeichne Schweif
				drawTail();				
//			}

		}else{
			//Ziel erreicht
			hasReachedDestination();
		}
	}
	
	
	private void hasReachedDestination(){
		
		
		
	}
	

}