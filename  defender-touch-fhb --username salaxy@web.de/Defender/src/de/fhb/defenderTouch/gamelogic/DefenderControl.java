package de.fhb.defenderTouch.gamelogic;

import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import TUIO.TuioClient;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioPoint;
import TUIO.TuioTime;
import de.fhb.defenderTouch.audio.FormatProblemException;
import de.fhb.defenderTouch.audio.SampleThread;
import de.fhb.defenderTouch.graphics.GraphicTools;
import de.fhb.defenderTouch.graphics.VectorHelper;
import de.fhb.defenderTouch.menu.Menu;
import de.fhb.defenderTouch.ui.Gestures;
import de.fhb.defenderTouch.units.movable.Fighter;
import de.fhb.defenderTouch.units.notmovable.Defence;
import de.fhb.defenderTouch.units.notmovable.Support;
import de.fhb.defenderTouch.units.root.Unit;

/**
 * Diese Klasse stellt die Verbindung zwischen Anzeige, also dem PApplet dar und
 * dem eigentlichen Spielinhalt wie die Units, Spiellogik usw. (und noch
 * zuk�nftige dinge)
 * 
 * @author Andy Klay <klay@fh-brandenburg.de>
 */
public class DefenderControl implements TuioListener {

	/**
	 * Gestenerkennung
	 */
	private Gestures gestures;

	int width = 1024;
	int height = 768;
	// TODO geh�rt wo anders hin....kommt eigentlich aus der view her

	// Spielkonstanten
	public static final int MOUSE_LEFT = 0;
	public static final int MOUSE_RIGHT = 1;

	// Konstanten fuer Spielerzugehoerigheit
	public static final int PLAYER_ONE_ID = 0;
	public static final int PLAYER_TWO_ID = 1;
	public static final int PLAYER_SYSTEM_ID = 2;

	// Ursprungspunkte fuer Spielerviews
	public final static Vector2f ORIGIN_POSITION_LEFT = new Vector2f(512f, 0f);
	public final static Vector2f ORIGIN_POSITION_RIGHT = new Vector2f(512f, 768f);

	/**
	 * Globale Liste an zu zeichnende Objekte/Units
	 */
	private CopyOnWriteArrayList<Unit> globalUnits;

	/**
	 * Spielerobjekt Spieler Eins
	 */
	private Player playerOne;

	/**
	 * Spielerobjekt Spieler Zwei
	 */
	private Player playerTwo;

	/**
	 * Spielerobjekt Spieler System
	 */
	private Player playerSystem;

	/**
	 * Spielkarte
	 */
	private Gamemap map;

	/**
	 * Menue Spieler Eins
	 */
	private Menu menuePlayerOne;

	/**
	 * Menue Spieler Zwei
	 */
	private Menu menuePlayerTwo;

	// /**
	// * Gamemap
	// */
	// private Gamemap gameMap;

	TuioClient tuioClient;

	public DefenderControl() {

		tuioClient = new TuioClient();

		gestures = new Gestures(tuioClient);
		gestures.setWidth(width);
		gestures.setHeight(height);

		// map init
		map = new Gamemap();

		// die beiden Spieler initialisieren
		playerOne = new Player(this, 90, 0.5f, ORIGIN_POSITION_LEFT, Color.blue, PLAYER_ONE_ID);
		playerTwo = new Player(this, 270, 0.5f, ORIGIN_POSITION_RIGHT, Color.green, PLAYER_TWO_ID);
		playerTwo.setOriginOffset(new Vector2f(0,320));
		playerSystem = new Player(this, 0, 1f, ORIGIN_POSITION_RIGHT, Color.black, PLAYER_SYSTEM_ID);

		globalUnits = new CopyOnWriteArrayList<Unit>();

		// Menue init
		menuePlayerOne = new Menu(this.globalUnits, playerOne);
		menuePlayerTwo = new Menu(this.globalUnits, playerTwo);

		// TODO mucka an
		 this.playBackgroundSound();

		tuioClient.addTuioListener(this);

		tuioClient.connect();
	}

