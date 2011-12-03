package de.fhb.defenderTouch.audio;

public class FormatProblemException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Konstruktor*/
	public FormatProblemException (){ super("Format Problem");}
	/** Konstruktor mit Parameter*/
	public FormatProblemException (String s){super(s);}

}