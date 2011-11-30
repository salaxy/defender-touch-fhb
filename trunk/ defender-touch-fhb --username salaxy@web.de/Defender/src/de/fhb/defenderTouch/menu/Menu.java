package de.fhb.defenderTouch.menu;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.graphics.Animations;
import de.fhb.defenderTouch.graphics.GraphicTools;
import de.fhb.defenderTouch.units.movable.Tank;
import de.fhb.defenderTouch.units.notmovable.Armory;
import de.fhb.defenderTouch.units.notmovable.Barracks;
import de.fhb.defenderTouch.units.notmovable.Defence;
import de.fhb.defenderTouch.units.notmovable.Support;
import de.fhb.defenderTouch.units.root.BaseUnit;
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
	protected final int DIAMETER = 120;

	/**
	 * Radius of the circle Is always half its diameter
	 */
	protected final int RADIUS;

	/**
	 * addation for the special text
	 */
	protected final int TEXTdistanceANCE;

	/**
	 * size of the menu text
	 */
	protected final int TEXTSIZE;

	/**
	 * activation distanceance of a menu point
	 */
	protected final int distanceANCE;

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
	protected CopyOnWriteArrayList<BaseUnit> buildings;

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
	private Player player;

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
	private Animations gl;

	/**
	 * Constructor of Menu
	 * 
	 */
	public Menu(CopyOnWriteArrayList<BaseUnit> buildings, Player player) {
		this.position = new Vector2f(0, 0);
		this.buildings = buildings;
		this.player = player;
		RADIUS = DIAMETER / 2;
		TEXTSIZE = DIAMETER / 4;
		distanceANCE = -(DIAMETER + 5);
		TEXTdistanceANCE = DIAMETER / 11;
		SIZEOFTEXTALIGNMENT = DIAMETER + TEXTSIZE * 2 + TEXTdistanceANCE * 2;
		SIZEOFARMORY = TEXTdistanceANCE * 3;
		SIZEOFDEFENCE = TEXTdistanceANCE * 2;
		SIZEOFSUPPORT = TEXTdistanceANCE * 3;
		SIZEOFDESTROY = TEXTdistanceANCE * 2;
		SIZEOFUPGRADE = TEXTdistanceANCE * 3;
		SIZEOFBARRACKS = TEXTdistanceANCE * 3;
		ANIMATIONS = DIAMETER / 8;

		for (int i = 0; i < menu.length; i++) {
			menu[i] = new Vector2f(-100, -100);
		}
		for (int i = 0; i < menuBuildings.length; i++) {
			menuBuildings[i] = new Vector2f(-100, -100);
		}
		gl = new Animations("small explosion", 17);
		smallExplosion = gl.getAni();

	}

	public void createBuilding(DefenderControl defenderControl) {
		switch (getActualChosenBuilding()) {
		case 0:
			System.out.println("building a Armory building");
			new Armory((int) getPositionX(), (int) getPositionY(), BaseUnit.MODE_NORMAL, this.player, defenderControl);
			break;
		case 1:
			System.out.println("building a Defence building");
			new Defence((int) getPositionX(), (int) getPositionY(), BaseUnit.MODE_NORMAL, this.player, defenderControl);
			break;
		case 2:
			System.out.println("building a Support building");
			new Support((int) getPositionX(), (int) getPositionY(), BaseUnit.MODE_NORMAL, this.player, defenderControl);
			break;
		case 3:
			System.out.println("building a Barracks building");
			new Barracks((int) getPositionX(), (int) getPositionY(), BaseUnit.MODE_NORMAL, this.player, defenderControl);
			break;
		default:
			System.out.println();
		}
	}

	/**
	 * is always been done
	 */
	public void drawMenu(Graphics graphics, Player player) {
		gl.setGraphics(graphics);

		/**
		 * here is the explosion when a building is destroyed
		 */
		if (smallExplosionPlaying)
			animationSmallExplosion(gl.getGraphics());

		/**
		 * here is the complete normal menu Ground Defence Support
		 * 
		 */
		if (menuOpen) {
			int rotation = 0;
			int counter = 0;
			int drehungProUntermenue = 360 / 6;

			calcDrawTransformation(graphics);
			rotateAndCreateMenu(counter++, rotation, graphics);
			graphics.setColor(Color.cyan);
			createBigMenuCircle(graphics);
			showBuildingArmory(graphics);
			createTinyMenuCircle(graphics);
			showPriceBuildings(graphics, Armory.PRICE);
			rotation += drehungProUntermenue;
			graphics.resetTransform();

			calcDrawTransformation(graphics);
			rotateAndCreateMenu(counter++, rotation, graphics);
			graphics.setColor(Color.lightGray);
			createBigMenuCircle(graphics);
			showBuildingDefence(graphics);
			createTinyMenuCircle(graphics);
			showPriceBuildings(graphics, Defence.PRICE);
			rotation += drehungProUntermenue;
			graphics.resetTransform();

			calcDrawTransformation(graphics);
			rotateAndCreateMenu(counter++, rotation, graphics);
			graphics.setColor(Color.magenta);
			createBigMenuCircle(graphics);
			showBuildingSupport(graphics);
			createTinyMenuCircle(graphics);
			showPriceBuildings(graphics, Support.PRICE);
			rotation += drehungProUntermenue;
			graphics.resetTransform();

			calcDrawTransformation(graphics);
			rotateAndCreateMenu(counter++, rotation, graphics);
			graphics.setColor(Color.orange);
			createBigMenuCircle(graphics);
			showBuildingBarracks(graphics);
			createTinyMenuCircle(graphics);
			showPriceBuildings(graphics, Tank.PRICE);
			rotation += drehungProUntermenue;
			graphics.resetTransform();

			calcDrawTransformation(graphics);
			rotateAndCreateMenu(counter++, rotation, graphics);
			createSmallMenuCircle(graphics);
			rotation += drehungProUntermenue;
			graphics.resetTransform();

			calcDrawTransformation(graphics);
			rotateAndCreateMenu(counter++, rotation, graphics);
			createSmallMenuCircle(graphics);
			rotation += drehungProUntermenue;
			graphics.resetTransform();
		}

		/**
		 * here is the complete menu for a specific building Upgrade Destroy
		 * Actual Level
		 */
		if (buildingOpen) {
			int rotation = 0;
			int counter = 0;
			int drehungProUntermenue = 360 / 4;

			calcDrawTransformationBuildings(graphics);
			rotateAndCreateMenuBuilding(counter++, rotation, graphics);
			graphics.setColor(Color.green);
			createBigMenuCircle(graphics);
			createTinyMenuCircle(graphics);
			showSignUpgrade(graphics);
			showPriceBuildings(graphics, getActualBuildingPrice());
			rotation += drehungProUntermenue;
			graphics.resetTransform();

			calcDrawTransformationBuildings(graphics);
			rotateAndCreateMenuBuilding(counter++, rotation, graphics);
			graphics.setColor(Color.red);
			createBigMenuCircle(graphics);
			showSignDestroy(graphics);
			rotation += drehungProUntermenue;
			graphics.resetTransform();

			calcDrawTransformationBuildings(graphics);
			graphics.rotate(0, 0, rotation);
			createSmallMenuCircle(graphics);
			graphics.rotate(0, 0, rotation);
			graphics.setColor(Color.white);
			graphics.drawString(getActualBuildingLevel(positionBuilding) + "", 0, Math.abs(distanceANCE) - TEXTdistanceANCE);
			graphics.resetTransform();
		}
	}

	/**
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
	 * 
	 * @param clickVector
	 *            (actual position of mouse click)
	 * @return true (if click was in a menu option circle of menu)
	 * 
	 *         looking if a menu point was clicked
	 */
	public boolean isInnerMenuElement(Vector2f clickVector) {
		if (this.menu[0].distance(clickVector) < this.RADIUS) {
			System.out.println("Menue 1");
			if (isEnoughCredits(Armory.PRICE)) {
				player.setCredits(player.getCredits() - Armory.PRICE);
				setActualChosenBuilding(0);
				setMenuOpen(false);
				return true;
			}
		}
		if (this.menu[1].distance(clickVector) < this.RADIUS) {
			System.out.println("Menue 2");
			if (isEnoughCredits(Defence.PRICE)) {
				player.setCredits(player.getCredits() - Defence.PRICE);
				setActualChosenBuilding(1);
				setMenuOpen(false);
				return true;
			}
		}
		if (this.menu[2].distance(clickVector) < this.RADIUS) {
			System.out.println("Menue 3");
			if (isEnoughCredits(Support.PRICE)) {
				player.setCredits(player.getCredits() - Support.PRICE);
				setActualChosenBuilding(2);
				setMenuOpen(false);
				return true;
			}
		}
		if (this.menu[3].distance(clickVector) < this.RADIUS) {
			System.out.println("Menue 4");
			if (isEnoughCredits(Tank.PRICE)) {
				player.setCredits(player.getCredits() - Tank.PRICE);
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
	 * @param clickVector
	 *            (actual position of mouse click)
	 * @return true (if click was in a specific circle of menu)
	 * 
	 *         looking if a specific building menu point was clicked
	 */
	public boolean isInnerBuildingElement(Vector2f clickVector) {

		if (this.menuBuildings[0].distance(clickVector) < this.RADIUS) {
			setBuildingOpen(false, null);
			upgradeBuilding();
			setActualChosenBuilding(0);
			return true;
		}
		if (this.menuBuildings[1].distance(clickVector) < this.RADIUS) {

			setBuildingOpen(false, null);
			setBuildingDestroyPrice();
			setActualChosenBuilding(1);
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
		if (player.getCredits() - getActualBuildingPrice() >= 0 && actualBuilding.getLevel() != 3) {
			player.setCredits(player.getCredits() - getActualBuildingPrice());
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
				for (BaseUnit bu : buildings) {
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
	 * @param clickVector
	 *            (actual position of mouse click)
	 * @return level (level of the specific building)
	 * 
	 *         searching for the actual building level
	 */
	public int getActualBuildingLevel(Vector2f clickVector) {
		return actualBuilding.getLevel();
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

	/**
	 * searching for the actual name of a building
	 */
	public void setActualBuildingName() {

		if (actualBuilding instanceof Defence)
			actualBuildingName = "Defence";

		if (actualBuilding instanceof Armory)
			actualBuildingName = "Ground";

		if (actualBuilding instanceof Support)
			actualBuildingName = "Support";

		if (actualBuilding instanceof Barracks) {
			actualBuildingName = "Barracks";
		}

		else
			actualBuildingName = "Nichts gewählt";

	}

	/**
	 * calculate the credits u get from destroying a building
	 */
	public void setBuildingDestroyPrice() {
		smallExplosionPlaying = true;
		smallExplosion.restart();
		if (actualBuilding instanceof Defence)
			player.setCredits(player.getCredits() + (Defence.PRICE * actualBuilding.getLevel()) / 2);

		if (actualBuilding instanceof Armory)
			player.setCredits(player.getCredits() + (Armory.PRICE * actualBuilding.getLevel()) / 2);

		if (actualBuilding instanceof Support)
			player.setCredits(player.getCredits() + (Support.PRICE * actualBuilding.getLevel()) / 2);

		if (actualBuilding instanceof Barracks) {
			player.setCredits(player.getCredits() + (Barracks.PRICE * actualBuilding.getLevel()) / 2);
		}

	}

	/**
	 * 
	 * @param clickVector
	 * @return if place for the new building is free
	 */
	public boolean isPlaceTaken(Vector2f clickVector) {
		for (BaseUnit building : buildings) {
			if (building.getPosition().distance(clickVector) < (building.getCollisionRadius())) {
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
	public int getActualBuildingCount(int playerNumber) {
		actualBuildingCountPlayerZERO = 0;
		actualBuildingCountPlayerONE = 0;

		for (BaseUnit building : buildings) {
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

	public String getActualBuildingName() {
		return actualBuildingName;
	}

	public boolean isBuildingOpen() {
		return buildingOpen;
	}

	public void setActualChosenBuilding(int actualChosenBuilding) {
		this.actualChosenBuilding = actualChosenBuilding;
	}

	public void setMenuOpen(boolean menuOpen) {
		this.menuOpen = menuOpen;
	}

	public boolean isMenuOpen() {
		return menuOpen;
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

	public void setPosition(Vector2f position) {
		this.position = position.copy();
	}

	public Building getActualBuilding() {
		return actualBuilding;
	}

	public void setPositionBuilding(Vector2f positionBuilding) {
		this.positionBuilding = positionBuilding;
	}

	/**
	 * Create a circle for one menu element (Ground, Defence...)
	 * 
	 * @param element
	 * @param rotation
	 * @param graphics
	 */
	public void rotateAndCreateMenu(int element, int rotation, Graphics graphics) {
		graphics.rotate(0, 0, rotation);
		menu[element] = new Vector2f(0, distanceANCE);
		menu[element].setTheta((rotation / 180f) * (float) Math.PI);
		menu[element].add(position);
	}

	/**
	 * Create a circle for a specific building (Upgrade, Destroy)
	 * 
	 * @param element
	 * @param rotation
	 * @param graphics
	 */
	public void rotateAndCreateMenuBuilding(int element, int rotation, Graphics graphics) {
		graphics.rotate(0, 0, rotation);
		menuBuildings[element] = new Vector2f(0, distanceANCE);
		menuBuildings[element].setTheta((rotation / 180f) * (float) Math.PI);
		menuBuildings[element].add(position);
	}

	/**
	 * Calculating the actual position of the click with coordinates
	 * 
	 * @param graphics
	 */
	public void calcDrawTransformation(Graphics graphics) {
		graphics.setColor(Color.darkGray);
		GraphicTools.calcDrawTransformationForSlick(player, graphics, position);
	}

	/**
	 * Calculating the actual position of the building with coordinates
	 * 
	 * @param graphics
	 */
	public void calcDrawTransformationBuildings(Graphics graphics) {
		graphics.setColor(Color.darkGray);
		GraphicTools.calcDrawTransformationForSlick(player, graphics, positionBuilding);
	}

	/**
	 * Creates a big Circle for the Elements (Ground, Defence... Upgrade
	 * Destroy)
	 * 
	 * @param graphics
	 */
	public void createBigMenuCircle(Graphics graphics) {
		graphics.fillOval(-DIAMETER / 2, distanceANCE - DIAMETER / 2, DIAMETER, DIAMETER);
		graphics.setColor(Color.darkGray);
		graphics.drawOval(-DIAMETER / 2, distanceANCE - DIAMETER / 2, DIAMETER, DIAMETER);
	}

	/**
	 * Creates a small Circle for the unused elements Destroy
	 * 
	 * @param graphics
	 */
	public void createSmallMenuCircle(Graphics graphics) {
		graphics.fillOval(-RADIUS / 2, distanceANCE - RADIUS / 2, RADIUS, RADIUS);
		graphics.setColor(Color.yellow);
		graphics.drawOval(-RADIUS / 2, distanceANCE - RADIUS / 2, RADIUS, RADIUS);
	}

	/**
	 * Creates a very small Circle for the costs of the buildings
	 * 
	 * @param graphics
	 */
	public void createTinyMenuCircle(Graphics graphics) {
		graphics.fillOval(-RADIUS / 2, distanceANCE + distanceANCE / 2 - RADIUS / 2, RADIUS, RADIUS);
		graphics.setColor(Color.magenta);
		graphics.drawOval(-RADIUS / 2, distanceANCE + distanceANCE / 2 - RADIUS / 2, RADIUS, RADIUS);
	}

	/**
	 * Showing the sign of the ground building
	 * 
	 * @param graphics
	 */
	public void showBuildingArmory(Graphics graphics) {
		graphics.setColor(Color.black);
		graphics.drawLine(-SIZEOFARMORY, SIZEOFARMORY + distanceANCE, 0, -SIZEOFARMORY + distanceANCE);
		graphics.drawLine(0, -SIZEOFARMORY + distanceANCE, SIZEOFARMORY, SIZEOFARMORY + distanceANCE);
		graphics.drawLine(+SIZEOFARMORY, +SIZEOFARMORY + distanceANCE, -SIZEOFARMORY, SIZEOFARMORY + distanceANCE);
	}

	/**
	 * Showing the sign of the defence building
	 * 
	 * @param graphics
	 */
	public void showBuildingDefence(Graphics graphics) {
//		graphics.setColor(Color.black);
//		ArrayList<Vector2f> vektoren1 = new ArrayList<Vector2f>();
//		vektoren1.add(new Vector2f(-SIZEOFDEFENCE, distanceANCE - SIZEOFDEFENCE));
//		vektoren1.add(new Vector2f(SIZEOFDEFENCE, distanceANCE - SIZEOFDEFENCE));
//		vektoren1.add(new Vector2f(0, distanceANCE - SIZEOFDEFENCE));
//		vektoren1.add(new Vector2f(0, distanceANCE - SIZEOFDEFENCE * 2));
//		vektoren1.add(new Vector2f(0, distanceANCE + SIZEOFDEFENCE));
//		vektoren1.add(new Vector2f(SIZEOFDEFENCE, distanceANCE + SIZEOFDEFENCE));
//		vektoren1.add(new Vector2f(-SIZEOFDEFENCE, distanceANCE + SIZEOFDEFENCE));
//		graphics.drawOval(-SIZEOFDEFENCE, distanceANCE - SIZEOFDEFENCE * 2, SIZEOFDEFENCE * 2, SIZEOFDEFENCE * 2);
//		GraphicTools.zeicheFigurNachVektoren(vektoren1, graphics);
		
		

		Image image = null;
		try {
			image = new Image("data/buildings/Armory.png");
			image = image.getScaledCopy(100, 100);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		graphics.drawImage(image, distanceANCE/2, distanceANCE - SIZEOFDEFENCE*2, 100, 100, 0f, 0f);
		graphics.resetTransform();
	}

	/**
	 * Showing the sign of the support building
	 * 
	 * @param graphics
	 */
	public void showBuildingSupport(Graphics graphics) {
		graphics.setColor(Color.black);
		ArrayList<Vector2f> vektoren2 = new ArrayList<Vector2f>();
		vektoren2.add(new Vector2f(0, distanceANCE - SIZEOFSUPPORT));
		vektoren2.add(new Vector2f(0, distanceANCE + SIZEOFSUPPORT));
		vektoren2.add(new Vector2f(0, distanceANCE));
		vektoren2.add(new Vector2f(SIZEOFSUPPORT, distanceANCE));
		vektoren2.add(new Vector2f(-SIZEOFSUPPORT, distanceANCE));
		GraphicTools.zeicheFigurNachVektoren(vektoren2, graphics);
	}

	/**
	 * Showing the sign of the tank building
	 * 
	 * @param graphics
	 */
	public void showBuildingBarracks(Graphics graphics) {
		graphics.setColor(Color.black);
		int difference = 10;
		graphics.drawLine(-SIZEOFBARRACKS, SIZEOFBARRACKS + distanceANCE, 0, -SIZEOFBARRACKS + distanceANCE + difference);
		graphics.drawLine(0, -SIZEOFBARRACKS + distanceANCE + difference, SIZEOFBARRACKS, SIZEOFBARRACKS + distanceANCE);
		graphics.drawLine(-SIZEOFBARRACKS, SIZEOFBARRACKS + distanceANCE, 0, -SIZEOFBARRACKS + distanceANCE + difference * 3);
		graphics.drawLine(0, -SIZEOFBARRACKS + distanceANCE + difference * 3, SIZEOFBARRACKS, SIZEOFBARRACKS + distanceANCE);
	
	}

	/**
	 * Showing the sign for the upgrade of the building
	 * 
	 * @param graphics
	 */
	public void showSignUpgrade(Graphics graphics) {
		graphics.setColor(Color.black);
		graphics.drawLine(-SIZEOFUPGRADE / 2, distanceANCE, 0, distanceANCE - SIZEOFUPGRADE);
		graphics.drawLine(0, distanceANCE - SIZEOFUPGRADE, SIZEOFUPGRADE / 2, distanceANCE);
		graphics.drawLine(SIZEOFUPGRADE / 2, distanceANCE, -SIZEOFUPGRADE / 2, distanceANCE);

		ArrayList<Vector2f> vektoren1 = new ArrayList<Vector2f>();
		vektoren1.add(new Vector2f(0, distanceANCE));
		vektoren1.add(new Vector2f(0, distanceANCE + SIZEOFUPGRADE));
		GraphicTools.zeicheFigurNachVektoren(vektoren1, graphics);
	}

	/**
	 * Showing the sign for destroying the building
	 * 
	 * @param graphics
	 */
	public void showSignDestroy(Graphics graphics) {
		graphics.setColor(Color.black);
		ArrayList<Vector2f> vektoren2 = new ArrayList<Vector2f>();
		vektoren2.add(new Vector2f(SIZEOFDESTROY, distanceANCE - SIZEOFDESTROY));
		vektoren2.add(new Vector2f(-SIZEOFDESTROY, distanceANCE + SIZEOFDESTROY));
		vektoren2.add(new Vector2f(0, distanceANCE));
		vektoren2.add(new Vector2f(SIZEOFDESTROY, distanceANCE + SIZEOFDESTROY));
		vektoren2.add(new Vector2f(-SIZEOFDESTROY, distanceANCE - SIZEOFDESTROY));
		GraphicTools.zeicheFigurNachVektoren(vektoren2, graphics);
	}

	/**
	 * Showing the price of the specific building
	 * 
	 * @param graphics
	 */
	public void showPriceBuildings(Graphics graphics, int price) {
		graphics.setColor(Color.white);
		graphics.drawString(price + "", -TEXTdistanceANCE, -SIZEOFTEXTALIGNMENT);
	}

	/**
	 * Playing the animation for the explosion when a building is destroyed
	 * 
	 * @param graphics
	 */
	public void animationSmallExplosion(Graphics graphics) {
		calcDrawTransformation(graphics);
		smallExplosion.draw(-smallExplosion.getHeight() / 2, -smallExplosion.getWidth() / 2, smallExplosion.getHeight() * player.getActualZoom(), smallExplosion.getWidth() * player.getActualZoom());
		graphics.resetTransform();
		if (smallExplosion.getFrame() == gl.getNumberPictures() - 1) {
			smallExplosionPlaying = false;
			smallExplosion.stop();
		}
	}
}
