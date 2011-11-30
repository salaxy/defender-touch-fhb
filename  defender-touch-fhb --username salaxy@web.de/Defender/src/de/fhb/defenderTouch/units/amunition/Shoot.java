package de.fhb.defenderTouch.units.amunition;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import de.fhb.defenderTouch.audio.FormatProblemException;
import de.fhb.defenderTouch.audio.SampleThread;
import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.graphics.VectorHelper;
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
	protected  BaseUnit destinationUnit;
	protected int damage;
	
	
	public Shoot(int x, int y, int mode, Player player, BaseUnit destinationUnit, int damage, DefenderControl gamelogic){
		super(x, y, mode, player, gamelogic);
		
		//Einheit ist sehr schnell
		this.movementSpeed=5f;
		
		this.damage=damage;
		this.destinationUnit=destinationUnit;
		this.commandDestination(destinationUnit.getPosition());
		
	}
	
	
	public void drawFigure(Graphics graphics){
		

		graphics.setColor(Color.black);
		graphics.scale(0.5f,0.5f);
	
		graphics.fillRect(0, 0, 10, 30);
		
		graphics.resetTransform();
		
	}
	
	/**
	 * Berechnen des neuen Position, wenn in Bewegung
	 */
	public void update(){
		
		this.commandDestination(destinationUnit.getPosition());


		Vector2f newPosition;
		
		//wenn aktuelle position noch weit weg vom ziel, dann weiter bewegen
		if(position.distance(destinationVector)>3){
			
			//neue Position erechnen, normierten Richtungsvector zur position hinzurechnen
			newPosition=VectorHelper.add(this.position, VectorHelper.mult(direction, movementSpeed));
			
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
		
//		this.playDestroySound();
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