package de.fhb.defenderTouch.gamelogic;

import java.util.ArrayList;

import processing.core.PApplet;

import de.fhb.defenderTouch.display.DefenderPApplet;
import de.fhb.defenderTouch.units.movable.BaseUnit;

/**
 * Diese Klasse stellt die Verbindung zwischen Anzeige, also dem PApplet dar
 * und dem eigentlichen Spielinhalt wie die Units, Spiellogik usw. (und noch zukünftige dinge)
 * @author Salaxy
 *
 */
public class DefenderTouch {
	
	// zum testen
	private ArrayList<BaseUnit> globalUnits=new ArrayList<BaseUnit>();
	
	private PApplet display;
	
	private static DefenderTouch instance=null;
	
	
	private DefenderTouch(PApplet display){
		this.display=display;
	}
	
	public static DefenderTouch getInstance(PApplet display){
		
		if(DefenderTouch.instance==null){
			DefenderTouch.instance=new DefenderTouch(display);
		}
		
		return instance;
	}
	

	public ArrayList<BaseUnit> getGlobalUnits() {
		return globalUnits;
	}
	
	
//	public void addUnit(int x, int y, int mode, int playerID, PApplet disp){
//		this.globalUnits.add(new  x, y, mode, playerID, disp);
//	}
}
