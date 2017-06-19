package com.bikereleven.castlegame;

import com.bikereleven.castlegame.utility.Reference;

public class CastleGame {
	
	public static void main(String[] args){
		
		//TODO: Build a game
		
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
