package de.fhb.defenderTouch.gamelogic;



import java.awt.Font;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

import processing.core.PApplet;
import processing.core.PVector;
import de.fhb.defenderTouch.audio.FormatProblemException;
import de.fhb.defenderTouch.audio.SampleThread;
import de.fhb.defenderTouch.graphics.GraphicTools;
import de.fhb.defenderTouch.menu.Menu;
import de.fhb.defenderTouch.units.root.BaseUnit;

/**
 * Diese Klasse stellt die Verbindung zwischen Anzeige, also dem PApplet dar und
 * dem eigentlichen Spielinhalt wie die Units, Spiellogik usw. (und noch
 * zuk�nftige dinge)
 * 
 * @author Salaxy 
 */
public class DefenderControl {
	
	public static final int MOUSE_LEFT = 0;
	public static final int MOUSE_RIGHT = 1;

	public static final int PLAYER_ONE = 0;
	public static final int PLAYER_TWO = 1;
	public static final int PLAYER_SYSTEM = 2;
	
	
	
	/**
	 * gibt an wo auf dem PApplet der screenLeft gezeichnet werden soll
	 */
	public static PVector SCREENLEFT_POSITION= new PVector(0, 0);
	
	/**
	 * gibt an wo auf dem PApplet der screenLeft gezeichnet werden soll
	 */
	public static PVector SCREENRIGHT_POSITION= new PVector(515, 0);
	

	private CopyOnWriteArrayList<BaseUnit> globalUnits;
//	private Graphics display;
	private static DefenderControl instance = null;
	private Player playerOne;
	private Player playerTwo;
//	private PGraphics screenLeft, screenRight;
	private Gamemap map;
	
	
	private Menu menuePlayerOne;
	private Menu menuePlayerTwo;

	public DefenderControl(
//			Graphics display
//			,PGraphics screenLeft,PGraphics screenRight
			) {
		//map init
		map=new Gamemap();
		
		//die beiden Spieler initialisieren
		playerOne = new Player(this,PApplet.HALF_PI, 0.65f, new PVector(512f,0f),Player.LEFT, new PVector(0f,0f),SCREENLEFT_POSITION, Color.blue);
//		playerTwo = new Player(this,3*PApplet.HALF_PI,0.65f, new PVector(0f, 768f),Player.RIGHT, new PVector(0f,0f),SCREENRIGHT_POSITION, Color.green);
		playerTwo = new Player(this,3*PApplet.HALF_PI,0.65f, new PVector(512f, 768f),Player.RIGHT, new PVector(0f,0f),SCREENRIGHT_POSITION, Color.green);
		
		globalUnits = new CopyOnWriteArrayList<BaseUnit>();
		
		//init der beiden images fuer links und rechts
//		this.screenLeft = screenLeft;
//		this.screenRight = screenRight;
//		
		
		//Menue init//TODO gifanimation
		menuePlayerOne = new Menu(this.globalUnits,playerOne, DefenderControl.PLAYER_ONE);
		menuePlayerTwo = new Menu(this.globalUnits,playerTwo, DefenderControl.PLAYER_TWO);
		
		

		
//		this.playBackgroundSound();

	}

	public Gamemap getMap() {
		return map;
	}


	public CopyOnWriteArrayList<BaseUnit> getGlobalUnits() {
		return globalUnits;

	}

