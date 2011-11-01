package de.fhb.defenderTouch.gamelogic;

import gifAnimation.Gif;

import java.util.concurrent.CopyOnWriteArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import de.fhb.defenderTouch.audio.FormatProblemException;
import de.fhb.defenderTouch.audio.SampleThread;
import de.fhb.defenderTouch.menu.Menu;
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


	public static final int PLAYER_SYSTEM = 2;

	private CopyOnWriteArrayList<BaseUnit> globalUnits;
	private PApplet display;
	private static DefenderControl instance = null;
	private Player playerOne;
	private Player playerTwo;
	private PGraphics screenLeft, screenRight;
	
	
	private Menu menu;
	private Gif nonLoopingGifDestroy;

	private DefenderControl(PApplet display,PGraphics screenLeft,PGraphics screenRight) {
		
		//die beiden Spieler initialisieren
		playerOne = new Player(this,PApplet.HALF_PI, 0.5f, new PVector(512f,0f),Player.LEFT, new PVector(0f,0f));
		playerTwo = new Player(this,3*PApplet.HALF_PI,0.5f, new PVector(0f, 768f),Player.RIGHT, new PVector(0f,0f));
		
		this.display = display;
		globalUnits = new CopyOnWriteArrayList<BaseUnit>();
		
		//init der beiden images fuer links und rechts
		this.screenLeft = screenLeft;
		this.screenRight = screenRight;
		
		
		//Menue init//TODO vorerst wird nur links gezeichnet
		menu = new Menu(this.globalUnits, nonLoopingGifDestroy);
		
		
		this.playBackgroundSound();

	}

	public static DefenderControl getInstance(PApplet display, PGraphics screenLeft,PGraphics screenRight) {
		if (DefenderControl.instance == null)
			DefenderControl.instance = new DefenderControl(display, screenLeft, screenRight);

		return instance;
	}

	public CopyOnWriteArrayList<BaseUnit> getGlobalUnits() {
		return globalUnits;

	}

	public void drawAll() {		
		
		
		//menue zeichen fuer player one
		
		this.menu.drawMenu(this.screenLeft, this.playerOne);
		
		
		// neue positionen berechnen
		for (BaseUnit unit : globalUnits) {
			unit.calcNewPosition();
		}

		
		// Linke Seite vorzeichnen
		screenLeft.beginDraw();		
		screenLeft.smooth();
		screenLeft.rectMode(PGraphics.CENTER);
		screenLeft.background(255f);
		
		//Feld zeichnen
		this.zeichneRahmen(screenLeft, playerOne);
		
		for (BaseUnit unit : globalUnits) {
			unit.paint(this.playerOne, screenLeft);
		}
		
		screenLeft.endDraw();


		
		// Rechte Seite vorzeichnen
		screenRight.beginDraw();
		screenRight.smooth();
		screenRight.rectMode(PGraphics.CENTER);
		screenRight.background(255f);
		
		//Feld zeichnen
		zeichneRahmen(screenRight, playerTwo);
		
		
		for (BaseUnit unit : globalUnits) {
			unit.paint(this.playerTwo, screenRight);
		}
		
		screenRight.endDraw();

		//die beiden Seiten auf dem PApplet zeichnen
		display.image(screenLeft, 0, 0);
		display.image(screenRight, 515, 0);
		
		
		
		//Trennlinie
		display.stroke(0);
		display.line(511f, 0f, 511f, 768f);
		display.line(512f, 0f, 512f, 768f);
		display.line(513f, 0f, 513f, 768f);
	}

	


	
	public Menu getMenu() {
		return menu;
	}

	public void zeichneRahmen(PGraphics display, Player player){
		
		//Feldumrandung zeichnen
		//*******************************************
		PVector drawPosition=new PVector(player.getViewPosition().x,player.getViewPosition().y);
		drawPosition.rotate(player.getGeneralAngle());
		drawPosition.add(player.getOriginPosition());
		
		//Transformationen		
		display.translate(drawPosition.x, +drawPosition.y);		
		display.scale(player.getActualZoom());	
		display.rotate(player.getGeneralAngle());
		
		display.fill(255, 255, 0,55);
		display.rect(512f, 384f, 1024, 768);
		
		display.resetMatrix();
		
	}
	

	public Player getPlayerOne() {
		return playerOne;
	}

	public Player getPlayerTwo() {
		return playerTwo;
	}
	
	private void playBackgroundSound(){
		try {
			new SampleThread("/sounds/background.mp3",10.0f,true).start();
		} catch (FormatProblemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
