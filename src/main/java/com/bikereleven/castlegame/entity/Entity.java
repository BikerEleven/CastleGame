package com.bikereleven.castlegame.entity;

import static com.google.common.base.Preconditions.*;

import java.util.UUID;

import javax.annotation.Nullable;

import com.bikereleven.castlegame.event.events.EntityDeath;
import com.bikereleven.castlegame.utility.Reference;
import com.bikereleven.castlegame.world.World;
import com.google.errorprone.annotations.ForOverride;

public abstract class Entity {

	private UUID id;

	protected int health = 100;
	protected int maxHealth = 100;
	protected double armor = 0;
	protected double maxArmor = 0;
	protected boolean degradeArmor = true;

	protected String name = "Bob";

	@ForOverride
	protected abstract void onDeath();

	@ForOverride
	protected void onTakeDamage(int damage) {
	}

	public Entity(){
		id = UUID.randomUUID();
	}
	
	public UUID getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealth() {
		return health;
	}

	/**
	 * Sets the entities health. Health must be nonnegative and less than or
	 * equal to MaxHealth
	 * 
	 * @param health
	 *            Entity Health
	 * @see setMaxHealth
	 */
	public void setHealth(int health) {
		checkArgument(health >= 0 && health <= maxHealth, "0 <= Health(%s) <= max Health(%s)", health, maxHealth);
		this.health = health;
		if (this.health == 0)
			die(null);
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	/**
	 * Sets the entities max health. Max health must be greater than zero and
	 * less than or equal to 999999
	 * 
	 * @param health
	 *            Entity Max Health
	 * @see setHealth
	 */
	public void setMaxHealth(int maxHealth) {
		checkArgument(maxHealth > 0 && maxHealth <= 999999, "0 < MaxHealth(%s) <= 999999", maxHealth);
		this.maxHealth = maxHealth;
		if (maxHealth < health)
			health = maxHealth;
	}

	public double getArmor() {
		return armor;
	}

	/**
	 * Sets the entities armor. Armor must be nonnegative and less than or equal
	 * to max armor. 100 armor will negate all damage.
	 * 
	 * @param armor
	 *            Entity Armor
	 */
	public void setArmor(double armor) {
		checkArgument(armor >= 0 && armor <= maxArmor, "0 <= Armor(%s) <= Max Armor(%s)", armor, maxArmor);
		this.armor = armor;
	}

	public double getMaxArmor() {
		return maxArmor;
	}

	/**
	 * Sets the entities max armor. Armor must be nonnegative and less than or
	 * equal to 100. 100 armor will negate all damage.
	 * 
	 * @param armor
	 *            Entity max Armor
	 */
	public void setMaxArmor(double maxArmor) {
		checkArgument(maxArmor >= 0 && maxArmor <= 100, "0 <= Max Armor(%s) <= 100", maxArmor);
		this.maxArmor = maxArmor;
	}

	public void takeDamage(int damage, @Nullable String attacker) {
		checkArgument(damage >= 0, "0 <= Damage({})", damage);

		Reference.LOGGER.traceEntry("Entity {} processing {} damage", toString(), damage);
		damage -= (damage * (armor / 100));
		Reference.LOGGER.trace("Entity {} took {} damage", toString(), damage);
		
		if (degradeArmor && armor > 0) {
			armor -= (damage / maxHealth); //Experimental
			if (armor < 0) armor = 0;
			Reference.LOGGER.trace("Entity {} lost {}% armor", toString(), (damage / maxHealth));
		}

		if (damage >= health) {
			health = 0;
			die(attacker);
		} else {
			setHealth(health - damage);
		}

		Reference.LOGGER.traceExit();
	}

	public void die(@Nullable String cause) {
		World.getEventBus().post(new EntityDeath(id, this, cause));
		Reference.LOGGER.trace("Entity {} has died from {}", toString(), cause);
	}

	public String toString() {
		return id + ":" + name;
	}

}
