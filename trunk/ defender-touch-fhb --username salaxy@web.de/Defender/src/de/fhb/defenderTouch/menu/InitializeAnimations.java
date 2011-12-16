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
	private Vector2f position;

	public InitializeAnimations() {
		tree1Loader = new Animations("tree1", 10);
		tree1 = tree1Loader.getAni();
		this.position = new Vector2f();
	}

	/**
	 * Playing the animation for the tree1 when a building is destroyed
	 * 
	 * @param graphics
	 * @param owner 
	 * @param owner2 
	 */
	public void showTree1(Graphics graphics, Player owner) {
		GraphicTools.calcDrawTransformationForSlick(owner, graphics, position);
		this.position = new Vector2f(300, 300);
		tree1.draw(0, 0, 50, 50);
		graphics.resetTransform();
		GraphicTools.calcDrawTransformationForSlick(owner, graphics, position);
		this.position = new Vector2f(800, 350);
		tree1.draw(0, 0, 50, 50);
		graphics.resetTransform();
		GraphicTools.calcDrawTransformationForSlick(owner, graphics, position);
		this.position = new Vector2f(350, 1200);
		tree1.draw(0, 0, 50, 50);
		graphics.resetTransform();
		GraphicTools.calcDrawTransformationForSlick(owner, graphics, position);
		this.position = new Vector2f(800, 1100);
		tree1.draw(0, 0, 50, 50);
		graphics.resetTransform();
		
	}
}
