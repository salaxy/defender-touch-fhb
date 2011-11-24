package de.fhb.defenderTouch.units.amunition;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import processing.core.PVector;
import de.fhb.defenderTouch.audio.FormatProblemException;
import de.fhb.defenderTouch.audio.SampleThread;
import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.units.root.BaseUnit;

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
	
	public Shoot(int x, int y, int mode, Player player, BaseUnit destinationUnit, int damage, DefenderControl gamelogic){
		super(x, y, mode, player, gamelogic);
		
		//Einheit ist sehr schnell
		this.movementSpeed=5f;
		
		this.damage=damage;
		this.destinationUnit=destinationUnit;
		this.commandDestination(destinationUnit.getPosition());
		
	}
	
	
	public void drawFigure(Graphics graphics){
		
	//	graphics.stroke(0);
	//	graphics.strokeWeight(10);
	//	graphics.fill(0);
//		this.entscheideLineFarbe(graphics);
		graphics.setColor(Color.black);
		graphics.scale(0.5f,0.5f);
	
		graphics.fillRect(0, 0, 10, 30);
	
	//	GraphicTools.zeicheFigurNachVektoren(vektoren,graphics);
		
		graphics.resetTransform();
//		graphics.stroke(0);
		
	}
	
	/**
	 * Berechnen des neuen Position, wenn in Bewegung
	 */
	public void update(){
		
		this.commandDestination(destinationUnit.getPosition());


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
		
		this.playDestroySound();
		this.destinationUnit.getDamage(damage);
		this.delete();
	}
	
	
	private void playDestroySound(){
		try {
			new SampleThread("/sounds/boom_kurz.mp3",10.0f,true).start();
		} catch (FormatProblemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}