	public Gamemap getMap() {
		return map;
	}

	public CopyOnWriteArrayList<Unit> getGlobalUnits() {
		return globalUnits;

	}

	public Player getPlayerSystem() {
		return playerSystem;
	}

	/**
	 * Zeichnet beide Spielfelder und Inhalte
	 */
	public void drawAll(Graphics graphics) {

		// Linke Seite zeichnen
		// zeichenbereich setzen
		graphics.setClip(0, 0, 510, 768);

		// Feld zeichnen
		this.map.zeichne(graphics, playerOne);

		// units playerOne zeichen
		for (Unit unit : globalUnits) {
			if (unit.isActive() && unit.getPlayerID() == PLAYER_ONE_ID) {
				// zeichne Aktiviert
				unit.paint(this.playerOne, graphics, true);
			} else {
				// zeichen normal
				unit.paint(this.playerOne, graphics, false);
			}
		}

		// menue zeichen fuer player one
		this.menuePlayerOne.drawMenu(graphics, this.playerOne);

		// info zeichnen
		graphics.setColor(Color.black);
		graphics.rotate(0, 0, 90);
//		graphics.scale(1.2f, 1.2f);
		graphics.drawString("Credits: " + playerOne.getCredits(), 10, -18);
		graphics.drawString("Geb�udeanzahl: " + menuePlayerOne.getActualBuildingCount(PLAYER_ONE_ID), 180, -18);
		graphics.drawString("Geb�ude: " + menuePlayerOne.getActualBuildingName(), 380, -18);
		// display.drawString("Aktuelles Geb�ude: " +
		// menuePlayerOne.getActualBuildingName(), 100, -15);

		graphics.resetTransform();
		// zeichenbereich leoschen
		graphics.clearClip();

		// Rechte Seite zeichnen
		// zeichenbereich setzen
		graphics.setClip(512, 0, 514, 768);

		// Feld zeichnen
		this.map.zeichne(graphics, playerTwo);
		graphics.resetTransform();

		// units playerTwo zeichen
		for (Unit unit : globalUnits) {
			if (unit.isActive() && unit.getPlayerID() == PLAYER_TWO_ID) {
				// zeichne Aktiviert
				unit.paint(this.playerTwo, graphics, true);
			} else {
				// zeichen normal
				unit.paint(this.playerTwo, graphics, false);
			}
		}

		// menue zeichen fuer playerTwo
		this.menuePlayerTwo.drawMenu(graphics, this.playerTwo);

		// info zeichnen
		graphics.setColor(Color.black);
		graphics.translate(510, 768);
		graphics.rotate(0, 0, -90);
		// display.scale(1.05f, 1.05f);
		// XXX ??? scalieren laesst schrift verschwinden, too gud :D

		graphics.drawString("Credits: " + playerTwo.getCredits(), 25, 490);
		graphics.drawString("Geb�udeanzahl: " + menuePlayerTwo.getActualBuildingCount(PLAYER_TWO_ID), 180, 490);
		graphics.drawString("Geb�ude: " + menuePlayerOne.getActualBuildingName(), 380, 490);
		// display.drawString("Aktuelles Geb�ude: " +
		// menuePlayerTwo.getActualBuildingName(), 100, 490);

		graphics.resetTransform();
		// zeichenbereich leoschen
		graphics.clearClip();

		// Trennlinie zeichnen
		graphics.setColor(Color.black);
		graphics.drawLine(511f, 0f, 511f, 768f);
		graphics.drawLine(512f, 0f, 512f, 768f);
		graphics.drawLine(513f, 0f, 513f, 768f);

		// Gesten Frames zaehlen
		gestures.countFrames();

		// ************************************
		// Debug fuer Touchsteuerung

		Vector tuioObjectList = tuioClient.getTuioObjects(); // gets all objects
																// which are
																// currently on
																// the screen
		for (int i = 0; i < tuioObjectList.size(); i++) {
			TuioObject tobj = (TuioObject) tuioObjectList.elementAt(i);
			graphics.setColor(Color.orange);
			graphics.pushTransform(); // save old coordinate system (bottom left
										// is 0,0)
			graphics.translate(tobj.getScreenX(width), tobj.getScreenY(height)); // translate
																					// coordinate-system
																					// that
																					// 0,0
																					// is
																					// at
																					// position
																					// of
																					// object
																					// (easier
																					// for
																					// drawing)
			graphics.rotate(0, 0, tobj.getAngle()); // rotate coordinate system
													// in same angle than object
													// is
			// rect(-obj_size/2,-obj_size/2,obj_size,obj_size); //draw rectangle
			graphics.popTransform(); // restore old coordinate system
			graphics.setColor(Color.white);
			graphics.drawString("" + tobj.getSymbolID(), tobj.getScreenX(width), tobj.getScreenY(height)); // draw
																											// objectID
																											// at
																											// position
																											// of
																											// object
		}

		Vector tuioCursorList = tuioClient.getTuioCursors(); // gets all cursors
																// (fingers)
																// which are
																// currently on
																// the screen
		for (int i = 0; i < tuioCursorList.size(); i++) {
			TuioCursor tcur = (TuioCursor) tuioCursorList.elementAt(i);
			Vector pointList = tcur.getPath(); // get path of cursors (the
												// positions they have already
												// been in the past)

			// if points exist (no points will exists when cursor not moved)
			if (pointList.size() > 0) { // draw path
				graphics.setColor(Color.blue);
				TuioPoint start_point = (TuioPoint) pointList.firstElement();
				for (int j = 0; j < pointList.size(); j++) {
					TuioPoint end_point = (TuioPoint) pointList.elementAt(j);
					graphics.drawLine(start_point.getScreenX(width), start_point.getScreenY(height), end_point.getScreenX(width), end_point.getScreenY(height));
					start_point = end_point;
				}
				graphics.setColor(Color.gray);
				graphics.fillOval(tcur.getScreenX(width), tcur.getScreenY(height), 10, 10); // draw
																							// ellipse
																							// at
				// tcur.getScreenY(height),cur_size,cur_size); //draw ellipse at
				// (current) position of cursor
				graphics.setColor(Color.gray);

				graphics.drawString("" + tcur.getCursorID(), tcur.getScreenX(width) - 5, tcur.getScreenY(height) + 5); // draw
																														// id
																														// and
																														// position
																														// at
																														// current
																														// position
																														// of
																														// cursor
			}
		}
		
//		int[] rangeDifferent= gestures.rangeDifferent(tuioCursorList);
//		if(rangeDifferent!=null){
//			zoomInterface(rangeDifferent[0],rangeDifferent[1],
//					rangeDifferent[2],rangeDifferent[3]);
//		}
		Vector2f v = gestures.twoFingersInRange(tuioCursorList, 2);
		if(v!=null){
			if(v.x < width/2){
				if(menuePlayerOne.isMenuOpen()){
					startMenueControlForMouse(v, 1);
				}else{
					startMenueControlForMouse(v, 0);
				}
			}else{
				if(menuePlayerTwo.isMenuOpen()){
					startMenueControlForMouse(v, 1);
				}else{
					startMenueControlForMouse(v, 0);
				}
			}			
		}	
		
		int[] schiebeCoordinate= gestures.schiebeMap(tuioCursorList);
		
		if(schiebeCoordinate!=null){
//			for(int i = 0; i<schiebeCoordinate.length; i++){
//				System.out.println("i="+i+" = "+schiebeCoordinate[i]);
//			}
//			if(schiebeCoordinate[0]!= -1){
//				schiebeInterface(schiebeCoordinate[0],schiebeCoordinate[1],
//						schiebeCoordinate[2],schiebeCoordinate[3]);
//			}
//			if(schiebeCoordinate[4]!= -1){
//				schiebeInterface(schiebeCoordinate[4],schiebeCoordinate[5],
//						schiebeCoordinate[6],schiebeCoordinate[7]);
//			}
			schiebeInterface(schiebeCoordinate[0],schiebeCoordinate[1],
					schiebeCoordinate[2],schiebeCoordinate[3]);
			schiebeInterfacePlayerTwo(schiebeCoordinate[0],schiebeCoordinate[1],
					schiebeCoordinate[2],schiebeCoordinate[3]);
		}

	}

