package com.bikereleven.castlegame;

import org.junit.Test;

import com.bikereleven.castlegame.screen.TextLoader;

import static org.junit.Assert.*;

public class TestCastleGame {
	
	@Test public void docLoader(){
		
		assertEquals("Failed DocLoaderTest", "Castleprenuer", TextLoader.request("screen.window.title"));
		
	}
	
	@Test public void docLoader2(){
		
		assertEquals("Failed DocLoaderTest", "Welcome to Castleprenuer!", TextLoader.request("screen.config.mainmenu.start"));
		
	}
	
    /*
    @Test public void initTest() { //Test to see if the game survives to init
        //Implement me!
    }
    
    @Test public void intoGameTest() { //Test to make sure we get into the game proper
       //Implement me!
    }
    
    @Test public void shutdownTest() { //Test to ensure it shuts down gracefully
        //Implement me!
    }*/
}