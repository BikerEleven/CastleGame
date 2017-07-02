package com.bikereleven.castlegame.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import com.bikereleven.castlegame.utility.Reference;
import com.bikereleven.castlegame.world.World;

/**
 * This class will manage our screen and delegate the drawing of it's parts
 * @author Biker
 *
 */
public class Screen implements Runnable {
	
	private static Graphics generalGraphics;
	private static Config loadedConfig;
	
	private static Screen instance;
	
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
	
	private Screen(){
		
		init();
		
	}
	
	public static Screen getInstance(){
		if (instance == null) createScreen();
		return instance;
	}
	
	public static Screen createScreen(){
		if (instance == null) instance = new Screen();
		return instance;
	}

	public static Graphics getGraphicsContext(){
		return generalGraphics;
	}
	
	/**
	 * This will attempt to bind an action to a key or key combination.
	 * @param key KeyStroke to register you <b>MUST</b> retain this object if you wish to unbind the KeyStroke later
	 * @param act The Abstract action to be executed when the keybind is pressed.
	 */
	public void addKeyBinding(KeyStroke key, Action act){
		((JComponent) appWindow.getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(key, key);
		((JComponent) appWindow.getContentPane()).getActionMap().put(key, act);
	}
	
	/**
	 * This will attempt to unbind a previously bound KeyStroke from the component
	 * @param key The KeyStoke object that was used to register the bind
	 */
	public void removeKeyBinding(KeyStroke key){
		((JComponent) appWindow.getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).remove(key);
		((JComponent) appWindow.getContentPane()).getActionMap().remove(key);
	}
	
	public void tick(){
		
	}
	
	@Override
	public void run() {
		
		
		
	}
	
}