	public void updateGame() {
		// Berechnen der Positionen aller Units
		for (Unit unit : globalUnits) {
			unit.update();
		}
	}

	public Menu getMenuePlayerOne() {
		return menuePlayerOne;
	}

	public Menu getMenuePlayerTwo() {
		return menuePlayerTwo;
	}

	public Player getPlayerOne() {
		return playerOne;
	}

	public Player getPlayerTwo() {
		return playerTwo;
	}

	private void playBackgroundSound() {
		try {
			new SampleThread("/data/sounds/background.mp3", 10.0f, true).start();
		} catch (FormatProblemException e) {
			e.printStackTrace();
		}
	}

	/**
	 * wertet die Menueaktionen aus
	 * 
	 * @param clickVector
	 */
	public void startMenueControlForMouse(Vector2f clickVector, int mouseButton) {

		// menue welches aufgerufen wird
		Menu menu = null;
		Vector2f realClickKoordinates = null;

//		 wenn bei Spielr Zwei
		if (clickVector.x > 512) {
			// menue aus der Hauptlogik holen
			menu = this.getMenuePlayerTwo();
			// spielkartenvektor berechnen
			realClickKoordinates = GraphicTools.calcInputVector(clickVector, this.getPlayerTwo());
		} else {
			// wenn bei Spieler Eins
			// menue aus der Hauptlogik holen
			menu = this.getMenuePlayerOne();
			// spielkartenvektor berechnen
			realClickKoordinates = GraphicTools.calcInputVector(clickVector, this.getPlayerOne());
		}

		if (mouseButton == MOUSE_LEFT) {
			// menue oeffnen
			openMenue(realClickKoordinates, menu);
		}

		if (mouseButton == MOUSE_RIGHT) {
			// menue schliessen
			closeMenue(realClickKoordinates, menu);
		}
	}

