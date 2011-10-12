package de.fhb.defenderTouch.units.grounded;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class Support extends Building{

	public Support(int x, int y, int mode, PApplet disp){
		super(x, y, mode, disp);
		// TODO Auto-generated constructor stub
	}

	
	public void drawFigure(){
		
		//farbewechsel bei Aktivierung
		this.entscheideLineFarbe();
		//Skalieren
		display.scale(4);
		//Nach norden ausrichten
		display.rotate((float)Math.PI);
		
		//Punkte hinzufuegen
		ArrayList<PVector> vektoren=new ArrayList<PVector>();
		vektoren.add(new PVector(0, -8));
		vektoren.add(new PVector(0, 8));
		vektoren.add(new PVector(0, 0));
		vektoren.add(new PVector(8,0));
		vektoren.add(new PVector(-8, 0));

		//zeichnen
		this.zeicheFigurNachVektoren(vektoren);
		
		//zurücksetzen der Umgebungseinstellungen
		display.resetMatrix();
		display.stroke(0);
		
	}
}
