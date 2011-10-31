package de.fhb.defenderTouch.units.movable;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import de.fhb.defenderTouch.gamelogic.DefenderControl;

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
	private int damage;
	
	public Shoot(int x, int y, int mode, int playerID, PApplet disp, BaseUnit destinationUnit, int damage, DefenderControl gamelogic){
		super(x, y, mode, playerID, disp, gamelogic);
		
		//Einheit ist sehr schnell
		this.movementSpeed=5f;
		
		this.damage=damage;
		this.destinationUnit=destinationUnit;
		this.commandDestination(destinationUnit.position);
		
	}
	
	
	public void drawFigure(PGraphics graphics){
		
	//	graphics.stroke(0);
	//	graphics.strokeWeight(10);
	//	graphics.fill(0);
		this.entscheideLineFarbe(graphics);
		graphics.scale(0.5f);
	
		graphics.rect(0, 0, 10, 30);
	
	//	GraphicTools.zeicheFigurNachVektoren(vektoren,graphics);
		
		graphics.resetMatrix();
		graphics.stroke(0);
		
	}
	
	/**
	 * Berechnen des neuen Position, wenn in Bewegung
	 */
	public void calcNewPosition(){
		
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
				
				this.isMoving=true;
//			}

		}else{
			//Ziel erreicht
			hasReachedDestination();
		}
	}
	
	
	private void hasReachedDestination(){
		this.destinationUnit.getDamage(damage);
		this.delete();
	}
	

}