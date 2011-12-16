package de.fhb.defenderTouch.menu;

import java.util.concurrent.CopyOnWriteArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Gamemap;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.graphics.Animations;
import de.fhb.defenderTouch.graphics.GraphicTools;
import de.fhb.defenderTouch.units.notmovable.Armory;
import de.fhb.defenderTouch.units.notmovable.Barracks;
import de.fhb.defenderTouch.units.notmovable.Defence;
import de.fhb.defenderTouch.units.notmovable.Support;
import de.fhb.defenderTouch.units.root.Unit;
import de.fhb.defenderTouch.units.root.Building;

public class Menu {

	// http://www.cppjj.com/slicktutorial/slickcollision.htm

	/**
	 * recent position of the click
	 */
	protected Vector2f position;

	/**
	 * recent position of the actual chosen building
	 */
	protected Vector2f positionBuilding;

	/**
	 * menu points, actual 6 points, 3 in use - Ground - Defence - Support -
	 * Tank?
	 */
	protected Vector2f menu[] = new Vector2f[6];

	/**
	 * buildings menu point, 4 points, 2 in use - Upgrade - Destroy
	 */
	protected Vector2f menuBuildings[] = new Vector2f[2];

	/**
	 * saves if the menu is open
	 */
	protected boolean menuOpen = false;

	/**
	 * saves if the building options is open
	 */
	protected boolean buildingOpen = false;

	/**
	 * activation raduis of a menupoint - just configure here! <----
	 */
	protected final int DOUBLERADIUS = 120;

	/**
	 * Radius of the circle Is always half its diameter
	 */
	protected final int RADIUS;

	/**
	 * addation for the special text
	 */
	protected final int TEXTDISTANCE;

	/**
	 * size of the menu text
	 */
	protected final int TEXTSIZE;

	/**
	 * activation distanceance of a menu point
	 */
	protected final int DISTANCE;

	/**
	 * size of Defence sign
	 */
	protected final int SIZEOFDEFENCE;

	/**
	 * size of Armory sign
	 */
	protected final int SIZEOFARMORY;

	/**
	 * size of Support sign
	 */
	protected final int SIZEOFSUPPORT;

	/**
	 * size of Defence sign
	 */
	protected final int SIZEOFBARRACKS;

	/**
	 * size of Destroy sign
	 */
	protected final int SIZEOFDESTROY;

	/**
	 * size of Upgrade sign
	 */
	protected final int SIZEOFUPGRADE;

	/**
	 * size of the text, like costs of buildings
	 */
	protected final int SIZEOFTEXTALIGNMENT;

	/**
	 * size of the images
	 */
	protected final int SIZEOFIMAGE;

	/**
	 * size the animations must be dragged
	 */
	protected final int ANIMATIONS;

	/**
	 * number of building
	 */
	protected int actualChosenBuilding = -1;

	/**
	 * actual address with the building, used for deleting it
	 */
	protected Building actualBuilding = null;

	/**
	 * array list with all its buildings
	 */
	protected CopyOnWriteArrayList<Unit> buildings;

	/**
	 * array list with all its buildings
	 */
	protected String actualBuildingName = "Nichts gewählt";

	/**
	 * Player ONE
	 */
	protected int actualBuildingCountPlayerZERO = 0;

	/**
	 * Player TWO
	 */
	protected int actualBuildingCountPlayerONE = 0;

	/**
	 * Besitzer dieses Menue objekts
	 */
	private Player owner;

	/**
	 * ANIMATION
	 */
	private Animation smallExplosion;

	/**
	 * ANIMATION playing?
	 */
	private boolean smallExplosionPlaying = false;

	/**
	 * GIFLOADER
	 */
	private Animations smallExplosionLoader;

	private InitializeAnimations iniAnimations;

