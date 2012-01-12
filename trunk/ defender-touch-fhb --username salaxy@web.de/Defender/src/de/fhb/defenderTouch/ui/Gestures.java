package de.fhb.defenderTouch.ui;

//package uniteFollowLine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.newdawn.slick.geom.Vector2f;

import TUIO.TuioClient;
import TUIO.TuioCursor;
import TUIO.TuioTime;

public class Gestures {

	// Variablen zum �berpr�fen von Zeiteinheiten
//	private boolean timeFlag = false;
//	private Cursor cursor;
	private int height;
	private int width;
	private ArrayList<Cursor> tuioCursorListOld = new ArrayList<Cursor>();
	private ArrayList<int[]> tuioCursorListForSecondsToWait = new ArrayList<int[]>();
	private ArrayList<Cursor> tuioCursorListActual = new ArrayList<Cursor>();
	private int zaehler = 2;
	TuioTime time;

	TuioClient tuioClient;
	

	public Gestures(TuioClient tuioClient) {
		this.tuioClient = tuioClient;
	}

	public void countFrames() {

		TuioCursor tcur1, tcur2;
		Cursor cursorOld;
		Vector tuioCursorList = tuioClient.getTuioCursors(); // gets all cursors
																// (fingers)
																// which are
																// currently on
																// the screen
		// alle X Frames aktuallisieren;
		if (zaehler == 2) {			
			tuioCursorListOld.clear();
			for (int i = 0; i < tuioCursorList.size(); i++) {
				tcur1 = (TuioCursor) tuioCursorList.elementAt(i);
				if(tuioCursorList.firstElement()==tuioCursorList.lastElement()){	
					tuioCursorListOld.add(new Cursor(tcur1.getCursorID(), tcur1.getScreenX(width),tcur1.getScreenY(height)));
				}
				for (int j = i + 1; j < tuioCursorList.size(); j++) {					
					tcur1 = (TuioCursor) tuioCursorList.elementAt(i);
					tcur2 = (TuioCursor) tuioCursorList.elementAt(j);
					tuioCursorListOld.add(new Cursor(tcur1.getCursorID(),tcur2.getCursorID(),getDistance(tcur1, tcur2),
							              tcur1.getScreenX(width),tcur1.getScreenY(height),tcur2.getScreenX(width),tcur2.getScreenY(height)));
				}
			}
			zaehler = 0;
		}
		zaehler++;		
		
		for(int i=0;i<tuioCursorList.size();i++){
			tcur1 = (TuioCursor)tuioCursorList.get(i);
			for(int j =0 ;j<tuioCursorListOld.size();j++){
				cursorOld = tuioCursorListOld.get(j);
				if(cursorOld.getTcur1ID() == tcur1.getCursorID()){
					if(tcur1.getScreenX(width) < width/2 && !cursorOld.getTcur1Player1()){
						cursorOld.setTcur1Player1(true);
					}
				}else if( cursorOld.getTcur2ID() == tcur1.getCursorID()){
					if(tcur1.getScreenX(width) < width/2 && !cursorOld.getTcur1Player1()){
						cursorOld.setTcur2Player1(true);
					}
				}
			}
		}
	}
	
