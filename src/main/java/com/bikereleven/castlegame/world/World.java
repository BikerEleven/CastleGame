package com.bikereleven.castlegame.world;

import java.util.HashMap;
import java.util.UUID;

import com.bikereleven.castlegame.entity.Entity;
import com.bikereleven.castlegame.event.events.ShutdownEvent;
import com.bikereleven.castlegame.utility.Reference;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class World {

	private static HashMap<UUID, Entity> entities;
	private static HashMap<String, Double> resourceStore;

	// private static TimePiece time; //Currently the TimePiece is abstract so
	// keeping an instance is not needed.
	private static EventBus ebus;

	public static EventBus createDefaultEventBus() {
		ebus = new EventBus();
		return ebus;
	}

	public static EventBus getEventBus() {
		return (ebus == null ? createDefaultEventBus() : ebus);
	}

	private void loadWorld() {

	}

	private void saveWorld() {

	}

	private void newGame() {

	}

	private void init() {

		getEventBus().register(new EventHook());

	}

	public World() {

		init();

	}

	public void exit() {

	}

	private class EventHook {
		@Subscribe
		public void onDeadEvent(DeadEvent evt) {
			Reference.logger.warn("Encountered a dead event ({})", evt.getEvent());
		}
		
		@Subscribe
		public void onShutdownEvent(ShutdownEvent evt) {
			Reference.logger.info("Game is shuting down");
			exit();
		}
	}

}
