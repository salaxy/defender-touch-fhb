package de.fhb.defenderTouch.units.root;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import de.fhb.defenderTouch.audio.FormatProblemException;
import de.fhb.defenderTouch.audio.SampleThread;
import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Gamemap;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.graphics.GraphicTools;
import de.fhb.defenderTouch.graphics.VectorHelper;
import de.fhb.defenderTouch.units.amunition.ShootWithRange;

/**
 * BaseUnit Version 0.8 vom 24.11.2011
 * 
 * @author Andy Klay <klay@fh-brandenburg.de>
 * 
 */
public class BaseUnit {

	protected DefenderControl gamelogic;

	protected int healthpointsMax = 250;
	protected int healthpointsStat = 250;
	protected int damagePerHit = 50;
	protected int attackRange = 200;

	protected boolean isAutoAttackOn = false;

	/**
	 * Spielerzugehoerigkeit der Unit
	 */
	protected int playerID;

	/**
	 * Referenz auf Spielkarte
	 */
	protected Gamemap map;

	/**
	 * Zeit des letzten Angriffs
	 */
	protected long lastShootTime = System.currentTimeMillis();

	/**
	 * Zeit bis zum naechsten Schuss
	 */
	protected long shootMinTime = 3000;

	/**
	 * beinhaltet alle Einheiten die existent sind
	 */
	public static int idCounter = 0;

	public static final int MODE_NORMAL = 0;
	public static final int MODE_ROTATE = 1;
	public static final int MODE_PULSE = 2;
	public static final int MODE_ROTATE_AND_PULSE = 3;
	public static final int MODE_HALO = 4;
	public static final int MODE_PULSE_IF_ACTIVE = 5;

	/**
	 * Skala nach der Einheit pulsiert
	 */
	protected float[] pulseSkala = { 1.0f, 0.9f, 0.8f, 0.7f, 0.6f, 0.5f };

	/**
	 * Skala fuer den Radius des Halo
	 */
	protected float[] haloSkala;

	/**
	 * aktuelle Position der Einheit
	 */
	protected Vector2f position;

	/**
	 * legt die erste Blickrichtungfest
	 */
	protected Vector2f direction = new Vector2f(0, 1);

	/**
	 * aktueller rotationsgrad (aktuelle Drehung der Rotation)
	 */
	protected float rotatingAngle = 0;

	/**
	 * aktuelle Skalierung fuer das Pulsieren
	 */
	protected int pulseStat = 0;

	/**
	 * aktuelle Skalierung fuer den Halo
	 */
	protected int haloStat = 0;

	/**
	 * legt das erscheinungbild der Einheit fest
	 */
	protected int mode;

	/**
	 * sagt aus ob Einheit steuerbar ist und als Aktiv erscheint
	 */
	protected boolean active = false;

	/**
	 * sagt aus um welchen längenfaktor der Schweif erscheint
	 */
	protected int schweiflaenge = 40;

	/**
	 * Blickrichtung
	 */
	protected float actualAngle = 0;

	/**
	 * Geschwindigkeitsfaktor der Rotation
	 */
	protected float rotationSpeed = 0.1f;

	/**
	 * Bewegungsgeschwindigkeit
	 */
	// bei zu großen werten von movementSpeed kann das objekt zum schwingen
	// kommen
	protected float movementSpeed = 2f;

	/**
	 * Farbe in der Einheit gezeichnet wird, wenn Einheit Aktiv ist, also
	 * Einheit Steuerbar ist
	 */
	protected Color activeColor = Color.red;

	/**
	 * Farbe in der Einheit gezeichnet wird, wenn Einheit NICHT Aktiv ist
	 */
	protected Color passiveColor = Color.black;

	/**
	 * Zielvektor der Einheit
	 */
	protected Vector2f destinationVector;

	/**
	 * Aktivierungsradius um das Zentrum der Einheit, in dem diese angeklickt
	 * werden kann
	 */
	protected float activateRadius = 20;

	/**
	 * Kollisionssradius um das Zentrum der Einheit
	 */
	protected float collisionRadius = 20;

	/**
	 * jede einheit hat eine eindeutige ID zur identifizierung
	 */
	protected int id;

	/**
	 * jedes Gebäude hat ein Level
	 */
	protected int level = 1;

