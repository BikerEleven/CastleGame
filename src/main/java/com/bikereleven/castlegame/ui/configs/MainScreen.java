package com.bikereleven.castlegame.ui.configs;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import com.bikereleven.castlegame.event.events.ShutdownEvent;
import com.bikereleven.castlegame.ui.Config;
import com.bikereleven.castlegame.ui.TextLoader;
import com.bikereleven.castlegame.ui.parts.Text;
import com.bikereleven.castlegame.world.World;

public class MainScreen extends Config {

	private int idex = 0;

	@Override
	public void init() {

		addPart(CATA.GUI,
				new Text(TextLoader.request("screen.config.mainmenu.start")).setColor(Color.WHITE).setX(50).setY(50),
				"MainMenu.Title", 1);
		addPart(CATA.GUI, new Text("->" + TextLoader.request("screen.config.mainmenu.startgame")).setColor(Color.WHITE)
				.setX(100).setY(80), "MainMenu.Enter", 1);
		addPart(CATA.GUI, new Text(TextLoader.request("screen.config.mainmenu.exitgame")).setColor(Color.WHITE)
				.setX(100).setY(90), "MainMenu.Exit", 1);

		bind("key.up", KeyEvent.VK_DOWN, 0, new AbstractAction() {
			private static final long serialVersionUID = 4560069510011727650L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (idex < 1) {
					idex++;
					((Text) getPart(CATA.GUI, "MainMenu.Enter", 1))
							.setText(TextLoader.request("screen.config.mainmenu.startgame"));
					((Text) getPart(CATA.GUI, "MainMenu.Exit", 1))
							.setText("->" + TextLoader.request("screen.config.mainmenu.exitgame"));
				}
			}
		});

		bind("key.down", KeyEvent.VK_UP, 0, new AbstractAction() {
			private static final long serialVersionUID = -6538706109728690713L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (idex > 0) {
					idex--;
					((Text) getPart(CATA.GUI, "MainMenu.Enter", 1))
							.setText("->" + TextLoader.request("screen.config.mainmenu.startgame"));
					((Text) getPart(CATA.GUI, "MainMenu.Exit", 1))
							.setText(TextLoader.request("screen.config.mainmenu.exitgame"));
				}
			}
		});

		bind("key.enter", KeyEvent.VK_ENTER, 0, new AbstractAction() {
			private static final long serialVersionUID = 2652622409615322121L;

			@Override
			public void actionPerformed(ActionEvent e) {
				switch (idex) {
				case 0:
					World.getInstance().newGame();
					break;
				case 1:
					World.getEventBus().post(new ShutdownEvent());
					break;
				}
			}
		});

	}

}
