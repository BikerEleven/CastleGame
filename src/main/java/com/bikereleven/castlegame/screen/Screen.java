package com.bikereleven.castlegame.screen;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import com.bikereleven.castlegame.utility.Reference;

/**
 * This class will manage our screen and delegate the drawing of it's parts
 * @author Biker
 *
 */
public class Screen implements Runnable {
	
	private static Graphics generalGraphics;
	private static Config loadedConfig;
	
	private static JFrame appWindow;
	
	private long lastTime = 0L;
	private long unprocessedSeconds = 0L;
	private int tickCount = 0;
	private int frames = 0;
	
	private void render(){
		
		loadedConfig.draw(appWindow.getContentPane().getGraphics());
		
		//TODO: add click and hover logic
		
		
	}
	
	private void init() {
		
		appWindow = new JFrame(TextLoader.request("screen.window.title"));
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int px = (int) ((dim.getWidth() / 2.0) - (Reference.RESOLUTION.getWidth() / 2));
		int py = (int) ((dim.getHeight() / 2.0) - (Reference.RESOLUTION.getHeight() / 2));
		appWindow.setBounds(px, py, 800, 600);
		
		
		
		generalGraphics = appWindow.getGraphics();
		
	}
	
	public static Graphics getGraphicsContext(){
		return generalGraphics;
	}
	
	public Screen(){
		
		init();
		
	}
	
	/**
	 * This will attempt to bind an action to a key or key combination.
	 * @param key KeyStroke to register you <b>MUST</b> retain this object if you wish to unbind the KeyStroke later
	 * @param act The Abstract action to be executed when the keybind is pressed.
	 */
	public static void addKeyBinding(KeyStroke key, Action act){
		((JComponent) appWindow.getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(key, key);
		((JComponent) appWindow.getContentPane()).getActionMap().put(key, act);
	}
	
	/**
	 * This will attempt to unbind a previously bound KeyStroke from the component
	 * @param key The KeyStoke object that was used to register the bind
	 */
	public static void removeKeyBinding(KeyStroke key){
		((JComponent) appWindow.getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).remove(key);
		((JComponent) appWindow.getContentPane()).getActionMap().remove(key);
	}
	
	@Override
	public void run() {
		
		while (appWindow.isDisplayable()){
			long now = System.nanoTime();
	        long passedTime = now - lastTime;
	        lastTime = now;
	        if (passedTime < 0) passedTime = 0;
	        if (passedTime > 100000000) passedTime = 100000000; //0.1 second in nanoseconds

	        unprocessedSeconds += passedTime / 1000000000.0;

	        boolean rendered = false;
	        while (unprocessedSeconds > 50000000) {
	        	render();
	            frames++;
	            unprocessedSeconds -= 50000000;
	            rendered = true;

	            if (++tickCount % 60 == 0) {
	            	if (frames < 55) Reference.LOGGER.trace("Loged low framerate {}", frames);
	                lastTime += 1000;
	                frames = 0;
	            }
	        }

	        if (!rendered) {
	            try {
	                Thread.sleep(16);
	            } catch (InterruptedException err) {
	                Reference.LOGGER.error(err);
	            }
	        }
		}
		
	}
	
}
