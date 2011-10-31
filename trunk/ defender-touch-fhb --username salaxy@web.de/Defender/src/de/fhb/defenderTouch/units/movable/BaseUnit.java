package de.fhb.defenderTouch.units.movable;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PVector;
import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.interfaces.Drawable;

/**
 * BaseUnit Version 0.5 vom 23.10.2011
 * 
 * @author Andy Klay <klay@fh-brandenburg.de>
 * 
 */
public class BaseUnit implements Drawable {

	private DefenderControl gamelogic;

	private int healthpointsMax = 250;
	private int healthpointsStat = 250;
	private int damagePerHit = 50;
	private int attackRange = 100;

	private int playerID;

	/**
	 * beinhaltet alle Einheiten die existent sind
	 */
	// public static ArrayList<BaseUnit> globalUnits=new ArrayList<BaseUnit>();

	/**
	 * beinhaltet alle Einheiten die existent sind
	 */
	public static int idCounter = 0;

	/**
	 * Ist das Applet auf dem die einheiten zugeordnet sind
	 */
	protected PApplet display;

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
	protected PVector position;

	/**
	 * legt die erste Blickrichtungfest
	 */
	protected PVector direction = new PVector(0, 1);

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
	protected Color activeColor = Color.ORANGE;

	/**
	 * Farbe in der Einheit gezeichnet wird, wenn Einheit NICHT Aktiv ist
	 */
	protected Color passiveColor = Color.BLACK;

	/**
	 * Zielvektor der Einheit
	 */
	protected PVector destinationVector;

	/**
	 * Aktivierungsradius um das Zentrum der Einheit, in dem diese angeklickt
	 * werden kann
	 */
	protected float activateRadius = 15;

	/**
	 * Kollisionssradius um das Zentrum der Einheit
	 */
	protected float collisionRadius = 15;

	/**
	 * jede einheit hat eine eindeutige ID zur identifizierung
	 */
	protected int id;
	
	/**
	 * jedes Gebäude hat ein Level
	 */
	protected int level = 1;
	
	
	protected boolean bewegtSichGerade=false;

