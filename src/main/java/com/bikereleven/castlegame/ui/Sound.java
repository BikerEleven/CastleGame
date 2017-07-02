package com.bikereleven.castlegame.ui;

import static com.google.common.base.Preconditions.checkArgument;

import java.net.URL;

import javax.sound.sampled.Clip;

/**
 * This represents a sound resource that may be playable.
 * @author Biker
 *
 */
public class Sound {
	
	private double pitch = 0.0;
	private double volume = 0.5;
	private int playFrame = 0;
	
	private double duration = -1;
	private boolean looping = false;
	
	private URL source;
	
	private void init(){
		Clip clip = SoundEngine.getInstance().getClip(source);
		if (clip != null){
			duration = clip.getFrameLength();
		}
	}
	
	public Sound(String path) {
		String soundPath = "/assets/castlegame/sounds/"+path;
		source = Sound.class.getResource(soundPath);
		checkArgument(source != null, "Sound file(%s) must exist", soundPath);
		
		init();
	}
	
	public void play(){
		playFrame = 0;
		SoundEngine.getInstance().playClip(this);
	}
	
	public void resume(){
		SoundEngine.getInstance().playClip(this);
	}
	
	public void stop(){
		SoundEngine.getInstance().stopClip(this);
	}
	
	public boolean isLooping(){
		return looping;
	}
	
	public Sound setLooping(boolean loop){
		looping = loop;
		return this;
	}

	public double getPitch() {
		return pitch;
	}

	public Sound setPitch(double pitch) {
		this.pitch = pitch;
		return this;
	}

	public double getVolume() {
		return volume;
	}

	public Sound setVolume(double volume) {
		this.volume = volume;
		return this;
	}

	public double getDuration() {
		return duration;
	}

	public URL getSource() {
		return source;
	}

	public Sound setSource(String source) {
		this.source = Sound.class.getResource("/assets/castlegame/sounds/"+source);
		return this;
	}

	public int getPlayFrame() {
		return playFrame;
	}

	public Sound setPlayFrame(int playFrame) {
		this.playFrame = playFrame;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Sound [pitch=").append(pitch).append(", volume=").append(volume).append(", playFrame=")
				.append(playFrame).append(", duration=").append(duration).append(", looping=").append(looping)
				.append(", source=").append(source).append("]");
		return builder.toString();
	}
	
	
	
}
