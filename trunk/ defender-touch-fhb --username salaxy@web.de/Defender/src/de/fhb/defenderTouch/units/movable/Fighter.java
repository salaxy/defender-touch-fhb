package de.fhb.defenderTouch.units.movable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public class Fighter extends TestUnit {

	public Fighter(int x, int y, int mode) {
		super(x, y, mode);
	}

	
	public void drawFigure(PApplet pa){
		
		pa.stroke(0);
//		pa.strokeWeight(10);
		pa.fill(0);
//		pa.triangle(-20,+20, 0, -20, +20, +20);
		ArrayList<PVector> vektoren=new ArrayList<PVector>();
		vektoren.add(new PVector(0, -8));
		vektoren.add(new PVector(-8, 8));
		vektoren.add(new PVector(-4, 6));
		vektoren.add(new PVector(0,8));
		vektoren.add(new PVector(0, -8));
		vektoren.add(new PVector(8,8));
		vektoren.add(new PVector(4,6));
		vektoren.add(new PVector(0,8));
		

		this.zeicheFigurNachVektoren(vektoren, pa);
		
	}
	
	
	public void zeicheFigurNachVektoren(List<PVector>liste,PApplet pa){
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
