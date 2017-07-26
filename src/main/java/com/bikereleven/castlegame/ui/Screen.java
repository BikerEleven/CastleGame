package com.bikereleven.castlegame.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.bikereleven.castlegame.event.events.ShutdownEvent;
import com.bikereleven.castlegame.utility.Reference;
import com.bikereleven.castlegame.world.World;

/**
 * This class will manage our screen and delegate the drawing of it's parts
 * 
 * @author Biker
 *
 */
public class Screen extends JPanel {

	private static final long serialVersionUID = -8996853402381599005L;
	private static Graphics generalGraphics;
	private Config loadedConfig;
	private boolean block = false;

	private static Screen instance;

	private JFrame appWindow;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 600);
		loadedConfig.draw(g);
	}

	private void init() {

		appWindow = new JFrame(TextLoader.request("screen.window.title"));

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int px = (int) ((dim.getWidth() / 2.0) - (Reference.RESOLUTION.getWidth() / 2));
		int py = (int) ((dim.getHeight() / 2.0) - (Reference.RESOLUTION.getHeight() / 2));
		appWindow.setBounds(px, py, 800, 600);
		appWindow.setResizable(false);
		appWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		appWindow.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {

			}

			@Override
			public void windowClosed(WindowEvent arg0) {

			}

			@Override
			public void windowClosing(WindowEvent evt) {
				if (evt.getID() == WindowEvent.WINDOW_CLOSING) {
					block = true;

					World.getEventBus().post(new ShutdownEvent());
				}
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {

			}

			@Override
			public void windowIconified(WindowEvent arg0) {

			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				Reference.LOGGER.trace("The window has displayed");
			}
		});

		appWindow.setResizable(false);
		appWindow.setIgnoreRepaint(true);
		appWindow.setContentPane(this);

	}

	private Screen() {

		init();

		appWindow.setVisible(true);
		generalGraphics = getGraphics();

	}

	public static Screen getInstance() {
		if (instance == null)
			createScreen();
		return instance;
	}

	public static Screen createScreen() {
		if (instance == null)
			instance = new Screen();
		return instance;
	}

	public static Graphics getGraphicsContext() {
		return generalGraphics;
	}

	public void loadConfig(Config cfg) {

		if (loadedConfig != null) {
			loadedConfig.unbindKeys();
		}
		loadedConfig = cfg;
		tick();

	}

	/**
	 * This will attempt to bind an action to a key or key combination.
	 * 
	 * @param key
	 *            KeyStroke to register you <b>MUST</b> retain this object if
	 *            you wish to unbind the KeyStroke later
	 * @param act
	 *            The Abstract action to be executed when the keybind is
	 *            pressed.
	 */
	public void addKeyBinding(KeyStroke key, Action act) {
		((JComponent) appWindow.getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(key, key);
		((JComponent) appWindow.getContentPane()).getActionMap().put(key, act);
	}

	/**
	 * This will attempt to unbind a previously bound KeyStroke from the
	 * component
	 * 
	 * @param key
	 *            The KeyStoke object that was used to register the bind
	 */
	public void removeKeyBinding(KeyStroke key) {
		((JComponent) appWindow.getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).remove(key);
		((JComponent) appWindow.getContentPane()).getActionMap().remove(key);
	}

	public synchronized void tick() {
		repaint();
	}

	protected void attachMouseAdapter(MouseAdapter adapt) {
		appWindow.getContentPane().addMouseListener(adapt);
	}

	public void cleanup() {
		if (!block) {
			appWindow.dispose();
		}
	}

}
