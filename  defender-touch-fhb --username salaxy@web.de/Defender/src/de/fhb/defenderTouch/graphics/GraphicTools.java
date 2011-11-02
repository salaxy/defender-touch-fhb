package de.fhb.defenderTouch.graphics;

import java.util.Iterator;
import java.util.List;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import de.fhb.defenderTouch.oldMapConcept.Player;

public class GraphicTools {

	
    /**
     * hilfsmethode zum Zeichnen von Figuren nach einer collection Vektoren
     */
	public static void zeicheFigurNachVektoren(List<PVector>liste, PApplet pa){
		PVector firstPoint=null;
		PVector actualPoint=null;
		PVector leastPoint=null;
		int num=0;
		
		//alle punkte durchlaufen
		for(Iterator<PVector> i=liste.iterator();i.hasNext();){
			//aktuellen punkt holen
			actualPoint=i.next();
			
			//wenn nicht erster punkt dann zeichen linen zwischen ersten und 
			if(num>0){
				pa.line(leastPoint.x, leastPoint.y, actualPoint.x, actualPoint.y);								
			}else{
				firstPoint=actualPoint;				
			}
				num++;	
				
				leastPoint=actualPoint;
		}
		
//		if(actualPoint!=null&&firstPoint!=null){
//			pa.line(actualPoint.x, actualPoint.y, firstPoint.x, firstPoint.y);		
//		}
		
	}
	
	
//    /**
//     * hilfsmethode zum Zeichnen von Figuren nach einer collection Vektoren
//     */
//	public static void zeicheFigurNachVektoren(List<PVector>liste, PGraphics pa){
//		PVector firstPoint=null;
//		PVector actualPoint=null;
//		PVector leastPoint=null;
//		int num=0;
//		
//		//alle punkte durchlaufen
//		for(Iterator<PVector> i=liste.iterator();i.hasNext();){
//			//aktuellen punkt holen
//			actualPoint=i.next();
//			
//			//wenn nicht erster punkt dann zeichen linen zwischen ersten und 
//			if(num>0){
//				pa.line(leastPoint.x, leastPoint.y, actualPoint.x, actualPoint.y);								
//			}else{
//				firstPoint=actualPoint;				
//			}
//				num++;	
//				
//				leastPoint=actualPoint;
//		}
//		
////		if(actualPoint!=null&&firstPoint!=null){
////			pa.line(actualPoint.x, actualPoint.y, firstPoint.x, firstPoint.y);		
////		}
//		
//	}
	
	
    /**
     * hilfsmethode zum Zeichnen von Figuren nach einer collection Vektoren
     */
	public static void zeicheFigurNachVektoren(List<PVector>liste, PGraphics pa){
		PVector firstPoint=null;
		PVector actualPoint=null;
		PVector leastPoint=null;
		int num=0;
		
		//alle punkte durchlaufen
		for(Iterator<PVector> i=liste.iterator();i.hasNext();){
			//aktuellen punkt holen
			actualPoint=i.next();
			
			//wenn nicht erster punkt dann zeichen linen zwischen ersten und 
			if(num>0){
				pa.line(leastPoint.x, leastPoint.y, actualPoint.x, actualPoint.y);								
			}else{
				firstPoint=actualPoint;				
			}
				num++;	
				
				leastPoint=actualPoint;
		}
		
//		if(actualPoint!=null&&firstPoint!=null){
//			pa.line(actualPoint.x, actualPoint.y, firstPoint.x, firstPoint.y);		
//		}
		
	}
	
	
	/**
	 * Führt die nötigen Transformationen aus die nötig sind um Objekte auf dem Bildschirm 
	 * an der richtigen zu zeichen
	 * @param player - Spielerobjekt, wichtig fuer die unterscheidung Rechter oder Linker Bildschrim (Berechnungstechnisch)
	 * @param graphics - gibt fuer welchem Bildschirm die Abbildung berechnet wird 
	 * @param position - Postion des zu zeichnenden Objekts
	 */
	public static void calcDrawTransformation(Player player,PGraphics graphics,PVector position){
		
		//Berechnung des neuen Koordinaten Ursprungs Vektors
		PVector drawPosition=new PVector(player.getViewPosition().x,player.getViewPosition().y);
		drawPosition.rotate(player.getGeneralAngle());
		drawPosition.add(player.getOriginPosition());
		
		//Transformationen im Verhältnis zum Ursprung (Zoom, Genereller Winkel)
		graphics.translate(drawPosition.x, +drawPosition.y);		
		graphics.scale(player.getActualZoom());	
		graphics.rotate(player.getGeneralAngle());//nötig???
		
		//zeichne an Position im Verhältnis zu gesamt Transformation
		graphics.translate(position.x, +position.y);	
		
	}
	
	public static PVector calcInputVector(PVector clickVector, Player player){
		
	    //Klickvektor zurück rechnen auf spielkoordinaten
		//***********************
		PVector realClickKoordinates=clickVector.get();		
		System.out.println("originalclick on Screen at: "+ realClickKoordinates.x + ", " +realClickKoordinates.y);
		realClickKoordinates.sub(player.getOriginPosition());
		realClickKoordinates.sub(new PVector(0f, 0f));//(ist dort wo das PGRaphics gezeichnet)//Korektur!!!Platzhalter
		realClickKoordinates.rotate(PApplet.TWO_PI-player.getGeneralAngle());
		realClickKoordinates.sub(player.getViewPosition());
		realClickKoordinates.mult(1/player.getActualZoom());
		System.out.println("click on gamemap at: "+ realClickKoordinates.x + ", " +realClickKoordinates.y);
		//***********************
		
		return realClickKoordinates;
		
	}
	
}
