package de.fhb.defenderTouch.gamelogic;

import gifAnimation.Gif;

import java.awt.Color;
import java.util.concurrent.CopyOnWriteArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import de.fhb.defenderTouch.audio.FormatProblemException;
import de.fhb.defenderTouch.audio.SampleThread;
import de.fhb.defenderTouch.graphics.GraphicTools;
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
	
	
	
	/**
	 * gibt an wo auf dem PApplet der screenLeft gezeichnet werden soll
	 */
	public static PVector SCREENLEFT_POSITION= new PVector(0, 0);
	
	/**
	 * gibt an wo auf dem PApplet der screenLeft gezeichnet werden soll
	 */
	public static PVector SCREENRIGHT_POSITION= new PVector(515, 0);
	

	private CopyOnWriteArrayList<BaseUnit> globalUnits;
	private PApplet display;
	private static DefenderControl instance = null;
	private Player playerOne;
	private Player playerTwo;
	private PGraphics screenLeft, screenRight;
	private Gamemap map;
	
	
	private Menu menuePlayerOne;
	private Menu menuePlayerTwo;
	private Gif nonLoopingGifDestroy;

	private DefenderControl(PApplet display,PGraphics screenLeft,PGraphics screenRight) {
		//map init
		map=new Gamemap(display);
		
		//die beiden Spieler initialisieren
		playerOne = new Player(this,PApplet.HALF_PI, 0.65f, new PVector(512f,0f),Player.LEFT, new PVector(0f,0f),SCREENLEFT_POSITION, Color.BLUE);
		playerTwo = new Player(this,3*PApplet.HALF_PI,0.65f, new PVector(0f, 768f),Player.RIGHT, new PVector(0f,0f),SCREENRIGHT_POSITION, Color.GREEN);
		// SlpitScrren für den zweiten Spieler *nicht* um 180° gedreht.
		//playerTwo = new Player(this,PApplet.HALF_PI,0.65f, new PVector(512f, 0f),Player.RIGHT, new PVector(0f,0f),SCREENRIGHT_POSITION, Color.GREEN);
		
		this.display = display;
		globalUnits = new CopyOnWriteArrayList<BaseUnit>();
		
		//init der beiden images fuer links und rechts
		this.screenLeft = screenLeft;
		this.screenRight = screenRight;
		
		
		//Menue init//TODO gifanimation
		menuePlayerOne = new Menu(this.globalUnits, nonLoopingGifDestroy,playerOne);
		menuePlayerTwo = new Menu(this.globalUnits, nonLoopingGifDestroy,playerTwo);
		
		

		
		this.playBackgroundSound();

	}

	public Gamemap getMap() {
		return map;
	}

	public static DefenderControl getInstance(PApplet display, PGraphics screenLeft,PGraphics screenRight) {
		if (DefenderControl.instance == null)
			DefenderControl.instance = new DefenderControl(display, screenLeft, screenRight);

		return instance;
	}

	public CopyOnWriteArrayList<BaseUnit> getGlobalUnits() {
		return globalUnits;

	}

	/**
	 * Zeichnet beide Spielfelder und Inhalte
	 */
	public void drawAll() {		
		
		

		
		
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
//		this.zeichneRahmen(screenLeft, playerOne);	
		this.map.zeichne(screenLeft, playerOne);	
		

		//units playerOne zeichen
		for (BaseUnit unit : globalUnits) {
			if(unit.isActive()&&unit.getPlayerID()==PLAYER_ONE){
				//zeichne Aktiviert
				unit.paint(this.playerOne, screenLeft,true);	
			}else{
				//zeichen normal
				unit.paint(this.playerOne, screenLeft,false);				
			}
		}
		
		//menue zeichen fuer player one
		this.menuePlayerOne.drawMenu(this.screenLeft, this.playerOne);
		
		//creditsinfo zeichnen
		screenLeft.textSize(20f);
		screenLeft.fill(0);
		screenLeft.rotate(PApplet.HALF_PI);
		screenLeft.text("Credits: " + playerOne.getCredits(), 20, -15);
//		screenLeft.text("Aktuelles Gebäude: " + menuePlayerOne.getActualBuildingName(), 350, -15);
		screenLeft.text("Gebäudeanzahl: " + menuePlayerOne.getActualBuildingCount(), 300, -15);
		
		screenLeft.endDraw();


		
		// Rechte Seite vorzeichnen
		screenRight.beginDraw();
		screenRight.smooth();
		screenRight.rectMode(PGraphics.CENTER);
		screenRight.background(255f);
		
		//Feld zeichnen
//		zeichneRahmen(screenRight, playerTwo);
		this.map.zeichne(screenRight, playerTwo);	
		
		//units playerTwo zeichen
		for (BaseUnit unit : globalUnits) {
			if(unit.isActive()&&unit.getPlayerID()==PLAYER_TWO){
				//zeichne Aktiviert
				unit.paint(this.playerTwo, screenRight,true);	
			}else{
				//zeichen normal
				unit.paint(this.playerTwo, screenRight,false);				
			}
		}
		
		//menue zeichen fuer playerTwo
		this.menuePlayerTwo.drawMenu(this.screenRight, this.playerTwo);
		
		//creditsinfo zeichnen
		screenRight.textSize(20f);
		screenRight.fill(0);
		screenRight.translate(510, 768);
		screenRight.rotate(-PApplet.HALF_PI);
		screenRight.text("Credits: " + playerTwo.getCredits(), 20, -15);
//		screenRight.text("Aktuelles Gebäude: " + menuePlayerTwo.getActualBuildingName(), 350, -15);
		screenRight.text("Gebäudeanzahl: " + menuePlayerTwo.getActualBuildingCount(), 300, -15);
		
		
		screenRight.endDraw();

		//die beiden Seiten auf dem PApplet zeichnen
		display.image(screenLeft, SCREENLEFT_POSITION.x, SCREENLEFT_POSITION.y);
		display.image(screenRight, SCREENRIGHT_POSITION.x, SCREENRIGHT_POSITION.y);
		
		
		
		//Trennlinie
		display.stroke(0);
		display.line(511f, 0f, 511f, 768f);
		display.line(512f, 0f, 512f, 768f);
		display.line(513f, 0f, 513f, 768f);
	}

//	public void zeichneRahmen(PGraphics graphics, Player player){
//		
//		
//		//Transformationen
//		GraphicTools.calcDrawTransformation(player, graphics, new PVector(0,0));
//	
//		//Feldumrandung zeichnen
//		graphics.fill(255, 255, 0,55);
//		graphics.stroke(0, 0, 0);
//		graphics.rect(512f, 384f, 1024, 768);
//		
//		graphics.resetMatrix();
//		
//	}
	

	public Menu getMenuePlayerOne() {
		return menuePlayerOne;
	}

	public Menu getMenuePlayerTwo() {
		return menuePlayerTwo;
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
