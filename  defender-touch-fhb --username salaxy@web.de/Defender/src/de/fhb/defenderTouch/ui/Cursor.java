package de.fhb.defenderTouch.ui;

public class Cursor {
	private int tcur1ID;	
	private int tcur2ID;
	private double distance;
	private int tcur1ScreenX;
	private int tcur1ScreenY;
	private int tcur2ScreenX;	
	private int tcur2ScreenY;
	private boolean tcur1Player1;
	private boolean tcur2Player1;
	
	public Cursor(int tcur1ID,int tcur1ScreenX,int tcur1ScreenY){
		this.tcur1ID = tcur1ID;
		this.tcur2ID = 0;
		this.distance = 0;
		this.tcur1ScreenX = tcur1ScreenX;
		this.tcur1ScreenY = tcur1ScreenY;
		this.tcur2ScreenX = 0;
		this.tcur2ScreenY = 0;	
		this.tcur1Player1 = setPlayer(tcur1ScreenX);
		this.tcur2Player1 = setPlayer(tcur2ScreenX);
	}
	
	public Cursor(int tcur1ID,int tcur2ID,double distance,int tcur1ScreenX,int tcur1ScreenY,int tcur2ScreenX, int tcur2ScreenY){
		this.tcur1ID = tcur1ID;
		this.tcur2ID = tcur2ID;
		this.distance = distance;
		this.tcur1ScreenX = tcur1ScreenX;
		this.tcur1ScreenY = tcur1ScreenY;
		this.tcur2ScreenX = tcur2ScreenX;
		this.tcur2ScreenY = tcur2ScreenY;		
		this.tcur1Player1 = setPlayer(tcur1ScreenX);
		this.tcur2Player1 = setPlayer(tcur2ScreenX);
		
	}
	
	private boolean setPlayer(int tcur){
		if(tcur<512){
			return true;
		}
		return false;
	}	
	
	public int getTcur1ID() {
		return tcur1ID;
	}
	public void setTcur1ID(int tcur1id) {
		tcur1ID = tcur1id;
	}
	public int getTcur2ID() {
		return tcur2ID;
	}
	public void setTcur2ID(int tcur2id) {
		tcur2ID = tcur2id;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getTcur1ScreenX() {
		return tcur1ScreenX;
	}
	public void setTcur1ScreenX(int tcur1ScreenX) {
		this.tcur1ScreenX = tcur1ScreenX;
	}
	public int getTcur1ScreenY() {
		return tcur1ScreenY;
	}
	public void setTcur1ScreenY(int tcur1ScreenY) {
		this.tcur1ScreenY = tcur1ScreenY;
	}
	public int getTcur2ScreenX() {
		return tcur2ScreenX;
	}
	public void setTcur2ScreenX(int tcur2ScreenX) {
		this.tcur2ScreenX = tcur2ScreenX;
	}
	public int getTcur2ScreenY() {
		return tcur2ScreenY;
	}
	public void setTcur2ScreenY(int tcur2ScreenY) {
		this.tcur2ScreenY = tcur2ScreenY;	
	}
	public boolean getTcur1Player1() {
		return tcur1Player1;
	}
	public void setTcur1Player1(boolean tcur1Player1) {
		this.tcur1Player1 = tcur1Player1;
	}
	public boolean getTcur2Player1() {
		return tcur2Player1;
	}
	public void setTcur2Player1(boolean tcur2Player1) {
		this.tcur2Player1 = tcur2Player1;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
}