	/**
	 * Methode die aufgerufen werden soll wenn das menue(Bau/Gebauedemenue)
	 * ge�ffnet werden soll
	 * 
	 * @param realClickKoordinates
	 *            - map koordinaten des Klicks
	 * @param menu
	 *            - menu welches geoeffnet werden soll
	 */
	private void openMenue(Vector2f realClickKoordinates, Menu menu) {
		System.out.println(map.isBuildable(realClickKoordinates, menu.getOwnerID()));
		if (!menu.isMenuOpen() && !menu.isBuildingOpen() && map.isBuildable(realClickKoordinates, menu.getOwnerID())) {
			menu.setPosition(realClickKoordinates);
			if (menu.isPlaceTaken(realClickKoordinates)) {
				System.out.println("open building menu");
				menu.setBuildingOpen(true, realClickKoordinates);
			} else {
				System.out.println("open main menu");
				menu.setMenuOpen(true);
			}
			menu.setActualBuildingName();
		} else if (menu.isBuildingOpen()) {
			if (menu.isBuildingElementClicked(realClickKoordinates)) {
				switch (menu.getActualChosenBuilding()) {
				case 0:
					System.out.println("do Building upgrade");
					menu.getActualBuilding().upgrade();
					break;
				case 1:
					System.out.println("do Building destroyed");
					menu.getActualBuilding().delete();
					break;
				default:
				}
			}
			menu.resetActualBuildingName();
		} else if (menu.isMenuOpen()) {
			if (menu.isMenuElementClicked(realClickKoordinates)) {
				System.out.println("choosing a building");
				menu.createBuilding(this);
			}
		}
	}

