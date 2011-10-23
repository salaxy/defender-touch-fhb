package de.fhb.defenderTouch.gamelogic;

import java.util.ArrayList;

import processing.core.PApplet;
import de.fhb.defenderTouch.display.DefenderPApplet;
import de.fhb.defenderTouch.units.movable.BaseUnit;

/**
 * Diese Klasse stellt die Verbindung zwischen Anzeige, also dem PApplet dar
 * und dem eigentlichen Spielinhalt wie die Units, Spiellogik usw. (und noch zukünftige dinge)
 * @author Salaxy
// * @deprecated Funktioniert Alles nicht, kannste vergessen ...scheiß Animationthread beißt sich alles,
// * da dann mehere sachen aufs selbe zugreifen gleichzeitig verdammt!!
 */
public class DefenderTouch {
	
	// zum testen
	private GlobalUnits globalUnits;
	
	private PApplet display;
	
	private static DefenderTouch instance=null;
	
	
	private DefenderTouch(PApplet display){
		this.display=display;
		globalUnits=new GlobalUnits();
	}
	
	public static DefenderTouch getInstance(PApplet display){
		
		if(DefenderTouch.instance==null){
			DefenderTouch.instance=new DefenderTouch(display);
		}
		
		return instance;
	}
	

	public ArrayList<BaseUnit> getGlobalUnits() {

		return globalUnits.getGlobalUnits();
	     
	}


}