	/**
	 * Constructor of Menu
	 * 
	 */
	public Menu(CopyOnWriteArrayList<Unit> buildings, Player owner) {
		this.position = new Vector2f(0, 0);
		this.buildings = buildings;
		this.owner = owner;
		RADIUS = DOUBLERADIUS / 2;
		TEXTSIZE = DOUBLERADIUS / 4;
		DISTANCE = -DOUBLERADIUS + 5;
		TEXTDISTANCE = DOUBLERADIUS / 11;
		SIZEOFTEXTALIGNMENT = DOUBLERADIUS + TEXTDISTANCE * 6;
		SIZEOFARMORY = TEXTDISTANCE * 3;
		SIZEOFDEFENCE = TEXTDISTANCE * 2;
		SIZEOFSUPPORT = TEXTDISTANCE * 3;
		SIZEOFDESTROY = TEXTDISTANCE * 2;
		SIZEOFUPGRADE = TEXTDISTANCE * 3;
		SIZEOFBARRACKS = TEXTDISTANCE * 3;
		ANIMATIONS = DOUBLERADIUS / 8;
		SIZEOFIMAGE = 80;

		for (int i = 0; i < menu.length; i++) {
			menu[i] = new Vector2f(0, 0);
		}
		for (int i = 0; i < menuBuildings.length; i++) {
			menuBuildings[i] = new Vector2f(0, 0);
		}
		smallExplosionLoader = new Animations("small explosion", 17);
		smallExplosion = smallExplosionLoader.getAni();

		iniAnimations = new InitializeAnimations();
	}

	public int getOwnerID() {
		return owner.getId();
	}

	/**
	 * Playing the animation for the explosion when a building is destroyed
	 * 
	 * @param graphics
	 */
	public void animationSmallExplosion(Graphics graphics) {
		calcDrawTransformation(graphics);
		smallExplosion.draw((-smallExplosion.getHeight() / 2) * owner.getActualZoom(), (-smallExplosion.getWidth() / 2) * owner.getActualZoom(),
				smallExplosion.getHeight() * owner.getActualZoom(), smallExplosion.getWidth() * owner.getActualZoom());
		graphics.resetTransform();
		if (smallExplosion.getFrame() == smallExplosionLoader.getNumberPictures() - 1) {
			smallExplosionPlaying = false;
			smallExplosion.stop();
		}
	}

	/**
	 * Calculating the actual position of the click with coordinates
	 * 
	 * @param graphics
	 */
	public void calcDrawTransformation(Graphics graphics) {
		GraphicTools.calcDrawTransformationForSlick(owner, graphics, position);
	}

	/**
	 * Calculating the actual position of the building with coordinates
	 * 
	 * @param graphics
	 */
	public void calcDrawTransformationBuildings(Graphics graphics) {
		GraphicTools.calcDrawTransformationForSlick(owner, graphics, positionBuilding);
	}

	/**
	 * Creates a big circle
	 * 
	 * @param graphics
	 */
	public void createBigMenuCircle(Graphics graphics, int difference) {
		graphics.setColor(Color.darkGray);
		graphics.drawOval(difference, difference, DOUBLERADIUS, DOUBLERADIUS);
	}

	/**
	 * Creates a complete Menu Point
	 * 
	 * @param defenderControl
	 */
	public void createBuilding(DefenderControl defenderControl) {
		switch (getActualChosenBuilding()) {
		case 0:
			System.out.println("building a Armory building");
			new Armory((int) getPositionX(), (int) getPositionY(), Unit.MODE_NORMAL, this.owner, defenderControl);
			break;
		case 1:
			System.out.println("building a Defence building");
			new Defence((int) getPositionX(), (int) getPositionY(), Unit.MODE_NORMAL, this.owner, defenderControl);
			break;
		case 2:
			System.out.println("building a Support building");
			new Support((int) getPositionX(), (int) getPositionY(), Unit.MODE_NORMAL, this.owner, defenderControl);
			break;
		case 3:
			System.out.println("building a Barracks building");
			new Barracks((int) getPositionX(), (int) getPositionY(), Unit.MODE_NORMAL, this.owner, defenderControl);
			break;
		default:
			System.out.println();
		}
	}

	/**
	 * Creates a complete Menu Point
	 * 
	 * @param element
	 * @param rotation
	 * @param graphics
	 * @param string
	 */
	public void createClickablePointMenu(int element, int rotation, Graphics graphics, String pathName, int price) {
		graphics.rotate(0, 0, rotation - 45);
		menu[element] = new Vector2f(0, DOUBLERADIUS);
		menu[element].setTheta(rotation);
		menu[element].add(position);
		// graphics.drawOval(DOUBLERADIUS / 2, DOUBLERADIUS / 2, 1, 1);
		int difference = 25;
		if (element >= 0 && element <= 3) {
			createBigMenuCircle(graphics, difference);
			showBuilding(graphics, pathName, rotation, difference);
			showPriceBuildings(graphics, price, difference);
		}
		graphics.resetTransform();
	}