	/**
	 * Methode die aufgerufen werden soll wenn das menue(Bau/Gebauedemenue)
	 * geschlossen werden soll
	 * 
	 * @param realClickKoordinates
	 *            - map koordinaten des Klicks
	 * @param menu
	 *            - menu welches geschlossen werden soll
	 */
	private void closeMenue(Vector2f realClickKoordinates, Menu menu) {

		if (menu.isMenuOpen() && menu.isInnerMainElement(realClickKoordinates)) {
			menu.setMenuOpen(false);
			System.out.println("close menu");
		}

		if (menu.isBuildingOpen() && menu.isPlaceTaken(realClickKoordinates)) {
			System.out.println("close building menu");
			menu.resetActualBuildingName();
			menu.setBuildingOpen(false, null);
		}
	}

	/**
	 * Controlling for the players
	 * 
	 * @param clickVector
	 * @param mouseButton
	 */
	public void startUnitControlForMouse(Vector2f clickVector, int mouseButton) {

		// wenn bei Spielr Zwei
		if (clickVector.x > 512) {
			this.unitControlRightSide(clickVector, mouseButton);
		} else {
			this.unitControlLeftSide(clickVector, mouseButton);
		}
	}

	/**
	 * Controlling for the units of the player ONE
	 * 
	 * @param clickVector
	 * @param mouseButton
	 */
	public void unitControlRightSide(Vector2f clickVector, int mouseButton) {
		boolean weitereAktivierung = false;
		boolean istAngriff = false;
		Unit destinationUnit = null;

		// Klickvektor zur�ck rechnen auf spielkoordinaten
		Vector2f realClickKoordinates = GraphicTools.calcInputVector(clickVector, this.getPlayerTwo());

		if (mouseButton == MOUSE_LEFT) {
			for (Unit u : this.getGlobalUnits()) {

				// wenn eine unit aktiviert wird dann die anderen deaktiveren
				if (u.getPlayerID() == DefenderControl.PLAYER_TWO_ID && u.isInner(realClickKoordinates)) {
					u.activate();
					// merken
					this.getPlayerTwo().getActiveUnits().add(u);
				}
			}

			// neues Ziel setzen wenn unit aktiv
			for (Unit u : this.getGlobalUnits()) {

				if (u.isActive()) {
					// wenn klick auf eine gegnerische Unit dann angriff
					for (Unit bu : this.getGlobalUnits()) {

						// auf gegnerische einheit geklickt??
						if (bu.getPlayerID() == DefenderControl.PLAYER_ONE_ID && bu.isInner(realClickKoordinates)) {
							istAngriff = true;
							destinationUnit = bu;
							System.out.println("Angriff initiiert!");
						}

						// auf weitere eigene einheit geklickt??
						if (bu.getPlayerID() == DefenderControl.PLAYER_TWO_ID && bu.isInner(realClickKoordinates)) {
							weitereAktivierung = true;
							bu.activate();
							System.out.println("Weitere Einheit aktiviert!");
						}

					}

					if (!weitereAktivierung) {
						// bewegung anweisen wenn kein angriff
						if (!istAngriff) {
							for (Unit activeUnit : this.getPlayerTwo().getActiveUnits()) {
								activeUnit.commandDestination(realClickKoordinates);
							}

						} else {
							// falls angriff dann Angriff anweisen
							for (Unit activeUnit : this.getPlayerTwo().getActiveUnits()) {
								activeUnit.attack(destinationUnit, false);
							}
						}
					}

				}
			}
		}

		if (mouseButton == MOUSE_RIGHT) {

			for (Unit u : this.getPlayerTwo().getActiveUnits()) {
				u.deactivate();
				this.getPlayerTwo().getActiveUnits().remove(u);
			}
		}

	}

