package de.fhb.defenderTouch.menu;

import java.util.ArrayList;

import de.fhb.defenderTouch.graphics.GraphicTools;
import de.fhb.defenderTouch.interfaces.Drawable;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Menu implements Drawable{

	/**
	 * Ist das Applet auf dem die Einheiten zugeordnet sind
	 */
	protected PApplet mainPoint;
	protected PApplet mainPoints;

	/**
	 * aktuelle Position der Einheit
	 */
	protected PVector position;

	/**
	 * aktuelle Sichtbarkeit
	 */
	protected boolean visible = false;
	
	/**
	 * aktuelle Sichtbarkeit
	 */
	protected boolean menuOpen = false;
	
	/**
	 * Aktivierungsradius eines Menupunkts
	 */
	protected float activateRadius=35;
	
	/**
	 * sagt aus ob ein Menupunkt geklickt wurde
	 */
	protected boolean active=false;

	/**
	 * Konstruktor
	 */
	public Menu(PApplet display) {
		this.position = new PVector(0, 0);
		this.mainPoint = display;
		this.mainPoints = display;
	}

	/**
	 * Menupunkt anzeigen
	 */
	public void showMenuPoint(PVector position) {
		this.position = position;
		this.visible = true;
	}


	/**
	 * nur Aufruf wenn auch wirklich zeichnen
	 */
	public void paint() {

		mainPoint.ellipseMode(PConstants.CENTER);
		mainPoint.translate(this.position.x, this.position.y);
		
		if (visible && menuOpen == false) {

			menuOpen = true;
			mainPoint.noFill();
			mainPoint.stroke(100);
			mainPoints.rotate((float)Math.PI/2);

			mainPoint.ellipse(0, 0, 20, 20);
			mainPoint.ellipse(-35, -35, 30, 30);
			mainPoints.triangle(-40,-30, -35, -40, -30, -30);
			mainPoint.ellipse(0, -35, 30, 30);
			mainPoint.ellipse(35, -35, 30, 30);
			
			
			
			
		}

	}
	
	/**
	 * 
	 * @param clickVector
	 * @return
	 */
	public boolean isInner(PVector clickVector){
		
		if(position.dist(clickVector)<this.activateRadius){
			//Einheit für die Steureung aktivieren
			active=true;
			return true;
		}else{
			return false;
		}
	}

}
