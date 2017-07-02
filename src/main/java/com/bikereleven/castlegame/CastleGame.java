package com.bikereleven.castlegame;

import com.bikereleven.castlegame.ui.Screen;
import com.bikereleven.castlegame.utility.Reference;
import com.bikereleven.castlegame.world.World;

public class CastleGame {
	
	private static boolean runLoop = false;
	
	public static void main(String[] args){
		
		//TODO: Build a game
		
		init();
		gameLoop();
		
	}
	
	private static void init() {

		// Init Modules

		World.createWorld();
		Screen.createScreen();
		//Sound.init(); //Might toss this into screen

		// End init

		runLoop = true;

	}
	
	private static void gameLoop(){
		
		int frames = 0;

		double unprocessedSeconds = 0;
		long lastTime = System.nanoTime();
		double secondsPerTick = 1 / 20.0; //Run the world 20 times a second
		int tickCount = 0;

		while (runLoop) {
			long now = System.nanoTime();
			long passedTime = now - lastTime;
			lastTime = now;
			if (passedTime < 0)
				passedTime = 0;
			if (passedTime > (10^8))
				passedTime = 10^8;

			unprocessedSeconds += passedTime / 1.0 * (10^9);

			boolean ticked = false;
			while (unprocessedSeconds > secondsPerTick) {
				World.getInstance().tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				
				if (++tickCount % 60 == 0) {
	            	if (frames < 55) Reference.LOGGER.trace("Loged low framerate {}", frames);
	                lastTime += 1000;
	                frames = 0;
	            }
				
			}

			if (ticked) { //We really only need to render new things whenever something updates
				Screen.getInstance().tick();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	/**
	 * Will figure out the location of the data file and return the file
	 * @return The data file as a File 
	 */
	public static java.io.File getDataFile() {
		return new java.io.File(getSettingsPath(), "CastleGame.dat");
	}

	/**
	 * Gets the location of this application's data folder on the system, Path may not exists
	 * @return Path to the data folder
	 */
	public static String getSettingsPath() {

		String profilePath;

		String OS = System.getProperty("os.name").toLowerCase();
		if ((OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0)) { // Unix|Linux
			profilePath = "/home/" + System.getProperty("user.name") + "/.BikerEleven/CastleGame/";
		} else if (OS.indexOf("win") >= 0) { //Windows
			profilePath = System.getenv("APPDATA") + "\\BikerEleven\\CastleGame\\";
		} else { //Unknown
			profilePath = java.io.File.separator + "BikerEleven" + java.io.File.separator + "CastleGame" + java.io.File.separator;
		}

		return profilePath;

	}

	/**
	 * This will display an error message to the user and allow them to easily copy the message.
	 * @param parent Parent GUI component to attach the message to
	 * @param message The error message to display to the user
	 */
	public static void showErrorMessage(java.awt.Component parent, String message) {

		javax.swing.JTextField errorText = new javax.swing.JTextField();
		errorText.setEditable(false);
		errorText.setText(message);

		javax.swing.JOptionPane.showMessageDialog(parent, errorText, "An Error was encountered", javax.swing.JOptionPane.ERROR_MESSAGE);
		Reference.LOGGER.error(message);

	}
	
}