	/**
	 * Controlling for the units of the player TWO
	 * 
	 * @param clickVector
	 * @param mouseButton
	 */
	public void unitControlLeftSide(Vector2f clickVector, int mouseButton) {

		boolean weitereAktivierung = false;
		boolean istAngriff = false;
		Unit destinationUnit = null;

		// Klickvektor zur�ck rechnen auf spielkoordinaten
		Vector2f realClickKoordinates = GraphicTools.calcInputVector(clickVector, this.getPlayerOne());

		if (mouseButton == MOUSE_LEFT) {

			for (Unit u : this.getGlobalUnits()) {

				// wenn eine unit aktiviert wird dann die anderen deaktiveren
				if (u.getPlayerID() == DefenderControl.PLAYER_ONE_ID && u.isInner(realClickKoordinates)) {
					u.activate();
					// merken
					this.getPlayerOne().getActiveUnits().add(u);
				}
			}

			// neues Ziel setzen wenn unit aktiv
			for (Unit u : this.getGlobalUnits()) {

				if (u.isActive()) {
					// wenn klick auf eine gegnerische Unit dann angriff
					for (Unit bu : this.getGlobalUnits()) {

						// auf gegnerische einheit geklickt??
						if (bu.getPlayerID() == DefenderControl.PLAYER_TWO_ID && bu.isInner(realClickKoordinates)) {
							istAngriff = true;
							destinationUnit = bu;
							System.out.println("Angriff initiiert!");
							// TODO Sound
						}

						// auf weitere eigene einheit geklickt??
						if (bu.getPlayerID() == DefenderControl.PLAYER_ONE_ID && bu.isInner(realClickKoordinates)) {
							weitereAktivierung = true;
							bu.activate();
							System.out.println("Weitere Einheit aktiviert!");
						}

					}

					if (!weitereAktivierung) {
						// bewegung anweisen wenn kein angriff
						if (!istAngriff) {
							for (Unit activeUnit : this.getPlayerOne().getActiveUnits()) {
								activeUnit.commandDestination(realClickKoordinates);
							}

						} else {
							// falls angriff dann Angriff anweisen
							for (Unit activeUnit : this.getPlayerOne().getActiveUnits()) {
								activeUnit.attack(destinationUnit, false);
							}
						}
					}

				}
			}
		}

		if (mouseButton == MOUSE_RIGHT) {

			for (Unit u : this.getPlayerOne().getActiveUnits()) {
				u.deactivate();
				this.getPlayerOne().getActiveUnits().remove(u);
			}
		}

	}

	@Override
	public void addTuioCursor(TuioCursor arg0) {
		// TODO MINKE
//		System.out.println("add cursor " + arg0.getCursorID() + " (" + arg0.getSessionID() + ") " + arg0.getX() + " " + arg0.getY());
		Vector2f clickVector = new Vector2f(arg0.getScreenX(width), arg0.getScreenY(height));

		if((menuePlayerOne.isMenuOpen() || menuePlayerOne.isBuildingOpen() )&& clickVector.x < (width/2)){
			this.startMenueControlForMouse(clickVector,0);
		}else{
			
		}
		if((menuePlayerTwo.isMenuOpen()||menuePlayerTwo.isBuildingOpen())&& clickVector.x > (width/2)){
			this.startMenueControlForMouse(clickVector,0);
		}
		


		this.startUnitControlForMouse(clickVector, 0);
		
	}
	@Override
	public void addTuioObject(TuioObject tobj) {
		//System.out.println("add object " + tobj.getSymbolID() + " (" + tobj.getSessionID() + ") " + tobj.getX() + " " + tobj.getY() + " " + tobj.getAngle());
	}

	@Override
	public void refresh(TuioTime arg0) {
		// XXX MINKE

	}

	@Override
	public void removeTuioCursor(TuioCursor tcur) {
		// XXX MINKE
		gestures.cleanUpTheList(tcur.getCursorID());
		// System.out.println("remove cursor " + tcur.getCursorID() + " ("
		// + tcur.getSessionID() + ")");
	}

	@Override
	public void removeTuioObject(TuioObject tobj) {
		// XXX MINKE
		//System.out.println("remove object " + tobj.getSymbolID() + " (" + tobj.getSessionID() + ")");

	}

