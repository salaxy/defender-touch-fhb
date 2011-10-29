package de.fhb.defenderTouch.menu;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PVector;
import de.fhb.defenderTouch.graphics.GraphicTools;
import de.fhb.defenderTouch.units.grounded.Building;
import de.fhb.defenderTouch.units.grounded.Defence;
import de.fhb.defenderTouch.units.grounded.Ground;
import de.fhb.defenderTouch.units.grounded.Navi;
import de.fhb.defenderTouch.units.grounded.Support;
import de.fhb.defenderTouch.units.movable.BaseUnit;

public class Menu {

	/**
	 * applet where the buildings are placed
	 */
	protected PApplet mainPoint;

	/**
	 * recent position of the click
	 */
	protected PVector position;

	/**
	 * recent position of the building
	 */
	protected PVector positionBuilding;

	/**
	 * menu points, actual 6 points, 3 in use
	 */
	protected PVector menu[] = new PVector[6];

	/**
	 * buildings menu point, 4 points, 2 in use
	 */
	protected PVector menuBuildings[] = new PVector[2];

	/**
	 * Vector with all saved building that palyed has built
	 */
	protected ArrayList<PVector> bBuildings = new ArrayList<PVector>();

	/**
	 * saves if the menu is open
	 */
	protected boolean menuOpen = false;

	/**
	 * saves if the building options is open
	 */
	protected boolean buildingOpen = false;

	/**
	 * Radius of the circle Is always half its diameter
	 */
	protected final int RADIUSCIRCLEMENU = 19;

	/**
	 * Aktivierungsradius eines Menupunkts
	 */
	protected final int DIAMETERCIRCLEMENU = 38;

	/**
	 * Aktivierungsradius eines Menupunkts
	 */
	protected final int DISTANCE = -40;

	/**
	 * Nummer des gebäudes
	 */
	protected int actualStatus = -1;

	/**
	 * Startcredit of every palyer
	 */
	protected int creditsPlayer = 200;

	/**
	 * actual address with the building, used for deleting it
	 */
	protected Building actualBuilding = null;

	/**
	 * array list with all its buildings
	 */
	protected ArrayList<BaseUnit> buildings;

	/**
	 * array list with all its buildings
	 */
	protected String actualBuildingName = "Nichts gewählt";

	/**
	 * array list with all its buildings
	 */
	protected int actualBuildingCount = 0;

	/**
	 * Constructor of Menu
	 */
	public Menu(PApplet mainPoint, ArrayList<BaseUnit> buildings) {
		this.position = new PVector(0, 0);
		this.mainPoint = mainPoint;
		this.buildings = buildings;

		for (int i = 0; i < menu.length; i++) {
			menu[i] = new PVector(-100, -100);
		}
		for (int i = 0; i < menuBuildings.length; i++) {
			menuBuildings[i] = new PVector(-100, -100);
		}
	}

