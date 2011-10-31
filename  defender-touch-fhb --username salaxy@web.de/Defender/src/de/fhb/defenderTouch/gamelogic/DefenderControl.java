package de.fhb.defenderTouch.gamelogic;

import java.util.concurrent.CopyOnWriteArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import de.fhb.defenderTouch.units.movable.BaseUnit;

/**
 * Diese Klasse stellt die Verbindung zwischen Anzeige, also dem PApplet dar und
 * dem eigentlichen Spielinhalt wie die Units, Spiellogik usw. (und noch
 * zukünftige dinge)
 * 
 * @author Salaxy // * @deprecated Funktioniert Alles nicht, kannste vergessen
 *         ...scheiß Animationthread beißt sich alles, // * da dann mehere
 *         sachen aufs selbe zugreifen gleichzeitig verdammt!!
 */
public class DefenderControl {

	public static final int PLAYER_ONE = 0;
	public static final int PLAYER_TWO = 1;

	public Player getPlayerOne() {
		return playerOne;
	}

	public Player getPlayerTwo() {
		return playerTwo;
	}

	public static final int PLAYER_SYSTEM = 2;

	private CopyOnWriteArrayList<BaseUnit> globalUnits;
	private PApplet display;
	private static DefenderControl instance = null;

	private Player playerOne;
	private Player playerTwo;

	private DefenderControl(PApplet display) {
		playerOne = new Player(this, PApplet.HALF_PI, 0.5f, new PVector(512f,0f),Player.LEFT, new PVector(0f,0f));
		playerTwo = new Player(this, 3 * PApplet.HALF_PI, 0.5f, new PVector(513f, 768f),Player.RIGHT, new PVector(0f,0f));
		this.display = display;
		globalUnits = new CopyOnWriteArrayList<BaseUnit>();
	}

	public static DefenderControl getInstance(PApplet display) {
		if (DefenderControl.instance == null)
			DefenderControl.instance = new DefenderControl(display);

		return instance;
	}

	public CopyOnWriteArrayList<BaseUnit> getGlobalUnits() {
		return globalUnits;

	}

	public void drawAll() {		
		
		//init der beiden images fuer links und rechts
//		PGraphics screenLeft = display.createGraphics(display.getWidth() / 2, display.getHeight(), PApplet.JAVA2D);
//		PGraphics screenRight = display.createGraphics(display.getWidth() / 2,display.getHeight(), PApplet.JAVA2D);

		// neue positionen berechnen
		for (BaseUnit unit : globalUnits) {
			unit.calcNewPosition();
		}

		// Linke Seite zeichnen
//		screenLeft.beginDraw();
//		screenLeft.rectMode(PGraphics.CENTER);
//		screenLeft.background(150f);
		
//		//Feldumrandung zeichnen
//		//*******************************************
//		PVector drawPosition=new PVector(0,0);
//		drawPosition.rotate(this.playerOne.getGeneralAngle());
//		drawPosition.add(this.playerOne.getOriginPosition());
//		
//		//Transformationen		
//		display.translate(drawPosition.x, +drawPosition.y);		
//		display.scale(this.playerOne.getActualZoom());	
//		display.rotate(this.playerOne.getGeneralAngle());
//
//
//		
//		display.fill(0, 0, 255,55);
////		display.rectMode(PApplet.CORNER);
//		
//		display.rect(512f, 384f, 1024, 768);
//		
////		display.rectMode(PApplet.CENTER);
//		display.resetMatrix();
//		//Feldumrandung Ende 
//		//**********************************************
		
		this.zeichneRahmen(display, playerOne);
		
		for (BaseUnit unit : globalUnits) {
			unit.paint(this.playerOne, null);
		}
		
//		screenLeft.endDraw();

		//Trennlinie
		display.line(511f, 0f, 511f, 768f);
		display.line(512f, 0f, 512f, 768f);
		display.line(513f, 0f, 513f, 768f);

//		// Rechte Seite Zeichnen
//		screenRight.beginDraw();
//		screenRight.rectMode(PGraphics.CENTER);
////		screenRight.background(150f);
		
		zeichneRahmen(display, playerTwo);
		
		
		for (BaseUnit unit : globalUnits) {
			unit.paint(this.playerTwo, null);
		}
		
////		screenRight.endDraw();
//
//		display.image(screenLeft, 0, 0);
//		display.image(screenRight, 512, 0);
	}

	


	
	public void zeichneRahmen(PApplet display, Player player){
		
		//Feldumrandung zeichnen
		//*******************************************
		PVector drawPosition=new PVector(player.getViewPosition().x,player.getViewPosition().y);
		drawPosition.rotate(player.getGeneralAngle());
		drawPosition.add(player.getOriginPosition());
		
		//Transformationen		
		display.translate(drawPosition.x, +drawPosition.y);		
		display.scale(player.getActualZoom());	
		display.rotate(player.getGeneralAngle());
		
		display.fill(0, 0, 255,55);
		display.rect(512f, 384f, 1024, 768);
		
		display.resetMatrix();
		
	}
}
