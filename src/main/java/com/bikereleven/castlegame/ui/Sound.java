package com.bikereleven.castlegame.ui;

import static com.google.common.base.Preconditions.checkArgument;

import java.net.URL;

import javax.sound.sampled.Clip;

/**
 * This represents a sound resource that can be played
 * @author Biker
 *
 */
public class Sound {
	
	private float gain = 0f;
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
	
	/**
	 * This will create a sound object with the sound file provided. The path should be relative to /assets/castlegame/sounds/
	 * @param path Path to the sound asset
	 */
	public Sound(String path) {
		String soundPath = "/assets/castlegame/sounds/"+path;
		source = Sound.class.getResource(soundPath);
		checkArgument(source != null, "Sound file(%s) must exist", soundPath);
		
		init();
	}
	
	/**
	 * This will play the sound resource from it's start
	 */
	public void play(){
		playFrame = 0;
		SoundEngine.getInstance().playClip(this);
	}
	
	/**
	 * This will play the sound resource from where it left off
	 */
	public void resume(){
		SoundEngine.getInstance().playClip(this);
	}
	
	/**
	 * This will stop a playing sound resource
	 */
	public void stop(){
		SoundEngine.getInstance().stopClip(this);
	}
	
	/**
	 * Returns true if the sound resource will loop when played
	 * @return Looping
	 */
	public boolean isLooping(){
		return looping;
	}
	
	/**
	 * Set if the source resource should loop when played
	 * @param loop True to loop infinitely
	 * @return Sound resource for chaining
	 */
	public Sound setLooping(boolean loop){
		looping = loop;
		return this;
	}

	/**
	 * Returns the gain added to the sound resource in dB
	 * @return Gain
	 */
	public float getGain() {
		return gain;
	}

	/**
	 * Sets the sound resources Gain, constrains: [-80dB, 6dB]
	 * @param gain
	 * @return Sound resource for chaining
	 */
	public Sound setGain(float gain) {
		checkArgument(gain >= -80 && gain <= 6, "-80 >= (%s) <= 6", gain);
		this.gain = gain;
		return this;
	}

	/**
	 * Returns the duration of the sound resource in sampled frames
	 * @return The total number of sample frames
	 */
	public double getDuration() {
		return duration;
	}
	
	/**
	 * Get's the sound resources location
	 * @return Sound source URL
	 */
	public URL getSource() {
		return source;
	}

	/**
	 This will create a sound object with the sound file provided. The path should be relative to /assets/castlegame/sounds/
	 * @param source Path to the sound asset
	 * @return Sound resource for chaining
	 */
	public Sound setSource(String source) {
		this.source = Sound.class.getResource("/assets/castlegame/sounds/"+source);
		checkArgument(source != null, "Source must exist (%s)", "/assets/castlegame/sounds/"+source);
		return this;
	}

	/**
	 * If the sound object was previously played then stopped this will be the point where it stopped
	 * @return The current frame 
	 */
	public int getPlayFrame() {
		return playFrame;
	}

	/**
	 * This will set the sound resources played frame allowing you to resume anywhere in the resource.
	 * @param playFrame Frame of play
	 * @return Sound resource for chaining
	 */
	public Sound setPlayFrame(int playFrame) {
		this.playFrame = playFrame;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Sound [gain=").append(gain).append(", playFrame=")
				.append(playFrame).append(", duration=").append(duration).append(", looping=").append(looping)
				.append(", source=").append(source).append("]");
		return builder.toString();
	}
	
	
	
}