	/**
	 * is always been done
	 */
	public void drawMenu() {
		mainPoint.text("Aktuelle Credits: " + creditsPlayer, 100, 15);
		mainPoint.text("Aktuelles Gebäude: " + actualBuildingName, 300, 15);
		mainPoint.text("Aktuelle Gebäudeanzahl: " + getActualBuildingCount(), 500, 15);
		mainPoint.ellipseMode(PConstants.CENTER);

		if (menuOpen) {
			/**
			 * here is the complete normal menu
			 */
			mainPoint.stroke(255);
			float drehung = 0f;
			float drehungProUntermenue = PApplet.TWO_PI / 6;

			mainPoint.translate(this.position.x, this.position.y);
			mainPoint.rotate(drehung);
			menu[0] = new PVector(0, DISTANCE);
			menu[0].rotate(drehung);
			menu[0].add(position);
			mainPoint.fill(20, 50, 20);
			mainPoint.ellipse(0, DISTANCE, DIAMETERCIRCLEMENU, DIAMETERCIRCLEMENU);
			mainPoint.triangle(-5, DISTANCE + 5, 0, DISTANCE - 5, +5, DISTANCE + 5);
			drehung += drehungProUntermenue;
			mainPoint.ellipse(0, DISTANCE + (DISTANCE / 2), RADIUSCIRCLEMENU, RADIUSCIRCLEMENU);
			mainPoint.fill(255);
			mainPoint.text(Ground.PRICE, 0, DISTANCE - 15);
			mainPoint.resetMatrix();

			mainPoint.translate(this.position.x, this.position.y);
			mainPoint.rotate(drehung);
			menu[1] = new PVector(0, DISTANCE);
			menu[1].rotate(drehung);
			menu[1].add(position);
			mainPoint.fill(20, 20, 50);
			mainPoint.ellipse(0, DISTANCE, DIAMETERCIRCLEMENU, DIAMETERCIRCLEMENU);
			ArrayList<PVector> vektoren1 = new ArrayList<PVector>();
			vektoren1.add(new PVector(-4, DISTANCE - 4));
			vektoren1.add(new PVector(4, DISTANCE - 4));
			vektoren1.add(new PVector(0, DISTANCE - 4));
			vektoren1.add(new PVector(0, DISTANCE - 8));
			vektoren1.add(new PVector(0, DISTANCE + 4));
			vektoren1.add(new PVector(4, DISTANCE + 4));
			vektoren1.add(new PVector(-4, DISTANCE + 4));
			mainPoint.ellipse(0, DISTANCE - 4, 8, 8);
			GraphicTools.zeicheFigurNachVektoren(vektoren1, mainPoint);
			drehung += drehungProUntermenue;
			mainPoint.ellipse(0, DISTANCE + (DISTANCE / 2), RADIUSCIRCLEMENU, RADIUSCIRCLEMENU);
			mainPoint.fill(255);
			mainPoint.text(Defence.PRICE, 0, DISTANCE - 15);
			mainPoint.resetMatrix();

			mainPoint.translate(this.position.x, this.position.y);
			mainPoint.rotate(drehung);
			menu[2] = new PVector(0, DISTANCE);
			menu[2].rotate(drehung);
			menu[2].add(position);
			mainPoint.fill(50, 20, 20);
			mainPoint.ellipse(0, DISTANCE, DIAMETERCIRCLEMENU, DIAMETERCIRCLEMENU);
			ArrayList<PVector> vektoren2 = new ArrayList<PVector>();
			vektoren2.add(new PVector(0, DISTANCE - 8));
			vektoren2.add(new PVector(0, DISTANCE + 8));
			vektoren2.add(new PVector(0, DISTANCE));
			vektoren2.add(new PVector(8, DISTANCE));
			vektoren2.add(new PVector(-8, DISTANCE));
			GraphicTools.zeicheFigurNachVektoren(vektoren2, mainPoint);
			drehung += drehungProUntermenue;
			mainPoint.ellipse(0, DISTANCE + (DISTANCE / 2), RADIUSCIRCLEMENU, RADIUSCIRCLEMENU);
			mainPoint.fill(255);
			mainPoint.text(Support.PRICE, 0, DISTANCE - 15);
			mainPoint.resetMatrix();

			mainPoint.translate(this.position.x, this.position.y);
			mainPoint.rotate(drehung);
			menu[3] = new PVector(0, DISTANCE);
			menu[3].rotate(drehung);
			menu[3].add(position);
			mainPoint.noFill();
			mainPoint.stroke(150);
			mainPoint.ellipse(0, DISTANCE, RADIUSCIRCLEMENU, RADIUSCIRCLEMENU);
			drehung += drehungProUntermenue;
			mainPoint.resetMatrix();

			mainPoint.translate(this.position.x, this.position.y);
			mainPoint.rotate(drehung);
			menu[4] = new PVector(0, DISTANCE);
			menu[4].rotate(drehung);
			menu[4].add(position);
			mainPoint.ellipse(0, DISTANCE, RADIUSCIRCLEMENU, RADIUSCIRCLEMENU);
			drehung += drehungProUntermenue;
			mainPoint.resetMatrix();

			mainPoint.translate(this.position.x, this.position.y);
			mainPoint.rotate(drehung);
			menu[5] = new PVector(0, DISTANCE);
			menu[5].rotate(drehung);
			menu[5].add(position);
			mainPoint.ellipse(0, DISTANCE, RADIUSCIRCLEMENU, RADIUSCIRCLEMENU);
			drehung += drehungProUntermenue;
			mainPoint.resetMatrix();
		}

		/**
		 * here is the complete menu for a specific building
		 */

		if (buildingOpen) {
			mainPoint.noFill();
			mainPoint.stroke(255);
			float drehung = 0f;
			float drehungProUntermenue = PApplet.TWO_PI / 4;

			mainPoint.translate(this.positionBuilding.x, this.positionBuilding.y);
			mainPoint.rotate(drehung);
			menuBuildings[0] = new PVector(0, DISTANCE);
			menuBuildings[0].rotate(drehung);
			menuBuildings[0].add(position);
			mainPoint.fill(20, 50, 20);
			mainPoint.ellipse(0, DISTANCE, DIAMETERCIRCLEMENU, DIAMETERCIRCLEMENU);
			mainPoint.fill(255);
			mainPoint.triangle(-5, DISTANCE, 0, DISTANCE - 10, +5, DISTANCE);
			mainPoint.fill(20, 50, 20);
			ArrayList<PVector> vektoren1 = new ArrayList<PVector>();
			vektoren1.add(new PVector(0, DISTANCE));
			vektoren1.add(new PVector(0, DISTANCE + 8));
			GraphicTools.zeicheFigurNachVektoren(vektoren1, mainPoint);
			drehung += drehungProUntermenue;
			mainPoint.ellipse(0, DISTANCE + (DISTANCE / 2), RADIUSCIRCLEMENU, RADIUSCIRCLEMENU);
			mainPoint.fill(255);
			mainPoint.text(getActualBuildingPrice(position), 0, DISTANCE - 15);
			mainPoint.resetMatrix();

			mainPoint.translate(this.positionBuilding.x, this.positionBuilding.y);
			mainPoint.rotate(drehung);
			menuBuildings[1] = new PVector(0, DISTANCE);
			menuBuildings[1].rotate(drehung);
			menuBuildings[1].add(position);
			mainPoint.fill(50, 20, 20);
			mainPoint.ellipse(0, DISTANCE, DIAMETERCIRCLEMENU, DIAMETERCIRCLEMENU);
			ArrayList<PVector> vektoren2 = new ArrayList<PVector>();
			vektoren2.add(new PVector(5, DISTANCE - 5));
			vektoren2.add(new PVector(-5, DISTANCE + 5));
			vektoren2.add(new PVector(0, DISTANCE));
			vektoren2.add(new PVector(5, DISTANCE + 5));
			vektoren2.add(new PVector(-5, DISTANCE - 5));
			GraphicTools.zeicheFigurNachVektoren(vektoren2, mainPoint);
			drehung += drehungProUntermenue;
			mainPoint.resetMatrix();

			mainPoint.translate(this.positionBuilding.x, this.positionBuilding.y);
			mainPoint.rotate(drehung);
			mainPoint.fill(150);
			mainPoint.ellipse(0, DISTANCE, RADIUSCIRCLEMENU, RADIUSCIRCLEMENU);
			// TODO ist unschön! aber mir fällt grad nix bessres ein, 3 steht
			// sonst falsch rum...
			mainPoint.rotate(drehung);
			mainPoint.fill(255);
			mainPoint.text(getActualBuildingLevel(position), 0, 45);
			mainPoint.resetMatrix();
		}
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
				creditsPlayer -= Ground.PRICE;
				setActualStatus(0);
				setMenuOpen(false);
				return true;
			}
		}
		if (this.menu[1].dist(clickVector) < this.RADIUSCIRCLEMENU) {
			// System.out.println("Menue 2");
			if (isEnoughCredits(Defence.PRICE)) {
				creditsPlayer -= Defence.PRICE;
				setActualStatus(1);
				setMenuOpen(false);
				return true;
			}
		}
		if (this.menu[2].dist(clickVector) < this.RADIUSCIRCLEMENU) {
			// System.out.println("Menue 3");
			if (isEnoughCredits(Support.PRICE)) {
				creditsPlayer -= Support.PRICE;
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
		if (creditsPlayer - credits >= 0) {
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
		if (creditsPlayer - getActualBuildingPrice(positionBuilding) >= 0 && actualBuilding.getLevel() != 3) {
			creditsPlayer -= getActualBuildingPrice(positionBuilding);
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

		if (clickVector != null) {
			for (BaseUnit bu : buildings) {
				if (bu.isInner(clickVector)) {
					if (bu instanceof Building) {
						actualBuilding = (Building) bu;
					}
				}
			}
		}

		this.buildingOpen = buildingOpen;
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
			actualBuildingName = "Nichts gewählt";
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
		if (position == null) {
			for (BaseUnit bu : buildings) {
				if (bu.isInner(position)) {
					if (bu instanceof Building) {
						if (bu instanceof Defence) {
							creditsPlayer += (Defence.PRICE * bu.getLevel()) / 2;
						}
						if (bu instanceof Ground) {
							creditsPlayer += (Ground.PRICE * bu.getLevel()) / 2;
						}
						if (bu instanceof Support) {
							creditsPlayer += (Support.PRICE * bu.getLevel()) / 2;
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
		for (BaseUnit building : buildings) {
			// if a building is clicked
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
		this.position = position;
	}

	public Building getActualBuilding() {
		return actualBuilding;
	}

	public void setPositionBuilding(PVector positionBuilding) {
		this.positionBuilding = positionBuilding;
	}

}
