package de.fhb.defenderTouch.menu;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PMatrix;
import processing.core.PVector;

public class Menu {

	/**
	 * Ist das Applet auf dem die Einheiten zugeordnet sind
	 */
	protected PApplet mainPoint;
	//protected PApplet mainPoints;

	/**
	 * aktuelle Position der Einheit
	 */
	protected PVector position;
	

	/**
	 * aktuelle Position der Einheit
	 */
	protected PVector menue1;
	
	/**
	 * aktuelle Position der Einheit
	 */
	protected PVector menue2;
	
	/**
	 * aktuelle Position der Einheit
	 */
	protected PVector menue3;
	
	/**
	 * aktuelle Position der Einheit
	 */
	protected PVector menue4;
	
	/**
	 * aktuelle Position der Einheit
	 */
	protected PVector menue5;

	
	/**
	 * aktuelle Position der Einheit
	 */
	protected PVector menue6;

	/**
	 * aktuelle Sichtbarkeit
	 */
	protected boolean menuOpen = false;

	/**
	 * aktuelle Sichtbarkeit
	 */
	protected boolean menuCloseClicked = false;

	/**
	 * Aktivierungsradius eines Menupunkts
	 */
	protected float activatediameterMenu = 38;

	/**
	 * Aktivierungsradius eines Menupunkts
	 */
	protected float activateRadiusBuildings = 35;
	
	// /**
	// * sagt aus ob ein Menupunkt geklickt wurde
	// */
	// protected boolean menuActive = false;

	/**
	 * Konstruktor
	 */
	public Menu(PApplet display) {
		this.position = new PVector(0, 0);
		this.mainPoint = display;
		//this.mainPoints = display;
		
		menue1=new PVector(-100,-100);
		menue2=new PVector(-100,-100);
		menue3=new PVector(-100,-100);
		menue4=new PVector(-100,-100);
		menue5=new PVector(-100,-100);
		menue6=new PVector(-100,-100);
		
	}

	/**
	 * Menupunkt anzeigen
	 */
	public void showMenuPoint(PVector position) {
		this.position = position;
		this.menuOpen = true;
	}

