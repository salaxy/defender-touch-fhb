package de.fhb.defenderTouch.gamelogic;

 

import java.util.concurrent.CopyOnWriteArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import processing.core.PApplet;
import processing.core.PVector;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;
import de.fhb.defenderTouch.audio.FormatProblemException;
import de.fhb.defenderTouch.audio.SampleThread;
import de.fhb.defenderTouch.display.DefenderViewSlick;
import de.fhb.defenderTouch.graphics.GraphicTools;
import de.fhb.defenderTouch.menu.Menu;
import de.fhb.defenderTouch.ui.Gestures;
import de.fhb.defenderTouch.units.movable.Fighter;
import de.fhb.defenderTouch.units.movable.Tank;
import de.fhb.defenderTouch.units.notmovable.Defence;
import de.fhb.defenderTouch.units.notmovable.Ground;
import de.fhb.defenderTouch.units.notmovable.Navi;
import de.fhb.defenderTouch.units.notmovable.Support;
import de.fhb.defenderTouch.units.root.BaseUnit;

/**
 * Diese Klasse stellt die Verbindung zwischen Anzeige, also dem PApplet dar und
 * dem eigentlichen Spielinhalt wie die Units, Spiellogik usw. (und noch
 * zukünftige dinge)
 * 
 * @author Andy Klay <klay@fh-brandenburg.de> 
 */
public class DefenderControl implements TuioListener{
	 
	/**
	 * Gestenerkennung
	 */
	private Gestures gestures=new Gestures();
	
	//Spielkonstanten
	public static final int MOUSE_LEFT = 0;
	public static final int MOUSE_RIGHT = 1;

	//Konstanten fuer Spielerzugehoerigheit
	public static final int PLAYER_ONE_ID = 0;
	public static final int PLAYER_TWO_ID = 1;
	public static final int PLAYER_SYSTEM_ID = 2;
	
	//Ursprungspunkte fuer Spielerviews
	public final static PVector ORIGIN_POSITION_LEFT= new PVector(512f,0f);
	public final static PVector ORIGIN_POSITION_RIGHT= new PVector(512f, 768f);
	
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

