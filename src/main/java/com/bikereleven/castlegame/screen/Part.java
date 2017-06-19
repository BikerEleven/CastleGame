package com.bikereleven.castlegame.screen;

import java.awt.Graphics;

import static com.google.common.base.Preconditions.*;

/**
 * This class represents a template for an object that can be drawn to the
 * screen
 * 
 * @author Biker
 *
 */
public abstract class Part {

	protected int x, y = 0;
	protected int w, h = 0;

	protected boolean interactive = false;

	/**
	 * This function checks to see if the object intersects with ours
	 * @param g
	 * @return
	 */
	protected boolean intersects(Graphics g){
		boolean inter = false;
		
		
		
		return inter;
	}
	
	/**
	 * It's our turn for the part to draw itself onto the screen
	 * 
	 * @param g
	 *            Graphics object to draw to
	 */
	public abstract void draw(Graphics g);

	/**
	 * Returns weather or not our part is interactive (clickable)
	 * 
	 * @return True if the part is clickable
	 */
	public boolean isInteractive() {
		return interactive;
	}

	/**
	 * Return the x position of the part
	 * 
	 * @return int X pos
	 */
	public int x() {
		return x;
	}

	/**
	 * Sets the x position of the part, must be nonnegative
	 * 
	 * @param x
	 *            X screen Position
	 * @return The part so you can chain the function
	 */
	public Part setX(int x) {
		checkArgument(x >= 0, "Argument was %s but expected nonnegative", x);
		this.x = x;
		return this;
	}

	/**
	 * Return the y position of the part
	 * 
	 * @return int Y pos
	 */
	public int y() {
		return y;
	}

	/**
	 * Sets the y position of the part, must be nonnegative
	 * 
	 * @param y
	 *            Y screen Position
	 * @return The part so you can chain the function
	 */
	public Part setY(int y) {
		checkArgument(x >= 0, "Argument was %s but expected nonnegative", y);
		this.y = y;
		return this;
	}

	/**
	 * Return the width of the part
	 * 
	 * @return int Width
	 */
	public int width() {
		return w;
	}

	/**
	 * Sets the width of the part, must be nonnegative
	 * 
	 * @param w
	 *            Width of part
	 * @return The part so you can chain the function
	 */
	public Part setWidth(int w) {
		checkArgument(x >= 0, "Argument was %s but expected nonnegative", w);
		this.w = w;
		return this;
	}

	/**
	 * Return the x position of the part
	 * 
	 * @return int Height
	 */
	public int height() {
		return h;
	}

	/**
	 * Sets the Height of the part, must be nonnegative
	 * 
	 * @param h
	 *            Height of part
	 * @return The part so you can chain the function
	 */
	public Part setHeight(int h) {
		checkArgument(x >= 0, "Argument was %s but expected nonnegative", h);
		this.h = h;
		return this;
	}

	/**
	 * Sends a click event as if a player did it if our part is interactive
	 */
	public void click() {
		if (interactive) {
			onClick();
		}
	}

	/**
	 * Called whenever our part is clicked
	 */
	public void onClick() {
	} // Does nothing!
	
	public void onHover() {
	}
	
	public void onDeHover(){
	}

	@Override
	public String toString() {
		return "Part [x=" + x + ", y=" + y + ", w=" + w + ", h=" + h + ", interactive=" + interactive + "]";
	}

}
