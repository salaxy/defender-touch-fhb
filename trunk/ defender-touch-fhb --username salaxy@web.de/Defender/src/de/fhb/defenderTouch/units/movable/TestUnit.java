package de.fhb.defenderTouch.units.movable;

import java.awt.Color;
import java.awt.Point;

import processing.core.PApplet;
import processing.core.PVector;

public class TestUnit 
//implements MouseWheelListener
{

	private final float TWO_PI=(float)Math.PI*2;
	public static final int MODE_NONE=0;
	public static final int MODE_ROTATE=1;
	public static final int MODE_PULSE=2;
	public static final int MODE_BOTH=3;
	public static final int MODE_HALO=4;
	private float[] pulseSkala={1.0f, 0.9f, 0.8f, 0.7f, 0.6f, 0.5f};
	private float[] haloSkala={1.2f, 1.4f, 1.6f, 1.8f, 2.0f, 2.2f};
	private float[] haloSkala2;
	private int pulseStat=0;
	private int haloStat=0;
	private PVector position;
	private float angle;
	private int mode;
	private boolean active=false;
	//bei zu groﬂen werten von movementSpeed kann das objekt zum schwingen kommen
	private float movementSpeed=6f;
	
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
		calcNewPosition();
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
		}

//		pa.fill(0);
//		this.drawFigure(pa);
		//zur¸ck stellen auf, Seiteneffekte vermeiden
//		pa.rotate(0);
//		pa.scale(1);
//		pa.translate(0, 0);
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
//		this.drawFigure(pa);
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
			angle+=0.1;
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
	
	public void calcNewPosition(){
		//wenn aktuelle position nicht nahe herankommt an ziel, dann weiter bewegen
		if(position.dist(destinationVector)>3){
			//Richtugnsvector berechnen
			PVector direction=position.sub( destinationVector, position);
			//richtungsvector normieren
			direction.normalize();
			//normierten Richtungsvector zur position hinzurechnen
			position.add(direction.mult(direction, movementSpeed));
		}

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
	

	private void entscheideFarbe(PApplet pa){
		if(this.active){
			pa.fill(this.activeColor.getRed(), this.activeColor.getGreen(), this.activeColor.getBlue());
		}else{
			pa.fill(this.passiveColor.getRed(), this.passiveColor.getGreen(), this.passiveColor.getBlue());
		}
	}
}
