package com.bikereleven.castlegame.utility;

import org.apache.logging.log4j.Logger;

import java.awt.Dimension;

import org.apache.logging.log4j.LogManager;

public abstract class Reference {
	
	public static final Logger LOGGER = LogManager.getLogger();
	public static String LOCALIZATION = "en"; //TODO: Eventually save this in a config localization
	public static Dimension RESOLUTION = new Dimension(800, 600);

}