	/**
	 * Zeichnet beide Spielfelder und Inhalte
	 */
	public void drawAll(Graphics display) {		
		

		
		//units playerOne zeichen
		for (BaseUnit unit : globalUnits) {
			unit.calcNewPosition();
		}
		
		// Linke Seite vorzeichnen
//		screenLeft.beginDraw();		
//		screenLeft.smooth();
//		screenLeft.rectMode(PGraphics.CENTER);
//		screenLeft.background(255f);
		
		//Feld zeichnen
//		this.zeichneRahmen(screenLeft, playerOne);	
		this.map.zeichne(display, playerOne);	
		

		//units playerOne zeichen
		for (BaseUnit unit : globalUnits) {
			if(unit.isActive()&&unit.getPlayerID()==PLAYER_ONE){
				//zeichne Aktiviert
				unit.paint(this.playerOne, display,true);	
			}else{
				//zeichen normal
				unit.paint(this.playerOne, display,false);				
			}
		}
		
		//menue zeichen fuer player one
		this.menuePlayerOne.drawMenu(display, this.playerOne);
		
		//creditsinfo zeichnen
//		display.textSize(20f);
//		display.fill(0);
//		display.rotate(PApplet.HALF_PI);
//		display.text("Credits: " + playerOne.getCredits(), 20, -15);

		
		display.setColor(Color.black);
//		display.translate(510, 768);
//		display.translate(510, 768);
		display.rotate(0,0,90);
		display.scale(1.2f, 1.2f);
		display.drawString("Credits: " + playerOne.getCredits(), 20, -15);
////		screenLeft.text("Aktuelles Geb�ude: " + menuePlayerOne.getActualBuildingName(), 350, -15);
//		display.text("Geb�udeanzahl: " + menuePlayerOne.getActualBuildingCount(), 300, -15);
		
//		screenLeft.endDraw();
		display.resetTransform();

		
		// Rechte Seite vorzeichnen
//		screenRight.beginDraw();
//		screenRight.smooth();
//		screenRight.rectMode(PGraphics.CENTER);
//		screenRight.background(255f);
		
		//Feld zeichnen
//		zeichneRahmen(screenRight, playerTwo);
		this.map.zeichne(display, playerTwo);	
		display.resetTransform();
		
		//units playerTwo zeichen
		for (BaseUnit unit : globalUnits) {
			if(unit.isActive()&&unit.getPlayerID()==PLAYER_TWO){
				//zeichne Aktiviert
				unit.paint(this.playerTwo, display,true);	
			}else{
				//zeichen normal
				unit.paint(this.playerTwo, display,false);				
			}
		}
		
		//menue zeichen fuer playerTwo
//		this.menuePlayerTwo.drawMenu(display, this.playerTwo);
		
//		Font f = new Font("Verdana", Font.PLAIN, 18);

//		TrueTypeFont ttf = new TrueTypeFont(f, false);

		//creditsinfo zeichnen
//		display.textSize(20f);
//		display.fill(0);
		display.setColor(Color.black);
//		display.translate(510, 768);
		display.translate(510, 768);
		display.rotate(0,0,-90);		
		display.drawString("Credits: " + playerOne.getCredits(), 25, 490);
//		ttf.drawString(10, 10, "Credits: " + playerTwo.getCredits());
//		screenRight.text("Aktuelles Geb�ude: " + menuePlayerTwo.getActualBuildingName(), 350, -15);
//		display.text("Geb�udeanzahl: " + menuePlayerTwo.getActualBuildingCount(), 300, -15);
		display.resetTransform();
		
		
//		display.endDraw();

//		//die beiden Seiten auf dem PApplet zeichnen
//		display.image(display, SCREENLEFT_POSITION.x, SCREENLEFT_POSITION.y);
//		display.image(display, SCREENRIGHT_POSITION.x, SCREENRIGHT_POSITION.y);
//		
		
		
		//Trennlinie
		display.setColor(Color.red);
//		display.stroke(0);
//		display.drawLine(arg0, arg1, arg2, arg3);
		display.drawLine(511f, 0f, 511f, 768f);
		display.drawLine(512f, 0f, 512f, 768f);
		display.drawLine(513f, 0f, 513f, 768f);
	}

//	public void zeichneRahmen(PGraphics graphics, Player player){
//		
//		
//		//Transformationen
//		GraphicTools.calcDrawTransformation(player, graphics, new PVector(0,0));
//	
//		//Feldumrandung zeichnen
//		graphics.fill(255, 255, 0,55);
//		graphics.stroke(0, 0, 0);
//		graphics.rect(512f, 384f, 1024, 768);
//		
//		graphics.resetMatrix();
//		
//	}
	

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
			// TODO Auto-generated catch block
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
	 * Methode die aufgerufen werden soll wenn das menue(Bau/Gebauedemenue) ge�ffnet werden soll
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
	    
	    //Klickvektor zur�ck rechnen auf spielkoordinaten
		PVector realClickKoordinates=GraphicTools.calcInputVector(clickVector, this.getPlayerTwo());


    	if(mouseButton==MOUSE_LEFT){    	
			for(BaseUnit u: this.getGlobalUnits()){
				
				//wenn eine unit aktiviert wird dann die anderen deaktiveren
				if(u.getPlayerID()==DefenderControl.PLAYER_TWO && u.isInner(realClickKoordinates)){	
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
						if(bu.getPlayerID()==DefenderControl.PLAYER_ONE && bu.isInner(realClickKoordinates)){
							istAngriff=true;
							destinationUnit=bu;
							System.out.println("Angriff initiiert!");
						}
						
						//auf weitere eigene einheit geklickt??
						if(bu.getPlayerID()==DefenderControl.PLAYER_TWO && bu.isInner(realClickKoordinates)){
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
	    
	    //Klickvektor zur�ck rechnen auf spielkoordinaten
		PVector realClickKoordinates=GraphicTools.calcInputVector(clickVector, this.getPlayerOne());
	    
	     		System.out.println(mouseButton); 
//	     		System.out.println(PConstants.LEFT);
    	if(mouseButton==MOUSE_LEFT){
 
			for(BaseUnit u: this.getGlobalUnits()){
				
				//wenn eine unit aktiviert wird dann die anderen deaktiveren
				if(u.getPlayerID()==DefenderControl.PLAYER_ONE && u.isInner(realClickKoordinates)){	
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
						if(bu.getPlayerID()==DefenderControl.PLAYER_TWO && bu.isInner(realClickKoordinates)){
							istAngriff=true;
							destinationUnit=bu;
							System.out.println("Angriff initiiert!");
							//TODO Sound
						}
						
						//auf weitere eigene einheit geklickt??
						if(bu.getPlayerID()==DefenderControl.PLAYER_ONE && bu.isInner(realClickKoordinates)){
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
	     
    				
		if(mouseButton==1){

			
			for(BaseUnit u: this.getPlayerOne().getActiveUnits()){
				u.deactivate();
				this.getPlayerOne().getActiveUnits().remove(u);
			}
		}
			
    }
	
	

}
