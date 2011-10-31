package de.fhb.defenderTouch.units.grounded;

import java.util.ArrayList;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.graphics.GraphicTools;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class Support extends Building{

	public static final int PRICE = 30;
	
	public Support(int x, int y, int mode, int playerID, PApplet disp,DefenderControl gamelogic) {
		super(x, y, mode, playerID, disp, gamelogic);
	}

	
	public void drawFigure(PGraphics graphics){
		
		//farbewechsel bei Aktivierung
		this.entscheideLineFarbe( graphics);
		//Skalieren
		graphics.scale(1.5f);
		//Nach norden ausrichten
		graphics.rotate((float)Math.PI);
		
		//Punkte hinzufuegen
		ArrayList<PVector> vektoren=new ArrayList<PVector>();
		vektoren.add(new PVector(0, -8));
		vektoren.add(new PVector(0, 8));
		vektoren.add(new PVector(0, 0));
		vektoren.add(new PVector(8,0));
		vektoren.add(new PVector(-8, 0));

		//zeichnen
		GraphicTools.zeicheFigurNachVektoren(vektoren,graphics);
		
		//zurücksetzen der Umgebungseinstellungen
		graphics.resetMatrix();
		graphics.stroke(0);
		
	}
}
