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
import de.fhb.defenderTouch.display.DefenderViewSlick;
import de.fhb.defenderTouch.graphics.GraphicTools;
import de.fhb.defenderTouch.menu.Menu;
import de.fhb.defenderTouch.ui.Gestures;
import de.fhb.defenderTouch.units.movable.Fighter;
import de.fhb.defenderTouch.units.movable.Tank;
import de.fhb.defenderTouch.units.notmovable.Armory;
import de.fhb.defenderTouch.units.notmovable.Barracks;
import de.fhb.defenderTouch.units.notmovable.Defence;
import de.fhb.defenderTouch.units.notmovable.Support;
import de.fhb.defenderTouch.units.root.BaseUnit;

/**
 * Diese Klasse stellt die Verbindung zwischen Anzeige, also dem PApplet dar und
 * dem eigentlichen Spielinhalt wie die Units, Spiellogik usw. (und noch
 * zukünftige dinge)
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
	//TODO gehört wo anders hin....kommt eigentlich aus der view her

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
	private CopyOnWriteArrayList<BaseUnit> globalUnits;

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
	
	
	TuioClient tuioClient;
	

	public DefenderControl() {
		
		tuioClient=new TuioClient();

		gestures= new Gestures(tuioClient);

		// map init
		map = new Gamemap();

		// die beiden Spieler initialisieren
		playerOne = new Player(this, 90, 0.65f, ORIGIN_POSITION_LEFT, Color.blue, PLAYER_ONE_ID);
		playerTwo = new Player(this, 270, 0.65f, ORIGIN_POSITION_RIGHT, Color.green, PLAYER_TWO_ID);
		playerSystem = new Player(this, 0, 1f, ORIGIN_POSITION_RIGHT, Color.black, PLAYER_SYSTEM_ID);

		globalUnits = new CopyOnWriteArrayList<BaseUnit>();

		// Menue init
		menuePlayerOne = new Menu(this.globalUnits, playerOne);
		menuePlayerTwo = new Menu(this.globalUnits, playerTwo);

		// this.playBackgroundSound();
		
		tuioClient.addTuioListener(this);
		
		tuioClient.connect();
	}

	public Gamemap getMap() {
		return map;
	}

	public CopyOnWriteArrayList<BaseUnit> getGlobalUnits() {
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
		for (BaseUnit unit : globalUnits) {
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
		graphics.scale(1.2f, 1.2f);
		graphics.drawString("Credits: " + playerOne.getCredits(), 10, -18);
		graphics.drawString("Gebäudeanzahl: " + menuePlayerOne.getActualBuildingCount(PLAYER_ONE_ID), 180, -18);
		// display.drawString("Aktuelles Gebäude: " +
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
		for (BaseUnit unit : globalUnits) {
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
		// XXX ??? scalieren laesst schrift verschwinden

		graphics.drawString("Credits: " + playerTwo.getCredits(), 25, 490);
		graphics.drawString("Gebäudeanzahl: " + menuePlayerTwo.getActualBuildingCount(PLAYER_TWO_ID), 180, 490);
		// display.drawString("Aktuelles Gebäude: " +
		// menuePlayerTwo.getActualBuildingName(), 100, 490);

		graphics.resetTransform();
		// zeichenbereich leoschen
		graphics.clearClip();

		// Trennlinie zeichnen
		graphics.setColor(Color.black);
		graphics.drawLine(511f, 0f, 511f, 768f);
		graphics.drawLine(512f, 0f, 512f, 768f);
		graphics.drawLine(513f, 0f, 513f, 768f);
		
		
		
		
		//Gesten Frames zaehlen
		gestures.countFrames();
		
		
		//************************************
		//Debug fuer Touchsteuerung
		
		
		Vector tuioObjectList = tuioClient.getTuioObjects(); //gets all objects which are currently on the screen
		  for (int i=0;i<tuioObjectList.size();i++) {
		     TuioObject tobj = (TuioObject)tuioObjectList.elementAt(i);
		     graphics.setColor(Color.orange);
		     graphics.pushTransform(); //save old coordinate system (bottom left is 0,0)
		     graphics.translate(tobj.getScreenX(width),tobj.getScreenY(height)); //translate coordinate-system that 0,0 is at position of object (easier for drawing)
		     graphics.rotate(0, 0, tobj.getAngle()); //rotate coordinate system in same angle than object is
//		     rect(-obj_size/2,-obj_size/2,obj_size,obj_size); //draw rectangle
		     graphics.popTransform(); //restore old coordinate system
		     graphics.setColor(Color.white);
		     graphics.drawString(""+tobj.getSymbolID(), tobj.getScreenX(width), tobj.getScreenY(height)); //draw objectID at position of object
		   }
		   
		   Vector tuioCursorList = tuioClient.getTuioCursors(); //gets all cursors (fingers) which are currently on the screen
		   for (int i=0;i<tuioCursorList.size();i++) {
		      TuioCursor tcur = (TuioCursor)tuioCursorList.elementAt(i);
		      Vector pointList = tcur.getPath(); // get path of cursors (the positions they have already been in the past)
		      
		    //if points exist (no points will exists when cursor not moved)
		      if (pointList.size()>0) { //draw path
				     graphics.setColor(Color.blue);
		        TuioPoint start_point = (TuioPoint)pointList.firstElement();
		        for (int j=0;j<pointList.size();j++) {
		           TuioPoint end_point = (TuioPoint)pointList.elementAt(j);
//		           line(start_point.getScreenX(width),start_point.getScreenY(height),end_point.getScreenX(width),end_point.getScreenY(height));
		           start_point = end_point;
		        }
			     graphics.setColor(Color.gray);
//		        ellipse( tcur.getScreenX(width), tcur.getScreenY(height),cur_size,cur_size); //draw ellipse at (current) position of cursor
			     graphics.setColor(Color.gray);
			     
			     graphics.drawString(""+ tcur.getCursorID(),  tcur.getScreenX(width)-5,  tcur.getScreenY(height)+5); //draw id and position at current position of cursor
		      }
		   }
		

	}
	
	
	
	public void updateGame(){
		
		// Berechnen der Positionen aller Units
		for (BaseUnit unit : globalUnits) {
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
			new SampleThread("/sounds/background.mp3", 10.0f, true).start();
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

		// wenn bei Spielr Zwei
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
	 * geöffnet werden soll
	 * 
	 * @param realClickKoordinates
	 *            - map koordinaten des Klicks
	 * @param menu
	 *            - menu welches geoeffnet werden soll
	 */
	private void openMenue(Vector2f realClickKoordinates, Menu menu) {

		if (!menu.isMenuOpen() && !menu.isBuildingOpen()) {
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
			if (menu.isInnerBuildingElement(realClickKoordinates)) {
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
		} else if (menu.isMenuOpen()) {
			if (menu.isMenuBuildingClicked(realClickKoordinates)) {
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
		BaseUnit destinationUnit = null;

		// Klickvektor zurück rechnen auf spielkoordinaten
		Vector2f realClickKoordinates = GraphicTools.calcInputVector(clickVector, this.getPlayerTwo());

		if (mouseButton == MOUSE_LEFT) {
			for (BaseUnit u : this.getGlobalUnits()) {

				// wenn eine unit aktiviert wird dann die anderen deaktiveren
				if (u.getPlayerID() == DefenderControl.PLAYER_TWO_ID && u.isInner(realClickKoordinates)) {
					u.activate();
					// merken
					this.getPlayerTwo().getActiveUnits().add(u);
				}
			}

			// neues Ziel setzen wenn unit aktiv
			for (BaseUnit u : this.getGlobalUnits()) {

				if (u.isActive()) {
					// wenn klick auf eine gegnerische Unit dann angriff
					for (BaseUnit bu : this.getGlobalUnits()) {

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
							for (BaseUnit activeUnit : this.getPlayerTwo().getActiveUnits()) {
								activeUnit.commandDestination(realClickKoordinates);
							}

						} else {
							// falls angriff dann Angriff anweisen
							for (BaseUnit activeUnit : this.getPlayerTwo().getActiveUnits()) {
								activeUnit.attack(destinationUnit,false);
							}
						}
					}

				}
			}
		}

		if (mouseButton == MOUSE_RIGHT) {

			for (BaseUnit u : this.getPlayerTwo().getActiveUnits()) {
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
		BaseUnit destinationUnit = null;

		// Klickvektor zurück rechnen auf spielkoordinaten
		Vector2f realClickKoordinates = GraphicTools.calcInputVector(clickVector, this.getPlayerOne());

		if (mouseButton == MOUSE_LEFT) {

			for (BaseUnit u : this.getGlobalUnits()) {

				// wenn eine unit aktiviert wird dann die anderen deaktiveren
				if (u.getPlayerID() == DefenderControl.PLAYER_ONE_ID && u.isInner(realClickKoordinates)) {
					u.activate();
					// merken
					this.getPlayerOne().getActiveUnits().add(u);
				}
			}

			// neues Ziel setzen wenn unit aktiv
			for (BaseUnit u : this.getGlobalUnits()) {

				if (u.isActive()) {
					// wenn klick auf eine gegnerische Unit dann angriff
					for (BaseUnit bu : this.getGlobalUnits()) {

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
							for (BaseUnit activeUnit : this.getPlayerOne().getActiveUnits()) {
								activeUnit.commandDestination(realClickKoordinates);
							}

						} else {
							// falls angriff dann Angriff anweisen
							for (BaseUnit activeUnit : this.getPlayerOne().getActiveUnits()) {
								activeUnit.attack(destinationUnit,false);
							}
						}
					}

				}
			}
		}

		if (mouseButton == MOUSE_RIGHT) {

			for (BaseUnit u : this.getPlayerOne().getActiveUnits()) {
				u.deactivate();
				this.getPlayerOne().getActiveUnits().remove(u);
			}
		}

	}

	@Override
	public void addTuioCursor(TuioCursor arg0) {
		// TODO MINKE
		System.out.println("add cursor " + arg0.getCursorID() + " (" + arg0.getSessionID() + ") " + arg0.getX() + " " + arg0.getY());

		Vector2f clickVector = new Vector2f(arg0.getScreenX(DefenderViewSlick.HEIGHT), arg0.getScreenY(DefenderViewSlick.WIDTH));
		
		this.startUnitControlForMouse(clickVector, 0);
	}

	@Override
	public void addTuioObject(TuioObject tobj) {
		  System.out.println("add object "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle());
	}

	@Override
	public void refresh(TuioTime arg0) {
		// XXX MINKE

	}

	@Override
	public void removeTuioCursor(TuioCursor tcur) {
		// XXX MINKE
		gestures.cleanUpTheList(tcur.getCursorID());
//		System.out.println("remove cursor " + tcur.getCursorID() + " ("
//				+ tcur.getSessionID() + ")");
	}

	@Override
	public void removeTuioObject(TuioObject tobj) {
		// XXX MINKE
		System.out.println("remove object "+tobj.getSymbolID()+" ("+tobj.getSessionID()+")");

	}

	@Override
	public void updateTuioCursor(TuioCursor tcur) {
//		println("update cursor " + tcur.getCursorID() + " ("
//		+ tcur.getSessionID() + ") " + tcur.getX() + " " + tcur.getY()
//		+ " " + tcur.getMotionSpeed() + " " + tcur.getMotionAccel());
		
		// XXX MINKE
		
		if(gestures.twoFingersInRange(tuioClient.getTuioCursors(), 2)){
			// XXX MINKE
		}
		
	}

	@Override
	public void updateTuioObject(TuioObject tobj) {
		  System.out.println("update object "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle()
		          +" "+tobj.getMotionSpeed()+" "+tobj.getRotationSpeed()+" "+tobj.getMotionAccel()+" "+tobj.getRotationAccel());
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
			
//			Vector2f old=GraphicTools.calcInputVector(new Vector2f(newx, newy), this.playerOne);
			
			this.getPlayerOne().setActualZoom(this.getPlayerOne().getActualZoom() + (newy - oldy) * 0.001f);
			
//			Vector2f neu=GraphicTools.calcInputVector(new Vector2f(newx, newy), this.playerOne);
			//Experiment Zoompunkt setzen			
//			Vector2f difference=VectorHelper.sub(old,neu);
//			this.getPlayerOne().setOriginOffset(this.getPlayerOne().getOriginOffset().sub(difference));
//			this.getPlayerOne().getOriginOffset().scale(1 +(( oldy - newy ) * 0.01f));

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
			Vector2f tempVec = this.getPlayerOne().getOriginOffset();
			tempVec.y = tempVec.y + newy - oldy;
			tempVec.x = tempVec.x + newx - oldx;
		} else {
			Vector2f tempVec = this.getPlayerTwo().getOriginOffset();

			tempVec.y = tempVec.y + newy - oldy;
			tempVec.x = tempVec.x + newx - oldx;
		}
	}

	/**
	 * Creating some Test Units
	 */
	public void createTestUnits() {

		// TestUnitBetas schaffen
		BaseUnit test = new BaseUnit(100, 200, BaseUnit.MODE_ROTATE, this.playerOne, this);
		test.commandDestination(new Vector2f(1000, 700));

		// Testflugstaffel
		new Tank(100, 50, BaseUnit.MODE_NORMAL, this.playerTwo, this);
		new Defence(200, 50, BaseUnit.MODE_NORMAL, this.playerTwo, this);
		new Armory(300, 50, BaseUnit.MODE_NORMAL, this.playerTwo, this);
		new Barracks(400, 50, BaseUnit.MODE_NORMAL, this.playerTwo, this);
		new Support(500, 50, BaseUnit.MODE_NORMAL, this.playerTwo, this);
		new Fighter(600, 50, BaseUnit.MODE_NORMAL, this.playerTwo, this);
		new Fighter(700, 50, BaseUnit.MODE_PULSE_IF_ACTIVE, this.playerTwo, this);

		// Testflugstaffel playerOne
		new Fighter(100, 700, BaseUnit.MODE_NORMAL, this.playerOne, this);
		new Fighter(200, 700, BaseUnit.MODE_NORMAL, this.playerOne, this);
		new Fighter(300, 700, BaseUnit.MODE_NORMAL, this.playerOne, this);
		new Fighter(400, 700, BaseUnit.MODE_NORMAL, this.playerOne, this);
		new Fighter(500, 700, BaseUnit.MODE_NORMAL, this.playerOne, this);
		new BaseUnit(600, 700, BaseUnit.MODE_PULSE, this.playerOne, this);
		new BaseUnit(700, 700, BaseUnit.MODE_ROTATE_AND_PULSE, this.playerOne, this);
		new BaseUnit(800, 700, BaseUnit.MODE_NORMAL, this.playerOne, this);

	}
}