	/**
	 * Creates a complete Menu Point for a specific building
	 * 
	 * @param element
	 * @param rotation
	 * @param graphics
	 */
	public void createClickablePointBuilding(int element, int rotation, Graphics graphics, String pathName, int price) {
		graphics.rotate(0, 0, rotation - 135);
		menuBuildings[element] = new Vector2f(0, DOUBLERADIUS);
		menuBuildings[element].setTheta(rotation - 90);
		menuBuildings[element].add(position);
		int difference = 25;
		if (element >= 0 && element <= 1) {
			createBigMenuCircle(graphics, difference);
			showBuilding(graphics, pathName, rotation, difference);
			if (element == 0) {
				showPriceBuildings(graphics, price, difference);
			}
		}
		graphics.resetTransform();
	}

	/**
	 * doing the draw
	 */
	public void drawMenu(Graphics graphics, Player player) {
		/**
		 * needed for saying the animation which grphics should be taken
		 */
		smallExplosionLoader.setGraphics(graphics);

		/**
		 * here is the explosion when a building is destroyed
		 */
		if (smallExplosionPlaying)
			animationSmallExplosion(smallExplosionLoader.getGraphics());
		iniAnimations.showTree2(graphics, owner);
		iniAnimations.showTree1(graphics, owner);
		

		/**
		 * here is the complete normal menu Ground Defence Support
		 * 
		 */
		if (menuOpen) {
			int rotation = -90;
			int counter = 0;
			int nextRotation = 360 / 6;

			calcDrawTransformation(graphics);
			createClickablePointMenu(counter++, rotation, graphics, "data/buildings/Armory.png", Armory.PRICE);
			rotation += nextRotation;

			calcDrawTransformation(graphics);
			createClickablePointMenu(counter++, rotation, graphics, "data/buildings/Defence.png", Defence.PRICE);
			rotation += nextRotation;

			calcDrawTransformation(graphics);
			createClickablePointMenu(counter++, rotation, graphics, "data/buildings/Support.png", Support.PRICE);
			rotation += nextRotation;

			calcDrawTransformation(graphics);
			createClickablePointMenu(counter++, rotation, graphics, "data/buildings/Barracks.png", Barracks.PRICE);
			rotation += nextRotation;
		}

		/**
		 * here is the complete menu for a specific building Upgrade Destroy
		 * Actual Level
		 */
		if (buildingOpen && player.getId() == getActualBuildingPlayerID()) {
			int rotation = 0;
			int counter = 0;
			int nextRotation = 360 / 4;

			calcDrawTransformationBuildings(graphics);
			createClickablePointBuilding(counter++, rotation, graphics, "data/signs/Upgrade.png", getActualBuildingPrice());
			rotation += nextRotation;

			calcDrawTransformationBuildings(graphics);
			createClickablePointBuilding(counter++, rotation, graphics, "data/signs/Destroy.png", 0);
			rotation += nextRotation;

			calcDrawTransformationBuildings(graphics);
			graphics.rotate(0, 0, rotation);
			graphics.fillOval(-RADIUS / 2, DISTANCE - RADIUS / 2, RADIUS, RADIUS);
			graphics.rotate(0, 0, rotation);
			graphics.setColor(Color.white);
			graphics.drawString(getActualBuildingLevel(positionBuilding) + "", 0, Math.abs(DISTANCE) - TEXTDISTANCE);
			graphics.resetTransform();
		} else
			buildingOpen = false;
	}