	/**
	 * is always been done
	 */
	public void drawMenu() {

		mainPoint.ellipseMode(PConstants.CENTER);
//		mainPoint.translate(this.position.x, this.position.y);
		
		if (menuOpen) {

			mainPoint.noFill();
			mainPoint.stroke(100);
			
			float drehung=0f;
			float drehungProUntermenue=PApplet.TWO_PI/6;
			
//			for(;drehung<PApplet.TWO_PI;){
//				mainPoint.translate(this.position.x, this.position.y);
//				System.out.println(drehung+"");
//				mainPoint.rotate(drehung);
//				PVector v=new PVector(0,-40);
//				v.rotate(drehung);
//				v.add(position);
//				
//				System.out.println(v+"");
//				mainPoint.ellipse(0, -40, 38, 38);
//				drehung=drehung+drehungProUntermenue;
//				mainPoint.resetMatrix();
//				
//			}
			
			
		mainPoint.translate(this.position.x, this.position.y);
//		System.out.println(drehung+"");
		mainPoint.rotate(0);
		menue1=new PVector(0,-40);
		menue1.rotate(0);
		menue1.add(position);
//		System.out.println(menue1+"");
		mainPoint.ellipse(0, -40, 38, 38);
		drehung=drehung+drehungProUntermenue;
		mainPoint.resetMatrix();
		
		
		mainPoint.translate(this.position.x, this.position.y);
//		System.out.println(drehung+"");
		mainPoint.rotate(PApplet.TWO_PI/6);
		menue2=new PVector(0,-40);
		menue2.rotate(PApplet.TWO_PI/6);
		menue2.add(position);
//		System.out.println(menue2+"");
		mainPoint.ellipse(0, -40, 38, 38);
		drehung=drehung+drehungProUntermenue;
		mainPoint.resetMatrix();
		
		mainPoint.translate(this.position.x, this.position.y);
//		System.out.println(drehung+"");
		mainPoint.rotate(PApplet.TWO_PI/6*2);
		menue3=new PVector(0,-40);
		menue3.rotate(PApplet.TWO_PI/6*2);
		menue3.add(position);
//		System.out.println(menue3+"");
		mainPoint.ellipse(0, -40, 38, 38);
		drehung=drehung+drehungProUntermenue;
		mainPoint.resetMatrix();
		
		
		mainPoint.translate(this.position.x, this.position.y);
//		System.out.println(drehung+"");
		mainPoint.rotate(PApplet.TWO_PI/6*3);
		menue4=new PVector(0,-40);
		menue4.rotate(PApplet.TWO_PI/6*3);
		menue4.add(position);
//		System.out.println(menue4+"");
		mainPoint.ellipse(0, -40, 38, 38);
		drehung=drehung+drehungProUntermenue;
		mainPoint.resetMatrix();
		
		mainPoint.translate(this.position.x, this.position.y);
//		System.out.println(drehung+"");
		mainPoint.rotate(PApplet.TWO_PI/6*4);
		menue5=new PVector(0,-40);
		menue5.rotate(PApplet.TWO_PI/6*4);
		menue5.add(position);
//		System.out.println(menue5+"");
		mainPoint.ellipse(0, -40, 38, 38);
		drehung=drehung+drehungProUntermenue;
		mainPoint.resetMatrix();
		
		
		mainPoint.translate(this.position.x, this.position.y);
		mainPoint.rotate(PApplet.TWO_PI/6*5);
		menue6=new PVector(0,-40);
		menue6.rotate(PApplet.TWO_PI/6*5);
		menue6.add(position);
		mainPoint.ellipse(0, -40, 38, 38);
		drehung=drehung+drehungProUntermenue;
		mainPoint.resetMatrix();

			

			
//			mainPoint.rotate((float) Math.PI);
//
//			// Menupoint
//			mainPoint.ellipse(0, 0, 20, 20);
//			// Groundunit Point
//			mainPoint.translate(-35, -15);
//			mainPoint.triangle(-5,+5, 0, -5, +5, +5);
//			mainPoint.ellipse(0, 0, 30, 30);
//			
//			
//			//mainPoints.triangle(-20, -10, -15, -20, -10, -10);
//			// Supportunit Point
//			mainPoint.translate(35, -20);
//			//Punkte hinzufuegen
//			ArrayList<PVector> vektoren=new ArrayList<PVector>();
//			vektoren.add(new PVector(0, -8));
//			vektoren.add(new PVector(0, 8));
//			vektoren.add(new PVector(0, 0));
//			vektoren.add(new PVector(8,0));
//			vektoren.add(new PVector(-8, 0));
//
//			//zeichnen
//			//GraphicTools.zeicheFigurNachVektoren(vektoren,display);
//			mainPoint.ellipse(0, 0, 30, 30);
//			// Defenceunit Point
//			mainPoint.translate(35, 20);
//			mainPoint.ellipse(0, 0, 30, 30);

			
			
			mainPoint.translate(-35, -15);
			
			// System.out.println(this.position.x + " und " + this.position.y);
		}

	}

	/**
	 * 
	 * @param clickVector
	 * @return
	 */
	public boolean isInner(PVector clickVector) {
		
		if(this.isMenuOpen()){
			
			if (this.menue1.dist(clickVector) < this.activatediameterMenu/2) {
				System.out.println("Menue 1");
			}
			
			
			if (this.menue2.dist(clickVector) < this.activatediameterMenu/2) {
				System.out.println("Menue 2");
			}
			
			if (this.menue3.dist(clickVector) < this.activatediameterMenu/2) {
				System.out.println("Menue 3");
			}
			
			if (this.menue4.dist(clickVector) < this.activatediameterMenu/2) {
				System.out.println("Menue 4");
			}
			
			if (this.menue5.dist(clickVector) < this.activatediameterMenu/2) {
				System.out.println("Menue 5");
			}
			
			if (this.menue6.dist(clickVector) < this.activatediameterMenu/2) {
				System.out.println("Menue 6");
			}
			
		}

		if (position.dist(clickVector) < this.activatediameterMenu/2) {
			// Einheit für die Steureung aktivieren
			// setMenuActive(true);
			return true;
		} else {
			// setMenuActive(false);
			return false;
		}
	}

	/**
	 * 
	 * @param clickVector
	 * @return
	 */
	public boolean isInnerGroundUnit(PVector clickVector) {
		PVector helpPosition = new PVector(0, 0);
		helpPosition.x = position.x + 35;
		helpPosition.y = position.y - 35;
		 System.out.println(position.x + " und "+position.y +
		 " oder "+helpPosition.x + " und " + helpPosition.y + " ist "+
		 helpPosition.dist(clickVector));
		if (helpPosition.dist(clickVector) < this.activateRadiusBuildings/2) {
			createGroundUnit();
			return true;
		} else {
			return false;
		}
	}

	public void createGroundUnit() {
		// verweis auf andies klasse
		// TODO
	}

	public void setMenuOpen(boolean menuOpen) {
		this.menuOpen = menuOpen;
	}

	public boolean isMenuOpen() {
		return menuOpen;
	}

}
