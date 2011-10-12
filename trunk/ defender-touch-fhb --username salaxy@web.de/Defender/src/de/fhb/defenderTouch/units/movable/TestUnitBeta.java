package de.fhb.defenderTouch.units.movable;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;
/**
 *  Unit Version 0.2 vom 13.10.2011
 *  (Erste Einsatzversion)
 *  
 * @author Andy Klay <klay@fh-brandenburg.de>
 *
 */
public class TestUnitBeta 
//implements MouseWheelListener
{	
	/**
	 * beinhaltet alle Einheiten die existent sind
	 */
	public static ArrayList<TestUnitBeta> globalUnits=new ArrayList<TestUnitBeta>();
	
	/**
	 * Ist das Applet auf dem die einheiten zugeordnet sind
	 */
	public PApplet display;
	
	public static final int MODE_NONE=0;
	public static final int MODE_ROTATE=1;
	public static final int MODE_PULSE=2;
	public static final int MODE_BOTH=3;
	public static final int MODE_HALO=4;
	public static final int MODE_PULSE_IF_ACTIVE=5;
	
	/**
	 * Skala nach der Einheit pulsiert
	 */
	protected float[] pulseSkala={1.0f, 0.9f, 0.8f, 0.7f, 0.6f, 0.5f};
	
	/**
	 * Skala fuer den Radius des Halo
	 */
	protected float[] haloSkala;
	
	/**
	 * aktuelle Position der Einheit
	 */
	protected PVector position;
	
	/**
	 * legt die erste Blickrichtungfest
	 */
	protected PVector direction=new PVector(0,1);
	
	/**
	 * aktueller rotationsgrad (aktuelle Drehung der Rotation)
	 */
	protected float rotatingAngle=0;
	
	/**
	 *  aktuelle Skalierung fuer das Pulsieren
	 */
	protected int pulseStat=0;
	
	/**
	 * aktuelle Skalierung fuer den Halo
	 */
	protected int haloStat=0;
	
	/**
	 * legt das erscheinungbild der Einheit fest
	 */
	protected int mode;
	
	/**
	 * sagt aus ob  Einheit steuerbar ist und als Aktiv erscheint
	 */
	protected boolean active=false;
	
	/**
	 * sagt aus um welchen längenfaktor der Schweif erscheint
	 */
	protected int schweiflaenge=10;
	
	/**
	 * Blickrichtung
	 */
	protected float actualAngle=0;
	
	/**
	 * Geschwindigkeitsfaktor der Rotation
	 */
	protected float rotationSpeed=0.1f;
	
	/**
	 * Bewegungsgeschwindigkeit
	 */
	//bei zu großen werten von movementSpeed kann das objekt zum schwingen kommen
	protected float movementSpeed=2f;
	
	/**
	 * Farbe in der Einheit gezeichnet wird, wenn Einheit Aktiv ist, also Einheit Steuerbar ist
	 */
	protected Color activeColor=Color.ORANGE;
	
	/**
	 * Farbe in der Einheit gezeichnet wird, wenn Einheit NICHT Aktiv ist
	 */
	protected Color passiveColor=Color.BLACK;
	
	/**
	 * Zielvektor der Einheit
	 */
	protected PVector destinationVector;
	
	/**
	 * Aktivierungsradius um das Zentrum der Einheit, in dem diese angeklickt werden kann
	 */
	protected float activateRadius=15;
	
	
	
	public TestUnitBeta(int x, int y, int mode, PApplet disp){
		
		this.display=disp;
		this.mode=mode;
		this.position=new PVector(x,y);
		this.destinationVector=new PVector(x,y);	
		this.berechneNeueBlickrichtung();
		
		initHaloSkala();
		
		//fuegt sich selbst zur globalen Menge der Einheiten hinzu
		TestUnitBeta.globalUnits.add(this);
	}
	
	/**
	 * initialisiert die Haloskala
	 */
	public void initHaloSkala(){
		haloSkala=new float[50];
		int i=0;
		for(float f=1.01f;i<haloSkala.length;f+=0.1f){
			haloSkala[i]=f;
			i++;
		}
	}
	
	
	public void paint(){
		
		//position überprüfen
		calcNewPosition();
		//punkte links,oben,rechts	
//		pa.translate(x, y);
		

		
		
		
		//korrektur für dreiecke??????
//		pa.translate(1%3*(-20+0+20),1%3*(20+-20+20));
		
		switch(mode){
		case MODE_ROTATE: 	
			this.rotate();
//			this.halo(pa);
		break;
		case MODE_PULSE :	
			this.drawPulseAppearance();
//			this.halo(pa);
		break;
		case MODE_BOTH :
			this.pulseAndRotate();
			this.haloWithoutFigure();
		break;
		case MODE_HALO :
//			this.halo();
			this.haloWithoutFigure();	
			this.drawNormalAppearance();
		break;
		case MODE_NONE :
			this.drawNormalAppearance();
		break;
		case MODE_PULSE_IF_ACTIVE :
			if(!active){
				//normal zeichnen
				display.translate(position.x, position.y);
				display.fill(0);
//				berechneNeueBlickrichtung();
				display.rotate(actualAngle);
				
				this.entscheideFarbe();		
				this.drawFigure();				
			}else{

				//pulsierend zeichnen
				this.drawPulseAppearance();
			}
		break;
		}


		//zurück stellen auf, Seiteneffekte vermeiden
		//TODO eigentlich unnötig
		display.rotate(0);
		display.scale(1);

		display.resetMatrix();
	}


//	@Override
//	public void mouseWheelMoved(MouseWheelEvent event) {
//		// TODO Auto-generated method stub
//		angle=event.getWheelRotation();
//		
//	}
	
	
	public void drawNormalAppearance(){
		display.translate(position.x, position.y);
		display.fill(0);
		berechneNeueBlickrichtung();
		display.rotate(actualAngle);
		
		this.entscheideFarbe();		
		this.drawFigure();
	}
	
	
	public void drawPulseAppearance(){
		display.translate(position.x, position.y);
		
		display.rotate(actualAngle);

		
		//solange die skala noch nicht durchlaufen ist
		if(pulseStat<pulseSkala.length-1){
			pulseStat++;
		}else{
			//sosnt wieder von vorne anfangen
			pulseStat=0;
		}
		
		//skalieren
		display.scale(pulseSkala[pulseStat]);	
		
		this.entscheideFarbe();
		this.drawFigure();
		
		display.resetMatrix();
		
	}
	
//	public void halo(){
//		
//		//solange die skala noch nicht durchlaufen ist
//		if(haloStat<haloSkala.length-1){
//			haloStat++;
//		}else{
//			//sosnt wieder von vorne anfangen
//			haloStat=0;
//		}
//		
//		//skalieren
//		display.scale(haloSkala[haloStat]);
//		
//		display.noFill();
//		display.stroke(0);
////		this.drawFigure(pa);		
//		display.translate(position.x, position.y);		
//		display.ellipse(0, 0, 20, 20);
//		
//		display.resetMatrix();
//		this.entscheideFarbe();	
//		
//		display.translate(position.x, position.y);
//		this.drawFigure();
//		display.resetMatrix();
//	}
	
	public void haloWithoutFigure(){
		
		display.translate(position.x, position.y);
		
		//solange die skala noch nicht durchlaufen ist
		if(haloStat<haloSkala.length-1){
			haloStat++;
		}else{
			//sosnt wieder von vorne anfangen
			haloStat=0;
		}
		
		//skalieren
		display.scale(haloSkala[haloStat]);
		
		display.noFill();
		display.stroke(0);
		display.ellipse(0, 0, 20, 20);
		
		display.resetMatrix();
	}
	
	
	public void rotate(){
		display.translate(position.x, position.y);
		
		//solange die skala noch nicht durchlaufen ist
		if(rotatingAngle<PApplet.TWO_PI){
			rotatingAngle+=this.rotationSpeed;
		}else{
			//sosnt wieder von vorne anfangen
			rotatingAngle=0;
		}
		
		//skalieren
		display.rotate(rotatingAngle);
		
		this.entscheideFarbe();
		this.drawFigure();
		
		display.resetMatrix();
		
	}
	
	
	public void pulseAndRotate(){
		
		display.translate(position.x, position.y);
		
		//solange die skala noch nicht durchlaufen ist
		if(rotatingAngle<PApplet.TWO_PI){
			rotatingAngle+=0.1;
		}else{
			//sosnt wieder von vorne anfangen
			rotatingAngle=0;
		}
		
		//skalieren
		display.rotate(rotatingAngle);
		
		//solange die skala noch nicht durchlaufen ist
		if(pulseStat<pulseSkala.length-1){
			pulseStat++;
		}else{
			//sosnt wieder von vorne anfangen
			pulseStat=0;
		}
		
		//skalieren
		display.scale(pulseSkala[pulseStat]);	
		
		//gefuelllt zeichnen
		this.entscheideFarbe();
		this.drawFigure();
		
		//Umgebung zurücksetzen
		display.resetMatrix();
		
	}
	
	public void drawFigure(){
		
		display.rect(0, 0, 0+20, 0+20);
//		pa.triangle(-20,+20, 0, -20, +20, +20);
		
	}
	
	//commandTarget
	public void commandDestination(PVector dest){
		//neues Zile setzen
		this.destinationVector=dest;
		//neue Blickrichtung berechnen
		berechneNeueBlickrichtung();
	}
	
	/**
	 * Platzhaltermethode fuer das angreifen einer andern Unit
	 * @param dest
	 */
	public void commandTarget(PVector dest){
//		//neues Zile setzen
//		this.destinationVector=dest;
//		//neue Blickrichtung berechnen
//		berechneNeueBlickrichtung();
	}
	
	public void calcNewPosition(){
		//wenn aktuelle position nicht nahe herankommt an ziel, dann weiter bewegen
		if(position.dist(destinationVector)>3){
			//Richtugnsvector berechnen
			direction=position.sub( destinationVector, position);
			//richtungsvector normieren
			direction.normalize();
			//normierten Richtungsvector zur position hinzurechnen
			position.add(direction.mult(direction, movementSpeed));
			//zeichne Schweif
			drawTail();
		}
	}
	



	protected void drawTail() {
		
		//Zielpunkt berechnen
		PVector ende=direction.get();
		ende.mult(this.movementSpeed*schweiflaenge*-1);
//		ende=ende.sub(position,ende);
//		ende=direction.add(ende, position);
		
		display.translate(position.x, position.y);
		display.fill(0);
		
		display.line(0, 0, ende.x/2, ende.y/2);
		display.line(1, 1, ende.x/2, ende.y/2);
		display.line(-1, -1, ende.x/2, ende.y/2);
		
		display.line(ende.x/2, ende.y/2, ende.x, ende.y);
		display.line(ende.x*1.1f, ende.y*1.1f, ende.x*1.2f, ende.y*1.2f);
		display.line(ende.x*1.3f, ende.y*1.3f, ende.x*1.4f, ende.y*1.4f);
		
//		for(int i=1;i<10;i+=4){
//			
//			pa.line(ende.x+i, ende.y+i, ende.x+i, ende.y+i);
//			
//		}
		
		display.resetMatrix();
		
	}


//	public void setDestination(PVector v){
//		this.destinationVector=v;	
//	}
	
	public void setMode(int mode){
		this.mode=mode;
	}

	
	public boolean isInner(PVector clickVector){
		if(position.dist(clickVector)<this.activateRadius){
			//Einheit für die Steureung aktivieren
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
	

	public void entscheideFarbe(){
		if(this.active){
			display.fill(this.activeColor.getRed(), this.activeColor.getGreen(), this.activeColor.getBlue());
		}else{
			display.fill(this.passiveColor.getRed(), this.passiveColor.getGreen(), this.passiveColor.getBlue());
		}
	}
	
	public void entscheideLineFarbe(){
		if(this.active){
			display.stroke(this.activeColor.getRed(), this.activeColor.getGreen(), this.activeColor.getBlue());
		}else{
			display.stroke(this.passiveColor.getRed(), this.passiveColor.getGreen(), this.passiveColor.getBlue());
		}
	}
	
	public void berechneNeueBlickrichtung(){
		//berechne neue Blickrichtung
		if(direction.x>0){
			//rechts
			actualAngle=PVector.angleBetween(direction, new PVector(0,-1));
		}else{
			//links
			actualAngle=PApplet.TWO_PI - PVector.angleBetween(direction, new PVector(0,-1));
		}
	}
	
	public void zeicheFigurNachVektoren(List<PVector>liste){
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
				display.line(leastPoint.x, leastPoint.y, actualPoint.x, actualPoint.y);								
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
	 * Methode soll benutzt werden, wenn Einheit zerstört wird
	 * (trägt einheit aus der globalen Einheiten Liste aus)
	 */
	public void delete(){
		TestUnitBeta.globalUnits.remove(this);
	}
}