	/**
	 * sagt aus ob sich die Unit gerade in Bewegung befindet
	 */
	protected boolean isMoving = false;

	/**
	 * Besitzer der Unit
	 */
	protected Player owner;

	/**
	 * 
	 * @param x
	 * @param y
	 * @param mode
	 * @param player
	 * @param gamelogic
	 * @param unitColor
	 */
	public BaseUnit(int x, int y, int mode, Player player, DefenderControl gamelogic) {

		this.mode = mode;
		this.owner = player;
		this.playerID = player.getId();
		this.position = new Vector2f(x, y);
		this.destinationVector = new Vector2f(x, y);
		this.berechneNeueBlickrichtung();
		this.initHaloSkala();
		this.passiveColor = this.owner.getUnitColor();
		this.gamelogic = gamelogic;
		this.map = gamelogic.getMap();

		// Id verpassen
		this.id = BaseUnit.idCounter;
		// und ids weiter zählen
		BaseUnit.idCounter++;

		// fuegt sich selbst zur globalen Menge der Einheiten hinzu
		this.gamelogic.getGlobalUnits().add(this);
	}

	/**
	 * initialisiert die Haloskala
	 */
	protected void initHaloSkala() {
		haloSkala = new float[50];
		int i = 0;
		for (float f = 1.01f; i < haloSkala.length; f += 0.1f) {
			haloSkala[i] = f;
			i++;
		}
	}

	/**
	 * zeichnet die Einheit, wird je Frame 1 mal aufgerufen!
	 * 
	 * @param player
	 * @param graphics
	 * @param focus
	 *            - wird Unit Aktiv gezeichnet oder nicht
	 */
	public void paint(Player player, Graphics graphics, boolean drawActive) {

		// Umrechnung auf Spielersicht
		// Transformationen
		calcDrawPosition(player, graphics);

		if (drawActive) {

			this.drawActiveAppearance(player, graphics);

		} else {

			// entscheide ueber erscheinungsbild
			switch (mode) {
			case MODE_ROTATE:
				this.drawRotateAppearance(player, graphics);
				break;
			case MODE_PULSE:
				this.drawPulseAppearance(player, graphics);
				break;
			case MODE_ROTATE_AND_PULSE:
				this.drawRotateAndPulseAppearance(player, graphics);
				this.drawHalo(player, graphics);
				break;
			case MODE_HALO:
				this.drawHalo(player, graphics);
				this.drawNormalAppearance(player, graphics);
				break;
			case MODE_NORMAL:
				this.drawNormalAppearance(player, graphics);
				break;
			case MODE_PULSE_IF_ACTIVE:
				this.drawPulseIfActive(player, graphics);
				break;
			}
		}

		// zeichne Schweif wenn in bewegung
		if (this.isMoving) {
			drawTail(player, graphics);
		}

		// zurücksetzen der Umgebung, Seiteneffekte vermeiden
		graphics.resetTransform();

	}

	/**
	 * Zeichne Figur im Aktiven Zustand
	 * 
	 * @param player
	 * @param graphics
	 */
	public void drawActiveAppearance(Player player, Graphics graphics) {

		graphics.setColor(this.activeColor);
		this.drawFigure(graphics);
		graphics.resetTransform();
	}

	/**
	 * zeichne Figur im Normalen Zustand
	 * 
	 * @param player
	 * @param graphics
	 */
	protected void drawNormalAppearance(Player player, Graphics graphics) {

		// Umrechnung auf Spielersicht

		// farben setzen
		graphics.setColor(this.passiveColor);

		// zeichnen
		this.drawFigure(graphics);
		graphics.resetTransform();
	}

	/**
	 * zeichne Figur im Normalen Zustand, wenn Aktiv dann pulsierend
	 * 
	 * @param player
	 * @param graphics
	 */
	protected void drawPulseIfActive(Player player, Graphics graphics) {
		// Wenn aktiv dann normal
		if (!active) {
			// normal zeichnen
			this.drawNormalAppearance(player, graphics);
		} else {
			// pulsierend zeichnen
			this.drawPulseAppearance(player, graphics);
		}
	}

