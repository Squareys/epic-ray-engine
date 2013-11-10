package de.squareys.EpicRay.Resource;

import de.squareys.EpicRay.Util.Screen;

public interface IGame {

	public void play();

	public void quit();

	public Screen getCurrentScreen();

	public void showScreen(Screen screen);

}
