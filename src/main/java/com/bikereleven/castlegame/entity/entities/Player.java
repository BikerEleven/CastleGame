package com.bikereleven.castlegame.entity.entities;

import com.bikereleven.castlegame.entity.Entity;
import com.bikereleven.castlegame.world.World;

public class Player extends Entity {
	
	@Override
	protected void onDeath() {
		
		World.getInstance().gameOver();//Dum dum dummmm!
		
	}
	
	@Override
	protected void onTakeDamage(int damage) {
		//Take damage sound
	}
	
	public Player(){
		super();
		
		name = "Player Name Here";
	}

}
