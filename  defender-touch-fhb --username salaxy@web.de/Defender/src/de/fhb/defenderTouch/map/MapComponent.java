package de.fhb.defenderTouch.map;

import java.awt.Color;
import java.util.LinkedList;
import de.fhb.defenderTouch.units.movable.BaseUnit;
import processing.core.PVector;

public class MapComponent {
	
	
	/* Attribute */
	
	
	/**
	 * Wo sich das MapComponent in der Welt befindet.
	 */
	protected PVector position;
	
	/**
	 * Einheiten, die sich auf diesem MapComponent befinden.
	 */
	private LinkedList<BaseUnit> units = new LinkedList<BaseUnit>();
	
	/**
	 * Dar�ber d�rfen Einheiten laufen.
	 */
	protected boolean isWalkable = true;
	
	/**
	 * Dar�ber d�rfen Einheiten fliegen.
	 */
	protected boolean isFlyable = true;
	
	/**
	 * Mit rotem Rahmen markiert oder nicht.
	 */
	protected boolean isHighlighted = false;
	
	/**
	 * Rahmenfarbe, vorl�ufiges Attribut
	 */
	protected Color borderColor = Color.BLACK;
	
	/**
	 * Highlightened Rahmenfarbe, vorl�ufiges Attribut
	 */
	protected Color borderColorHighlightened = Color.RED;
	
	/**
	 * Hintergrundfarbe
	 */
	protected Color backgroundColor = new Color(64, 128, 64);
	
	
	
	
	/* Methoden */
	
	
	
	
	/**
	 * Konstruktor f�r ein MapComponent (Kartenbestandteil)
	 */
	public MapComponent(PVector position) {
		this.position = position;
	}
	
	/**
	 * Zeichnet sich selbst.
	 */
	public void paint() {
		Map.display.translate(position.x, position.y);
		
		Map.display.fill(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue());
		
		if (isHighlighted)
			Map.display.stroke(borderColorHighlightened.getRed(), borderColorHighlightened.getGreen(), borderColorHighlightened.getBlue());
		else
			Map.display.stroke(borderColor.getRed(), borderColor.getGreen(), borderColor.getBlue());
			
		Map.display.rect(0, 0, 31, 31);
		Map.display.rect(0, 0, 19, 19);

		Map.display.resetMatrix();
	}
	
	/**
	 * Gibt die direkt angrenzenden MapComponents zur�ck.
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
	
	

	/* Setter & Getter */
	
	
	
	/**
	 * Gibt eine Liste der Einheiten zur�ck, die sich auf diesem MapComponent befinden.
	 * @return
	 */
	public LinkedList<BaseUnit> getUnits() {
		return units;
	}

	/**
	 *  L�sst das MapComponent wissen, dass sich eine Einheit auf ihm befindet.
	 * @param unit - Einheit, die sich nun auf diesem MapComponent befindet. 
	 */
	public void AddUnit(BaseUnit unit) {
		if (unit != null) units.add(unit);
	}
	
	/**
	 * L�sst das MapComponent wissen, dass sich eine Einheit nicht mehr auf ihm befindet.
	 * @param unit - Einheit, die sich nun auf diesem MapComponent befindet. 
	 */
	public void DelUnit(BaseUnit unit) {
		if (unit != null)
			if (units.contains(unit)) units.remove(unit);
	}
	
	/**
	 * Gibt zur�ck, ob dieses MapComponent von Bodeneinheiten passierbar ist.
	 * @return
	 */
	public boolean isWalkable() {
		return isWalkable;
	}

	/**
	 * Legt fest, ob dieses MapComponent von Bordeneinheiten passierbar ist.
	 * @param isWalkable
	 */
	public void setWalkable(boolean isWalkable) {
		this.isWalkable = isWalkable;
	}

	/**
	 * Gibt zur�ck, ob dieses MapComponent von Lufteinheiten passierbar ist.
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