	public DefenderControl() {
		
		//map init
		map=new Gamemap();
		
		//die beiden Spieler initialisieren
		playerOne = new Player(this,(float)Math.PI/2, 0.65f, ORIGIN_POSITION_LEFT, Color.blue, PLAYER_ONE_ID);
		playerTwo = new Player(this,3*(float)Math.PI/2,0.65f, ORIGIN_POSITION_RIGHT, Color.green, PLAYER_TWO_ID);
		playerSystem = new Player(this,02,1f, ORIGIN_POSITION_RIGHT, Color.black, PLAYER_SYSTEM_ID);
		
		globalUnits = new CopyOnWriteArrayList<BaseUnit>();
		
		//Menue init
		menuePlayerOne = new Menu(this.globalUnits,playerOne);
		menuePlayerTwo = new Menu(this.globalUnits,playerTwo);
		
		
//		this.playBackgroundSound();
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
		
		//Berechnen der Positionen aller Units
		for (BaseUnit unit : globalUnits) {
			unit.calcNewPosition();
		}
		
		
		// Linke Seite zeichnen
		//zeichenbereich setzen
		graphics.setClip(0, 0, 510, 768);
		
		//Feld zeichnen
		this.map.zeichne(graphics, playerOne);	
		

		//units playerOne zeichen
		for (BaseUnit unit : globalUnits) {
			if(unit.isActive()&&unit.getPlayerID()==PLAYER_ONE_ID){
				//zeichne Aktiviert
				unit.paint(this.playerOne, graphics,true);	
			}else{
				//zeichen normal
				unit.paint(this.playerOne, graphics,false);				
			}
		}
		
		//menue zeichen fuer player one
		this.menuePlayerOne.drawMenu(graphics, this.playerOne);
		
		//info zeichnen
		graphics.setColor(Color.black);
		graphics.rotate(0,0,90);
		graphics.scale(1.2f, 1.2f);
		graphics.drawString("Credits: " + playerOne.getCredits(), 10, -18);
		graphics.drawString("Gebäudeanzahl: " + menuePlayerOne.getActualBuildingCount(), 180, -18);
		//display.drawString("Aktuelles Gebäude: " + menuePlayerOne.getActualBuildingName(), 100, -15);
		
		
		graphics.resetTransform();
		//zeichenbereich leoschen
		graphics.clearClip();
		
		// Rechte Seite zeichnen
		//zeichenbereich setzen
		graphics.setClip(512,0,514,768);
		
		//Feld zeichnen
		this.map.zeichne(graphics, playerTwo);	
		graphics.resetTransform();
		
		//units playerTwo zeichen
		for (BaseUnit unit : globalUnits) {
			if(unit.isActive()&&unit.getPlayerID()==PLAYER_TWO_ID){
				//zeichne Aktiviert
				unit.paint(this.playerTwo, graphics,true);	
			}else{
				//zeichen normal
				unit.paint(this.playerTwo, graphics,false);				
			}
		}
		
		//menue zeichen fuer playerTwo
		this.menuePlayerTwo.drawMenu(graphics, this.playerTwo);

		//info zeichnen
		graphics.setColor(Color.black);		
		graphics.translate(510, 768);
		graphics.rotate(0,0,-90);
//		display.scale(1.05f, 1.05f);
		//XXX ??? scalieren laesst schrift verschwinden

		graphics.drawString("Credits: " + playerTwo.getCredits(), 25, 490);
		graphics.drawString("Gebäudeanzahl: " + menuePlayerTwo.getActualBuildingCount(), 180, 490);
		//display.drawString("Aktuelles Gebäude: " + menuePlayerTwo.getActualBuildingName(), 100, 490);
		
		graphics.resetTransform();
		//zeichenbereich leoschen
		graphics.clearClip();
		
		//Trennlinie zeichnen
		graphics.setColor(Color.black);
		graphics.drawLine(511f, 0f, 511f, 768f);
		graphics.drawLine(512f, 0f, 512f, 768f);
		graphics.drawLine(513f, 0f, 513f, 768f);
		

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
	
	private void playBackgroundSound(){
		try {
			new SampleThread("/sounds/background.mp3",10.0f,true).start();
		} catch (FormatProblemException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * wertet die Menueaktionen aus
	 * @param clickVector
	 */
	public void startMenueControlForMouse(PVector clickVector,int mouseButton) {
		
		//menue welches aufgerufen wird
		Menu menu=null;
		PVector realClickKoordinates= null;
			
		//wenn bei Spielr Zwei
		if (clickVector.x > 512) {  		
			//menue aus der Hauptlogik holen
			menu=this.getMenuePlayerTwo();	
			//spielkartenvektor berechnen
			realClickKoordinates=GraphicTools.calcInputVector(clickVector, this.getPlayerTwo());
		} else{
			//wenn bei Spieler Eins
			//menue aus der Hauptlogik holen
			menu=this.getMenuePlayerOne();
			//spielkartenvektor berechnen
			realClickKoordinates=GraphicTools.calcInputVector(clickVector, this.getPlayerOne());
		}

		if (mouseButton == MOUSE_LEFT) {
			//menue oeffnen
			openMenue(realClickKoordinates, menu);
		}

		if (mouseButton == MOUSE_RIGHT) {
			//menue schliessen
			closeMenue(realClickKoordinates, menu);
		}
	}
	
	
	
	/**
	 * Methode die aufgerufen werden soll wenn das menue(Bau/Gebauedemenue) geöffnet werden soll
	 * 
	 * @param realClickKoordinates - map koordinaten des Klicks
	 * @param menu - menu welches geoeffnet werden soll
	 */
	private void openMenue(PVector realClickKoordinates, Menu menu){	
		
		if (!menu.isMenuOpen() && !menu.isBuildingOpen()) {
			menu.setPosition(realClickKoordinates);
			if (menu.isTaken(realClickKoordinates)) {
				// IF A BUILDING IS CLICKED
				System.out.println("open building menu");
				menu.setBuildingOpen(true, realClickKoordinates);
			} else {
				// OPENS MAIN MENU
				System.out.println("open menu");
				menu.setMenuOpen(true);
			}
			menu.setActualBuildingName(realClickKoordinates);
		} else if (menu.isBuildingOpen()) {
			// WHEN UPGRADE OR DESTROY WAS CLICKED
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
			
			// CHOOSING A BUILDING
			if (menu.isInnerMenuElement(realClickKoordinates)) {
				menu.createBuilding(this);
			}
		}
	}
	
	/**
	 * Methode die aufgerufen werden soll wenn das menue(Bau/Gebauedemenue) geschlossen werden soll
	 * 
	 * @param realClickKoordinates - map koordinaten des Klicks
	 * @param menu - menu welches geschlossen werden soll
	 */
	private void closeMenue(PVector realClickKoordinates, Menu menu){
		
		// CLOSES MENU
		if (menu.isMenuOpen() && menu.isInnerMainElement(realClickKoordinates)) {
			menu.setMenuOpen(false);
			System.out.println("close menu");
		}
		
		// CLOSES BUILDING MENU
		if (menu.isBuildingOpen() && menu.isTaken(realClickKoordinates)) {
			System.out.println("close building menu");
			menu.setBuildingOpen(false, null);
		}
	}
	
    //Einheiten Kontrolle
    public void startUnitControlForMouse(PVector clickVector,int mouseButton){
    	
		//wenn bei Spielr Zwei
		if (clickVector.x > 512) {  		
     		this.unitControlRightSide(clickVector, mouseButton);  
		} else{
     		this.unitControlLeftSide(clickVector, mouseButton);  
		}
    }
    
    
    //Einheiten Kontrolle Spieler Zwei
    public void unitControlRightSide(PVector clickVector,int mouseButton){
    	boolean weitereAktivierung=false;
	    boolean istAngriff=false;
	    BaseUnit destinationUnit=null;
	    
	    //Klickvektor zurück rechnen auf spielkoordinaten
		PVector realClickKoordinates=GraphicTools.calcInputVector(clickVector, this.getPlayerTwo());


    	if(mouseButton==MOUSE_LEFT){    	
			for(BaseUnit u: this.getGlobalUnits()){
				
				//wenn eine unit aktiviert wird dann die anderen deaktiveren
				if(u.getPlayerID()==DefenderControl.PLAYER_TWO_ID && u.isInner(realClickKoordinates)){	
					u.activate();
					//merken
					this.getPlayerTwo().getActiveUnits().add(u);
				}
			}

			//neues Ziel setzen wenn unit aktiv
			for(BaseUnit u: this.getGlobalUnits()){
			
				if(u.isActive()){
					//wenn  klick auf eine gegnerische Unit dann angriff
					for(BaseUnit bu: this.getGlobalUnits()){
						
						//auf gegnerische einheit geklickt??
						if(bu.getPlayerID()==DefenderControl.PLAYER_ONE_ID && bu.isInner(realClickKoordinates)){
							istAngriff=true;
							destinationUnit=bu;
							System.out.println("Angriff initiiert!");
						}
						
						//auf weitere eigene einheit geklickt??
						if(bu.getPlayerID()==DefenderControl.PLAYER_TWO_ID && bu.isInner(realClickKoordinates)){
							weitereAktivierung=true;
							bu.activate();
							System.out.println("Weitere Einheit aktiviert!");
						}

					}					
					
					if(!weitereAktivierung){
						//bewegung anweisen wenn kein angriff
						if(!istAngriff){
							for(BaseUnit activeUnit: this.getPlayerTwo().getActiveUnits()){
								activeUnit.commandDestination(realClickKoordinates);								
							}

						}else{
							//falls angriff dann Angriff anweisen
							for(BaseUnit activeUnit: this.getPlayerTwo().getActiveUnits()){
								activeUnit.attack(destinationUnit);								
							}
						}
					}

				}
			}	
    	}
	     
    				
		if(mouseButton==MOUSE_RIGHT){
			
			for(BaseUnit u: this.getPlayerTwo().getActiveUnits()){
				u.deactivate();
				this.getPlayerTwo().getActiveUnits().remove(u);
			}
		}
			
    	
    }
    
    //Einheiten Kontrolle Spieler Eins
    public void unitControlLeftSide(PVector clickVector,int mouseButton){
    	
    	boolean weitereAktivierung=false;
	    boolean istAngriff=false;
	    BaseUnit destinationUnit=null;
	    
	    //Klickvektor zurück rechnen auf spielkoordinaten
		PVector realClickKoordinates=GraphicTools.calcInputVector(clickVector, this.getPlayerOne());
	    
    	if(mouseButton==MOUSE_LEFT){
 
			for(BaseUnit u: this.getGlobalUnits()){
				
				//wenn eine unit aktiviert wird dann die anderen deaktiveren
				if(u.getPlayerID()==DefenderControl.PLAYER_ONE_ID && u.isInner(realClickKoordinates)){	
					u.activate();
					//merken
					this.getPlayerOne().getActiveUnits().add(u);
				}
			}

			//neues Ziel setzen wenn unit aktiv
			for(BaseUnit u: this.getGlobalUnits()){
			
				if(u.isActive()){
					//wenn  klick auf eine gegnerische Unit dann angriff
					for(BaseUnit bu: this.getGlobalUnits()){
						
						//auf gegnerische einheit geklickt??
						if(bu.getPlayerID()==DefenderControl.PLAYER_TWO_ID && bu.isInner(realClickKoordinates)){
							istAngriff=true;
							destinationUnit=bu;
							System.out.println("Angriff initiiert!");
							//TODO Sound
						}
						
						//auf weitere eigene einheit geklickt??
						if(bu.getPlayerID()==DefenderControl.PLAYER_ONE_ID && bu.isInner(realClickKoordinates)){
							weitereAktivierung=true;
							bu.activate();
							System.out.println("Weitere Einheit aktiviert!");
						}

					}					
					
					if(!weitereAktivierung){
						//bewegung anweisen wenn kein angriff
						if(!istAngriff){
							for(BaseUnit activeUnit: this.getPlayerOne().getActiveUnits()){
								activeUnit.commandDestination(realClickKoordinates);								
							}

						}else{
							//falls angriff dann Angriff anweisen
							for(BaseUnit activeUnit: this.getPlayerOne().getActiveUnits()){
								activeUnit.attack(destinationUnit);								
							}
						}
					}

				}
			}	
    	}
	     
    				
		if(mouseButton==MOUSE_RIGHT){

			
			for(BaseUnit u: this.getPlayerOne().getActiveUnits()){
				u.deactivate();
				this.getPlayerOne().getActiveUnits().remove(u);
			}
		}
			
    }

	@Override
	public void addTuioCursor(TuioCursor arg0) {
		// TODO MINKE
		  System.out.println("add cursor "+arg0.getCursorID()+" ("+arg0.getSessionID()+ ") " +arg0.getX()+" "+arg0.getY());
		  
		  	PVector vector=new PVector(arg0.getScreenX(DefenderViewSlick.HEIGHT),arg0.getScreenY(DefenderViewSlick.WIDTH));
		  	boolean wurdeEbendAktiviert=false;	
		  	boolean warSchonAktiv=false;
		
			for(BaseUnit u: this.getGlobalUnits()){
				
				
				//wenn eine unit aktiviert wird dann die anderen deaktiveren
				if(!wurdeEbendAktiviert){
					
					//wenn bereits aktiv dann deaktivieren
					warSchonAktiv=u.isActive();
					wurdeEbendAktiviert=u.isInner(vector);	
					
					if(wurdeEbendAktiviert&&warSchonAktiv){
						u.deactivate();
					}
					
				}else{
					u.deactivate();
				}
			}
			
			//neues Ziel setzen wenn unit aktiv
			for(BaseUnit u: this.getGlobalUnits()){
				if(u.isActive()){
					u.commandDestination(vector);				
				}
			}
	}

	@Override
	public void addTuioObject(TuioObject arg0) {
		// TODO MINKE
		//gestures.methode
		
	}

	@Override
	public void refresh(TuioTime arg0) {
		// TODO MINKE
		
	}

	@Override
	public void removeTuioCursor(TuioCursor arg0) {
		// TODO MINKE
		
	}

	@Override
	public void removeTuioObject(TuioObject arg0) {
		// TODO MINKE
		
	}

	@Override
	public void updateTuioCursor(TuioCursor arg0) {
		// TODO MINKE
		
	}

	@Override
	public void updateTuioObject(TuioObject arg0) {
		// TODO MINKE
		
	}
	
	/**
	 * Zoomen der Anzeige
	 * 
	 * @param oldx
	 * @param oldy
	 * @param newx
	 * @param newy
	 */
	public void zoomInterface(int oldx, int oldy, int newx, int newy){
		if (newx < 512) {
			this.getPlayerOne().setActualZoom(this.getPlayerOne().getActualZoom() + (newy -  oldy) * 0.01f);    			
		} else {
			this.getPlayerTwo().setActualZoom(this.getPlayerTwo().getActualZoom()  + (newy -  oldy) * 0.01f);    			
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
	public void schiebeInterface(int oldx, int oldy, int newx, int newy){
		if (newx < 512) {
			PVector tempVec = this.getPlayerOne().getViewPosition();
			tempVec.y = tempVec.y + oldx - newx;
			tempVec.x = tempVec.x + newy - oldy;
		} else {
			PVector tempVec = this.getPlayerTwo().getViewPosition();
			tempVec.y = tempVec.y + newx  - oldx;
			tempVec.x = tempVec.x + oldy - newy ;
		}
	}

	
	public void createTestUnits(){
		
		  //TestUnitBetas schaffen
		BaseUnit test=new BaseUnit(100,200,BaseUnit.MODE_ROTATE,this.playerOne,this);
		test.commandDestination(new PVector(1000,700));
	
		  //Testflugstaffel
		  new Tank(100,50,BaseUnit.MODE_NORMAL,this.playerTwo,this);
		  new Defence(200,50,BaseUnit.MODE_NORMAL,this.playerTwo,this);
		  new Ground(300,50,BaseUnit.MODE_NORMAL,this.playerTwo,this);
		  new Navi(400,50,BaseUnit.MODE_NORMAL,this.playerTwo,this);
		  new Support(500,50,BaseUnit.MODE_NORMAL,this.playerTwo,this);	  
		  new Fighter(600,50,BaseUnit.MODE_NORMAL,this.playerTwo,this);
		  new Fighter(700,50,BaseUnit.MODE_PULSE_IF_ACTIVE,this.playerTwo,this);
		
		  //Testflugstaffel playerOne
		  new Fighter(100,700,BaseUnit.MODE_NORMAL,this.playerOne,this);
		  new Fighter(200,700,BaseUnit.MODE_NORMAL,this.playerOne,this);
		  new Fighter(300,700,BaseUnit.MODE_NORMAL,this.playerOne,this);
		  new Fighter(400,700,BaseUnit.MODE_NORMAL,this.playerOne,this);
		  new Fighter(500,700,BaseUnit.MODE_NORMAL,this.playerOne,this);
		  new BaseUnit(600,700,BaseUnit.MODE_PULSE,this.playerOne,this);
		  new BaseUnit(700,700,BaseUnit.MODE_ROTATE_AND_PULSE,this.playerOne,this);
		  new BaseUnit(800,700,BaseUnit.MODE_NORMAL,this.playerOne,this);

	}
}