	@Override
	public void updateTuioCursor(TuioCursor tcur) {
		// println("update cursor " + tcur.getCursorID() + " ("
		// + tcur.getSessionID() + ") " + tcur.getX() + " " + tcur.getY()
		// + " " + tcur.getMotionSpeed() + " " + tcur.getMotionAccel());

		// XXX MINKE

//		if (gestures.twoFingersInRange(tuioClient.getTuioCursors(), 2)) {
//			// XXX MINKE
//		}

	}

	@Override
	public void updateTuioObject(TuioObject tobj) {
		System.out.println("update object " + tobj.getSymbolID() + " (" + tobj.getSessionID() + ") " + tobj.getX() + " " + tobj.getY() + " " + tobj.getAngle() + " "
				+ tobj.getMotionSpeed() + " " + tobj.getRotationSpeed() + " " + tobj.getMotionAccel() + " " + tobj.getRotationAccel());
	}

	/**
	 * Zoomen der Anzeige
	 * 
	 * @param oldx
	 * @param oldy
	 * @param newx
	 * @param newy
	 */
	public void zoomInterface(int oldx, int oldy, int newx, int newy) {
		if (newx < 512) {

//			 Vector2f old=GraphicTools.calcInputVector(new Vector2f(newx,
//			 newy), this.playerOne);
//
//			this.getPlayerOne().setActualZoom(this.getPlayerOne().getActualZoom() + (newy - oldy) * 0.001f);
//
//			 Vector2f neu=GraphicTools.calcInputVector(new Vector2f(newx,
//			 newy), this.playerOne);
////			 Experiment Zoompunkt setzen
//			 Vector2f difference=VectorHelper.sub(old,neu);
//			 this.getPlayerOne().setOriginOffset(this.getPlayerOne().getOriginOffset().sub(difference));
//			 this.getPlayerOne().getOriginOffset().scale(1 +(( oldy - newy ) *
//			 0.01f));

		} else {
			this.getPlayerTwo().setActualZoom(this.getPlayerTwo().getActualZoom() + (newy - oldy) * 0.001f);
		}
	}

	/**
	 * Schieben der anzeige
	 * 
	 * @param oldx
	 * @param oldy
	 * @param newx
	 * @param newy
	 */
	public void schiebeInterface(float oldx, float oldy, float newx, float newy) {

		if (newx < 512) {
			
			Vector2f tempVec = this.getPlayerOne().getOriginOffset().copy();
			tempVec.y = tempVec.y + newy - oldy;
			tempVec.x = tempVec.x + newx - oldx;
			
			this.getPlayerOne().setOriginOffset(tempVec);
		} else {
			
			Vector2f tempVec = this.getPlayerTwo().getOriginOffset().copy();
			tempVec.y = tempVec.y + newy - oldy;
			tempVec.x = tempVec.x + newx - oldx;
			
			this.getPlayerTwo().setOriginOffset(tempVec);
		}
	}


	public void schiebeInterfacePlayerTwo(float oldx, float oldy, float newx, float newy){
		if(newx>=512) {
		
		Vector2f tempVec = this.getPlayerTwo().getOriginOffset().copy();
		tempVec.y = tempVec.y + newy - oldy;
		tempVec.x = tempVec.x + newx - oldx;
		
		this.getPlayerTwo().setOriginOffset(tempVec);
	}
	}
	
