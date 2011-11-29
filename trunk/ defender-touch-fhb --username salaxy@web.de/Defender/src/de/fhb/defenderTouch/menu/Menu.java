package de.fhb.defenderTouch.menu;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import processing.core.PVector;
import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.graphics.GraphicTools;
import de.fhb.defenderTouch.units.movable.Tank;
import de.fhb.defenderTouch.units.notmovable.Building;
import de.fhb.defenderTouch.units.notmovable.Defence;
import de.fhb.defenderTouch.units.notmovable.Ground;
import de.fhb.defenderTouch.units.notmovable.Support;
import de.fhb.defenderTouch.units.root.BaseUnit;

public class Menu {

	// http://www.cppjj.com/slicktutorial/slickcollision.htm

	/**
	 * recent position of the click
	 */
	protected PVector position;

	/**
	 * recent position of the actual chosen building
	 */
	protected PVector positionBuilding;

	/**
	 * menu points, actual 6 points, 3 in use - Ground - Defence - Support -
	 * Tank?
	 */
	protected PVector menu[] = new PVector[6];

	/**
	 * buildings menu point, 4 points, 2 in use - Upgrade - Destroy
	 */
	protected PVector menuBuildings[] = new PVector[2];

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
	protected final int TEXTDISTANCE;

	/**
	 * size of the menu text
	 */
	protected final int TEXTSIZE;

	/**
	 * activation distance of a menu point
	 */
	protected final int DISTANCE;

	/**
	 * size of Defence sign
	 */
	protected final int SIZEOFDEFENCE;

	/**
	 * size of Ground sign
	 */
	protected final int SIZEOFGROUND;

	/**
	 * size of Support sign
	 */
	protected final int SIZEOFSUPPORT;

	/**
	 * size of Defence sign
	 */
	protected final int SIZEOFTANK;

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
	private Animation animation;

	/**
	 * ANIMATION playing?
	 */
	private boolean smallExplosionPlaying = false;

	/**
	 * GIFLOADER
	 */
	private GifLoader gl;

	/**
	 * Constructor of Menu
	 * 
	 */
	public Menu(CopyOnWriteArrayList<BaseUnit> buildings, Player player) {
		this.position = new PVector(0, 0);
		this.buildings = buildings;
		this.player = player;
		RADIUS = DIAMETER / 2;
		TEXTSIZE = DIAMETER / 4;
		DISTANCE = -(DIAMETER + 5);
		TEXTDISTANCE = DIAMETER / 11;
		SIZEOFTEXTALIGNMENT = DIAMETER + TEXTSIZE * 2 + TEXTDISTANCE * 2;
		SIZEOFGROUND = TEXTDISTANCE * 3;
		SIZEOFDEFENCE = TEXTDISTANCE * 2;
		SIZEOFSUPPORT = TEXTDISTANCE * 3;
		SIZEOFDESTROY = TEXTDISTANCE * 2;
		SIZEOFUPGRADE = TEXTDISTANCE * 3;
		SIZEOFTANK = TEXTDISTANCE * 2;
		ANIMATIONS = DIAMETER / 8;

		for (int i = 0; i < menu.length; i++) {
			menu[i] = new PVector(-100, -100);
		}
		for (int i = 0; i < menuBuildings.length; i++) {
			menuBuildings[i] = new PVector(-100, -100);
		}
		gl = new GifLoader("small explosion", 17);
		animation = gl.getAni();

	}

	public void createBuilding(DefenderControl defenderControl) {
		switch (getActualChosenBuilding()) {
		case 0:
			System.out.println("building a Ground unit");
			new Ground((int) getPositionX(), (int) getPositionY(), BaseUnit.MODE_NORMAL, this.player, defenderControl);
			break;
		case 1:
			System.out.println("building a Defence unit");
			new Defence((int) getPositionX(), (int) getPositionY(), BaseUnit.MODE_NORMAL, this.player, defenderControl);
			break;
		case 2:
			System.out.println("building a Support unit");
			new Support((int) getPositionX(), (int) getPositionY(), BaseUnit.MODE_NORMAL, this.player, defenderControl);
			break;
		case 3:
			System.out.println("building a Tank unit");
			new Tank((int) getPositionX(), (int) getPositionY(), BaseUnit.MODE_NORMAL, this.player, defenderControl);
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
			showBuildingGround(graphics);
			createTinyMenuCircle(graphics);
			showPriceBuildings(graphics, Ground.PRICE);
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
			showBuildingTank(graphics);
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
			graphics.drawString(getActualBuildingLevel(positionBuilding) + "", 0, Math.abs(DISTANCE) - TEXTDISTANCE);
			graphics.resetTransform();
		}
	}

