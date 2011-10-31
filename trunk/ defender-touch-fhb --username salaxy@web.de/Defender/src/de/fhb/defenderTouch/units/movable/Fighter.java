package de.fhb.defenderTouch.units.movable;

import java.util.ArrayList;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.graphics.GraphicTools;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class Fighter extends BaseUnit {

//	private float movementSpeed=5f;
	
	public Fighter(int x, int y, int mode, int playerID, PApplet disp,DefenderControl gamelogic) {
		super(x, y, mode, playerID, disp, gamelogic);
		this.movementSpeed=5f;
	}

	
	public void drawFigure(PGraphics graphics){
		
//		graphics.stroke(0);
//		graphics.strokeWeight(10);
//		graphics.fill(0);
		this.entscheideLineFarbe(graphics);
		graphics.scale(2);
//		graphics.triangle(-20,+20, 0, -20, +20, +20);
		ArrayList<PVector> vektoren=new ArrayList<PVector>();
		vektoren.add(new PVector(0, -8));
		vektoren.add(new PVector(-8, 8));
		vektoren.add(new PVector(-4, 6));
		vektoren.add(new PVector(0,8));
		vektoren.add(new PVector(0, -8));
		vektoren.add(new PVector(8,8));
		vektoren.add(new PVector(4,6));
		vektoren.add(new PVector(0,8));
		

		GraphicTools.zeicheFigurNachVektoren(vektoren,graphics);
		
		graphics.resetMatrix();
		graphics.stroke(0);
		
	}
	
//	public void calcNewPosition(PApplet graphics){
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
