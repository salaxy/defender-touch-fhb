package de.fhb.defenderTouch.gamelogic;

import java.util.concurrent.CopyOnWriteArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import de.fhb.defenderTouch.units.movable.BaseUnit;

/**
 * Diese Klasse stellt die Verbindung zwischen Anzeige, also dem PApplet dar
 * und dem eigentlichen Spielinhalt wie die Units, Spiellogik usw. (und noch zukünftige dinge)
 * @author Salaxy
// * @deprecated Funktioniert Alles nicht, kannste vergessen ...scheiß Animationthread beißt sich alles,
// * da dann mehere sachen aufs selbe zugreifen gleichzeitig verdammt!!
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
	
	private CopyOnWriteArrayList <BaseUnit> globalUnits;
	private PApplet display;
	private static DefenderControl instance=null;
	
	private Player playerOne;
	private Player playerTwo;
	
	
	private DefenderControl(PApplet display) {
		playerOne=new Player(this,PApplet.HALF_PI,3f,new PVector(512f,0f));
		playerTwo=new Player(this,3*PApplet.HALF_PI,0.5f,new PVector(513f,768f));
		this.display = display;
		globalUnits = new CopyOnWriteArrayList <BaseUnit>();
	}
	
	public static DefenderControl getInstance(PApplet display) {
		if(DefenderControl.instance == null) DefenderControl.instance = new DefenderControl(display);
		
		return instance;
	}
	

	public CopyOnWriteArrayList <BaseUnit> getGlobalUnits() {
		return globalUnits;
	     
	}
	
	public void drawAll(){
		
		// neue positionen berechnen
		  for(BaseUnit unit: globalUnits){
			  unit.calcNewPosition();	  
		  }
		
		  for(BaseUnit unit: globalUnits){
			  unit.paint(this.playerOne);	  
		  }
		
		  display.line(511f, 0f, 511f, 768f);
		  display.line(512f, 0f, 512f, 768f);
		  display.line(513f, 0f, 513f, 768f);
		  
		  for(BaseUnit unit: globalUnits){
			  unit.paint(this.playerTwo);	  
		  }
		  
	}


}
