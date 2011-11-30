package de.fhb.defenderTouch.gamelogic;

import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

import de.fhb.defenderTouch.units.root.BaseUnit;
/**
 * Dies Klasse beinhaltet einstellungen und Positionen 
 * eines Spielers 
 * 
 * @author Andy Klay <klay@fh-brandenburg.de> 
 * 
 */
public class Player {
	
	/**
	 * generelle Drehung der View des Spielers
	 */
	private float generalAngle;
	
	/**
	 * Position des Ursprungs auf dem Bildschirm fuer den Spieler
	 */
	private Vector2f originPosition;
	
	private final float zoomMin=0.3f;
	private final float zoomMax=3.0f;
	
	/**
	 * Relative Position der Sicht im Verhältnis zum Ursprung (originPosition) des Spielers
	 * als Vektor
	 */
	private Vector2f viewPosition= new Vector2f(0,0);
	
	/**
	 * Zoomfaktor der aktuellen Spielersicht
	 */
	private float actualZoom=0.5f;
	
	/**
	 * Einheitenfarbe des Spielers
	 */
	private Color unitColor;
	
	/**
	 * Ressourceneinheiten des Spielers
	 */
	private int credits=500;

	/**
	 * Liste der aktivierten Units des Spielers
	 */
	private CopyOnWriteArrayList<BaseUnit> activeUnits;
	
	private int id;

	public Player(DefenderControl gamelogic, float generalAngle, float actualZoom, Vector2f originPosition, Color unitColor,int id) {
		
		this.id=id;
		this.generalAngle=generalAngle;
		this.actualZoom=actualZoom;
		this.originPosition=originPosition;		
		this.viewPosition=new Vector2f(0,0);
		this.activeUnits= new CopyOnWriteArrayList<BaseUnit>();
		this.unitColor=unitColor;
	}

	public Color getUnitColor() {
		return unitColor;
	}


	public CopyOnWriteArrayList<BaseUnit> getActiveUnits() {
		return activeUnits;
	}


	
	public int getId() {
		return id;
	}
	
	
	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	
	/**
	 * Gibt den Ursprungsvector des Players zurueck
	 * @return Vector2f
	 */
	public Vector2f getOriginPosition() {
		return originPosition;
	}
	
	/**
	 * Gibt den Sichtvektor zureuck
	 * @return Vector2f
	 */
	public Vector2f getViewPosition() {
		return viewPosition;
	}

	/**
	 * setzt den Sichtvektor
	 * @param viewPosition
	 */
	public void setViewPosition(Vector2f viewPosition) {
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
