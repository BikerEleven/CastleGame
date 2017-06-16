package com.bikereleven.castlegame.event;

import com.google.common.eventbus.*;

public class Hook {
	
	private static EventBus ebus;
	
	public static EventBus createDefaultEventBus(){
		
		ebus = new EventBus();
		return ebus;
		
	}
	
	public static void addHook() {
		
	}
	
}