	/**
	 * Creating some Test Units
	 */
	public void createTestUnits() {

//		 // TestUnitBetas schaffen
//		 Unit test = new Unit(100, 200, Unit.MODE_ROTATE,
//		 this.playerOne, this);
//		 test.commandDestination(new Vector2f(1000, 700));
//		
//		 // Testflugstaffel
//		 new Tank(100, 50, Unit.MODE_NORMAL, this.playerTwo, this);
//		 new Defence(200, 50, Unit.MODE_NORMAL, this.playerTwo, this);
//		 new Armory(300, 50, Unit.MODE_NORMAL, this.playerTwo, this);
//		 new Barracks(400, 50, Unit.MODE_NORMAL, this.playerTwo, this);
//		 new Support(500, 50, Unit.MODE_NORMAL, this.playerTwo, this);
//		 new Fighter(600, 50, Unit.MODE_NORMAL, this.playerTwo, this);
//		 new Fighter(700, 50, Unit.MODE_PULSE_IF_ACTIVE, this.playerTwo,
//		 this);
//		
//		 // Testflugstaffel playerOne
//		 new Fighter(100, 700, Unit.MODE_NORMAL, this.playerOne, this);
//		 new Fighter(200, 700, Unit.MODE_NORMAL, this.playerOne, this);
//		 new Fighter(300, 700, Unit.MODE_NORMAL, this.playerOne, this);
//		 new Fighter(400, 700, Unit.MODE_NORMAL, this.playerOne, this);
//		 new Fighter(500, 700, Unit.MODE_NORMAL, this.playerOne, this);
//		 new Unit(600, 700, Unit.MODE_PULSE, this.playerOne, this);
//		 new Unit(700, 700, Unit.MODE_ROTATE_AND_PULSE,
//		 this.playerOne, this);
//		 new Unit(800, 700, Unit.MODE_NORMAL, this.playerOne, this);

	}
	
	/**
	 * Creating some Test Units
	 */
	public void createPraesentationUnits() {

		//***************************************************************
		//Player One
		new Defence(900, 1120, Unit.MODE_NORMAL, this.playerOne, this);
		new Defence(900, 1080, Unit.MODE_NORMAL, this.playerOne, this);
		new Defence(900, 1040, Unit.MODE_NORMAL, this.playerOne, this);
		new Defence(900, 1000, Unit.MODE_NORMAL, this.playerOne, this);
		
		new Defence(900, 510, Unit.MODE_NORMAL, this.playerOne, this);
		new Defence(900, 470, Unit.MODE_NORMAL, this.playerOne, this);
		new Defence(900, 430, Unit.MODE_NORMAL, this.playerOne, this);
		new Defence(900, 390, Unit.MODE_NORMAL, this.playerOne, this);
		
		new Support(265, 980, Unit.MODE_NORMAL, this.playerOne, this);
		new Support(297, 474, Unit.MODE_NORMAL, this.playerOne, this);
		
		new Fighter(671, 800, Unit.MODE_NORMAL, this.playerOne, this);
		new Fighter(671, 700, Unit.MODE_NORMAL, this.playerOne, this);
		new Fighter(671, 600, Unit.MODE_NORMAL, this.playerOne, this);
		
		
		//***************************************************************
		//Player Two
		new Defence(2200, 1120, Unit.MODE_NORMAL, this.playerTwo, this);
		new Defence(2200, 1080, Unit.MODE_NORMAL, this.playerTwo, this);
		new Defence(2200, 1040, Unit.MODE_NORMAL, this.playerTwo, this);
		new Defence(2200, 1000, Unit.MODE_NORMAL, this.playerTwo, this);
		
		new Defence(2200, 620, Unit.MODE_NORMAL, this.playerTwo, this);
		new Defence(2200, 580, Unit.MODE_NORMAL, this.playerTwo, this);
		new Defence(2200, 540, Unit.MODE_NORMAL, this.playerTwo, this);
		new Defence(2200, 500, Unit.MODE_NORMAL, this.playerTwo, this);
		
		new Support(2691, 1231, Unit.MODE_NORMAL, this.playerTwo, this);
		new Support(2637, 400, Unit.MODE_NORMAL, this.playerTwo, this);
		
		new Fighter(2482, 1000, Unit.MODE_NORMAL, this.playerTwo, this);
		new Fighter(2482, 900, Unit.MODE_NORMAL, this.playerTwo, this);
		new Fighter(2482, 800, Unit.MODE_NORMAL, this.playerTwo, this);

	}
}