	public int[] schiebeMap(Vector tuioCursorList){
		int coordinate1[]=new int[4] ;
		int coordinate2[]=new int[4] ;
		int tcurP1=0;
		int tcurP2=0;
		TuioCursor tcur1;
		
//		for(int i = 0 ; i<tuioCursorList.size();i++){
//			tcur1=(TuioCursor)tuioCursorList.get(i);
//			if(tcur1.getScreenX(width) < width/2){
//				countCurP1++;
//			}else{
//				countCurP2++;
//			}
//		}
		
//		if(twoFingersInRange(tuioCursorList,0)!= null && rangeDifferent(tuioCursorList) != null){
//			return null;
//		}
		
		for(int i=0;i<tuioCursorList.size(); i++){
			tcur1= (TuioCursor)tuioCursorList.get(i);
//			for(int j = 0; j<tuioCursorListOld.size();j++){
//					if(tuioCursorListOld.get(j).getTcur1ID()==tcur1.getCursorID()){
//						if(tuioCursorListOld.get(j).getTcur1Player1()){
						if(tcur1.getScreenX(width) <= width/2){
							tcurP1++;
//							coordinate1[0]=(int)tuioCursorListOld.get(j).getTcur1ScreenX();
//							coordinate1[1]=(int)tuioCursorListOld.get(j).getTcur1ScreenY();
//							coordinate1[2]=tcur1.getScreenX(width);
//							coordinate1[3]=tcur1.getScreenY(height);
//							System.out.println("tcur1= "+tcurP1);
						}else{
							tcurP2++;
//							coordinate2[0]=(int)tuioCursorListOld.get(j).getTcur1ScreenX();
//							coordinate2[1]=(int)tuioCursorListOld.get(j).getTcur1ScreenY();
//							coordinate2[2]=tcur1.getScreenX(width);
//							coordinate2[3]=tcur1.getScreenY(height);
//							System.out.println("tcur2= "+tcurP2);
						}						
//					}
//			}
		
			
		}
		for(int i=0;i<tuioCursorList.size(); i++){
			tcur1= (TuioCursor)tuioCursorList.get(i);
			for(int j = 0; j<tuioCursorListOld.size();j++){
				if(tcurP1 == 1 && tcur1.getScreenX(width) <= width/2){
					System.out.println("hallo");
					if(tuioCursorListOld.get(j).getTcur1ID() == tcur1.getCursorID()){
						System.out.println("bin ich drin oder was");
						coordinate1[0]=(int)tuioCursorListOld.get(j).getTcur1ScreenX();
						coordinate1[1]=(int)tuioCursorListOld.get(j).getTcur1ScreenY();
						coordinate1[2]=tcur1.getScreenX(width);
						coordinate1[3]=tcur1.getScreenY(height);
						return coordinate1;
					}
				}
				if(tcurP2 == 1 && tcur1.getScreenX(width) >= width/2){
					System.out.println("buhuuuuu");;
					if(tuioCursorListOld.get(j).getTcur1ID() == tcur1.getCursorID()){
						System.out.println("ich bin drin");
						coordinate1[0]=(int)tuioCursorListOld.get(j).getTcur1ScreenX();
						coordinate1[1]=(int)tuioCursorListOld.get(j).getTcur1ScreenY();
						coordinate1[2]=tcur1.getScreenX(width);
						coordinate1[3]=tcur1.getScreenY(height);
						return coordinate1;
					}
				}
			}
		}
		
		
//		if(tcurP1==1){
//			return coordinate1;
//		}
//		if(tcurP2==1){
//			return coordinate2;
//		}	
		return null;
	}

	public void cleanUpTheList(int tcurID) {
		int actualElement[] = new int[4];
		for (int i = 0; i < tuioCursorListForSecondsToWait.size(); i++) {
			actualElement = tuioCursorListForSecondsToWait.get(i);
			if (actualElement[0] == tcurID || actualElement[1] == tcurID) {
				tuioCursorListForSecondsToWait.remove(i);
			}
		}
	}

	public void permutationOfCursors(Vector tuioCursorList) {
		int a[] = new int[4];
		TuioCursor tcur1, tcur2;
		for (int i = 0; i < tuioCursorList.size(); i++) {
			for (int j = i + 1; j < tuioCursorList.size(); j++) {
				tcur1 = (TuioCursor) tuioCursorList.elementAt(i);
				tcur2 = (TuioCursor) tuioCursorList.elementAt(j);
				a[0] = tcur1.getCursorID();
				a[1] = tcur2.getCursorID();
				a[2] = 0;
				a[3] = 0;
				tuioCursorListForSecondsToWait.add(a);
			}
		}
	}

	/**
	 * 
	 * @param tcur1
	 *            (actual position of cursor1)
	 * @param tcur1
	 *            (actual position of cursor2)
	 * @return double (range between cursors)
	 * 
	 *         calculate the range between two cursors.
	 */
	private double getDistance(TuioCursor tcur1, TuioCursor tcur2) {
		float a, b;
		double range;
		a = Math.abs((tcur1.getX() - tcur2.getX()));
		a *= a;
		b = Math.abs((tcur1.getY() - tcur2.getY()));
		b *= b;
		range = Math.sqrt((a + b));
		return range;
	}

