package de.fhb.defenderTouch.units.movable;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.graphics.GraphicTools;
import de.fhb.defenderTouch.units.root.BaseUnit;

public class Fighter extends BaseUnit {

	// private float movementSpeed=5f;

	public Fighter(int x, int y, int mode, Player player, DefenderControl gamelogic) {
		super(x, y, mode, player, gamelogic);
		this.movementSpeed = 5f;
	}

	public void drawFigure(Graphics graphics) {

		// graphics.stroke(0);
		// graphics.strokeWeight(10);
		// graphics.fill(0);
		// this.entscheideLineFarbe(graphics);
		graphics.scale(2, 2);
		// graphics.triangle(-20,+20, 0, -20, +20, +20);
		ArrayList<Vector2f> vektoren = new ArrayList<Vector2f>();
		vektoren.add(new Vector2f(0, -8));
		vektoren.add(new Vector2f(-8, 8));
		vektoren.add(new Vector2f(-4, 6));
		vektoren.add(new Vector2f(0, 8));
		vektoren.add(new Vector2f(0, -8));
		vektoren.add(new Vector2f(8, 8));
		vektoren.add(new Vector2f(4, 6));
		vektoren.add(new Vector2f(0, 8));

		GraphicTools.zeicheFigurNachVektoren(vektoren, graphics);

		graphics.resetTransform();
		// graphics.stroke(0);

	}

	// public void calcNewPosition(PApplet graphics){
	// //wenn aktuelle position nicht nahe herankommt an ziel, dann weiter
	// bewegen
	// if(position.dist(destinationVector)>3){
	// //Richtugnsvector berechnen
	// direction=position.sub( destinationVector, position);
	// //richtungsvector normieren
	// direction.normalize();
	// //normierten Richtungsvector zur position hinzurechnen
	// position.add(direction.mult(direction, movementSpeed));
	// //zeichne Schweif
	// drawTail();
	// }
	// }
	//

}
