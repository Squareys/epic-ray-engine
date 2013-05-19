package de.squareys.EpicRay.implementation;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.squareys.EpicRay.framework.AbstractJoypad;
import de.squareys.EpicRay.framework.IEntity;
import de.squareys.EpicRay.framework.IGame;
import de.squareys.EpicRay.framework.ITileMap;
import de.squareys.EpicRay.framework.IWorld;
import de.squareys.EpicRay.framework.Screen;

/**
 * TODO: very incomplete
 * @author Squareys
 *
 */
public class Game extends Panel implements IGame, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3419294756259121818L;

	private Screen screen;
	
	private boolean running;
	private Thread thread;
	
	private IWorld m_world;
	private Player m_player;
	
	public Game(ITileMap tileMap){
		m_world = new World(tileMap);
		
		Toolkit.getDefaultToolkit().addAWTEventListener(CustomJoypad.getInstance(), AWTEvent.KEY_EVENT_MASK);
		
		m_player = new Player(11, 12, 10, 10, null, m_world);
		
		screen = new Viewport3D(m_world, m_player, 640, 480);
		running = true;
		
		add(screen);
		setPreferredSize(new Dimension(640, 480));
	}
	
	private void update(){
		CustomJoypad joypad = (CustomJoypad) CustomJoypad.getInstance();
		
		if (joypad.keyIsDown(CustomJoypad.KEY_FORWARD)){
			//move player forward
			m_player.setLocalMovementDir(Player.DIR_FORWARD);
		} else if (joypad.keyIsDown(CustomJoypad.KEY_BACKWARD)){
			m_player.setLocalMovementDir(Player.DIR_BACKWARD);
		} else {
			m_player.setMovementDir(0.0, 0.0);
		}
		
		if (joypad.keyIsDown(CustomJoypad.KEY_TURN_LEFT)){
			m_player.setSpin(-1);
		} else if (joypad.keyIsDown(CustomJoypad.KEY_TURN_RIGHT)){
			m_player.setSpin(1);
		} else {
			m_player.setSpin(0);
		}
		
		m_player.onUpdate();
	}
	
	@Override
	public void play() {
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void quit() {
		throw new RuntimeException("Unimplemented Method!");
	}

	@Override
	public Screen getCurrentScreen() {
		return screen;
	}

	@Override
	public void showScreen(Screen screen) {
		this.screen = screen;
	}
	
	public synchronized void start() {
		if (running) return;
		running = true;
	}

	public synchronized void stop() {
		if (!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		int frames = 0;
		
		double unprocessedSeconds = 0;
		long lastTime = System.nanoTime();
		double secondsPerTick = 1 / 100.0;
		int tickCount = 0;

		requestFocus();

		while (running) {
			long now = System.nanoTime();
			long passedTime = now - lastTime;
			lastTime = now;
			if (passedTime < 0) passedTime = 0;
			if (passedTime > 100000000) passedTime = 100000000;

			unprocessedSeconds += passedTime / 1000000000.0;

			boolean ticked = false;
			while (unprocessedSeconds > secondsPerTick) {

				update();
				
				unprocessedSeconds -= secondsPerTick;
				ticked = true;

				tickCount++;
				if (tickCount % 60 == 0) {
					System.out.println(frames + " fps");
					lastTime += 1000;
					frames = 0;
				}
			}
			
			if (ticked) {
				screen.present();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

}