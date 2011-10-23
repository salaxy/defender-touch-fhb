package de.fhb.defenderTouch.gamelogic;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import de.fhb.defenderTouch.units.movable.BaseUnit;

public class GlobalUnits{

	private ArrayList<BaseUnit> globalUnits=new ArrayList<BaseUnit>();
	public static Semaphore semaphore = new Semaphore( 1 );
	
	public GlobalUnits(){
		
	}

	public ArrayList<BaseUnit> getGlobalUnits() {

		return globalUnits;
	}
	
	
	public void add(BaseUnit bu) {
		
//        try 
//        { 
//          semaphore.acquire(); 
//          System.out.println( "Thread=" + Thread.currentThread().getName() +
//            ", Available Permits=" + semaphore.availablePermits() ); 
        		globalUnits.add(bu);  
//        } 
//        catch ( InterruptedException e ) { 
//          e.printStackTrace(); 
//        } 
//        finally { 
//          semaphore.release(); 
//        } 
	}
	
	public void remove(BaseUnit bu) {
		
		globalUnits.remove(bu);
	}
}
