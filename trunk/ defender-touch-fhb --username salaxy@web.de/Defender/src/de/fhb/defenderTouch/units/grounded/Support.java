package de.fhb.defenderTouch.units.grounded;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class Support extends Building{

	public Support(int x, int y, int mode) {
		super(x, y, mode);
		// TODO Auto-generated constructor stub
	}

	
	public void drawFigure(PApplet pa){
		
		//farbewechsel bei Aktivierung
		this.entscheideLineFarbe(pa);
		//Skalieren
		pa.scale(4);
		//Nach norden ausrichten
		pa.rotate((float)Math.PI);
		
		//Punkte hinzufuegen
		ArrayList<PVector> vektoren=new ArrayList<PVector>();
		vektoren.add(new PVector(0, -8));
		vektoren.add(new PVector(0, 8));
		vektoren.add(new PVector(0, 0));
		vektoren.add(new PVector(8,0));
		vektoren.add(new PVector(-8, 0));

		//zeichnen
		this.zeicheFigurNachVektoren(vektoren, pa);
		
		//zurücksetzen der Umgebungseinstellungen
		pa.resetMatrix();
		pa.stroke(0);
		
	}
}
