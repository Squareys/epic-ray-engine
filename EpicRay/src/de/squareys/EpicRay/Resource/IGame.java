package de.squareys.epicray.resource;

import de.squareys.epicray.util.Screen;

public interface IGame {

	public void play();

	public void quit();

	public Screen getCurrentScreen();

	public void showScreen(Screen screen);

}
