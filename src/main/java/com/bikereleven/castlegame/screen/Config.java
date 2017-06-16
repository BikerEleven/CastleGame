package com.bikereleven.castlegame.screen;

import java.awt.Graphics;
import java.util.List;

/**
 * Think of this class as the template for a screen the user sees. For example you can make a StartScreen that extends Config so when the 
 * @author Biker
 *
 */
public abstract class Config {
	
	private List<Part> parts;
	
	public void draw(Graphics g){
		
		for (Part p : parts){
			p.draw(g);
		}
		
	}
	
	/**
	 * This is where you add and setup your parts.
	 */
	public abstract void init();
	
	public Config(){
		init();
	}
	
}
