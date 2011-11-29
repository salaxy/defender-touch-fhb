package de.fhb.defenderTouch.menu;

import org.newdawn.slick.*;

public class GifLoader {

	private Image[] images = new Image[17];
	private Graphics graphics;
	private Animation ani;

	public GifLoader() {

		for (int i = 0; i < images.length; i++) {
			try {
				images[i] = new Image("data/small explosion/" + (i + 1) + ".png");
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}

		ani = new Animation(images, 50);
	}

	public Animation getAni() {
		return ani;
	}

	public Graphics getGraphics() {
		return graphics;
	}

	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}
}
