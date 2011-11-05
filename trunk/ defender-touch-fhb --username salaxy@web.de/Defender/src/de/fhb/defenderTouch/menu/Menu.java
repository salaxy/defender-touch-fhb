package de.fhb.defenderTouch.menu;

import gifAnimation.Gif;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.graphics.GraphicTools;
import de.fhb.defenderTouch.units.grounded.Building;
import de.fhb.defenderTouch.units.grounded.Defence;
import de.fhb.defenderTouch.units.grounded.Ground;
import de.fhb.defenderTouch.units.grounded.Support;
import de.fhb.defenderTouch.units.movable.BaseUnit;

public class Menu {

	/**
	 * applet where the buildings are placed
	 */
	// protected PGraphics graphics;

	/**
	 * recent position of the click
	 */
	protected PVector position;

	/**
	 * recent position of the building
	 */
	protected PVector positionBuilding;

	/**
	 * menu points, actual 6 points, 3 in use - Ground - Defence - Support
	 */
	protected PVector menu[] = new PVector[6];

	/**
	 * buildings menu point, 4 points, 2 in use - Upgrade - Destroy
	 */
	protected PVector menuBuildings[] = new PVector[2];

	/**
	 * Vector with all saved building that palyed has built
	 */
	// protected CopyOnWriteArrayList<PVector> bBuildings = new
	// CopyOnWriteArrayList<PVector>();

	/**
	 * saves if the menu is open
	 */
	protected boolean menuOpen = false;

	/**
	 * saves if the building options is open
	 */
	protected boolean buildingOpen = false;

	/**
	 * activation raduis of a menupoint
	 */
	protected final int DIAMETERCIRCLEMENU = 100;

	/**
	 * Radius of the circle Is always half its diameter
	 */
	protected final int RADIUSCIRCLEMENU;

	/**
	 * size of the menu text
	 */
	protected final int TEXTSIZE;

	/**
	 * Aktivierungsradius eines Menupunkts
	 */
	protected final int DISTANCE;

	/**
	 * Nummer des geb�udes
	 */
	protected int actualStatus = -1;

	/**
	 * actual address with the building, used for deleting it
	 */
	protected Building actualBuilding = null;

	/**
	 * array list with all its buildings
	 */
	protected CopyOnWriteArrayList<BaseUnit> buildings;

	/**
	 * array list with all its buildings
	 */
	protected String actualBuildingName = "Nichts gew�hlt";

	/**
	 * array list with all its buildings
	 */
	protected int actualBuildingCount = 0;

	/**
	 * Gif animation of destroying a building
	 */
	protected Gif nonLoopingGifDestroy;

	// /**
	// * if destroying a building should be shown
	// */
	// protected boolean showLoopingGifDestroy = false;

	/**
	 * Besitzer dieses Menue objekts
	 */
	private Player player;

