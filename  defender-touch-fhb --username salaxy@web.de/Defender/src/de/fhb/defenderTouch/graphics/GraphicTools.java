package de.fhb.defenderTouch.graphics;

import java.util.Iterator;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

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
	
	
}
