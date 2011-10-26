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
public class DefenderTouchControl {
	
	
	private CopyOnWriteArrayList <BaseUnit> globalUnits;
	
	private PApplet display;
	
	private static DefenderTouchControl instance=null;
	
	
	private DefenderTouchControl(PApplet display){
		this.display=display;
		globalUnits=new CopyOnWriteArrayList <BaseUnit>();
	}
	
	public static DefenderTouchControl getInstance(PApplet display){
		
		if(DefenderTouchControl.instance==null){
			DefenderTouchControl.instance=new DefenderTouchControl(display);
		}
		
		return instance;
	}
	

	public CopyOnWriteArrayList <BaseUnit> getGlobalUnits() {

		return globalUnits;
	     
	}


}