	/**
	 * 
	 * @return if building is shows
	 */
	public boolean isBuildingOpen() {
		return buildingOpen;
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
		if (owner.getCredits() - credits >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * Watching if the click was in the Circle
	 * 
	 * @param clickVector
	 *            (actual position of mouse click)
	 * @return true (if click was in middle circle of menu)
	 */
	public boolean isInnerMainElement(Vector2f clickVector) {

		if (this.position.distance(clickVector) < this.RADIUS) {
			System.out.println("Menu close choosed");
			setMenuOpen(false);
			return true;
		}
		return false;

	}

	/**
	 * Watching if a building Menu point was clicked
	 * 
	 * @param clickVector
	 *            (actual position of mouse click)
	 * @return true (if click was in a specific circle of menu)
	 * 
	 *         looking if a specific building menu point was clicked
	 */
	public boolean isBuildingElementClicked(Vector2f clickVector) {

		if (this.menuBuildings[0].distance(clickVector) < this.RADIUS) {
			System.out.println("Menue Building 1 - Upgrade");
			setBuildingOpen(false, null);
			upgradeBuilding();
			setActualChosenBuilding(0);
			return true;
		}
		if (this.menuBuildings[1].distance(clickVector) < this.RADIUS) {
			System.out.println("Menue Building 2 - Destroy");
			setBuildingOpen(false, null);
			setBuildingDestroyPrice();
			setActualChosenBuilding(1);
			return true;
		}
		return false;
	}

	/**
	 * Watching if a Menu point was clicked
	 * 
	 * @param clickVector
	 *            (actual position of mouse click)
	 * @return true (if click was in a menu option circle of menu)
	 * 
	 *         looking if a menu point was clicked
	 */
	public boolean isMenuElementClicked(Vector2f clickVector) {
		if (this.menu[0].distance(clickVector) < this.RADIUS) {
			System.out.println("Menue 1");
			if (isEnoughCredits(Armory.PRICE)) {
				owner.setCredits(owner.getCredits() - Armory.PRICE);
				setActualChosenBuilding(0);
				setMenuOpen(false);
				return true;
			}
		}
		if (this.menu[1].distance(clickVector) < this.RADIUS) {
			System.out.println("Menue 2");
			if (isEnoughCredits(Defence.PRICE)) {
				owner.setCredits(owner.getCredits() - Defence.PRICE);
				setActualChosenBuilding(1);
				setMenuOpen(false);
				return true;
			}
		}
		if (this.menu[2].distance(clickVector) < this.RADIUS) {
			System.out.println("Menue 3");
			if (isEnoughCredits(Support.PRICE)) {
				owner.setCredits(owner.getCredits() - Support.PRICE);
				setActualChosenBuilding(2);
				setMenuOpen(false);
				return true;
			}
		}
		if (this.menu[3].distance(clickVector) < this.RADIUS) {
			System.out.println("Menue 4");
			if (isEnoughCredits(Barracks.PRICE)) {
				owner.setCredits(owner.getCredits() - Barracks.PRICE);
				setActualChosenBuilding(3);
				setMenuOpen(false);
				return true;
			}
		}
		if (this.menu[4].distance(clickVector) < this.RADIUS) {
			System.out.println("Menue 5");
		}
		if (this.menu[5].distance(clickVector) < this.RADIUS) {
			System.out.println("Menue 6");
		}
		return false;
	}

	/**
	 * 
	 * @return tell you wheater the menu is open or not
	 */
	public boolean isMenuOpen() {
		return menuOpen;
	}

	/**
	 * Watching if the Place is already take by another building
	 * 
	 * @param clickVector
	 * @return if place for the new building is free
	 */
	public boolean isPlaceTaken(Vector2f clickVector) {
		for (Unit building : buildings) {
			if (building.getPosition().distance(clickVector) < (building.getCollisionRadius())) {
				setPositionBuilding(building.getPosition());
				return true;
			}
		}
		return false;
	}

	/**
	 * looking if there are enough credits
	 */
	public void upgradeBuilding() {
		if (owner.getCredits() - getActualBuildingPrice() >= 0 && actualBuilding.getLevel() != 3) {
			owner.setCredits(owner.getCredits() - getActualBuildingPrice());
		}
	}

	/**
	 * searching for the actual name of a building
	 */
	public void setActualBuildingName() {
		if (actualBuilding instanceof Defence)
			actualBuildingName = "Defence";

		if (actualBuilding instanceof Armory)
			actualBuildingName = "Armory";

		if (actualBuilding instanceof Support)
			actualBuildingName = "Support";

		if (actualBuilding instanceof Barracks)
			actualBuildingName = "Barracks";
	}

	/**
	 * searching for the actual name of a building
	 */
	public void resetActualBuildingName() {
		actualBuildingName = "Nichts gewählt";
	}

	/**
	 * Setting the actual building
	 * 
	 * @param actualChosenBuilding
	 */
	public void setActualChosenBuilding(int actualChosenBuilding) {
		this.actualChosenBuilding = actualChosenBuilding;
	}

	/**
	 * calculate the credits u get from destroying a building
	 */
	public void setBuildingDestroyPrice() {
		smallExplosionPlaying = true;
		smallExplosion.restart();
		if (actualBuilding instanceof Defence)
			owner.setCredits(owner.getCredits() + (Defence.PRICE * actualBuilding.getLevel()) / 2);
		if (actualBuilding instanceof Armory)
			owner.setCredits(owner.getCredits() + (Armory.PRICE * actualBuilding.getLevel()) / 2);
		if (actualBuilding instanceof Support)
			owner.setCredits(owner.getCredits() + (Support.PRICE * actualBuilding.getLevel()) / 2);
		if (actualBuilding instanceof Barracks) {
			owner.setCredits(owner.getCredits() + (Barracks.PRICE * actualBuilding.getLevel()) / 2);
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
	public void setBuildingOpen(boolean buildingOpen, Vector2f clickVector) {

		if (!buildingOpen) {
			this.buildingOpen = false;
		} else {
			if (clickVector != null) {
				for (Unit bu : buildings) {
					if (bu.isInner(clickVector)) {
						if (bu instanceof Building) {
							actualBuilding = (Building) bu;
							this.buildingOpen = buildingOpen;
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param menuOpen
	 */
	public void setMenuOpen(boolean menuOpen) {
		this.menuOpen = menuOpen;
	}

	/**
	 * 
	 * @param position
	 */
	public void setPosition(Vector2f position) {
		this.position = position;
	}

	/**
	 * 
	 * @param positionBuilding
	 */
	public void setPositionBuilding(Vector2f positionBuilding) {
		this.positionBuilding = positionBuilding;
	}

	/**
	 * Showing the sign of a building
	 * 
	 * @param graphics
	 * @param difference
	 */
	public void showBuilding(Graphics graphics, String pathName, int rotation, int difference) {
		Image image = null;
		try {
			image = new Image(pathName);
			image = image.getScaledCopy(SIZEOFIMAGE, SIZEOFIMAGE);
			image.rotate(-45);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		graphics.drawImage(image, difference + SIZEOFIMAGE / 4, difference + SIZEOFIMAGE / 4, SIZEOFIMAGE, SIZEOFIMAGE, 0f, 0f);
	}

	/**
	 * Showing the price of the specific building
	 * 
	 * @param graphics
	 */
	public void showPriceBuildings(Graphics graphics, int price, int x) {
		graphics.setColor(Color.black);
		graphics.fillOval(x * 4, x * 4, RADIUS, RADIUS);
		graphics.setColor(Color.gray);
		graphics.drawOval(x * 4, x * 4, RADIUS, RADIUS);
		graphics.setColor(Color.white);
		graphics.translate(x * 5.4f, x * 5.4f);
		graphics.rotate(0, 0, 132);
		graphics.drawString(price + "", -7, -7);

	}

	/**
	 * 
	 * @return
	 */
	public Building getActualBuilding() {
		return actualBuilding;
	}

	/**
	 * 
	 * @return Number of buildings on map
	 */
	public int getActualBuildingCount(int playerNumber) {
		actualBuildingCountPlayerZERO = 0;
		actualBuildingCountPlayerONE = 0;

		for (Unit building : buildings) {
			if (building.getPlayerID() == 0)
				if (building instanceof Building)
					actualBuildingCountPlayerZERO++;
			if (building.getPlayerID() == 1)
				if (building instanceof Building)
					actualBuildingCountPlayerONE++;
		}

		if (playerNumber == 0)
			return actualBuildingCountPlayerZERO;
		if (playerNumber == 1)
			return actualBuildingCountPlayerONE;
		else
			return 0;
	}

	/**
	 * 
	 * @param clickVector
	 * @return actual level of the building
	 */
	public int getActualBuildingLevel(Vector2f clickVector) {
		return actualBuilding.getLevel();
	}

	public String getActualBuildingName() {
		return actualBuildingName;
	}

	public int getActualBuildingPlayerID() {
		return actualBuilding.getPlayerID();
	}

	/**
	 * 
	 * @return PRICE (price of the specific building)
	 * 
	 *         searching for the actual price of a building
	 */
	public int getActualBuildingPrice() {
		if (actualBuilding instanceof Armory)
			return Armory.PRICE;

		if (actualBuilding instanceof Defence)
			return Defence.PRICE;

		if (actualBuilding instanceof Support)
			return Support.PRICE;

		if (actualBuilding instanceof Barracks) {
			return Barracks.PRICE;
		} else
			return 0;
	}

	public int getActualChosenBuilding() {
		return actualChosenBuilding;
	}

	public float getPositionX() {
		return position.x;
	}

	public float getPositionY() {
		return position.y;
	}

}
