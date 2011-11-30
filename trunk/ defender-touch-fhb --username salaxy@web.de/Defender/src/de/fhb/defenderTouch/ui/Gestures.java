package de.fhb.defenderTouch.ui;
//package uniteFollowLine;

import java.util.Vector;

import TUIO.TuioCursor;
import TUIO.TuioTime;






public class Gestures {
	//Variablen zum überprüfen von Zeiteinheiten
	private boolean timeFlag = false;
	private int seconds = 0;
//	private TuioTime time ;
	
	
	
	public Gestures(){
		this.timeFlag = false;
		this.seconds = 0;
	}
	
	/**
	 * 
	 * @param tuioCursorList
	 *         		(Liste der Cursor auf dem Feld)
	 * @return double 
	 * 				(retrun 0.0, wenn sich der Abstand nicht verändert hat
	 * 				 return positiven Wert, wenn sich 2 Cursor voneinander entfernen
	 *               return negativen Wert, wenn 2 Cursor ihren Abstand verringern  )
	 * 					
	 */
	public double rangeDifferent(Vector tuioCursorList){
		TuioCursor tcur1, tcur2;	
		double startRange = 0;
		double endRange = 0;
		double rangeDifferent = 0;
		//Den Abstand von 2 Cursor besorgen
		for (int i=0;i<tuioCursorList.size();i++){
			tcur1 = (TuioCursor)tuioCursorList.elementAt(i);
				for (int j=i+1;j<tuioCursorList.size();j++){
					if(tcur1 != tuioCursorList.lastElement()){
						tcur2 = (TuioCursor)tuioCursorList.elementAt(j);						
						startRange=getDistance(tcur1, tcur2);
						//Solange sich ein Cursor mit angemessener Geschwindigkeit bewegt überprüfe die neuen Abstande
						while( tcur1.getMotionSpeed()>0.02 || tcur2.getMotionSpeed()>0.02){
							
							endRange = getDistance(tcur1, tcur2);
							rangeDifferent = endRange - startRange;
							if(rangeDifferent <0)System.out.println("decreased");
							if(rangeDifferent >0)System.out.println("increased");
							
							if(rangeDifferent!=0){
								System.out.println(rangeDifferent);
								return rangeDifferent;		
							}	
						}
					}
				}
		}		
		return rangeDifferent;
	}
	/**
	 * 
	 * @param tuioCursorList
	 *         		(Liste der Cursor auf dem Feld)
	 * @param secondsTwoWait
	 * 				(sekunden die gewartet werden müssen)
	 * @return boolean 
	 * 				(retrun true, wenn 2 Cursor, in einer Zeitspanne von 2 Sekunden, nah beieinander liegen)
	 * 					
	 */
	public boolean twoFingersInRange(Vector tuioCursorList, int secondsTwoWait){
		TuioCursor tcur1, tcur2;
		TuioCursor tcur[] = new TuioCursor[2];		
		double range;		
		//Die Entfernung von 3 Cursor zueinander vergleichen
		for (int i=0;i<tuioCursorList.size();i++){
			tcur1 = (TuioCursor)tuioCursorList.elementAt(i);
			for (int j=i+1;j<tuioCursorList.size();j++){
				if(tcur1 != tuioCursorList.lastElement()){
					tcur2 = (TuioCursor)tuioCursorList.elementAt(j);
					range=getDistance(tcur1,tcur2);
					if(range<=0.025){ 		
						//überprüft ob XSekunden 2 Cursor nah beieinander sind
						if(waitXSeconds(secondsTwoWait)){	
							System.out.println("2 Finger erkannt" );
							return true;
						}							
					}	
				}
			}	
		}
		return false;
	}
	
	/**
	 * 
	 * @param tuioCursorList
	 *         		(Liste der Cursor auf dem Feld)
	 * @return boolean 
	 * 				(retrun true, wenn 3 Cursor, in einer Zeitspanne von 2 Sekunden, nah beieinander liegen)
	 * 					
	 */
	public void threeFingersInRange(Vector tuioCursorList){
		TuioCursor tcur1, tcur2, tcur3;
		double range1,range2,range3;	
		//Die Entfernung von 3 Cursor zueinander vergleichen
		for (int i=0;i<tuioCursorList.size();i++){
			tcur1 = (TuioCursor)tuioCursorList.elementAt(i);
			for (int j=i+1;j<tuioCursorList.size();j++){
				tcur2 = (TuioCursor)tuioCursorList.elementAt(j);
				for(int k=j+1;k<tuioCursorList.size(); k++)						
					if(tcur1 != tuioCursorList.lastElement() && tcur2 != tuioCursorList.lastElement()){
					tcur3 = (TuioCursor)tuioCursorList.elementAt(k);						
					range1 = getDistance(tcur1,tcur2);						
					range2 = getDistance(tcur1,tcur3);						
					range3 = getDistance(tcur2,tcur3);		
					//abfrage der Permutationen der Cursorpositionen
					if((range1<=0.025 && (range2<=0.025 || range3<=0.025)) || 
					   (range2<=0.025 && (range1<=0.025 || range3<=0.025)) ||
					   (range3<=0.025 && (range1<=0.025 || range2<=0.025))){ 
							System.out.println("3 Finger erkannt" );
					}
				}
			}
		}	
	}
	
	/**
	 * 
	 * @param xseconds
	 *         		(zum warten der von X Zeiteinheiten in Sekunden)
	 * @return boolean 
	 * 				(retrun true, wenn die X Zeiteinheiten gewartet wurden)
	 * 
	 * 				warte X Sekunden und fahre fort 					
	 */
	private boolean waitXSeconds(int xseconds){	
		//zähle sekunden hoch
		if(!(timeFlag)) {
			seconds=(int) TuioTime.getSystemTime().getSeconds();
			timeFlag=true;
			seconds+=xseconds;
		}
		if(TuioTime.getSystemTime().getSeconds() == seconds){
			timeFlag=false;
			seconds= 0;
			return true;
		}		
		return false;
	}
	
	/**
	 * 
	 * @param tcur1
	 *            (actual position of cursor1)
     * @param tcur1
	 *            (actual position of cursor2)
	 * @return double 
	 * 			  (range between cursors)
	 * 
	 * 			  calculate the range between two cursors.
	 */
	private double getDistance(TuioCursor tcur1, TuioCursor tcur2){
		float a,b;
		double range;
		a = Math.abs((tcur1.getX()- tcur2.getX()));
		a *=a;
		b = Math.abs((tcur1.getY()- tcur2.getY()));
		b *=b;
		range= Math.sqrt((a+b));
		return range;
	}
}

