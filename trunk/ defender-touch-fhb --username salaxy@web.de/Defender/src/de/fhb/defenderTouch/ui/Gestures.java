package de.fhb.defenderTouch.ui;

//package uniteFollowLine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import TUIO.TuioClient;
import TUIO.TuioCursor;
import TUIO.TuioTime;

public class Gestures {

	// Variablen zum überprüfen von Zeiteinheiten
//	private boolean timeFlag = false;
	private ArrayList<double[]> tuioCursorListOld = new ArrayList<double[]>();
	private ArrayList<int[]> tuioCursorListForSecondsToWait = new ArrayList<int[]>();
	private int zaehler = 15;

	TuioTime time;

	TuioClient tuioClient;

	public Gestures(TuioClient tuioClient) {
		this.tuioClient = tuioClient;

	}

	public void countFrames() {

		TuioCursor tcur1, tcur2;

		Vector tuioCursorList = tuioClient.getTuioCursors(); // gets all cursors
																// (fingers)
																// which are
																// currently on
																// the screen

		// alle X Frames aktuallisieren;
		if (zaehler == 15) {
			double a[] = new double[3];

			tuioCursorListOld.clear();
			for (int i = 0; i < tuioCursorList.size(); i++) {
				for (int j = i + 1; j < tuioCursorList.size(); j++) {
					tcur1 = (TuioCursor) tuioCursorList.elementAt(i);
					tcur2 = (TuioCursor) tuioCursorList.elementAt(j);
					a[0] = tcur1.getCursorID();
					a[1] = tcur2.getCursorID();
					a[2] = getDistance(tcur1, tcur2);
					tuioCursorListOld.add(a);
				}

			}
			zaehler = 0;
		}
		zaehler++;

		twoFingersInRange(tuioCursorList, 2);
		rangeDifferent(tuioCursorList);

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

	public void setListElement(TuioCursor tcur1, TuioCursor tcur2, int xseconds) {
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
	 *            (sekunden die gewartet werden müssen)
	 * @return boolean (retrun true, wenn 2 Cursor, in einer Zeitspanne von 2
	 *         Sekunden, nah beieinander liegen)
	 * 
	 */
	public boolean twoFingersInRange(Vector tuioCursorList,
			int secondsTwoWait) {
		TuioCursor tcur1, tcur2;
		TuioCursor tcur[] = new TuioCursor[2];
		double range;
		// Die Entfernung von 3 Cursor zueinander vergleichen
		for (int i = 0; i < tuioCursorList.size(); i++) {
			tcur1 = (TuioCursor) tuioCursorList.elementAt(i);
			for (int j = i + 1; j < tuioCursorList.size(); j++) {
				if (tcur1 != tuioCursorList.lastElement()) {
					tcur2 = (TuioCursor) tuioCursorList.elementAt(j);
					range = getDistance(tcur1, tcur2);
					if (range <= 0.100) {
						// überprüft ob XSekunden 2 Cursor nah beieinander sind
						if (waitXSeconds(secondsTwoWait, tcur1, tcur2)) {
							System.out.println("2 Finger erkannt");
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
	 *            (Liste der Cursor auf dem Feld)
	 * @return boolean (retrun true, wenn 3 Cursor, in einer Zeitspanne von 2
	 *         Sekunden, nah beieinander liegen)
	 * 
	 */
	public void threeFingersInRange(Vector tuioCursorList) {
		TuioCursor tcur1, tcur2, tcur3;
		double range1, range2, range3;
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
						}
					}
			}
		}
	}

	/**
	 * 
	 * @param tuioCursorList
	 *            (Liste der Cursor auf dem Feld)
	 * @return double (retrun 0.0, wenn sich der Abstand nicht verändert hat
	 *         return positiven Wert, wenn sich 2 Cursor voneinander entfernen
	 *         return negativen Wert, wenn 2 Cursor ihren Abstand verringern )
	 * 
	 */
	public double rangeDifferent(Vector tuioCursorList) {
		TuioCursor tcur1, tcur2;
		double startRange = 0;
		double endRange = 0;
		double rangeDifferent = 0;
		double actualCursor[] = null;

		// Den Abstand von 2 Cursor besorgen
		for (int i = 0; i < tuioCursorList.size(); i++) {
			tcur1 = (TuioCursor) tuioCursorList.elementAt(i);
			for (int j = i + 1; j < tuioCursorList.size(); j++) {
				if (tcur1 != tuioCursorList.lastElement()) {
					tcur2 = (TuioCursor) tuioCursorList.elementAt(j);
					for (Iterator<double[]> k = tuioCursorListOld.iterator(); k
							.hasNext();) {
						actualCursor = k.next();
						if (actualCursor[0] == tcur1.getCursorID()
								&& actualCursor[1] == tcur2.getCursorID()) {
							startRange = actualCursor[2];
						}
					}
					endRange = getDistance(tcur1, tcur2);
					// Solange sich ein Cursor mit angemessener Geschwindigkeit
					// bewegt überprüfe die neuen Abstaende
					if (tcur1.getMotionSpeed() > 0.10
							|| tcur2.getMotionSpeed() > 0.10) {
						rangeDifferent = endRange - startRange;
						if (rangeDifferent < 0)
							System.out.println("decreased");
						if (rangeDifferent > 0)
							System.out.println("increased");
						if (rangeDifferent != 0) {
							return rangeDifferent;
						}
					}
				}
			}
		}
		return rangeDifferent;
	}
}
