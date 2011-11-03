package de.fhb.defenderTouch.units.grounded;

import java.awt.Color;
import java.util.ArrayList;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.graphics.GraphicTools;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class Support extends Building{

	public static final int PRICE = 30;
	
	private int frameTimesToRaiseCredits=50;
	private int framesTemp=0;
	
	private int creditsPerTime=20;
	
	public Support(int x, int y, int mode, int playerID,DefenderControl gamelogic) {
		super(x, y, mode, playerID, gamelogic);
	}

	
	public void drawFigure(PGraphics graphics){
		

		
		//farbewechsel bei Aktivierung
//		this.entscheideLineFarbe( graphics);
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
	
	
	 public void calcNewPosition(){

		 	framesTemp++;				
		 	
		 	if(framesTemp==frameTimesToRaiseCredits){
				if(this.playerID==DefenderControl.PLAYER_ONE){
					gamelogic.getPlayerOne().addCredits(this.creditsPerTime);
				}
	
				if(this.playerID==DefenderControl.PLAYER_TWO){
					gamelogic.getPlayerTwo().addCredits(this.creditsPerTime);
				}
			}
		 				

	 }

}
