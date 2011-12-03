package de.fhb.defenderTouch.units.amunition;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.graphics.Animations;
import de.fhb.defenderTouch.graphics.GraphicTools;
import de.fhb.defenderTouch.units.root.BaseUnit;
import org.newdawn.slick.Graphics;
public class ShootWithRange extends Shoot {

	protected int maxRange = 300;
	protected Vector2f startPostion;
	private Animation smallExplosion;
	private Player player;
	private  Animations gl;
	private Graphics graphics;

	public ShootWithRange(int x, int y, int mode, Player player, BaseUnit destinationUnit, int damage, DefenderControl gamelogic) {

		super(x, y, mode, player, destinationUnit, damage, gamelogic);
		this.startPostion = new Vector2f(x, y);
		this.player = player;
		this.gl = new Animations("small explosion", 17);
		this.smallExplosion = gl.getAni();
	}

	public void update() {
		super.update();

		// out of Range
		if (this.startPostion.distance(this.position) > maxRange) {
			this.delete();
		}
	}

	public void delete() {
//		GraphicTools.calcDrawTransformationForSlick(player, graphics, position);
//		smallExplosion.draw((-smallExplosion.getHeight() / 2) * player.getActualZoom(), (-smallExplosion.getWidth() / 2) * player.getActualZoom(), smallExplosion.getHeight()
//				* player.getActualZoom(), smallExplosion.getWidth() * player.getActualZoom());
////		graphics.resetTransform();
//		System.out.println(1234);
//		if (smallExplosion.getFrame() == gl.getNumberPictures() - 1) {
////			smallExplosionPlaying = false;
//			
////			smallExplosion.stop();
//		}
		// TODO hier Explosion einbauen
		// *************************

		// ************************* muss vor delete
		super.delete();
	}
}