	/**
	 * zeichne Figur im pulsierend
	 * 
	 * @param player
	 * @param graphics
	 */
	protected void drawPulseAppearance(Player player, Graphics graphics) {

		// solange die skala noch nicht durchlaufen ist
		if (pulseStat < pulseSkala.length - 1) {
			pulseStat++;
		} else {
			// sonst wieder von vorne anfangen
			pulseStat = 0;
		}

		// skalieren
		graphics.scale(pulseSkala[pulseStat], pulseSkala[pulseStat]);

		graphics.setColor(this.passiveColor);
		this.drawFigure(graphics);

		graphics.resetTransform();
	}

	/**
	 * zeichne Halo
	 * 
	 * @param player
	 * @param graphics
	 */
	protected void drawHalo(Player player, Graphics graphics) {

		// Umrechnung auf Spielersicht
		this.calcDrawPosition(player, graphics);

		// solange die skala noch nicht durchlaufen ist
		if (haloStat < haloSkala.length - 1) {
			haloStat++;
		} else {
			// sosnt wieder von vorne anfangen
			haloStat = 0;
		}

		// skalieren
		graphics.scale(haloSkala[haloStat], haloSkala[haloStat]);

		// zeichnen
		graphics.setColor(Color.black);
		graphics.drawOval(-10, -10, 20, 20);

		graphics.resetTransform();
	}

	/**
	 * zeichne Figur im rotierend
	 * 
	 * @param player
	 * @param graphics
	 */
	protected void drawRotateAppearance(Player player, Graphics graphics) {

		// solange die skala noch nicht durchlaufen ist
		if (rotatingAngle < 360) {
			rotatingAngle += this.rotationSpeed;
		} else {
			// sosnt wieder von vorne anfangen
			rotatingAngle = 0;
		}

		// skalieren
		graphics.rotate(0, 0, rotatingAngle);

		graphics.setColor(this.passiveColor);
		this.drawFigure(graphics);

		graphics.resetTransform();

	}

	/**
	 * zeichne Figur im rotierend und pulsierend
	 * 
	 * @param player
	 * @param graphics
	 */
	protected void drawRotateAndPulseAppearance(Player player, Graphics graphics) {

		// solange die skala noch nicht durchlaufen ist
		if (rotatingAngle < (float)Math.PI*2) {
			rotatingAngle += this.rotationSpeed;
		} else {
			// sosnt wieder von vorne anfangen
			rotatingAngle = 0;
		}

		// skalieren
		graphics.rotate(0, 0, rotatingAngle);

		// solange die skala noch nicht durchlaufen ist
		if (pulseStat < pulseSkala.length - 1) {
			pulseStat++;
		} else {
			// sosnt wieder von vorne anfangen
			pulseStat = 0;
		}

		// skalieren
		graphics.scale(pulseSkala[pulseStat], pulseSkala[pulseStat]);

		// gefuelllt zeichnen
		graphics.setColor(this.passiveColor);
		this.drawFigure(graphics);

		// Umgebung zurücksetzen
		graphics.resetTransform();

	}

	/**
	 * zeichnen des normalen Erscheinungs bildes ohne Effekte
	 */
	protected void drawFigure(Graphics graphics) {
		// zeichne gefuelltes Quadrat
		graphics.fillRect(-10, -10, 20, 20);
	}

	/**
	 * neues Ziel befehlen zu dem die Einheit sich versucht hinzubewegen
	 * 
	 * @param Ziel
	 *            - Vector2f
	 */
	public void commandDestination(Vector2f dest) {

		// neuen Zielvektor setzen
		this.destinationVector = dest;

		// Richtungsvektor berechnen
		direction = VectorHelper.sub(destinationVector, position);
		// richtungsvektor normieren
		direction.normalise();

		// neue Blickrichtung berechnen
		berechneNeueBlickrichtung();
	}

	/**
	 * Platzhaltermethode fuer das angreifen einer andern Unit
	 * 
	 * @param dest
	 */
	public void commandTarget(Vector2f dest) {
		// TODO coming soon
		// setzen des Angroffsziels
	}

