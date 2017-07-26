package com.bikereleven.castlegame.ui;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;
import javax.swing.Action;
import javax.swing.KeyStroke;

import com.google.common.base.Strings;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

/**
 * Think of this class as the template for a screen the user sees. For example
 * you can make a StartScreen that extends Config so when the
 * 
 * @author Biker
 *
 */
public abstract class Config {

	private ArrayList<HashMap<String, Part>> guiParts;
	private ArrayList<HashMap<String, Part>> effects;
	private ArrayList<HashMap<String, Part>> worldParts;

	private Map<String, KeyStroke> keyBinds;

	private Part hoverTrack = null;

	public void draw(Graphics g) {

		for (Map<String, Part> layer : worldParts) {
			for (Part p : layer.values()) {
				p.draw(g);
			}
		}

		for (Map<String, Part> layer : effects) {
			for (Part p : layer.values()) {
				p.draw(g);
			}
		}

		for (Map<String, Part> layer : guiParts) {
			for (Part p : layer.values()) {
				p.draw(g);
			}
		}

	}

	/**
	 * Creates a key bind on the window to capture user input
	 * 
	 * @param id
	 *            Unique ID of the keybind
	 * @param key
	 *            Keyboard key, use KeyMap to find value
	 * @param modf
	 *            Keyboard modifier key
	 * @param act
	 *            Action to be performed on keydown
	 */
	protected void bind(String id, int key, int modf, Action act) {
		checkArgument(!Strings.isNullOrEmpty(id), "Id is required");
		KeyStroke stroke = KeyStroke.getKeyStroke(key, modf);
		keyBinds.put(id, stroke);

		Screen.getInstance().addKeyBinding(stroke, checkNotNull(act, "The action can not be null"));
	}

	/**
	 * Unbinds a bound keybind using the provided Unique ID
	 * 
	 * @param id
	 *            The ID of an existing keybind
	 * @throws IllegalArgumentException
	 *             If the ID is not found
	 */
	protected void unbind(String id) {
		checkArgument(keyBinds.containsKey(id), "KeyBind id {%s} not found", id);
		Screen.getInstance().removeKeyBinding(keyBinds.remove(id));
	}

	/**
	 * Removes all keybinds, useful during cleanup
	 */
	protected void unbindKeys() {
		for (KeyStroke key : keyBinds.values()) {
			Screen.getInstance().removeKeyBinding(key);
		}

		keyBinds.clear();
	}

	/**
	 * This will add a Part to the GUI draw list. You can place parts in 1 of 2
	 * layers lower numbered layers draw above higher numbered layers.
	 * 
	 * @param part
	 *            Screen part to add
	 * @param id
	 *            Unique ID of the part. If null a random Unique ID will be
	 *            generated
	 * @param layer
	 *            The layer the part will draw in
	 * @return A generated Unique ID if null was passed in
	 */
	@CanIgnoreReturnValue
	protected String addPart(CATA cat, Part part, @Nullable String id, int layer) {
		if (id == null) {
			id = UUID.randomUUID().toString();
		} else {
			switch (cat) {
			case GUI:
				checkArgument(!guiParts.get(checkElementIndex(layer, guiParts.size())).containsKey(id),
						"You can not overwrite an existing part");
				break;
			case EFFECT:
				checkArgument(!effects.get(checkElementIndex(layer, effects.size())).containsKey(id),
						"You can not overwrite an existing part");
				break;
			case WORLD:
				checkArgument(!worldParts.get(checkElementIndex(layer, worldParts.size())).containsKey(id),
						"You can not overwrite an existing part");
				break;

			}
		}
		
		switch (cat) {
		case GUI:
			guiParts.get(checkElementIndex(layer, guiParts.size())).put(id, part);
			break;
		case EFFECT:
			effects.get(checkElementIndex(layer, effects.size())).put(id, part);
			break;
		case WORLD:
			worldParts.get(checkElementIndex(layer, worldParts.size())).put(id, part);
			break;

		}

		return id;
	}

