package com.bikereleven.castlegame.entity.entities;

import java.util.UUID;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.*;

import com.bikereleven.castlegame.entity.Entity;
import com.bikereleven.castlegame.ui.TextLoader;

public class NPC extends Entity {

	protected Type npcType = Type.NULL;
	protected int level = 0;
	protected UUID home;
	protected UUID work;
	
	@Override
	protected void onDeath() {
		
		//NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO

	}
	
	public NPC(){
		super();
		
		name = TextLoader.request("entity.npc.NULL.name");
	}
	
	public Type getNpcType() {
		return npcType;
	}

	public void setNpcType(Type npcType) {
		this.npcType = checkNotNull(npcType);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		checkArgument(level >= 0 && level <= 10, "0 <= Level <= 10");
		this.level = level;
	}

	public UUID getHome() {
		return home;
	}

	public void setHome(@Nullable UUID home) {
		this.home = home;
	}

	public UUID getWork() {
		return work;
	}

	public void setWork(@Nullable UUID work) {
		this.work = work;
	}

	public enum Type {
		PHYSICIST,
		CHEMIST,
		BLACKSMITH,
		SPOTTER,
		ACCOUNTANT,
		MATHEMATICIAN,
		NULL
	}

}
