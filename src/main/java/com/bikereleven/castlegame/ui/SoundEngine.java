package com.bikereleven.castlegame.ui;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.bikereleven.castlegame.utility.Reference;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class SoundEngine {

	private static SoundEngine instance;
	//private static SourceDataLine bgSound;

	private static Clip loadClipSource(URL source)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		Reference.LOGGER.traceEntry("Loading sound clip ({})", source);
		AudioInputStream ais = AudioSystem.getAudioInputStream(source);
		Clip clip = AudioSystem.getClip();
		clip.open(ais);
		
		Reference.LOGGER.traceExit();
		return clip;
	}

	/**
	 * This manages the internal cache of sound sources so we can replay a sound
	 * at a moments notice
	 */
	private static LoadingCache<URL, Clip> soundCache = CacheBuilder.newBuilder().maximumSize(10)
			.build(new CacheLoader<URL, Clip>() {
				@Override
				public Clip load(URL key) throws Exception {
					return loadClipSource(key);
				}
			});

	private SoundEngine() {
	}

	/*
	private void switchbgSource(URL source) {

	}*/

	protected void playClip(Sound sound) {
		Reference.LOGGER.traceEntry("Attempting to play sound {}", sound);
		try {
			Clip clip = soundCache.get(sound.getSource());
			new Thread() {
				public void run() {
					synchronized (clip) {
						clip.stop();
						clip.setFramePosition(sound.getPlayFrame());
						if (sound.isLooping()){
							clip.setLoopPoints(0, -1);
							clip.loop(Clip.LOOP_CONTINUOUSLY);
						} else {
							clip.start();
						}
					}
				}
			}.start();
		} catch (ExecutionException err) {
			Reference.LOGGER.error("Failed to load a clip", err);
		}
		
		Reference.LOGGER.traceExit();
	}

	protected void stopClip(Sound sound) {
		try {
			Clip clip = soundCache.get(sound.getSource());
			synchronized (clip) {
				sound.setPlayFrame(clip.getFramePosition());
				clip.stop();
			}
		} catch (ExecutionException err) {
			Reference.LOGGER.error("Failed to load a clip", err);
		}
	}

	protected Clip getClip(Sound sound) {
		try {
			return soundCache.get(sound.getSource());
		} catch (ExecutionException err) {
			Reference.LOGGER.error("Failed to load a clip", err);
		}
		
		return null;
	}
	
	protected Clip getClip(URL source) {
		try {
			return soundCache.get(source);
		} catch (ExecutionException err) {
			Reference.LOGGER.error("Failed to load a clip", err);
		}
		
		return null;
	}

	public static SoundEngine getInstance() {
		if (instance == null)
			createSoundEngine();
		return instance;
	}

	public static SoundEngine createSoundEngine() {
		if (instance == null)
			instance = new SoundEngine();
		return instance;
	}

}
