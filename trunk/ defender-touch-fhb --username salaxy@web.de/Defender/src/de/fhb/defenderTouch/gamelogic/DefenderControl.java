package de.fhb.defenderTouch.gamelogic;

import java.util.concurrent.CopyOnWriteArrayList;

import processing.core.PApplet;
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
	public static final int PLAYER_SYSTEM = 2;
	
	private CopyOnWriteArrayList <BaseUnit> globalUnits;
	
	private PApplet display;
	
	private static DefenderControl instance=null;
	
	
	private DefenderControl(PApplet display) {
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


}