	/**
	 * Berechnen des neuen Position, wenn in Bewegung
	 */
	public void update() {

		Vector2f newPosition;

		// wenn aktuelle position noch weit weg vom ziel, dann weiter bewegen
		if (position.distance(destinationVector) > 3) {

			// neue Position erechnen, normierten Richtungsvector zur position
			// hinzurechnen
			newPosition = VectorHelper.add(this.position, VectorHelper.mult(direction, movementSpeed));

			// wenn keien kollision dann bewegen
			if (!isCollision(newPosition)) {

				// neue Position setzen
				this.position = newPosition;

				isMoving = true;

			} else {
				isMoving = false;
			}

		} else {
			isMoving = false;
		}

		// AutoAngriffs Algorithmus aufrufen wenn moeglich
		if (isAutoAttackOn && isAttackPossible()) {
			autoAttack();
		}
	}

	/**
	 * Automatischer Angriff
	 */
	protected void autoAttack() {

		for (BaseUnit u : this.gamelogic.getGlobalUnits()) {

			// wenn eine unit aktiviert wird dann die anderen deaktiveren
			if (u.getPosition().distance(this.position) > this.attackRange) {
				// zu weit weg
			} else {
				// nah dran
				if (this.getPlayerID() != u.getPlayerID() && u.getPlayerID() != DefenderControl.PLAYER_SYSTEM_ID) {
					// Feind erkannt
					// angriff
					this.attack(u);
				}
			}
		}

	}

	/**
	 * Methode sagt vorraus ob Unit kollidieren würde mit einer anderen Unit
	 * 
	 * @param newPosition
	 * @return boolean
	 */
	private boolean isCollision(Vector2f newPosition) {

		for (BaseUnit unit : gamelogic.getGlobalUnits()) {
			// wenn nicht diese Unit (die ist in der menge mit drin)
			if (this.id != unit.id) {
				// und wenn entfernung kleiner ist als die kollisionsradien der
				// beiden einheiten zusammen
				if (VectorHelper.distance(unit.position, newPosition) < (unit.collisionRadius + this.collisionRadius)) {
					System.out.println("UNIT " + this.id + " is in collision at " + newPosition + " with UNIT " + unit.id + " at " + unit.position);

					// nur wenn die naechste position nicht weiter entfernt sein
					// wird, soll sich unit nicht mehr weiter bewegen
					if (!(VectorHelper.distance(unit.position, newPosition) > VectorHelper.distance(unit.position, this.position))) {
						// Kollision liegt vor, bewegung stoppen
						// reset des zielvector (auf aktuelle position)
						// this.destinationVector = this.position;

						// dann ist es eine moegliche kollison
						return true;
					}// sonst wenn die naechste position weiter entfernt sein
						// würde,
						// darf unit sich aber bewegen
				}
			}
		}

		return false;
	}

	/**
	 * Zeichne Schweif
	 */
	protected void drawTail(Player player, Graphics graphics) {

		// Zielpunkt hinter der Einheit berechnen
		// end Vektor fuer jeweiligen Spieler berechnen
		Vector2f ende = direction.copy();
		// Vektor auf laenge 1 kuerzen
		ende.normalise();
		// Richtungsvektor umkehren zum schweif
		ende.scale(schweiflaenge * -2);

		// Berechne Zeichnenposition und setzte
		// Abblidungsmatrix(Transformationsmatix)
		calcDrawPosition(player, graphics);

		// Eigendrehung ausgleichen (wird in calcDrawPostion gesetzt)
		graphics.rotate(0, 0, -this.actualAngle);

		// linien zeichnen
		graphics.drawLine(0, 0, ende.x / 2, ende.y / 2);
		graphics.drawLine(1, 1, ende.x / 2, ende.y / 2);
		graphics.drawLine(-1, -1, ende.x / 2, ende.y / 2);

		graphics.drawLine(ende.x / 2, ende.y / 2, ende.x, ende.y);
		graphics.drawLine(ende.x * 1.1f, ende.y * 1.1f, ende.x * 1.2f, ende.y * 1.2f);
		graphics.drawLine(ende.x * 1.3f, ende.y * 1.3f, ende.x * 1.4f, ende.y * 1.4f);

		graphics.resetTransform();
	}

	/**
	 * legt das erscheinungbild der Einheit neu fest
	 * 
	 * @param mode
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}

	/**
	 * gibt zurück ob Vektor im Aktivierungradius liegt und aktiviert die
	 * Einheit zur Steuerung
	 * 
	 * @param clickVector
	 * @return
	 */
	public boolean isInner(Vector2f clickVector) {

		if (position.distance(clickVector) < this.activateRadius) {
			return true;
		} else {
			return false;
		}
	}

