package com.bikereleven.castlegame.event.events;

import java.util.UUID;

import com.bikereleven.castlegame.entity.Entity;
import com.google.common.base.Strings;

public class EntityDeath {
	
	private UUID id;
	private Entity ent;
	private String cause;
	
	public EntityDeath(UUID id, Entity ent,String cause){
		this.id = id;
		this.ent = ent;
		this.cause = Strings.emptyToNull(cause.trim());
	}
	
	public UUID getID() {
		return id;
	}
	
	public Entity getEntity(){
		return ent;
	}
	
	public String getCause() {
		return cause;
	}
	
}
