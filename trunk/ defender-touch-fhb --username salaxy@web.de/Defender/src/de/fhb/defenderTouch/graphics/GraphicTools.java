package de.fhb.defenderTouch.graphics;

import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import de.fhb.defenderTouch.gamelogic.Player;

public class GraphicTools {

	/**
	 * hilfsmethode zum Zeichnen von Figuren nach einer collection Vektoren
	 */
	public static void zeicheFigurNachVektoren(List<Vector2f> liste, Graphics pa) {
//		Vector2f firstPoint = null;
		Vector2f actualPoint = null;
		Vector2f leastPoint = null;
		int num = 0;

		// alle punkte durchlaufen
		for (Iterator<Vector2f> i = liste.iterator(); i.hasNext();) {
			// aktuellen punkt holen
			actualPoint = i.next();

			// wenn nicht erster punkt dann zeichen linen zwischen ersten und
			if (num > 0) {
				pa.drawLine(leastPoint.x, leastPoint.y, actualPoint.x, actualPoint.y);
			} else {
//				firstPoint = actualPoint;
			}
			num++;

			leastPoint = actualPoint;
		}

	}

	/**
	 * F�hrt die n�tigen Transformationen aus die n�tig sind um Objekte auf dem
	 * Bildschirm an der richtigen zu zeichen
	 * 
	 * @param player
	 *            - Spielerobjekt, wichtig fuer die unterscheidung Rechter oder
	 *            Linker Bildschrim (Berechnungstechnisch)
	 * @param graphics
	 *            - gibt fuer welchem Bildschirm die Abbildung berechnet wird
	 * @param position
	 *            - Postion des zu zeichnenden Objekts
	 */
	public static void calcDrawTransformationForSlick(Player player, Graphics graphics, Vector2f position) {
		
		// Berechnung des neuen Koordinaten Ursprungs Vektors
		Vector2f drawPosition = new Vector2f(0,0);		
		drawPosition.setTheta(player.getGeneralAngle());
		drawPosition.add(player.getOriginPosition());
		drawPosition.add(player.getOriginOffset());
//		drawPosition.add(player.getAbsolutViewPoint());

		
		// Transformationen im Verh�ltnis zum Ursprung (Zoom, Genereller Winkel)
		graphics.translate(drawPosition.x, drawPosition.y);
		graphics.scale(player.getActualZoom(), player.getActualZoom());
		graphics.rotate(0, 0, player.getGeneralAngle());

		// zeichne an Position im Verh�ltnis zu gesamt Transformation
		graphics.translate(position.x, +position.y);		
	}

	public static Vector2f calcInputVector(Vector2f clickVector, Player player) {

		// Klickvektor zur�ck rechnen auf spielkoordinaten
		Vector2f realClickKoordinates = clickVector.copy();
//		System.out.println("originalclick on Screen at: " + realClickKoordinates.x + ", " + realClickKoordinates.y);
		realClickKoordinates.sub(player.getOriginPosition());
		//herausrechnen von spielerdrehung(qausi zueuckdrehen)
		realClickKoordinates.sub(player.getOriginOffset());
//		realClickKoordinates.sub(player.getAbsolutViewPoint());
		realClickKoordinates.setTheta(realClickKoordinates.getTheta()-player.getGeneralAngle());
		realClickKoordinates.scale(1 / player.getActualZoom());
		System.out.println("click on gamemap at: " + realClickKoordinates.x + ", " + realClickKoordinates.y);

		return realClickKoordinates;
	}

}