	/**
	 * Constructor of Menu
	 * 
	 * @param nonLoopingGif
	 */
	public Menu(CopyOnWriteArrayList<BaseUnit> buildings, Gif nonLoopingGifDestroy, Player player) {
		this.position = new PVector(0, 0);
		// this.graphics = graphics;
		this.buildings = buildings;
		this.player = player;
		this.RADIUSCIRCLEMENU = this.DIAMETERCIRCLEMENU / 2;
		this.TEXTSIZE = this.RADIUSCIRCLEMENU / 2;
		this.DISTANCE = -(this.DIAMETERCIRCLEMENU + 5);
		System.out.println(DISTANCE);
		// this.nonLoopingGifDestroy = new Gif(graphics, "expl_kl.gif");
		// TODO hier Gif erstellen

		for (int i = 0; i < menu.length; i++) {
			menu[i] = new PVector(-100, -100);
		}
		for (int i = 0; i < menuBuildings.length; i++) {
			menuBuildings[i] = new PVector(-100, -100);
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void rotateMenu(int element, float rotation, PGraphics graphics) {
		graphics.rotate(rotation);
		menu[element] = new PVector(0, DISTANCE);
		menu[element].rotate(rotation);
		menu[element].add(position);
	}

	public void rotateMenuBuildings(int element, float rotation, PGraphics graphics) {
		graphics.rotate(rotation);
		menuBuildings[element] = new PVector(0, DISTANCE);
		menuBuildings[element].rotate(rotation);
		menuBuildings[element].add(position);
	}

	public void calcDrawTransformation(PGraphics graphics) {
		GraphicTools.calcDrawTransformation(player, graphics, position);
	}

	public void calcDrawTransformationBuildings(PGraphics graphics) {
		GraphicTools.calcDrawTransformation(player, graphics, positionBuilding);
	}

	public void showBigMenuCircle(PGraphics graphics) {
		graphics.ellipse(0, DISTANCE, DIAMETERCIRCLEMENU, DIAMETERCIRCLEMENU);
	}

	public void showSmallMenuCircle(PGraphics graphics) {
		graphics.ellipse(0, DISTANCE, RADIUSCIRCLEMENU, RADIUSCIRCLEMENU);
	}

	public void showVerySmallMenuCircle(PGraphics graphics) {
		graphics.ellipse(0, DISTANCE + (DISTANCE / 2), RADIUSCIRCLEMENU, RADIUSCIRCLEMENU);
	}

	public void showSignGround(PGraphics graphics) {
		graphics.triangle(-5, DISTANCE + 5, 0, DISTANCE - 5, +5, DISTANCE + 5);
	}

	public void showSignDefence(PGraphics graphics) {
		ArrayList<PVector> vektoren1 = new ArrayList<PVector>();
		vektoren1.add(new PVector(-4, DISTANCE - 4));
		vektoren1.add(new PVector(4, DISTANCE - 4));
		vektoren1.add(new PVector(0, DISTANCE - 4));
		vektoren1.add(new PVector(0, DISTANCE - 8));
		vektoren1.add(new PVector(0, DISTANCE + 4));
		vektoren1.add(new PVector(4, DISTANCE + 4));
		vektoren1.add(new PVector(-4, DISTANCE + 4));
		graphics.ellipse(0, DISTANCE - 4, 8, 8);
		GraphicTools.zeicheFigurNachVektoren(vektoren1, graphics);
	}

	public void showSignSupport(PGraphics graphics) {
		ArrayList<PVector> vektoren2 = new ArrayList<PVector>();
		vektoren2.add(new PVector(0, DISTANCE - 8));
		vektoren2.add(new PVector(0, DISTANCE + 8));
		vektoren2.add(new PVector(0, DISTANCE));
		vektoren2.add(new PVector(8, DISTANCE));
		vektoren2.add(new PVector(-8, DISTANCE));
		GraphicTools.zeicheFigurNachVektoren(vektoren2, graphics);
	}

	public void showSignUpgrade(PGraphics graphics) {
		graphics.triangle(-5, DISTANCE, 0, DISTANCE - 10, +5, DISTANCE);
		ArrayList<PVector> vektoren1 = new ArrayList<PVector>();
		vektoren1.add(new PVector(0, DISTANCE));
		vektoren1.add(new PVector(0, DISTANCE + 8));
		GraphicTools.zeicheFigurNachVektoren(vektoren1, graphics);
	}
	
	public void showSignDestroy(PGraphics graphics) {
		ArrayList<PVector> vektoren2 = new ArrayList<PVector>();
		vektoren2.add(new PVector(5, DISTANCE - 5));
		vektoren2.add(new PVector(-5, DISTANCE + 5));
		vektoren2.add(new PVector(0, DISTANCE));
		vektoren2.add(new PVector(5, DISTANCE + 5));
		vektoren2.add(new PVector(-5, DISTANCE - 5));
		GraphicTools.zeicheFigurNachVektoren(vektoren2, graphics);
	}

	public void changeActualColor(Color color, PGraphics graphics) {
		graphics.fill(color.getRed(), color.getGreen(), color.getBlue());
	}

	public void showPriceBuildings(PGraphics graphics, int price) {
		graphics.text(price, 0, DISTANCE - RADIUSCIRCLEMENU);
	}

	/**
	 * is always been done
	 */
	public void drawMenu(PGraphics graphics, Player player) {

		// graphics.text("Aktuelle Credits: " + creditsPlayer, 100, 15);
		// graphics.text("Aktuelles Geb�ude: " + actualBuildingName, 350, 15);
		// graphics.text("Aktuelle Geb�udeanzahl: " + getActualBuildingCount(),
		// 600, 15);

		// calcDrawPosition(player , graphics );

		graphics.ellipseMode(PConstants.CENTER);

		// TODO wieder einbauen...muss aber �ber Pgraphics zeichenabr sein
		// /**
		// * here is the animation of destroying a building
		// */
		// if (isShowLoopingGifDestroy() && positionBuilding != null) {
		// graphics.image(nonLoopingGifDestroy, positionBuilding.x - 11,
		// positionBuilding.y - 15);
		// nonLoopingGifDestroy.loop();
		// if (nonLoopingGifDestroy.currentFrame() == 15) {
		// setShowLoopingGifDestroy(false);
		// nonLoopingGifDestroy.stop();
		// }
		// }

		/**
		 * here is the complete normal menu
		 */
		if (menuOpen) {

			graphics.textAlign(PApplet.CENTER);
			graphics.textSize(TEXTSIZE);

			graphics.stroke(255);
			float rotation = 0f;
			float drehungProUntermenue = PApplet.TWO_PI / 6;

			calcDrawTransformation(graphics);
			rotateMenu(0, rotation, graphics);
			changeActualColor(Color.CYAN, graphics);
			showBigMenuCircle(graphics);
			showSignGround(graphics);
			showVerySmallMenuCircle(graphics);
			changeActualColor(Color.WHITE, graphics);
			showPriceBuildings(graphics, Ground.PRICE);
			rotation += drehungProUntermenue;
			graphics.resetMatrix();

			calcDrawTransformation(graphics);
			rotateMenu(1, rotation, graphics);
			changeActualColor(Color.BLUE, graphics);
			showBigMenuCircle(graphics);
			showSignDefence(graphics);
			showVerySmallMenuCircle(graphics);
			changeActualColor(Color.WHITE, graphics);
			showPriceBuildings(graphics, Defence.PRICE);
			rotation += drehungProUntermenue;
			graphics.resetMatrix();

			calcDrawTransformation(graphics);
			rotateMenu(2, rotation, graphics);
			changeActualColor(Color.MAGENTA, graphics);
			showBigMenuCircle(graphics);
			showSignSupport(graphics);
			showVerySmallMenuCircle(graphics);
			changeActualColor(Color.WHITE, graphics);
			showPriceBuildings(graphics, Support.PRICE);
			rotation += drehungProUntermenue;
			graphics.resetMatrix();

			graphics.noFill();
			graphics.stroke(150);
			calcDrawTransformation(graphics);
			rotateMenu(3, rotation, graphics);
			showSmallMenuCircle(graphics);
			rotation += drehungProUntermenue;
			graphics.resetMatrix();

			calcDrawTransformation(graphics);
			rotateMenu(4, rotation, graphics);
			showSmallMenuCircle(graphics);
			rotation += drehungProUntermenue;
			graphics.resetMatrix();

			calcDrawTransformation(graphics);
			rotateMenu(5, rotation, graphics);
			showSmallMenuCircle(graphics);
			rotation += drehungProUntermenue;
			graphics.resetMatrix();
		}

		/**
		 * here is the complete menu for a specific building
		 */
		if (buildingOpen) {
			graphics.textAlign(PApplet.CENTER);
			graphics.textSize(TEXTSIZE);
			graphics.noFill();
			graphics.stroke(255);

			float rotation = 0f;
			float drehungProUntermenue = PApplet.TWO_PI / 4;

			calcDrawTransformationBuildings(graphics);
			rotateMenuBuildings(0, rotation, graphics);
			changeActualColor(Color.GREEN, graphics);
			showBigMenuCircle(graphics);
			showVerySmallMenuCircle(graphics);
			changeActualColor(Color.WHITE, graphics);
			showSignUpgrade(graphics);
			showPriceBuildings(graphics, getActualBuildingPrice(positionBuilding));
			rotation += drehungProUntermenue;
			graphics.resetMatrix();

			calcDrawTransformationBuildings(graphics);
			rotateMenuBuildings(1, rotation, graphics);
			changeActualColor(Color.RED, graphics);
			showBigMenuCircle(graphics);
			showSignDestroy(graphics);
			rotation += drehungProUntermenue;
			graphics.resetMatrix();

			calcDrawTransformationBuildings(graphics);
			graphics.rotate(rotation);
			graphics.fill(150);
			showSmallMenuCircle(graphics);
			// TODO ist unsch�n! aber mir f�llt grad nix bessres ein, 3 steht
			// sonst falsch rum...
			graphics.rotate(rotation);
			changeActualColor(Color.WHITE, graphics);
			graphics.text(getActualBuildingLevel(positionBuilding), 0, Math.abs(DISTANCE) + 5);
			graphics.resetMatrix();
		}

		graphics.resetMatrix();
		graphics.textAlign(PApplet.CORNER);
	}

	public String getActualBuildingName() {
		return actualBuildingName;
	}

	/**
	 * 
	 * @param clickVector
	 *            (actual position of mouse click)
	 * @return true (if click was in middle circle of menu)
	 */
	public boolean isInnerMainElement(PVector clickVector) {

		if (this.position.dist(clickVector) < this.RADIUSCIRCLEMENU) {
			// System.out.println("Menu close choosed");
			setMenuOpen(false);
			return true;
		}
		return false;

	}

	/**
	 * 
	 * @param clickVector
	 *            (actual position of mouse click)
	 * @return true (if click was in a menu option circle of menu)
	 * 
	 *         looking if a menu point was clicked
	 */
	public boolean isInnerMenuElement(PVector clickVector) {
		if (this.menu[0].dist(clickVector) < this.RADIUSCIRCLEMENU) {
			// System.out.println("Menue 1");

			if (isEnoughCredits(Ground.PRICE)) {
				player.setCredits(player.getCredits() - Ground.PRICE);
				setActualStatus(0);
				setMenuOpen(false);
				return true;
			}
		}
		if (this.menu[1].dist(clickVector) < this.RADIUSCIRCLEMENU) {
			// System.out.println("Menue 2");
			if (isEnoughCredits(Defence.PRICE)) {
				player.setCredits(player.getCredits() - Defence.PRICE);
				setActualStatus(1);
				setMenuOpen(false);
				return true;
			}
		}
		if (this.menu[2].dist(clickVector) < this.RADIUSCIRCLEMENU) {
			// System.out.println("Menue 3");
			if (isEnoughCredits(Support.PRICE)) {
				player.setCredits(player.getCredits() - Support.PRICE);
				setActualStatus(2);
				setMenuOpen(false);
				return true;
			}
		}
		if (this.menu[3].dist(clickVector) < this.RADIUSCIRCLEMENU) {
			// System.out.println("Menue 4");
		}
		if (this.menu[4].dist(clickVector) < this.RADIUSCIRCLEMENU) {
			// System.out.println("Menue 5");
		}
		if (this.menu[5].dist(clickVector) < this.RADIUSCIRCLEMENU) {
			// System.out.println("Menue 6");
		}

		return false;

	}

	/**
	 * 
	 * @param clickVector
	 *            (actual position of mouse click)
	 * @return true (if click was in a specific circle of menu)
	 * 
	 *         looking if a specific building menu point was clicked
	 */
	public boolean isInnerBuildingElement(PVector clickVector) {

		if (this.menuBuildings[0].dist(clickVector) < this.RADIUSCIRCLEMENU) {
			setBuildingOpen(false, null);
			upgradeBuilding();
			setActualStatus(0);
			return true;
		}
		if (this.menuBuildings[1].dist(clickVector) < this.RADIUSCIRCLEMENU) {
			setBuildingOpen(false, null);
			setActualBuildingDestroyPrice();
			setActualStatus(1);
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @param credits
	 *            (credits of a player)
	 * @return true (if enough credits is there)
	 * 
	 *         looking if enough credits are there
	 */
	public boolean isEnoughCredits(int credits) {
		if (player.getCredits() - credits >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param credits
	 *            (credits of a player)
	 * @return true (if enough credits is there)
	 * 
	 *         looking if enough credits are there
	 */
	public void upgradeBuilding() {
		if (player.getCredits() - getActualBuildingPrice(positionBuilding) >= 0 && actualBuilding.getLevel() != 3) {
			player.setCredits(player.getCredits() - getActualBuildingPrice(positionBuilding));
		}
	}

	/**
	 * 
	 * @param buildingOpen
	 *            (status of the building menu)
	 * @param clickVector
	 *            (actual position of mouse click)
	 * 
	 *            changes the actual building, so that we know which building is
	 *            active
	 */
	public void setBuildingOpen(boolean buildingOpen, PVector clickVector) {

		// damit menue acuh geschlossen werden kann
		if (!buildingOpen) {
			this.buildingOpen = false;
		} else {
			// es soll das baumenu aktiviert werden
			if (clickVector != null) {
				for (BaseUnit bu : buildings) {
					if (bu.isInner(clickVector)) {
						if (bu instanceof Building) {
							actualBuilding = (Building) bu;
							// nur baumenue oeffnen wenn auch Gebauede
							// angeclickt
							this.buildingOpen = buildingOpen;
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param clickVector
	 *            (actual position of mouse click)
	 * @return level (level of the specific building)
	 * 
	 *         searching for the actual building level
	 */
	public int getActualBuildingLevel(PVector clickVector) {
		// XXX geht wesentlich besser
		if (clickVector != null) {
			for (BaseUnit bu : buildings) {
				if (bu.isInner(clickVector)) {
					if (bu instanceof Building) {
						return actualBuilding.getLevel();
					}
				}
			}
		}
		return 0;
	}

	/**
	 * 
	 * @param clickVector
	 *            (actual position of mouse click)
	 * @return PRICE (price of the specific building)
	 * 
	 *         searching for the actual price of a building
	 */
	public int getActualBuildingPrice(PVector clickVector) {
		// XXX geht wesentlich besser, genau daselbe
		if (clickVector != null) {
			for (BaseUnit bu : buildings) {
				if (bu.isInner(clickVector)) {
					if (bu instanceof Building) {
						if (bu instanceof Ground) {
							return Ground.PRICE;
						}
						if (bu instanceof Defence) {
							return Defence.PRICE;
						}
						if (bu instanceof Support) {
							return Support.PRICE;
						}
					}
				}
			}
		}
		return 0;
	}

	/**
	 * 
	 * @param clickVector
	 *            (actual position of mouse click)
	 * @return PRICE (price of the specific building)
	 * 
	 *         searching for the actual price of a building
	 */
	public void setActualBuildingName(PVector clickVector) {
		// XXX geht wesentlich besser, genau daselbe
		if (isTaken(clickVector)) {
			for (BaseUnit bu : buildings) {
				if (bu.isInner(positionBuilding)) {
					if (bu instanceof Building) {
						if (bu instanceof Defence) {
							actualBuildingName = "Defence";
						}
						if (bu instanceof Ground) {
							actualBuildingName = "Ground";
						}
						if (bu instanceof Support) {
							actualBuildingName = "Support";
						}
					}
				}
			}
		} else {
			actualBuildingName = "Nichts gew�hlt";
		}
	}

	/**
	 * 
	 * @param clickVector
	 *            (actual position of mouse click)
	 * @return PRICE (price of the specific building)
	 * 
	 *         calculate the credits u get from destroying a building
	 */
	public void setActualBuildingDestroyPrice() {
		// XXX geht wesentlich besser, genau daselbe
		if (positionBuilding != null) {
			for (BaseUnit bu : buildings) {
				if (bu.isInner(positionBuilding)) {
					if (bu instanceof Building) {
						// setShowLoopingGifDestroy(true);
						if (bu instanceof Defence) {
							player.setCredits(player.getCredits() + (Defence.PRICE * bu.getLevel()) / 2);
						}
						if (bu instanceof Ground) {
							player.setCredits(player.getCredits() + (Ground.PRICE * bu.getLevel()) / 2);
						}
						if (bu instanceof Support) {
							player.setCredits(player.getCredits() + (Support.PRICE * bu.getLevel()) / 2);
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param clickVector
	 * @return if place for the new building is free
	 */
	public boolean isTaken(PVector clickVector) {
		for (BaseUnit building : buildings) {
			// BUILDING IS CLICKED
			if (building.getPosition().dist(clickVector) < (building.getCollisionRadius())) {
				// System.out.println("ficke dich");
				setPositionBuilding(building.getPosition());
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @return Number of buildings on map
	 */
	public int getActualBuildingCount() {
		actualBuildingCount = 0;
		// TODO funzt net richtig, zeigt Globale anzahl an Geb�ude an!
		for (BaseUnit building : buildings) {
			// if a building is clicked
			if (building instanceof Building)
				actualBuildingCount++;
		}
		return actualBuildingCount;
	}

	public boolean isBuildingOpen() {
		return buildingOpen;
	}

	public void setActualStatus(int actualStatus) {
		this.actualStatus = actualStatus;
	}

	public void setMenuOpen(boolean menuOpen) {
		this.menuOpen = menuOpen;
	}

	public boolean isMenuOpen() {
		return menuOpen;
	}

	public int getActualStatus() {
		return actualStatus;
	}

	public float getPositionX() {
		return position.x;
	}

	public float getPositionY() {
		return position.y;
	}

	public PVector getPosition() {
		return position;
	}

	public void setPosition(PVector position) {
		this.position = position.get();
	}

	public Building getActualBuilding() {
		return actualBuilding;
	}

	public void setPositionBuilding(PVector positionBuilding) {
		this.positionBuilding = positionBuilding;
	}

	// public boolean isShowLoopingGifDestroy() {
	// return showLoopingGifDestroy;
	// }
	//
	// public void setShowLoopingGifDestroy(boolean showLoopingGifDestroy) {
	// this.showLoopingGifDestroy = showLoopingGifDestroy;
	// }

	// /**
	// * Berechnet Zeichnenposition und setzt
	// Abblidungsmatrix(Transformationsmatix)
	// * @return
	// */
	// private void calcDrawPosition(Player player,PGraphics graphics){
	//
	// //Berechnung des neuen Koordinaten Ursprungs Vektors
	// PVector drawPosition=new
	// PVector(player.getViewPosition().x,player.getViewPosition().y);
	// drawPosition.rotate(player.getGeneralAngle());
	// drawPosition.add(player.getOriginPosition());
	//
	// //Transformationen im Verh�ltnis zum Ursprung (Zoom, Genereller Winkel)
	// graphics.translate(drawPosition.x, +drawPosition.y);
	// graphics.scale(player.getActualZoom());
	// graphics.rotate(player.getGeneralAngle());//n�tig???
	//
	// //zeichne an Position im Verh�ltnis zu gesamt Transformation
	// graphics.translate(position.x, +position.y);
	//
	// // //eigendrehung hinzu
	// // graphics.rotate(this.actualAngle);
	// }

}
