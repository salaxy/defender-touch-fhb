package de.fhb.defenderTouch.map;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PVector;

public class MapComponent {
	
	/**
	 *  Das Applet, auf dem dieses Component anzeigt werden soll.
	 */
	protected PApplet display;
	
	/**
	 * Referenz auf das Map-Objekt, zu dem das Component gehört.
	 */
	protected Map map;
	
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
	 * Konstruktor für ein Component (Kartenbestandteil)
	 */
	public MapComponent(PApplet display, Map map) {
		this.display = display;
		this.map = map;
	}
	
	/**
	 * Zeichnet sich selbst.
	 */
	public void paint(PVector position) {
		display.translate(position.x, position.y);
		display.rotate(map.angle);
		
		
		if (isHighlighted)
			display.stroke(borderColorHighlightened.getRed(), borderColorHighlightened.getGreen(), borderColorHighlightened.getBlue());
		else
			display.stroke(borderColor.getRed(), borderColor.getGreen(), borderColor.getBlue());
			
		display.rect(0, 0, 31, 31);

		display.resetMatrix();
	}
	
}
