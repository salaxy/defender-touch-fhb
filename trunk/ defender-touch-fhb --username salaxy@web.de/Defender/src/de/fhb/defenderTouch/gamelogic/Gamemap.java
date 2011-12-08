package de.fhb.defenderTouch.gamelogic;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import de.fhb.defenderTouch.graphics.GraphicTools;

public class Gamemap {

	/**
	 * Sichtbare Karte
	 */
	private Image visibleMap;

	/**
	 * Karte mit Informationen dar�ber, wer wo bauen kann, wo Bodeneinheiten
	 * passieren k�nnen usw.
	 */
	private Image informationalMap;

	/**
	 * Konstruktor
	 * 
	 * L�dt die Karte.
	 */
	public Gamemap() {

		try {
			// Kleine Testmap:
			// visibleMap = new Image("./maps/vtestmap_small.png");
			// informationalMap = new Image("./maps/itestmap_small.png");

			// Gro�e Testmap:
			visibleMap = new Image("./maps/vtestmap.png");
			informationalMap = new Image("./maps/itestmap.png");

			// Debug-Map:
			// visibleMap = new Image("./maps/vdebug.png");
			// informationalMap = new Image("./maps/idebug.png");			

			// Neue Map:
			 visibleMap = new Image("./maps/vnewmap.png");
			 informationalMap = new Image("./maps/inewmap.png");

		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Zeichnet die Karte f�r einen angegebenen Spieler.
	 * 
	 * @param graphics
	 * @param player
	 */
	public void zeichne(Graphics graphics, Player player) {

		GraphicTools.calcDrawTransformationForSlick(player, graphics, new Vector2f(0, 0));
		visibleMap.draw();

		// Feldumrandung zeichnen
		// graphics.setColor(new Color(255, 255, 0,55));
		// graphics.fillRect(0f, 0f, 1024, 768);

		graphics.resetTransform();
	}

	/**
	 * Gibt zur�ck, ob Flugeinheiten diese Stelle �berfliegen k�nnen.
	 * 
	 * @param position
	 * @return true oder false
	 */
	public boolean isFlyable(Vector2f position) {
		return (position.x > 10f && position.y < informationalMap.getHeight() - 10f && position.y > 10f && position.y < informationalMap.getWidth() - 10f) ? true : false;
	}

	/**
	 * Gibt zur�ck, ob Bodeneinheiten diese Stelle passieren k�nnen.
	 * 
	 * @param position
	 * @return true oder false
	 */
	public boolean isWalkable(Vector2f position) {
		if (position.x > 0 && position.x < informationalMap.getWidth() - 1 && position.y > 0 && position.y < informationalMap.getHeight() - 1)
			return informationalMap.getColor((int) position.x, (int) position.y).equals(Color.black) ? false : true;
		return false;
	}

	/**
	 * �berpr�ft, ob in diesem Rechteck ein Geb�ude f�r den angegeben Spieler
	 * gebaut werden darf. Es wird dabei nur die Karte beachtet, nicht andere
	 * Einheiten oder Geb�ude.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param player
	 * @return
	 */
	private boolean isBuildable(float x1, float y1, float x2, float y2, int player) {
		float temp = 0f;

		if (x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0 || x1 > informationalMap.getWidth() - 1 || x2 > informationalMap.getWidth() - 1 || y1 > informationalMap.getHeight() - 1
				|| y2 > informationalMap.getHeight() - 1)
			return false;

		for (int x = (int) x1; x < (int) x2; x++)
			for (int y = (int) y1; y < (int) y2; y++) {
				if (player == DefenderControl.PLAYER_ONE_ID) {
					temp = informationalMap.getColor(x, y).r;
					if (informationalMap.getColor(x, y).r < 0.5f) {
						return false;
					}
				}
				if (player == DefenderControl.PLAYER_TWO_ID) {
					temp = informationalMap.getColor(x, y).g;
					if (informationalMap.getColor(x, y).g < 0.5f) {
						return false;
					}
				}
				if (player == DefenderControl.PLAYER_SYSTEM_ID) {
					temp = informationalMap.getColor(x, y).b;
					if (informationalMap.getColor(x, y).b < 0.5f) {
						return false;
					}
				}
			}
		return true;
	}

	/**
	 * �berpr�ft, ob in diesem Rechteck ein Geb�ude f�r den angegeben Spieler
	 * gebaut werden darf. Es wird dabei nur die Karte beachtet, nicht andere
	 * Einheiten oder Geb�ude.
	 * 
	 * @param position
	 * @param player
	 * @return
	 */
	public boolean isBuildable(Vector2f position, int player) {
		return isBuildable(position.x - 20, position.y - 20, position.x + 20, position.y + 20, player);
	}
}
