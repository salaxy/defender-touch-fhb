package de.fhb.defenderTouch.oldMapConcept;

import java.awt.Color;
import java.util.LinkedList;
import de.fhb.defenderTouch.units.movable.BaseUnit;
import processing.core.PGraphics;
import processing.core.PVector;

public class MapComponent {
		
	/**
	 * Wo sich das MapComponent in der Welt befindet.
	 */
	protected PVector position;
	
	/**
	 * Einheiten, die sich auf diesem MapComponent befinden.
	 */
	private LinkedList<BaseUnit> units = new LinkedList<BaseUnit>();
	
	/**
	 * Darüber dürfen Einheiten laufen.
	 */
	protected boolean isWalkable = true;
	
	/**
	 * Darüber dürfen Einheiten fliegen.
	 */
	protected boolean isFlyable = true;
	
	/**
	 * Mit rotem Rahmen markiert oder nicht.
	 */
	protected boolean isHighlighted = false;
	
	/**
	 * Rahmenfarbe, vorläufiges Attribut
	 */
	protected Color borderColor = Color.BLACK;
	
	/**
	 * Highlightened Rahmenfarbe, vorläufiges Attribut
	 */
	protected Color borderColorHighlightened = Color.RED;
	
	/**
	 * Hintergrundfarbe
	 */
	protected Color backgroundColor = new Color(64, 128, 64);
	
	public static int number = 0;
	
	protected int nummer;
	
	/**
	 * Konstruktor für ein MapComponent (Kartenbestandteil)
	 */
	public MapComponent(PVector position) {
		this.position = position;
		nummer = ++number;
	}
	
	/**
	 * Zeichnet sich selbst.
	 */
	public void paint(PGraphics display, PVector position, float zoom) {
		display.translate( (-position.x + this.position.x) * zoom, (-position.y + this.position.y) * zoom);
		display.scale(zoom);
		
		display.fill(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue());
		
		if (isHighlighted)
			display.stroke(borderColorHighlightened.getRed(), borderColorHighlightened.getGreen(), borderColorHighlightened.getBlue());
		else
			display.stroke(borderColor.getRed(), borderColor.getGreen(), borderColor.getBlue());
			
		display.rect(0, 0, 31, 31);
		
		display.fill(0);
		display.text(nummer, 1, 13);
		//display.text(zoom, 1, 13);
		//display.text(position.x, 1, 24);
		//display.rect(0, 0, 19, 19);
		display.resetMatrix();
	}
	
	/**
	 * Gibt die direkt angrenzenden MapComponents zurück.
	 * @return
	 */
	public LinkedList<MapComponent> getSurroundingMapComponents() {
		LinkedList<MapComponent> rueckgabe = new LinkedList<MapComponent>();
		
		for (MapComponent element : Map.karte)
			if (element.position.x == position.x &&
				element.position.y == position.y + 32)
				rueckgabe.add(element);
		
		return rueckgabe;
	}
	
	/**
	 * Gibt eine Liste der Einheiten zurück, die sich auf diesem MapComponent befinden.
	 * @return
	 */
	public LinkedList<BaseUnit> getUnits() {
		return units;
	}

	/**
	 *  Lässt das MapComponent wissen, dass sich eine Einheit auf ihm befindet.
	 * @param unit - Einheit, die sich nun auf diesem MapComponent befindet. 
	 */
	public void AddUnit(BaseUnit unit) {
		if (unit != null) units.add(unit);
	}
	
	/**
	 * Lässt das MapComponent wissen, dass sich eine Einheit nicht mehr auf ihm befindet.
	 * @param unit - Einheit, die sich nun auf diesem MapComponent befindet. 
	 */
	public void DelUnit(BaseUnit unit) {
		if (unit != null)
			if (units.contains(unit)) units.remove(unit);
	}
	
	/**
	 * Gibt zurück, ob dieses MapComponent von Bodeneinheiten passierbar ist.
	 * @return
	 */
	public boolean isWalkable() {
		return isWalkable;
	}

	/**
	 * Legt fest, ob dieses MapComponent von Bodeneinheiten passierbar ist.
	 * @param isWalkable
	 */
	public void setWalkable(boolean isWalkable) {
		this.isWalkable = isWalkable;
	}

	/**
	 * Gibt zurück, ob dieses MapComponent von Lufteinheiten passierbar ist.
	 * @return
	 */
	public boolean isFlyable() {
		return isFlyable;
	}

	/**
	 * Legt fest, ob dieses MapComponent von Lufteinheiten passierbar ist.
	 * @return
	 */
	public void setFlyable(boolean isFlyable) {
		this.isFlyable = isFlyable;
	}

	/**
	 * Weltkoordinaten des MapComponent abfragen
	 * @return
	 */
	public PVector getPosition() {
		return position;
	}

	/**
	 * Weltkoordinaten des MapComponent setzen (Vorsicht!)
	 * @param position
	 */
	public void setPosition(PVector position) {
		this.position = position;
	}
}
