package com.bikereleven.castlegame.ui.parts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.bikereleven.castlegame.ui.Part;
import com.bikereleven.castlegame.ui.Screen;
import com.bikereleven.castlegame.utility.Reference;
import com.google.common.base.Strings;
import static com.google.common.base.Preconditions.*;

public class Text extends Part {

	private String value;
	private Font stringFont;

	private Color color;

	private float size = -1;

	@Override
	public void draw(Graphics g) {

		if (color != null) {
			g.setColor(color);
		}

		if (stringFont != null) {
			g.setFont(stringFont);
		} else if (size > 0) {
			stringFont = g.getFont().deriveFont(size);
			g.setFont(stringFont);
		}

		g.drawString(value, x, y);
	}
	
	public Text(String content) {
		checkArgument(!Strings.isNullOrEmpty(content), "The text must not be null or empty");
		value = content;
	}

	public Text(String content, float size) {
		checkArgument(!Strings.isNullOrEmpty(content), "The text must not be null or empty");
		checkArgument(size > 0, "Size (%s) must be greater than zero", size);

		value = content;
		this.size = size;
	}

	public Text(String content, float size, Color color) {
		checkArgument(!Strings.isNullOrEmpty(content), "The text must not be null or empty");
		checkArgument(size > 0, "Size (%s) must be greater than zero", size);
		value = content;
		this.size = size;
		this.color = checkNotNull(color);
	}

	public Text(String content, Font font) {
		checkArgument(!Strings.isNullOrEmpty(content), "The text must not be null or empty");
		value = content;
		stringFont = checkNotNull(font);
	}

	public Text(String content, Font font, Color color) {
		checkArgument(!Strings.isNullOrEmpty(content), "The text must not be null or empty");
		value = content;
		stringFont = checkNotNull(font);
		this.color = checkNotNull(color);
	}

	public Part setSize(float size) {
		checkArgument(size > 0, "Argument (%s) must be greater than zero", size);

		if (stringFont != null) {
			stringFont = stringFont.deriveFont(size);
		}

		this.size = size;
		
		return this;
	}
	
	public String getText(){
		return value;
	}
	
	public Text setText(String text){
		checkArgument(!Strings.isNullOrEmpty(text), "The text must not be null or empty");
		value = text;
		return this;
	}

	public Text setColor(Color color) {
		this.color = checkNotNull(color);
		return this;
	}
	
	@Override
	public int width() {
		if (stringFont != null){
			return Screen.getGraphicsContext().getFontMetrics(stringFont).stringWidth(value);
		} else {
			return Screen.getGraphicsContext().getFontMetrics().stringWidth(value);
		}
	}
	
	@Override
	public int height() {
		if (stringFont != null){
			return Screen.getGraphicsContext().getFontMetrics(stringFont).getHeight();
		} else {
			return Screen.getGraphicsContext().getFontMetrics().getHeight();
		}
	}
	
	@Override
	public void onClick() {
		Reference.LOGGER.trace("Someone clicked me!");
	}

}
