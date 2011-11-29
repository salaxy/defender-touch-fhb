package de.fhb.defenderTouch.menu;

import org.newdawn.slick.*;

public class GifLoader {

	private Image[] images;
	private Graphics graphics;
	private Animation ani;

	public GifLoader(String path, int number) {
		images = new Image[number];
		for (int i = 0; i < images.length; i++) {
			try {
				images[i] = new Image("data/" + path + "/" + (i + 1) + ".png");
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

	public int getNumberPictures() {
		return images.length;
	}
}