	/**
	 * 
	 * @param clickVector
	 *            (actual position of mouse click)
	 * @return true (if click was in middle circle of menu)
	 */
	public boolean isInnerMainElement(PVector clickVector) {

		if (this.position.dist(clickVector) < this.RADIUS) {
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
	public boolean isInnerMenuElement(PVector clickVector) {
		if (this.menu[0].dist(clickVector) < this.RADIUS) {
			System.out.println("Menue 1");
			if (isEnoughCredits(Ground.PRICE)) {
				player.setCredits(player.getCredits() - Ground.PRICE);
				setActualChosenBuilding(0);
				setMenuOpen(false);
				return true;
			}
		}
		if (this.menu[1].dist(clickVector) < this.RADIUS) {
			System.out.println("Menue 2");
			if (isEnoughCredits(Defence.PRICE)) {
				player.setCredits(player.getCredits() - Defence.PRICE);
				setActualChosenBuilding(1);
				setMenuOpen(false);
				return true;
			}
		}
		if (this.menu[2].dist(clickVector) < this.RADIUS) {
			System.out.println("Menue 3");
			if (isEnoughCredits(Support.PRICE)) {
				player.setCredits(player.getCredits() - Support.PRICE);
				setActualChosenBuilding(2);
				setMenuOpen(false);
				return true;
			}
		}
		if (this.menu[3].dist(clickVector) < this.RADIUS) {
			System.out.println("Menue 4");
			if (isEnoughCredits(Tank.PRICE)) {
				player.setCredits(player.getCredits() - Tank.PRICE);
				setActualChosenBuilding(3);
				setMenuOpen(false);
				return true;
			}
		}
		if (this.menu[4].dist(clickVector) < this.RADIUS) {
			System.out.println("Menue 5");
		}
		if (this.menu[5].dist(clickVector) < this.RADIUS) {
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
	public boolean isInnerBuildingElement(PVector clickVector) {

		if (this.menuBuildings[0].dist(clickVector) < this.RADIUS) {
			setBuildingOpen(false, null);
			upgradeBuilding();
			setActualChosenBuilding(0);
			return true;
		}
		if (this.menuBuildings[1].dist(clickVector) < this.RADIUS) {

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
	public void setBuildingOpen(boolean buildingOpen, PVector clickVector) {

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
	public int getActualBuildingLevel(PVector clickVector) {
		return actualBuilding.getLevel();
	}

	/**
	 * 
	 * @return PRICE (price of the specific building)
	 * 
	 *         searching for the actual price of a building
	 */
	public int getActualBuildingPrice() {
		if (actualBuilding instanceof Ground)
			return Ground.PRICE;

		if (actualBuilding instanceof Defence)
			return Defence.PRICE;

		if (actualBuilding instanceof Support)
			return Support.PRICE;

		// if (actualBuilding instanceof Tank) {
		// return Tank.PRICE;
		// }
		else
			return 0;

	}

	/**
	 * searching for the actual name of a building
	 */
	public void setActualBuildingName() {

		if (actualBuilding instanceof Defence)
			actualBuildingName = "Defence";

		if (actualBuilding instanceof Ground)
			actualBuildingName = "Ground";

		if (actualBuilding instanceof Support)
			actualBuildingName = "Support";

		// if (actualBuilding instanceof Tank) {
		// actualBuildingName = "Tank";
		// }

		else
			actualBuildingName = "Nichts gewählt";

	}

	/**
	 * calculate the credits u get from destroying a building
	 */
	public void setBuildingDestroyPrice() {
		smallExplosionPlaying = true;
		animation.restart();
		if (actualBuilding instanceof Defence)
			player.setCredits(player.getCredits() + (Defence.PRICE * actualBuilding.getLevel()) / 2);

		if (actualBuilding instanceof Ground)
			player.setCredits(player.getCredits() + (Ground.PRICE * actualBuilding.getLevel()) / 2);

		if (actualBuilding instanceof Support)
			player.setCredits(player.getCredits() + (Support.PRICE * actualBuilding.getLevel()) / 2);

		// if (actualBuilding instanceof Tank) {
		// player.setCredits(player.getCredits() + (Tank.PRICE * bu.getLevel())
		// / 2);
		// }

	}

	/**
	 * 
	 * @param clickVector
	 * @return if place for the new building is free
	 */
	public boolean isPlaceTaken(PVector clickVector) {
		for (BaseUnit building : buildings) {
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
		else return 0;
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

	public void setPosition(PVector position) {
		this.position = position.get();
	}

	public Building getActualBuilding() {
		return actualBuilding;
	}

	public void setPositionBuilding(PVector positionBuilding) {
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
		menu[element] = new PVector(0, DISTANCE);
		menu[element].rotate((rotation / 180f) * (float) Math.PI);
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
		menuBuildings[element] = new PVector(0, DISTANCE);
		menuBuildings[element].rotate((rotation / 180f) * (float) Math.PI);
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
		graphics.fillOval(-DIAMETER / 2, DISTANCE - DIAMETER / 2, DIAMETER, DIAMETER);
		graphics.setColor(Color.darkGray);
		graphics.drawOval(-DIAMETER / 2, DISTANCE - DIAMETER / 2, DIAMETER, DIAMETER);
	}

	/**
	 * Creates a small Circle for the unused elements Destroy)
	 * 
	 * @param graphics
	 */
	public void createSmallMenuCircle(Graphics graphics) {
		graphics.fillOval(-RADIUS / 2, DISTANCE - RADIUS / 2, RADIUS, RADIUS);
		graphics.setColor(Color.yellow);
		graphics.drawOval(-RADIUS / 2, DISTANCE - RADIUS / 2, RADIUS, RADIUS);
	}

	/**
	 * Creates a very small Circle for the costs of the buildings
	 * 
	 * @param graphics
	 */
	public void createTinyMenuCircle(Graphics graphics) {
		graphics.fillOval(-RADIUS / 2, DISTANCE + DISTANCE / 2 - RADIUS / 2, RADIUS, RADIUS);
		graphics.setColor(Color.magenta);
		graphics.drawOval(-RADIUS / 2, DISTANCE + DISTANCE / 2 - RADIUS / 2, RADIUS, RADIUS);
	}

	/**
	 * Showing the sign of the ground building
	 * 
	 * @param graphics
	 */
	public void showBuildingGround(Graphics graphics) {
		graphics.setColor(Color.black);
		graphics.drawLine(-SIZEOFGROUND, SIZEOFGROUND + DISTANCE, 0, -SIZEOFGROUND + DISTANCE);
		graphics.drawLine(0, -SIZEOFGROUND + DISTANCE, SIZEOFGROUND, SIZEOFGROUND + DISTANCE);
		graphics.drawLine(+SIZEOFGROUND, +SIZEOFGROUND + DISTANCE, -SIZEOFGROUND, SIZEOFGROUND + DISTANCE);
	}

	/**
	 * Showing the sign of the defence building
	 * 
	 * @param graphics
	 */
	public void showBuildingDefence(Graphics graphics) {
		graphics.setColor(Color.black);
		ArrayList<PVector> vektoren1 = new ArrayList<PVector>();
		vektoren1.add(new PVector(-SIZEOFDEFENCE, DISTANCE - SIZEOFDEFENCE));
		vektoren1.add(new PVector(SIZEOFDEFENCE, DISTANCE - SIZEOFDEFENCE));
		vektoren1.add(new PVector(0, DISTANCE - SIZEOFDEFENCE));
		vektoren1.add(new PVector(0, DISTANCE - SIZEOFDEFENCE * 2));
		vektoren1.add(new PVector(0, DISTANCE + SIZEOFDEFENCE));
		vektoren1.add(new PVector(SIZEOFDEFENCE, DISTANCE + SIZEOFDEFENCE));
		vektoren1.add(new PVector(-SIZEOFDEFENCE, DISTANCE + SIZEOFDEFENCE));
		graphics.drawOval(-SIZEOFDEFENCE, DISTANCE - SIZEOFDEFENCE * 2, SIZEOFDEFENCE * 2, SIZEOFDEFENCE * 2);
		GraphicTools.zeicheFigurNachVektoren(vektoren1, graphics);
	}

	/**
	 * Showing the sign of the support building
	 * 
	 * @param graphics
	 */
	public void showBuildingSupport(Graphics graphics) {
		graphics.setColor(Color.black);
		ArrayList<PVector> vektoren2 = new ArrayList<PVector>();
		vektoren2.add(new PVector(0, DISTANCE - SIZEOFSUPPORT));
		vektoren2.add(new PVector(0, DISTANCE + SIZEOFSUPPORT));
		vektoren2.add(new PVector(0, DISTANCE));
		vektoren2.add(new PVector(SIZEOFSUPPORT, DISTANCE));
		vektoren2.add(new PVector(-SIZEOFSUPPORT, DISTANCE));
		GraphicTools.zeicheFigurNachVektoren(vektoren2, graphics);
	}

	/**
	 * Showing the sign of the tank building
	 * 
	 * @param graphics
	 */
	public void showBuildingTank(Graphics graphics) {
		graphics.setColor(Color.black);
		graphics.drawRect(-SIZEOFTANK, DISTANCE - SIZEOFTANK, SIZEOFTANK * 2, SIZEOFTANK * 3);
		graphics.drawOval(-SIZEOFTANK / 2, DISTANCE + SIZEOFTANK - SIZEOFTANK / 4, SIZEOFTANK, SIZEOFTANK);
		graphics.drawRect(-SIZEOFTANK / 4, DISTANCE - SIZEOFTANK - SIZEOFTANK / 4, SIZEOFTANK / 2, SIZEOFTANK * 2);
	}

	/**
	 * Showing the sign for the upgrade of the building
	 * 
	 * @param graphics
	 */
	public void showSignUpgrade(Graphics graphics) {
		graphics.setColor(Color.black);
		graphics.drawLine(-SIZEOFUPGRADE / 2, DISTANCE, 0, DISTANCE - SIZEOFUPGRADE);
		graphics.drawLine(0, DISTANCE - SIZEOFUPGRADE, SIZEOFUPGRADE / 2, DISTANCE);
		graphics.drawLine(SIZEOFUPGRADE / 2, DISTANCE, -SIZEOFUPGRADE / 2, DISTANCE);

		ArrayList<PVector> vektoren1 = new ArrayList<PVector>();
		vektoren1.add(new PVector(0, DISTANCE));
		vektoren1.add(new PVector(0, DISTANCE + SIZEOFUPGRADE));
		GraphicTools.zeicheFigurNachVektoren(vektoren1, graphics);
	}

	/**
	 * Showing the sign for destroying the building
	 * 
	 * @param graphics
	 */
	public void showSignDestroy(Graphics graphics) {
		graphics.setColor(Color.black);
		ArrayList<PVector> vektoren2 = new ArrayList<PVector>();
		vektoren2.add(new PVector(SIZEOFDESTROY, DISTANCE - SIZEOFDESTROY));
		vektoren2.add(new PVector(-SIZEOFDESTROY, DISTANCE + SIZEOFDESTROY));
		vektoren2.add(new PVector(0, DISTANCE));
		vektoren2.add(new PVector(SIZEOFDESTROY, DISTANCE + SIZEOFDESTROY));
		vektoren2.add(new PVector(-SIZEOFDESTROY, DISTANCE - SIZEOFDESTROY));
		GraphicTools.zeicheFigurNachVektoren(vektoren2, graphics);
	}

	/**
	 * Showing the price of the specific building
	 * 
	 * @param graphics
	 */
	public void showPriceBuildings(Graphics graphics, int price) {
		graphics.setColor(Color.white);
		graphics.drawString(price + "", -TEXTDISTANCE, -SIZEOFTEXTALIGNMENT);
	}

	/**
	 * Playing the animation for the explosion when a building is destroyed
	 * 
	 * @param graphics
	 */
	public void animationSmallExplosion(Graphics graphics) {
		calcDrawTransformation(graphics);
		System.out.println(animation.getWidth());
		graphics.drawAnimation(animation, -animation.getWidth() / 2, -animation.getWidth() / 2);
		graphics.resetTransform();
		if (animation.getFrame() == gl.getNumberPictures() - 1) {
			smallExplosionPlaying = false;
			animation.stop();
		}
		// TODO Größe der animation anpassen
	}
}
