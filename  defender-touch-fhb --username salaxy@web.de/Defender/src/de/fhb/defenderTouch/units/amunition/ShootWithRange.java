package de.fhb.defenderTouch.units.amunition;

import org.newdawn.slick.geom.Vector2f;

import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.units.root.BaseUnit;

public class ShootWithRange extends Shoot {

	protected int maxRange=400;
	protected Vector2f startPostion;
	
	public ShootWithRange(int x, int y, int mode, Player player,BaseUnit destinationUnit, int damage, DefenderControl gamelogic) {
		super(x, y, mode, player, destinationUnit, damage, gamelogic);
		this.startPostion=new Vector2f(x,y);
	}

	
	
	public void update(){
		super.update();
		
		//out of Range
		if(this.startPostion.distance(this.position)>maxRange){
			this.delete();
		}
	}
	
	public void delete(){
		//TODO hier Explosion einbauen
		//*************************
		
		
		
		//************************* muss vor delete
		super.delete();
	}
}
