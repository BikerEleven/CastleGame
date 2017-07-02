package com.bikereleven.castlegame.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.swing.Action;
import javax.swing.KeyStroke;

import com.google.common.base.Strings;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import static com.google.common.base.Preconditions.*;

/**
 * Think of this class as the template for a screen the user sees. For example you can make a StartScreen that extends Config so when the 
 * @author Biker
 *
 */
public abstract class Config {
	
	private ArrayList<HashMap<String, Part>> guiParts;
	private ArrayList<HashMap<String, Part>> effects;
	private ArrayList<HashMap<String, Part>> worldParts;
	
	private Map<String, KeyStroke> keyBinds;
	
	public void draw(Graphics g){
		
		for (Map<String, Part> layer : worldParts){
			for (Part p : layer.values()){
				p.draw(g);
			}
		}
		
		for (Map<String, Part> layer : effects){
			for (Part p : layer.values()){
				p.draw(g);
			}
		}
		
		for (Map<String, Part> layer : guiParts){
			for (Part p : layer.values()){
				p.draw(g);
			}
		}
		
	}
	
	/**
	 * Creates a key bind on the window to capture user input
	 * @param id Unique ID of the keybind
	 * @param key Keyboard key, use KeyMap to find value
	 * @param modf Keyboard modifier key
	 * @param act Action to be performed on keydown
	 */
	protected void bind(String id, int key, int modf, Action act){
		checkArgument(!Strings.isNullOrEmpty(id), "Id is required");
		KeyStroke stroke = KeyStroke.getKeyStroke(key, modf);
		keyBinds.put(id, stroke);
		
		Screen.getInstance().addKeyBinding(stroke, checkNotNull(act, "The action can not be null"));
	}
	
	/**
	 * Unbinds a bound keybind using the provided Unique ID
	 * @param id The ID of an existing keybind
	 * @throws IllegalArgumentException If the ID is not found
	 */
	protected void unbind(String id){
		checkArgument(keyBinds.containsKey(id), "KeyBind id {%s} not found", id);
		Screen.getInstance().removeKeyBinding(keyBinds.remove(id));
	}
	
	/**
	 * Removes all keybinds, useful during cleanup
	 */
	protected void unbindKeys(){
		for (KeyStroke key : keyBinds.values()){
			Screen.getInstance().removeKeyBinding(key);
		}
		
		keyBinds.clear();
	}
	
	/**
	 * This will add a Part to the GUI draw list. You can place parts in 1 of 2 layers lower numbered layers draw above higher numbered layers.
	 * @param part Screen part to add
	 * @param id Unique ID of the part. If null a random Unique ID will be generated
	 * @param layer The layer the part will draw in
	 * @return A generated Unique ID if null was passed in
	 */
	@CanIgnoreReturnValue
	protected String addGUIPart(Part part, @Nullable String id, int layer){
		if (id == null){
			id = UUID.randomUUID().toString();
		} else {
			checkArgument(!guiParts.get(checkElementIndex(layer, guiParts.size())).containsKey(id), "You can not overwrite an existing part");
		}
		guiParts.get(checkElementIndex(layer, guiParts.size())).put(id, part);
		
		return id;
	}
	
	/**
	 * Removes a part from the draw list.
	 * @param id Unique ID of the part
	 * @param layer The layer the part is on
	 */
	protected void removeGUIPart(String id, int layer){
		checkArgument(guiParts.get(checkElementIndex(layer, guiParts.size())).containsKey(id), "Part id {%s} not found", id);
		guiParts.get(layer).remove(id);
	}
	
	/**
	 * This will add a Part to the Effects draw list. You can place parts in 1 of 7 layers lower numbered layers draw above higher numbered layers.
	 * @param part Screen part to add
	 * @param id Unique ID of the part. If null a random Unique ID will be generated
	 * @param layer The layer the part will draw in
	 * @return A generated Unique ID if null was passed in
	 */
	protected String addEffectPart(Part part, @Nullable String id, int layer){
		if (id == null){
			id = UUID.randomUUID().toString();
		} else {
			checkArgument(!effects.get(checkElementIndex(layer, effects.size())).containsKey(id), "You can not overwrite an existing part");
		}
		effects.get(checkElementIndex(layer, effects.size())).put(id, part);
		
		return id;
	}
	
	protected void removeEffecPart(String id, int layer){
		checkArgument(effects.get(checkElementIndex(layer, effects.size())).containsKey(id), "Part id {%s} not found", id);
		effects.get(layer).remove(id);
	}
	
	/**
	 * This will add a Part to the World draw list. You can place parts in 1 of 7 layers lower numbered layers draw above higher numbered layers.
	 * @param part Screen part to add
	 * @param id Unique ID of the part. If null a random Unique ID will be generated
	 * @param layer The layer the part will draw in
	 * @return A generated Unique ID if null was passed in
	 */
	protected String addWorldPart(Part part, @Nullable String id, int layer){
		if (id == null){
			id = UUID.randomUUID().toString();
		} else {
			checkArgument(!worldParts.get(checkElementIndex(layer, worldParts.size())).containsKey(id), "You can not overwrite an existing part");
		}
		worldParts.get(checkElementIndex(layer, worldParts.size())).put(id, part);
		
		return id;
	}
	
	protected void removeWorldPart(String id, int layer){
		checkArgument(guiParts.get(checkElementIndex(layer, guiParts.size())).containsKey(id), "Part id {%s} not found", id);
		guiParts.get(layer).remove(id);
	}
	
	/**
	 * This is where you add and setup your parts.
	 */
	public abstract void init();
	
	public Config(){
		guiParts = new ArrayList<HashMap<String, Part>>();
		for (int i = 0; i < 2; i++){
			guiParts.add(new HashMap<String, Part>());
		}
		
		effects = new ArrayList<HashMap<String, Part>>();
		for (int i = 0; i < 7; i++){
			effects.add(new HashMap<String, Part>());
		}
		
		worldParts = new ArrayList<HashMap<String, Part>>();
		for (int i = 0; i < 7; i++){
			worldParts.add(new HashMap<String, Part>());
		}
		
		keyBinds = new HashMap<String, KeyStroke>();
		
		init();
	}
	
}