	public void activate() {

		active = true;

		try {
			new SampleThread("/sounds/fehler.mp3", 10.0f, true).start();
		} catch (FormatProblemException e) {
			e.printStackTrace();
		}
	}

	/**
	 * gibt zurück ob Einheit Aktiv
	 * 
	 * @return
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * deaktiviert Einheit
	 */
	public void deactivate() {
		active = false;
	}

	// /**
	// * setzt Füll-Farbe zum Zeichen nach Status der einheit
	// */
	// protected void entscheideFillFarbe(Graphics graphics) {
	//
	// if (this.active) {
	// graphics.setColor(this.activeColor);
	// } else {
	// graphics.setColor(this.passiveColor);
	// }
	// }

	/**
	 * // * setzt Linien-Farbe zum Zeichen nach Status der einheit //
	 */
	// protected void entscheideLineFarbe(Graphics graphics) {
	//
	// if (this.active) {
	// graphics.setColor(this.activeColor);
	// } else {
	// graphics.setColor(this.passiveColor);
	// }
	// }

	/**
	 * Berechnet Blickrichtung der Einheit nach dem Bewegungsvektor
	 */
	protected void berechneNeueBlickrichtung() {
		// berechne neue Blickrichtung
		if (direction.x > 0) {
			// rechts
			actualAngle = VectorHelper.angleBetween(direction, new Vector2f(0, -1));
		} else {
			// links
			actualAngle = VectorHelper.angleBetween(direction, new Vector2f(0, -1));
		}
	}

	/**
	 * Methode soll benutzt werden, wenn Einheit zerstört wird (trägt einheit
	 * aus der globalen Einheiten Liste aus)
	 */
	public void delete() {
		this.deactivate();
		gamelogic.getGlobalUnits().remove(this);
	}

	/**
	 * Angreifen einer anderen Unit
	 * 
	 * @param destinationUnit
	 */
	public void attack(BaseUnit destinationUnit) {
		System.out.println("UNIT " + this.id + " is attacking UNIT " + destinationUnit.id);

		// if(!this.isAutoAttackOn){
		// moveToAttackDestination(destinationUnit);
		// }

		if (isAttackPossible()) {
			this.lastShootTime = System.currentTimeMillis();
			this.startShoot(destinationUnit);
		}

		// //neue Blickrichtung berechnen
		// berechneNeueBlickrichtung();
	}

	/**
	 * erzeugt Schuss
	 * 
	 * @param destinationUnit
	 */
	protected void startShoot(BaseUnit destinationUnit) {
		new ShootWithRange((int) this.position.x, (int) this.position.y, BaseUnit.MODE_NORMAL, this.gamelogic.getPlayerSystem(), destinationUnit, this.damagePerHit, gamelogic);
	}

	/**
	 * Angriffskommando wenn nicht in Reichweite des Ziels dann zum ziel bewegn
	 * 
	 * @param destinationUnit
	 */
	public void moveToAttackDestination(BaseUnit destinationUnit) {

		// TODO verbessern
		// wenn nicht in Angriffsreichweite dann hinfliegen bis in reichweite
		if (this.position.distance(destinationUnit.getPosition()) > this.attackRange) {
			this.commandDestination(destinationUnit.getPosition());
			this.commandDestination(VectorHelper.add(this.position, VectorHelper.mult(direction, (float) (this.attackRange + 5))));
		}

	}

	protected boolean isAttackPossible() {
		return (System.currentTimeMillis() - lastShootTime) > this.shootMinTime;
	}

	public void getDamage(int damage) {
		this.healthpointsStat = this.healthpointsStat - damage;

		if (this.healthpointsStat < 0) {
			this.delete();
		}
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public Vector2f getPosition() {
		return position;
	}

	public float getCollisionRadius() {
		return collisionRadius;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Berechnet Zeichnenposition und setzt
	 * Abblidungsmatrix(Transformationsmatix)
	 * 
	 * @return
	 */
	private void calcDrawPosition(Player player, Graphics graphics) {

		GraphicTools.calcDrawTransformationForSlick(player, graphics, position);

		// eigendrehung hinzurechnen
		graphics.rotate(0, 0, this.actualAngle);
	}

}