	/**
	 * 
	 * @param xseconds
	 *            (zum warten der von X Zeiteinheiten in Sekunden)
	 * @return boolean (retrun true, wenn die X Zeiteinheiten gewartet wurden)
	 * 
	 *         warte X Sekunden und fahre fort
	 */
	private boolean waitXSeconds(int xseconds, TuioCursor tcur1,
			TuioCursor tcur2) {
		int actualCursor[] = new int[4];
		int checkCursor[] = new int[2];
		int listSize = tuioCursorListForSecondsToWait.size();
		int counter = 0;

		if (tuioCursorListForSecondsToWait.isEmpty()) {
			setListElement(tcur1, tcur2, xseconds);
			counter++;
		}
		for (int i = 0; i < listSize; i++) {
			actualCursor = tuioCursorListForSecondsToWait.get(i);
			if ((actualCursor[0] == tcur1.getCursorID())
					&& (actualCursor[1] == tcur2.getCursorID())) {
				if ((time.getSystemTime().getSeconds()) == actualCursor[2]
						&& (actualCursor[3] == 0)) {
					actualCursor[3] = 1;
					System.out.println("<(^.^<) ^(^.^)^ (>^.^)>");
					return true;
				}
				counter++;
			} else {
				for (int j = 0; j < i; j++) {
					checkCursor = tuioCursorListForSecondsToWait.get(j);
					if (tcur1.getCursorID() == checkCursor[0]
							&& tcur2.getCursorID() == checkCursor[1]) {
						counter++;
					}
				}
			}
		}
		if (counter == 0) {
			setListElement(tcur1, tcur2, xseconds);
		}
		return false;
	}

	private void setListElement(TuioCursor tcur1, TuioCursor tcur2, int xseconds) {
		int a[] = new int[4];
		a[0] = tcur1.getCursorID();
		a[1] = tcur2.getCursorID();
		a[2] = (int) time.getSystemTime().getSeconds() + xseconds;
		a[3] = 0;
		tuioCursorListForSecondsToWait.add(a);
	}

