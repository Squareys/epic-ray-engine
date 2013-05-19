package de.squareys.EpicRay.framework;

public interface IGame {
	
	public void play();
	
	public void quit();
	
	public Screen getCurrentScreen();
	
	public void showScreen(Screen screen);
	
}
