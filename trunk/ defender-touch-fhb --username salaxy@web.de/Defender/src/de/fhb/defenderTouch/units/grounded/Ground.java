package de.fhb.defenderTouch.units.grounded;

import processing.core.PApplet;

public class Ground extends Building {

	public static final int PRICE = 50;

	public Ground(int x, int y, int mode, int playerID, PApplet disp) {
		super(x, y, mode, playerID, disp);
		// TODO Auto-generated constructor stub
	}

	public void drawFigure() {

		// display.stroke(0);
		// display.strokeWeight(10);
		// display.fill(0);
		display.noFill();
		display.scale(2);
//		display.rotate((float) Math.PI);
		this.entscheideLineFarbe();
//		display.scale(1);
		display.triangle(-5, +5, 0, -5, +5, +5);

		display.resetMatrix();
		display.stroke(0);

	}

}