	/**
	 * 
	 * @param tuioCursorList
	 *            (Liste der Cursor auf dem Feld)
	 * @param secondsTwoWait
	 *            (sekunden die gewartet werden m�ssen)
	 * @return boolean (retrun true, wenn 2 Cursor, in einer Zeitspanne von 2
	 *         Sekunden, nah beieinander liegen)
	 * 
	 */
	public Vector2f twoFingersInRange(Vector tuioCursorList,
			int secondsTwoWait) {

		TuioCursor tcur1, tcur2;
		Vector2f tcur;
		double range;
		// Die Entfernung von 2 Cursor zueinander vergleichen
		for (int i = 0; i < tuioCursorList.size(); i++) {
			tcur1 = (TuioCursor) tuioCursorList.elementAt(i);
			for (int j = i + 1; j < tuioCursorList.size(); j++) {
				if (tcur1 != tuioCursorList.lastElement()) {
					tcur2 = (TuioCursor) tuioCursorList.elementAt(j);
					if(bestimmeSpieler(tcur1)==bestimmeSpieler(tcur2)){
						range = getDistance(tcur1, tcur2);
						if (range <= 0.100) {
							// �berpr�ft ob XSekunden 2 Cursor nah beieinander sind
							if (waitXSeconds(secondsTwoWait, tcur1, tcur2)) {
								System.out.println("2 Finger erkannt");
								tcur= new Vector2f(tcur1.getScreenX(width),tcur1.getScreenY(height));
								return tcur;
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param tuioCursorList
	 *            (Liste der Cursor auf dem Feld)
	 * @return boolean (retrun true, wenn 3 Cursor, in einer Zeitspanne von 2
	 *         Sekunden, nah beieinander liegen)
	 * 
	 */
	public Vector2f threeFingersInRange(Vector tuioCursorList, int secondsTwoWait) {
		TuioCursor tcur1, tcur2, tcur3;
		double range1, range2, range3;
		Vector2f tcur;
		// Die Entfernung von 3 Cursor zueinander vergleichen
		for (int i = 0; i < tuioCursorList.size(); i++) {
			tcur1 = (TuioCursor) tuioCursorList.elementAt(i);
			for (int j = i + 1; j < tuioCursorList.size(); j++) {
				tcur2 = (TuioCursor) tuioCursorList.elementAt(j);
				for (int k = j + 1; k < tuioCursorList.size(); k++)
					if (tcur1 != tuioCursorList.lastElement()
							&& tcur2 != tuioCursorList.lastElement()) {
						tcur3 = (TuioCursor) tuioCursorList.elementAt(k);
						range1 = getDistance(tcur1, tcur2);
						range2 = getDistance(tcur1, tcur3);
						range3 = getDistance(tcur2, tcur3);
						// abfrage der Permutationen der Cursorpositionen
						if ((range1 <= 0.025 && (range2 <= 0.025 || range3 <= 0.025))
								|| (range2 <= 0.025 && (range1 <= 0.025 || range3 <= 0.025))
								|| (range3 <= 0.025 && (range1 <= 0.025 || range2 <= 0.025))) {
							System.out.println("3 Finger erkannt");
							if (waitXSeconds(secondsTwoWait, tcur1, tcur2) && waitXSeconds(secondsTwoWait, tcur1, tcur3) &&waitXSeconds(secondsTwoWait, tcur3, tcur2)){
								
							}
							tcur= new Vector2f(tcur1.getScreenX(width),tcur1.getScreenY(height));
							return tcur;
						}
					}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param tuioCursorList
	 *            (Liste der Cursor auf dem Feld)
	 * @return double (retrun 0.0, wenn sich der Abstand nicht ver�ndert hat
	 *         return positiven Wert, wenn sich 2 Cursor voneinander entfernen
	 *         return negativen Wert, wenn 2 Cursor ihren Abstand verringern )
	 * 
	 */
	public int[] rangeDifferent(Vector tuioCursorList) {
		TuioCursor tcur1, tcur2;
		int trailCoordinate[] = new int[4];
		double startRange = 0;
		double endRange = 0;
		double rangeDifferent = 0;
		double actualCursor[] = null;

		// Den Abstand von 2 Cursor besorgen
		for (int i = 0; i < tuioCursorList.size(); i++) {
			tcur1 = (TuioCursor) tuioCursorList.elementAt(i);
			for (int j = i + 1; j < tuioCursorList.size(); j++) {
					tcur2 = (TuioCursor) tuioCursorList.elementAt(j);
						endRange = getDistance(tcur1, tcur2);
						// Solange sich ein Cursor mit angemessener Geschwindigkeit
						// bewegt �berpr�fe die neuen Abstaende
						for (int k = 0; k<tuioCursorListOld.size();k++){
							
//							if(tuioCursorListOld.get(k).getTcur1Player1()){
								if ((tuioCursorListOld.get(k).getTcur1ID() == tcur1.getCursorID()&& tuioCursorListOld.get(k).getTcur2ID() == tcur2.getCursorID()) ||
										(tuioCursorListOld.get(k).getTcur1ID() == tcur2.getCursorID()&& tuioCursorListOld.get(k).getTcur2ID() == tcur1.getCursorID())){
								
									if(tuioCursorListOld.get(k).getTcur1Player1() == tuioCursorListOld.get(k).getTcur2Player1()){
										if (tcur1.getMotionSpeed() > 0.10){
											rangeDifferent = endRange - tuioCursorListOld.get(k).getDistance();
											if (rangeDifferent !=0){
												trailCoordinate[0]= tuioCursorListOld.get(k).getTcur1ScreenX();
												trailCoordinate[1]= tuioCursorListOld.get(k).getTcur1ScreenY();
												trailCoordinate[2]=tcur1.getScreenX(width);
												trailCoordinate[3]=tcur1.getScreenY(height);
												return trailCoordinate;
											}
										}else{
											if(tcur2.getMotionSpeed() > 0.10){
												trailCoordinate[0]= tuioCursorListOld.get(k).getTcur2ScreenX();
												trailCoordinate[1]= tuioCursorListOld.get(k).getTcur2ScreenY();
												trailCoordinate[2]=tcur2.getScreenX(width);
												trailCoordinate[3]=tcur2.getScreenY(height);
												return trailCoordinate;
											}
										}
									}									
								}
//							}else{
//								if ((tuioCursorListOld.get(k).getTcur1ID() == tcur1.getCursorID()&& tuioCursorListOld.get(k).getTcur2ID() == tcur2.getCursorID()) ||
//										(tuioCursorListOld.get(k).getTcur1ID() == tcur2.getCursorID()&& tuioCursorListOld.get(k).getTcur2ID() == tcur1.getCursorID())){
//									if (tcur1.getMotionSpeed() > 0.10
//											|| tcur2.getMotionSpeed() > 0.10) {
//										rangeDifferent = endRange - tuioCursorListOld.get(k).getDistance();
//										if (rangeDifferent !=0){
//											trailCoordinate[0]= tuioCursorListOld.get(k).getTcur1ScreenX();
//											trailCoordinate[1]= tuioCursorListOld.get(k).getTcur1ScreenY();
//											trailCoordinate[2]=tcur1.getScreenX(width);
//											trailCoordinate[3]=tcur1.getScreenY(height);
//											return trailCoordinate;
//										}
//									}
//								}
//							}
						}
			}
		}
		return null;
	}
	
	public int bestimmeSpieler(TuioCursor tcur){
		if (tcur.getX()<0.5){
			return 1;
		}else{
			return 2;
		}		
	}


	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
}