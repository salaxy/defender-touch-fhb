package de.fhb.defenderTouch.gamelogic;

import java.util.concurrent.CopyOnWriteArrayList;
import org.newdawn.slick.Color;
import processing.core.PVector;
import de.fhb.defenderTouch.units.root.BaseUnit;
/**
 * Dies Klasse beinhaltet einstellungen und Positionen 
 * eines Spielers 
 * 
 * @author Andy Klay <klay@fh-brandenburg.de> 
 * 
 */
public class Player {
	
	private float generalAngle;
	
	/**
	 * Position des Ursprungs auf dem Bildschirm fuer den Spieler
	 */
	private PVector originPosition;
	
	private final float zoomMin=0.3f;
	private final float zoomMax=3.0f;
	
	private int side;
	
	/**
	 * Relative Position der Sicht im Verhältnis zum Ursprung (originPosition) des Spielers
	 * als Vektor
	 */
	private PVector viewPosition= new PVector(0,0);
	
	/**
	 * Zoomfaktor der aktuellen Spielersicht
	 */
	private float actualZoom=0.5f;
	
	public static final int RIGHT=0;
	public static final int LEFT=1;
	
	/**
	 * Einheitenfarbe des Spielers
	 */
	private Color unitColor;
	
	/**
	 * Ressourceneinheiten des Spielers
	 */
	private int credits=500;

	/**
	 * Liste der aktivierten Units
	 */
	private CopyOnWriteArrayList<BaseUnit> activeUnits;
	

	public Player(DefenderControl gamelogic, float generalAngle, float actualZoom, PVector originPosition, int side, Color unitColor) {
		
		this.generalAngle=generalAngle;
		this.actualZoom=actualZoom;
		this.originPosition=originPosition;		
		this.side=side;
		this.viewPosition=new PVector(0,0);
		this.activeUnits= new CopyOnWriteArrayList<BaseUnit>();
		this.unitColor=unitColor;
	}

	public Color getUnitColor() {
		return unitColor;
	}

	public boolean isRight() {
		if(this.side==Player.RIGHT){
			return true;
		}else{
			return false;	
		}
	}

	public CopyOnWriteArrayList<BaseUnit> getActiveUnits() {
		return activeUnits;
	}

	public boolean isLeft() {
		if(this.side==Player.LEFT){
			return true;
		}else{
			return false;	
		}
	}
	
	
	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	
	/**
	 * Gibt den Ursprungsvector des Players zurueck
	 * @return PVector
	 */
	public PVector getOriginPosition() {
		return originPosition;
	}
	
	/**
	 * Gibt den Sichtvektor zureuck
	 * @return PVector
	 */
	public PVector getViewPosition() {
		return viewPosition;
	}

	/**
	 * setzt den Sichtvektor
	 * @param viewPosition
	 */
	public void setViewPosition(PVector viewPosition) {
		this.viewPosition = viewPosition;
	}

	public float getActualZoom() {
		return actualZoom;
	}

	public void setActualZoom(float actualZoom) {
		
		//wenn minimum oder maximum nicht überschritten
		if(!(actualZoom>zoomMax||actualZoom<zoomMin)){
			this.actualZoom = actualZoom;			
		}

	}

	public float getGeneralAngle() {
		return generalAngle;
	}
	
	public void addCredits(int credits){
		this.credits=this.credits + credits;
	}


}
