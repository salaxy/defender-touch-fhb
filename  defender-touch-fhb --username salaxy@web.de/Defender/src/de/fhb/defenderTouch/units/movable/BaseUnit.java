package de.fhb.defenderTouch.units.movable;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import de.fhb.defenderTouch.audio.FormatProblemException;
import de.fhb.defenderTouch.audio.SampleThread;
import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.graphics.GraphicTools;

/**
 * BaseUnit Version 0.5 vom 23.10.2011
 * 
 * @author Andy Klay <klay@fh-brandenburg.de>
 * 
 */
public class BaseUnit {

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
//	protected PApplet graphics;

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
	protected Color activeColor = Color.RED;

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
	
	/**
	 * sagt aus ob sich die Unit gerade in Bewegung befindet
	 */
	protected boolean isMoving=false;

	public BaseUnit(int x, int y, int mode, int playerID, PApplet disp, DefenderControl gamelogic) {

//		this.graphics = disp;
		this.mode = mode;
		this.playerID = playerID;
		this.position = new PVector(x, y);
		this.destinationVector = new PVector(x, y);
		this.berechneNeueBlickrichtung();
		this.initHaloSkala();

//		gamelogic = DefenderControl.getInstance(graphics);
		this.gamelogic=gamelogic;

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
	 * @param player
	 * @param graphics
	 * @param focus - wird Unit Aktiv gezeichnet oder nicht
	 */
	public void paint(Player player, PGraphics graphics,boolean drawActive) {

		//Umrechnung auf Spielersicht	
		//Transformationen
		calcDrawPosition(player, graphics);

			
		if(drawActive){
			
			drawActiveAppearance( player,  graphics);
			
			// zeichne Schweif wenn in bewegung
			if(this.isMoving){
				drawTail(player, graphics);			
			}
			
		}else{
			
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
			
			// zeichne Schweif wenn in bewegung
			if(this.isMoving){
				drawTail(player, graphics);			
			}			
			
			
		}
		
		
		// zurücksetzen der Umgebung, Seiteneffekte vermeiden
		graphics.resetMatrix();
		
	}
	
	/**
	 * Zeichne Figur im Aktiven Zustand
	 * @param player
	 * @param graphics
	 */
	public void drawActiveAppearance(Player player, PGraphics graphics) {
	

		graphics.stroke(this.activeColor.getRed(), this.activeColor.getGreen(), this.activeColor.getBlue());
		graphics.fill(this.activeColor.getRed(), this.activeColor.getGreen(), this.activeColor.getBlue());
		this.drawFigure(graphics);
		graphics.resetMatrix();
		
	}
	

	/**
	 * zeichne Figur im Normalen Zustand
	 * @param player 
	 * @param graphics 
	 */
	protected void drawNormalAppearance(Player player, PGraphics graphics) {
			
		//Umrechnung auf Spielersicht
		//Umrechnung/Transformation nun bereits in this.paint() drin	
		
		//farben setzen
		this.entscheideFillFarbe(graphics);
		this.entscheideLineFarbe(graphics);
		
		
		this.drawFigure(graphics);
		graphics.resetMatrix();
	}

	/**
	 * zeichne Figur im Normalen Zustand, wenn Aktiv dann pulsierend
	 * @param player 
	 * @param graphics 
	 */
	protected void drawPulseIfActive(Player player, PGraphics graphics) {
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
	 * @param player 
	 * @param graphics 
	 */
	protected void drawPulseAppearance(Player player, PGraphics graphics) {

		//Umrechnung auf Spielersicht
		//Umrechnung/Transformation nun bereits in this.paint() drin

		// solange die skala noch nicht durchlaufen ist
		if (pulseStat < pulseSkala.length - 1) {
			pulseStat++;
		} else {
			// sosnt wieder von vorne anfangen
			pulseStat = 0;
		}

		// skalieren
		graphics.scale(pulseSkala[pulseStat]);

//		this.entscheideFarbe(graphics);
		this.entscheideFillFarbe(graphics);
		this.drawFigure(graphics);

		graphics.resetMatrix();
	}

	/**
	 * zeichne Halo
	 * @param player 
	 * @param graphics 
	 */
	protected void drawHalo(Player player, PGraphics graphics) {

		//Umrechnung auf Spielersicht
		this.calcDrawPosition(player, graphics);

		// solange die skala noch nicht durchlaufen ist
		if (haloStat < haloSkala.length - 1) {
			haloStat++;
		} else {
			// sosnt wieder von vorne anfangen
			haloStat = 0;
		}

		// skalieren
		graphics.scale(haloSkala[haloStat]);

		//zeichnen
		graphics.noFill();
		graphics.stroke(0);
		graphics.ellipse(0, 0, 20, 20);

		graphics.resetMatrix();
	}

	/**
	 * zeichne Figur im rotierend
	 * @param player 
	 * @param graphics 
	 */
	protected void drawRotateAppearance(Player player, PGraphics graphics) {

		//Umrechnung auf Spielersicht
		//Umrechnung/Transformation nun bereits in this.paint() drin

		// solange die skala noch nicht durchlaufen ist
		if (rotatingAngle < PApplet.TWO_PI) {
			rotatingAngle += this.rotationSpeed;
		} else {
			// sosnt wieder von vorne anfangen
			rotatingAngle = 0;
		}

		// skalieren
		graphics.rotate(rotatingAngle);

		this.entscheideFillFarbe(graphics);
		this.drawFigure(graphics);

		graphics.resetMatrix();

	}

	/**
	 * zeichne Figur im rotierend und pulsierend
	 * @param player 
	 * @param graphics 
	 */
	protected void drawRotateAndPulseAppearance(Player player, PGraphics graphics) {

		//Umrechnung auf Spielersicht
		//Umrechnung/Transformation nun bereits in this.paint() drin

		// solange die skala noch nicht durchlaufen ist
		if (rotatingAngle < PApplet.TWO_PI) {
			rotatingAngle += this.rotationSpeed;
		} else {
			// sosnt wieder von vorne anfangen
			rotatingAngle = 0;
		}

		// skalieren
		graphics.rotate(rotatingAngle);

		// solange die skala noch nicht durchlaufen ist
		if (pulseStat < pulseSkala.length - 1) {
			pulseStat++;
		} else {
			// sosnt wieder von vorne anfangen
			pulseStat = 0;
		}

		// skalieren
		graphics.scale(pulseSkala[pulseStat]);

		// gefuelllt zeichnen
		this.entscheideFillFarbe( graphics);
		this.drawFigure(graphics);

		// Umgebung zurücksetzen
		graphics.resetMatrix();

	}

	/**
	 * zeichnen des normalen Erscheinungs bildes ohne Effekte
	 */
	protected void drawFigure(PGraphics graphics) {

		graphics.rect(0, 0, 0 + 20, 0 + 20);
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
				
				isMoving=true;

			}else{
				isMoving=false;
			}

		}else{
			isMoving=false;
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
	protected void drawTail(Player player,PGraphics graphics) {
			

		// Zielpunkt hinter der Einheit berechnen
		//end Vektor fuer jeweiligen Spieler berechnen
		PVector ende = direction.get();
		//Vektor auf laenge 1 kuerzen
		ende.normalize();
		//Richtungsvektor umkehren zum schweif
		ende.mult(schweiflaenge * -2);
		
		//Berechne Zeichnenposition und setzte Abblidungsmatrix(Transformationsmatix)
		calcDrawPosition(player, graphics); 
	

		//Eigendrehung ausgleichen (wird in calcDrawPostion gesetzt)
		graphics.rotate(PApplet.TWO_PI-this.actualAngle);
		


		// linien in schwarz zeichnen
//		graphics.stroke(0);

		graphics.line(0, 0, ende.x / 2, ende.y / 2);
		graphics.line(1, 1, ende.x / 2, ende.y / 2);
		graphics.line(-1, -1, ende.x / 2, ende.y / 2);

		graphics.line(ende.x / 2, ende.y / 2, ende.x, ende.y);
		graphics.line(ende.x * 1.1f, ende.y * 1.1f, ende.x * 1.2f, ende.y * 1.2f);
		graphics.line(ende.x * 1.3f, ende.y * 1.3f, ende.x * 1.4f, ende.y * 1.4f);

		graphics.resetMatrix();
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
		
		try {
			new SampleThread("/sounds/fehler.mp3",10.0f,true).start();
		} catch (FormatProblemException e) {
			// TODO Auto-generated catch block
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

	/**
	 * setzt Füll-Farbe zum Zeichen nach Status der einheit
	 */
	protected void entscheideFillFarbe(PGraphics graphics) {
		
		if(this.playerID==DefenderControl.PLAYER_SYSTEM){
			if (this.active) {
				graphics.fill(this.activeColor.getRed(), this.activeColor.getGreen(), this.activeColor.getBlue());
			} else {
				graphics.fill(this.passiveColor.getRed(), this.passiveColor.getGreen(), this.passiveColor.getBlue());
			}		
		}
		
		if(this.playerID==DefenderControl.PLAYER_ONE){
			Player p = this.gamelogic.getPlayerOne();
			Color unitColor=p.getUnitColor();
			graphics.fill(unitColor.getRed(),unitColor.getBlue(),unitColor.getGreen());
		}

		if(this.playerID==DefenderControl.PLAYER_TWO){
			Player p = this.gamelogic.getPlayerTwo();
			Color unitColor=p.getUnitColor();
			graphics.fill(unitColor.getRed(),unitColor.getBlue(),unitColor.getGreen());
		}
	}

	/**
	 * setzt Linien-Farbe zum Zeichen nach Status der einheit
	 */
	protected void entscheideLineFarbe(PGraphics graphics) {
		
		if(this.playerID==DefenderControl.PLAYER_SYSTEM){
			if (this.active) {
				graphics.stroke(this.activeColor.getRed(), this.activeColor.getGreen(), this.activeColor.getBlue());
			} else {
				graphics.stroke(this.passiveColor.getRed(), this.passiveColor.getGreen(), this.passiveColor.getBlue());
			}		
		}
		
		if(this.playerID==DefenderControl.PLAYER_ONE){
			Player p = this.gamelogic.getPlayerOne();
			Color unitColor=p.getUnitColor();
			graphics.stroke(unitColor.getRed(),unitColor.getBlue(),unitColor.getGreen());
		}

		if(this.playerID==DefenderControl.PLAYER_TWO){
			Player p = this.gamelogic.getPlayerTwo();
			Color unitColor=p.getUnitColor();
			graphics.stroke(unitColor.getRed(),unitColor.getBlue(),unitColor.getGreen());
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

		new Shoot((int) this.position.x, (int) this.position.y, BaseUnit.MODE_NORMAL, DefenderControl.PLAYER_SYSTEM, null, destinationUnit, this.damagePerHit,gamelogic);

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
	 * Berechnet Zeichnenposition und setzt Abblidungsmatrix(Transformationsmatix)
	 * @return
	 */
	private void calcDrawPosition(Player player,PGraphics graphics){

		GraphicTools.calcDrawTransformation( player, graphics, position);
		
		//eigendrehung hinzurechnen
		graphics.rotate(this.actualAngle);
	}
	

}
