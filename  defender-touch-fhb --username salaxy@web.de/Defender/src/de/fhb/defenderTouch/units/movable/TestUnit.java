package de.fhb.defenderTouch.units.movable;

import java.awt.Color;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;
/**
 * 
 * @author Andy Klay <klay@fh-brandenburg.de>
 *
 */
public class TestUnit 
//implements MouseWheelListener
{

	private final float TWO_PI=(float)Math.PI*2;
	public static final int MODE_NONE=0;
	public static final int MODE_ROTATE=1;
	public static final int MODE_PULSE=2;
	public static final int MODE_BOTH=3;
	public static final int MODE_HALO=4;
	public static final int MODE_PULSE_IF_ACTIVE=5;
	private float[] pulseSkala={1.0f, 0.9f, 0.8f, 0.7f, 0.6f, 0.5f};
	private float[] haloSkala={1.2f, 1.4f, 1.6f, 1.8f, 2.0f, 2.2f};
	private float[] haloSkala2;
	private int pulseStat=0;
	private int haloStat=0;
	private PVector position;
	private PVector direction=new PVector(0,1);
	private float angle;
	private int mode;
	private boolean active=false;
	private int schweiflaenge=20;
	private float actualAngle=0;
	
	private float rotationSpeed=0.1f;
	//bei zu groﬂen werten von movementSpeed kann das objekt zum schwingen kommen
	private float movementSpeed=2f;
	
	private Color activeColor=Color.ORANGE;
	private Color passiveColor=Color.BLACK;
	
	private PVector destinationVector;
	
	
	public TestUnit(int x, int y, int mode){
		this.position=new PVector(x,y);
		this.angle=0;
		this.mode=mode;
		
		destinationVector=new PVector(x,y);
		
		haloSkala2=new float[50];
		int i=0;
		for(float f=1.01f;i<haloSkala2.length;f+=0.1f){
			haloSkala2[i]=f;
			i++;
		}
	}
	
	
	public void paint(PApplet pa){
		
		//position ¸berpr¸fen
		calcNewPosition(pa);
		//punkte links,oben,rechts	
//		pa.translate(x, y);
		

		
		
		
		//korrektur f¸r dreiecke??????
//		pa.translate(1%3*(-20+0+20),1%3*(20+-20+20));
		
		switch(mode){
		case MODE_ROTATE: 	
			this.rotate(pa);
//			this.halo(pa);
		break;
		case MODE_PULSE :	
			this.pulse(pa);
//			this.halo(pa);
		break;
		case MODE_BOTH :
			this.pulseAndRotate(pa);
			this.haloWithoutFigure(pa);
		break;
		case MODE_HALO :
			this.halo(pa);
		break;
		case MODE_NONE :
			this.haloWithoutFigure(pa);	
			
			pa.translate(position.x, position.y);
			pa.fill(0);
			berechneNeueBlickrichtung();
			pa.rotate(actualAngle);
			
			this.entscheideFarbe(pa);		
			this.drawFigure(pa);
		break;
		case MODE_PULSE_IF_ACTIVE :
			if(!active){
				//normal zeichnen
				pa.translate(position.x, position.y);
				pa.fill(0);
				berechneNeueBlickrichtung();
				pa.rotate(actualAngle);
				
				this.entscheideFarbe(pa);		
				this.drawFigure(pa);				
			}else{
				//pulsierend zeichnen
				this.pulse(pa);
			}
		break;
		}


		//zur¸ck stellen auf, Seiteneffekte vermeiden
//		pa.rotate(0);
//		pa.scale(1);

		pa.resetMatrix();
	}


//	@Override
//	public void mouseWheelMoved(MouseWheelEvent event) {
//		// TODO Auto-generated method stub
//		angle=event.getWheelRotation();
//		
//	}
	
	
	public void pulse(PApplet pa){
		pa.translate(position.x, position.y);
		
		berechneNeueBlickrichtung();
		pa.rotate(actualAngle);

		
		//solange die skala noch nicht durchlaufen ist
		if(pulseStat<pulseSkala.length-1){
			pulseStat++;
		}else{
			//sosnt wieder von vorne anfangen
			pulseStat=0;
		}
		
		//skalieren
		pa.scale(pulseSkala[pulseStat]);	
		
		this.entscheideFarbe(pa);
		this.drawFigure(pa);
		
		pa.resetMatrix();
		
	}
	
	public void halo(PApplet pa){
		

		
		//solange die skala noch nicht durchlaufen ist
		if(haloStat<haloSkala2.length-1){
			haloStat++;
		}else{
			//sosnt wieder von vorne anfangen
			haloStat=0;
		}
		
		//skalieren
		pa.scale(haloSkala2[haloStat]);
		
		pa.noFill();
		pa.stroke(0);
//		this.drawFigure(pa);		
		pa.translate(position.x, position.y);		
		pa.ellipse(0, 0, 20, 20);
		
		pa.resetMatrix();
		this.entscheideFarbe(pa);	
		
		pa.translate(position.x, position.y);
		this.drawFigure(pa);
		pa.resetMatrix();
	}
	
	public void haloWithoutFigure(PApplet pa){
		
		pa.translate(position.x, position.y);
		
		//solange die skala noch nicht durchlaufen ist
		if(haloStat<haloSkala2.length-1){
			haloStat++;
		}else{
			//sosnt wieder von vorne anfangen
			haloStat=0;
		}
		
		//skalieren
		pa.scale(haloSkala2[haloStat]);
		
		pa.noFill();
		pa.stroke(0);
		pa.ellipse(0, 0, 20, 20);
		
		pa.resetMatrix();
	}
	
	
	public void rotate(PApplet pa){
		
		pa.translate(position.x, position.y);
		
		//solange die skala noch nicht durchlaufen ist
		if(angle<TWO_PI){
			angle+=this.rotationSpeed;
		}else{
			//sosnt wieder von vorne anfangen
			angle=0;
		}
		
		//skalieren
		pa.rotate(angle);
		
		this.entscheideFarbe(pa);
		this.drawFigure(pa);
		
		pa.resetMatrix();
		
	}
	
	
	public void pulseAndRotate(PApplet pa){
		
		pa.translate(position.x, position.y);
		
		//solange die skala noch nicht durchlaufen ist
		if(angle<TWO_PI){
			angle+=0.1;
		}else{
			//sosnt wieder von vorne anfangen
			angle=0;
		}
		
		//skalieren
		pa.rotate(angle);
		
		//solange die skala noch nicht durchlaufen ist
		if(pulseStat<pulseSkala.length-1){
			pulseStat++;
		}else{
			//sosnt wieder von vorne anfangen
			pulseStat=0;
		}
		
		//skalieren
		pa.scale(pulseSkala[pulseStat]);	
		
		//gefuelllt zeichnen
		this.entscheideFarbe(pa);
		this.drawFigure(pa);
		
		//Umgebung zur¸cksetzen
		pa.resetMatrix();
		
	}
	
	public void drawFigure(PApplet pa){
		
		pa.rect(0, 0, 0+20, 0+20);
//		pa.triangle(-20,+20, 0, -20, +20, +20);
		
	}
	
	
	public void commandDirection(int x,int y){
		this.destinationVector=new PVector(x,y);
	}
	
	public void calcNewPosition(PApplet pa){
		//wenn aktuelle position nicht nahe herankommt an ziel, dann weiter bewegen
		if(position.dist(destinationVector)>3){
			//Richtugnsvector berechnen
			direction=position.sub( destinationVector, position);
			//richtungsvector normieren
			direction.normalize();
			//normierten Richtungsvector zur position hinzurechnen
			position.add(direction.mult(direction, movementSpeed));
			
			//zeichne Schweif
			drawTail(direction, pa);
			

		}

	}
	



	private void drawTail(PVector direction, PApplet pa) {
		
		//Zielpunkt berechnen
		PVector ende=direction.get();
		ende.mult(this.movementSpeed*schweiflaenge*-1);
//		ende=ende.sub(position,ende);
//		ende=direction.add(ende, position);
		
		pa.translate(position.x, position.y);
		pa.fill(0);
		
		pa.line(0, 0, ende.x/2, ende.y/2);
		pa.line(1, 1, ende.x/2, ende.y/2);
		pa.line(-1, -1, ende.x/2, ende.y/2);
		
		pa.line(ende.x/2, ende.y/2, ende.x, ende.y);
		pa.line(ende.x*1.1f, ende.y*1.1f, ende.x*1.2f, ende.y*1.2f);
		pa.line(ende.x*1.3f, ende.y*1.3f, ende.x*1.4f, ende.y*1.4f);
		
//		for(int i=1;i<10;i+=4){
//			
//			pa.line(ende.x+i, ende.y+i, ende.x+i, ende.y+i);
//			
//		}
		
		pa.resetMatrix();
		
	}


	public void setDestination(PVector v){
		this.destinationVector=v;	
	}
	
	public void setMode(int mode){
		this.mode=mode;
	}

	
	public boolean isInner(PVector clickVector){
		if(position.dist(clickVector)<10){
			//Einheit f¸r die Steureung aktivieren
			active=true;
			return true;

		}else{
			return false;
		}
		
	}


	public boolean isActive() {
		return active;
	}
	
	public void deactivate(){
		active=false;
	}
	

	public void entscheideFarbe(PApplet pa){
		if(this.active){
			pa.fill(this.activeColor.getRed(), this.activeColor.getGreen(), this.activeColor.getBlue());
		}else{
			pa.fill(this.passiveColor.getRed(), this.passiveColor.getGreen(), this.passiveColor.getBlue());
		}
	}
	
	public void entscheideLineFarbe(PApplet pa){
		if(this.active){
			pa.stroke(this.activeColor.getRed(), this.activeColor.getGreen(), this.activeColor.getBlue());
		}else{
			pa.stroke(this.passiveColor.getRed(), this.passiveColor.getGreen(), this.passiveColor.getBlue());
		}
	}
	
	public void berechneNeueBlickrichtung(){
		//berechne neue Blickrichtung
		if(direction.x>0){
			//rechts
			actualAngle=PVector.angleBetween(direction, new PVector(0,-1));
		}else{
			//links
			actualAngle=2f*(float)Math.PI - PVector.angleBetween(direction, new PVector(0,-1));
		}
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
