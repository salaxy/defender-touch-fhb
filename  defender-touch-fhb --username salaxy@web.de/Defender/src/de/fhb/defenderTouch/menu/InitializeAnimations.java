package de.fhb.defenderTouch.menu;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.graphics.Animations;
import de.fhb.defenderTouch.graphics.GraphicTools;

public class InitializeAnimations {

	private Animation tree1;
	private Animations tree1Loader;
	private Animation tree2;
	private Animations tree2Loader;
	private Vector2f position;

	public InitializeAnimations() {
		this.position = new Vector2f();
		tree1Loader = new Animations("tree1", 10);
		tree1 = tree1Loader.getAni();
		tree2Loader = new Animations("tree2", 9);
		tree2 = tree2Loader.getAni();
	}

	/**
	 * Playing the animation for the tree1 when a building is destroyed
	 * 
	 * @param graphics
	 * @param owner
	 */
	public void showTree1(Graphics graphics, Player owner) {
		GraphicTools.calcDrawTransformationForSlick(owner, graphics, position);
		tree1.draw(0, 0, 50, 50);
		this.position = new Vector2f(300, 300);
		graphics.resetTransform();
	}

	/**
	 * Playing the animation for the tree2 when a building is destroyed
	 * 
	 * @param graphics
	 * @param owner
	 */
	public void showTree2(Graphics graphics, Player owner) {
		GraphicTools.calcDrawTransformationForSlick(owner, graphics, position);
		tree2.draw(0, 0, 50, 50);
		this.position = new Vector2f(400, 400);
		graphics.resetTransform();
	}
}
