package de.fhb.defenderTouch.units.movable;

import processing.core.PApplet;

public class TestUnit 
//implements MouseWheelListener
{

	private final float TWO_PI=(float)Math.PI*2;
	public static final int MODE_NONE=0;
	public static final int MODE_ROTATE=1;
	public static final int MODE_PULSE=2;
	public static final int MODE_BOTH=3;
	private float[] pulseSkala={1.0f, 0.9f, 0.8f, 0.7f, 0.6f, 0.5f};
	private int pulseStat=0;
	private int x;
	private int y;
	private float angle;
	private int mode;
	
	public TestUnit(int x, int y, int mode){
		this.x=x;
		this.y=y;
		this.angle=0;
		this.mode=mode;
	}
	
	
	public void paint(PApplet pa){
		//punkte links,oben,rechts	
		pa.translate(x, y);
		
		//korrektur für dreiecke??????
		pa.translate(1%3*(-20+0+20),1%3*(20+-20+20));
		
		switch(mode){
		case MODE_ROTATE: 	
			this.rotate(pa);
		break;
		case MODE_PULSE :	
			this.pulse(pa);
		break;
		case MODE_BOTH :
			this.rotate(pa);
			this.pulse(pa);
		break;
		}
		

		

		pa.triangle(-20,+20, 0, -20, +20, +20);
//		pa.rect(0, 0, 0+20, 0+20);
		//zurück stellen auf, Seiteneffekte vermeiden
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
		

		
		//solange die skala noch nicht durchlaufen ist
		if(pulseStat<pulseSkala.length-1){
			pulseStat++;
		}else{
			//sosnt wieder von vorne anfangen
			pulseStat=0;
		}
		
		//skalieren
		pa.scale(pulseSkala[pulseStat]);
		
	}
	
	public void rotate(PApplet pa){
		

		
		//solange die skala noch nicht durchlaufen ist
		if(angle<TWO_PI){
			angle+=0.1;
		}else{
			//sosnt wieder von vorne anfangen
			angle=0;
		}
		
		//skalieren
		pa.rotate(angle);
		
	}
}