	/**
	 * Removes a part from the draw list.
	 * 
	 * @param id
	 *            Unique ID of the part
	 * @param layer
	 *            The layer the part is on
	 */
	protected void removePart(CATA cat, String id, int layer) {
		switch (cat) {
		case GUI:
			checkArgument(guiParts.get(checkElementIndex(layer, guiParts.size())).containsKey(id), "Part id {%s} not found",
					id);
			guiParts.get(layer).remove(id);
			break;
		case EFFECT:
			checkArgument(effects.get(checkElementIndex(layer, effects.size())).containsKey(id), "Part id {%s} not found",
					id);
			effects.get(layer).remove(id);
			break;
		case WORLD:
			checkArgument(worldParts.get(checkElementIndex(layer, worldParts.size())).containsKey(id), "Part id {%s} not found",
					id);
			worldParts.get(layer).remove(id);
			break;

		}
	}
	
	/**
	 * This will get a part from the list with the provided keys
	 * @param cat Category of the part
	 * @param id Part unique id
	 * @param layer Part layer
	 * @return The specified part
	 */
	protected Part getPart(CATA cat, String id, int layer){
		checkArgument(!Strings.isNullOrEmpty(id), "ID must not be empty");
		switch (cat) {
		case GUI:
			return guiParts.get(checkElementIndex(layer, guiParts.size())).get(id);
		case EFFECT:
			return effects.get(checkElementIndex(layer, effects.size())).get(id);
		case WORLD:
			return worldParts.get(checkElementIndex(layer, worldParts.size())).get(id);
		default:
			return null;
		}
	}

	/**
	 * This is where you add and setup your parts.
	 */
	public abstract void init();

	public Config() {
		guiParts = new ArrayList<HashMap<String, Part>>();
		for (int i = 0; i < 2; i++) {
			guiParts.add(new HashMap<String, Part>());
		}

		effects = new ArrayList<HashMap<String, Part>>();
		for (int i = 0; i < 7; i++) {
			effects.add(new HashMap<String, Part>());
		}

		worldParts = new ArrayList<HashMap<String, Part>>();
		for (int i = 0; i < 7; i++) {
			worldParts.add(new HashMap<String, Part>());
		}

		keyBinds = new HashMap<String, KeyStroke>();

		Screen.getInstance().attachMouseAdapter(new MouseManager());

		init();
	}

	private Part getOverlap(Point pt) {

		Part find = null;

		for (Map<String, Part> layer : guiParts) {
			for (Part p : layer.values()) {
				if (p.isInteractive()) {
					if (pt.getX() >= p.x && pt.getX() <= p.x + p.width()) {
						if (pt.getY() >= p.y - p.height() && pt.getY() <= p.y) {
							find = p;
							break;
						}
					}
				}
			}
		}

		if (find == null) {
			for (Map<String, Part> layer : effects) {
				for (Part p : layer.values()) {
					if (p.isInteractive()) {
						if (pt.getX() >= p.x && pt.getX() <= p.x + p.width()) {
							if (pt.getY() >= p.y - p.height() && pt.getY() <= p.y) {
								find = p;
								break;
							}
						}
					}
				}
			}
		}

		return find;
	}

	public enum CATA {
		GUI, EFFECT, WORLD
	}

	private class MouseManager extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent evt) {
			Part clickedPart = getOverlap(evt.getPoint());
			if (clickedPart != null) {
				clickedPart.onClick();
			}
		}

		@Override
		public void mouseMoved(MouseEvent evt) {
			Part hoverPart = getOverlap(evt.getPoint());
			if (hoverPart != null && hoverTrack != hoverPart) {
				hoverPart.onHover();
			} else if (hoverTrack != null && hoverTrack != hoverPart) {
				hoverTrack.onDeHover();
				hoverTrack = null;
			}
		}

	}

}