	public BaseUnit(int x, int y, int mode, int playerID, PApplet disp) {

		this.display = disp;
		this.mode = mode;
		this.playerID = playerID;
		this.position = new PVector(x, y);
		this.destinationVector = new PVector(x, y);
		this.berechneNeueBlickrichtung();
		this.initHaloSkala();

		gamelogic = DefenderControl.getInstance(display);

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
	 */
	public void paint(Player player) {



		// entscheide ueber erscheinungsbild
		switch (mode) {
		case MODE_ROTATE:
			this.drawRotateAppearance(player);
			break;
		case MODE_PULSE:
			this.drawPulseAppearance(player);
			break;
		case MODE_ROTATE_AND_PULSE:
			this.drawRotateAndPulseAppearance(player);
			this.drawHalo(player);
			break;
		case MODE_HALO:
			this.drawHalo(player);
			this.drawNormalAppearance(player);
			break;
		case MODE_NORMAL:
			this.drawNormalAppearance(player);
			break;
		case MODE_PULSE_IF_ACTIVE:
			this.drawPulseIfActive(player);
			break;
		}

		
		// zeichne Schweif wenn in bewegung
		if(this.bewegtSichGerade){
			drawTail(player);			
		}

		
		// zurücksetzen der Umgebung, Seiteneffekte vermeiden
		// TODO hier eigentlich unnötig
		display.resetMatrix();
	}

	/**
	 * zeichne Figur im Normalen Zustand
	 * @param player 
	 */
	protected void drawNormalAppearance(Player player) {
		
		//Umrechnung auf Spielersicht
		PVector drawPosition=calcDrawPosition(player);
		//Transformationen
		display.translate(drawPosition.x, drawPosition.y);		
		display.rotate(actualAngle+player.getGeneralAngle());
		display.scale(0+player.getGeneralScale());		
		
		display.fill(0);
		this.entscheideFarbe();
		this.drawFigure();
		display.resetMatrix();
	}

	/**
	 * zeichne Figur im Normalen Zustand, wenn Aktiv dann pulsierend
	 * @param player 
	 */
	protected void drawPulseIfActive(Player player) {
		// Wenn aktiv dann normal
		if (!active) {
			// normal zeichnen
			this.drawNormalAppearance(player);
		} else {
			// pulsierend zeichnen
			this.drawPulseAppearance(player);
		}
	}

	/**
	 * zeichne Figur im pulsierend
	 * @param player 
	 */
	protected void drawPulseAppearance(Player player) {

		//Umrechnung auf Spielersicht
		PVector drawPosition=calcDrawPosition(player);
		//Transformationen
		display.translate(drawPosition.x, drawPosition.y);		
		display.rotate(actualAngle+player.getGeneralAngle());
		display.scale(0+player.getGeneralScale());	

		// solange die skala noch nicht durchlaufen ist
		if (pulseStat < pulseSkala.length - 1) {
			pulseStat++;
		} else {
			// sosnt wieder von vorne anfangen
			pulseStat = 0;
		}

		// skalieren
		display.scale(pulseSkala[pulseStat]);

		this.entscheideFarbe();
		this.drawFigure();

		display.resetMatrix();
	}

	/**
	 * zeichne Halo
	 * @param player 
	 */
	protected void drawHalo(Player player) {

		//Umrechnung auf Spielersicht
		PVector drawPosition=calcDrawPosition(player);
		//Transformationen
		display.translate(drawPosition.x, drawPosition.y);		
		display.rotate(actualAngle+player.getGeneralAngle());
		display.scale(0+player.getGeneralScale());	

		// solange die skala noch nicht durchlaufen ist
		if (haloStat < haloSkala.length - 1) {
			haloStat++;
		} else {
			// sosnt wieder von vorne anfangen
			haloStat = 0;
		}

		// skalieren
		display.scale(haloSkala[haloStat]);

		display.noFill();
		display.stroke(0);
		display.ellipse(0, 0, 20, 20);

		display.resetMatrix();
	}

	/**
	 * zeichne Figur im rotierend
	 * @param player 
	 */
	protected void drawRotateAppearance(Player player) {

		//Umrechnung auf Spielersicht
		PVector drawPosition=calcDrawPosition(player);
		//Transformationen
		display.translate(drawPosition.x, drawPosition.y);		
		display.rotate(actualAngle+player.getGeneralAngle());
		display.scale(0+player.getGeneralScale());	

		// solange die skala noch nicht durchlaufen ist
		if (rotatingAngle < PApplet.TWO_PI) {
			rotatingAngle += this.rotationSpeed;
		} else {
			// sosnt wieder von vorne anfangen
			rotatingAngle = 0;
		}

		// skalieren
		display.rotate(rotatingAngle);

		this.entscheideFarbe();
		this.drawFigure();

		display.resetMatrix();

	}

	/**
	 * zeichne Figur im rotierend und pulsierend
	 * @param player 
	 */
	protected void drawRotateAndPulseAppearance(Player player) {

		//Umrechnung auf Spielersicht
		PVector drawPosition=calcDrawPosition(player);
		//Transformationen
		display.translate(drawPosition.x, drawPosition.y);		
		display.rotate(actualAngle+player.getGeneralAngle());
		display.scale(0+player.getGeneralScale());	

		// solange die skala noch nicht durchlaufen ist
		if (rotatingAngle < PApplet.TWO_PI) {
			rotatingAngle += this.rotationSpeed;
		} else {
			// sosnt wieder von vorne anfangen
			rotatingAngle = 0;
		}

		// skalieren
		display.rotate(rotatingAngle);

		// solange die skala noch nicht durchlaufen ist
		if (pulseStat < pulseSkala.length - 1) {
			pulseStat++;
		} else {
			// sosnt wieder von vorne anfangen
			pulseStat = 0;
		}

		// skalieren
		display.scale(pulseSkala[pulseStat]);

		// gefuelllt zeichnen
		this.entscheideFarbe();
		this.drawFigure();

		// Umgebung zurücksetzen
		display.resetMatrix();

	}

	/**
	 * zeichnen des normalen Erscheinungs bildes ohne Effekte
	 */
	protected void drawFigure() {

		display.rect(0, 0, 0 + 20, 0 + 20);
		// pa.triangle(-20,+20, 0, -20, +20, +20);

	}

	/**
	 * neues Ziel befehlen zu dem die Einheit sich versucht hinzubewegen
	 * 
	 * @param Ziel
	 *            - PVector
	 */
	public void commandDestination(PVector dest) {

		// neuen Zielvektor setzen
		this.destinationVector = dest;

		// Richtungsvektor berechnen
		direction = PVector.sub(destinationVector, position);
		// richtungsvektor normieren
		direction.normalize();

		// neue Blickrichtung berechnen
		berechneNeueBlickrichtung();
	}

	/**
	 * Platzhaltermethode fuer das angreifen einer andern Unit
	 * 
	 * @param dest
	 */
	public void commandTarget(PVector dest) {
		// TODO coming soon
	}

	/**
	 * Berechnen des neuen Position, wenn in Bewegung
	 */
	public void calcNewPosition() {

		PVector newPosition;

		// wenn aktuelle position noch weit weg vom ziel, dann weiter bewegen
		if (position.dist(destinationVector) > 3) {

			// neue Position erechnen, normierten Richtungsvector zur position
			// hinzurechnen
			newPosition = PVector.add(this.position, PVector.mult(direction, movementSpeed));

			// wenn keien kollision dann bewegen
			if (!isCollision(newPosition)) {

				// neue Position setzen
				this.position = newPosition;
				
				bewegtSichGerade=true;

			}else{
				bewegtSichGerade=false;
			}

		}else{
			bewegtSichGerade=false;
		}
	}

	/**
	 * Methode sagt vorraus ob Unit kollidieren würde mit einer anderen Unit
	 * 
	 * @param newPosition
	 * @return
	 */
	private boolean isCollision(PVector newPosition) {

		for (BaseUnit unit : gamelogic.getGlobalUnits()) {
			// wenn nicht diese Unit (die ist in der menge mit drin)
			if (this.id != unit.id) {
				// und wenn entfernung kleiner ist als die kollisionsradien der
				// beiden einheiten zusammen
				if (PVector.dist(unit.position, newPosition) < (unit.collisionRadius + this.collisionRadius)) {
					System.out.println("UNIT " + this.id + " is in collision at " + newPosition + " with UNIT " + unit.id + " at " + unit.position);

					// nur wenn die naechste position nicht weiter entfernt sein
					// wird, soll sich unit nicht mehr weiter bewegen
					if (!(PVector.dist(unit.position, newPosition) > PVector.dist(unit.position, this.position))) {
						// Kollision liegt vor, bewegung stoppen
						// reset des zielvector (auf aktuelle position)
						this.destinationVector = this.position;

						// dann ist es eine moegliche kollison
						return true;
					}// sonstwenn die naechste position weiter entfernt sein
						// würde, darf unit sich aber bewegen
				}
			}
		}

		return false;
	}

	/**
	 * Zeichne Schweif
	 */
	protected void drawTail(Player player) {
			

		// Zielpunkt hinter der Einheit berechnen
		PVector ende = direction.get();
		//end Vektor fuer jeweiligen Spieler berechnen
		ende.rotate(player.getGeneralAngle());
		ende.normalize();
		ende.mult(schweiflaenge * -1);

		//Umrechnung auf Spielersicht
		PVector drawPosition=calcDrawPosition(player);
		//Transformationen
		display.translate(drawPosition.x, drawPosition.y);		
		display.scale(0+player.getGeneralScale());	

		// linien in schwarz zeichnen
		display.stroke(0);

		display.line(0, 0, ende.x / 2, ende.y / 2);
		display.line(1, 1, ende.x / 2, ende.y / 2);
		display.line(-1, -1, ende.x / 2, ende.y / 2);

		display.line(ende.x / 2, ende.y / 2, ende.x, ende.y);
		display.line(ende.x * 1.1f, ende.y * 1.1f, ende.x * 1.2f, ende.y * 1.2f);
		display.line(ende.x * 1.3f, ende.y * 1.3f, ende.x * 1.4f, ende.y * 1.4f);

		display.resetMatrix();
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
	public boolean isInner(PVector clickVector) {

		if (position.dist(clickVector) < this.activateRadius) {
			// Einheit für die Steureung aktivieren
			// active=true;
			return true;
		} else {
			return false;
		}
	}

	public void activate() {

		active = true;
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

	/**
	 * setzt Füll-Farbe zum Zeichen nach Status der einheit
	 */
	protected void entscheideFarbe() {
		if (this.active) {
			display.fill(this.activeColor.getRed(), this.activeColor.getGreen(), this.activeColor.getBlue());
		} else {
			display.fill(this.passiveColor.getRed(), this.passiveColor.getGreen(), this.passiveColor.getBlue());
		}
	}

	/**
	 * setzt Linien-Farbe zum Zeichen nach Status der einheit
	 */
	protected void entscheideLineFarbe() {
		if (this.active) {
			display.stroke(this.activeColor.getRed(), this.activeColor.getGreen(), this.activeColor.getBlue());
		} else {
			display.stroke(this.passiveColor.getRed(), this.passiveColor.getGreen(), this.passiveColor.getBlue());
		}
	}

	/**
	 * Berechnet Blickrichtung der Einheit nach dem Bewegungsvektor
	 */
	protected void berechneNeueBlickrichtung() {
		// berechne neue Blickrichtung
		if (direction.x > 0) {
			// rechts
			actualAngle = PVector.angleBetween(direction, new PVector(0, -1));
		} else {
			// links
			actualAngle = PApplet.TWO_PI - PVector.angleBetween(direction, new PVector(0, -1));
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

	public void attack(BaseUnit destinationUnit) {
		System.out.println("UNIT " + this.id + " is attacking UNIT " + destinationUnit.id);
		// TODO Reichweite einbauen
		// TODO Wiederholung einbauen....nach einer Schuss rate (d.h. erinheit
		// muss sich ziel merken)
		// Schuss erstellen

		new Shoot((int) this.position.x, (int) this.position.y, BaseUnit.MODE_NORMAL, DefenderControl.PLAYER_SYSTEM, display, destinationUnit, this.damagePerHit);

		// //neue Blickrichtung berechnen
		// berechneNeueBlickrichtung();
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

	public PVector getPosition() {
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
	 * berechnet die neue zeichnen Position
	 * @return
	 */
	private PVector calcDrawPosition(Player player){
		//Umrechnung auf Spielersicht
		//***********************
		PVector drawPosition=this.position.get();
		drawPosition.rotate(player.getGeneralAngle());
		drawPosition.add(player.getOriginPosition());
		//***********************
		
		return drawPosition;
	}

}
