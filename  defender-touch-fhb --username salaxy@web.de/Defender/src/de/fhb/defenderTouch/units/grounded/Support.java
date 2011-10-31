package de.fhb.defenderTouch.units.grounded;

import java.util.ArrayList;

import de.fhb.defenderTouch.graphics.GraphicTools;

import processing.core.PApplet;
import processing.core.PVector;

public class Support extends Building{

	public static final int PRICE = 30;
	
	public Support(int x, int y, int mode, int playerID, PApplet disp){
		super(x, y, mode, playerID, disp);
		// TODO Auto-generated constructor stub
	}

	
	public void drawFigure(){
		
		//farbewechsel bei Aktivierung
		this.entscheideLineFarbe();
		//Skalieren
		display.scale(1.5f);
		//Nach norden ausrichten
//		display.rotate((float)Math.PI);
		
		//Punkte hinzufuegen
		ArrayList<PVector> vektoren=new ArrayList<PVector>();
		vektoren.add(new PVector(0, -8));
		vektoren.add(new PVector(0, 8));
		vektoren.add(new PVector(0, 0));
		vektoren.add(new PVector(8,0));
		vektoren.add(new PVector(-8, 0));

		//zeichnen
		GraphicTools.zeicheFigurNachVektoren(vektoren,display);
		
		//zurücksetzen der Umgebungseinstellungen
		display.resetMatrix();
		display.stroke(0);
		
	}
}
