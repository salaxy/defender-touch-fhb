package de.fhb.defenderTouch.units.amunition;

import processing.core.PVector;
import de.fhb.defenderTouch.gamelogic.DefenderControl;
import de.fhb.defenderTouch.gamelogic.Player;
import de.fhb.defenderTouch.units.root.BaseUnit;

public class ShootWithRange extends Shoot {

	protected int maxRange=400;
	protected PVector startPostion;
	
	public ShootWithRange(int x, int y, int mode, Player player,BaseUnit destinationUnit, int damage, DefenderControl gamelogic) {
		super(x, y, mode, player, destinationUnit, damage, gamelogic);
		this.startPostion=new PVector(x,y);
	}

	
	
	public void update(){
		super.update();
		
		//out of Range
		if(this.startPostion.dist(this.position)>maxRange){
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
