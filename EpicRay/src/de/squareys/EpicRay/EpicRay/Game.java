package de.squareys.EpicRay.EpicRay;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import de.squareys.EpicRay.GameLogic.ITileMap;
import de.squareys.EpicRay.GameLogic.IWorld;
import de.squareys.EpicRay.Resource.IGame;
import de.squareys.EpicRay.Util.Screen;

/**
 * TODO: very incomplete
 * @author Squareys
 *
 */
public class Game extends Container implements IGame, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3419294756259121818L;

	private Screen screen;
	
	private boolean running;
	private Thread thread;
	
	private IWorld m_world;
	private Player m_player;
	
	public Game(ITileMap map){
		this(map, 640, 480);
	}
	
	public Game(ITileMap tileMap, int windowWidth, int windowHeight){
		m_world = new World(tileMap);
		
		Toolkit.getDefaultToolkit().addAWTEventListener(CustomJoypad.getInstance(), AWTEvent.KEY_EVENT_MASK);
		
		m_player = new Player(tileMap.getWidth() >> 1, tileMap.getHeight() >> 1, 10, 10, null, m_world);
		
		screen = new Viewport3D(m_world, m_player, windowWidth, windowHeight);
		running = true;
		
		setBackground(Color.pink);
		
		add(screen);
		
		setPreferredSize(new Dimension(windowWidth, windowHeight));
	}
	
	private void update(float timeDiff){
		CustomJoypad joypad = (CustomJoypad) CustomJoypad.getInstance();
		
		if (joypad.keyIsDown(CustomJoypad.KEY_FORWARD)){
			//move player forward
			m_player.setLocalMovementDir(Player.DIR_FORWARD);
		} else if (joypad.keyIsDown(CustomJoypad.KEY_BACKWARD)){
			m_player.setLocalMovementDir(Player.DIR_BACKWARD);
		} else {
			m_player.setMovementDir(0.0f, 0.0f);
		}
		
		if (joypad.keyIsDown(CustomJoypad.KEY_TURN_LEFT)){
			m_player.setSpin(-1);
		} else if (joypad.keyIsDown(CustomJoypad.KEY_TURN_RIGHT)){
			m_player.setSpin(1);
		} else {
			m_player.setSpin(0);
		}
		
		m_player.onUpdate(timeDiff);
	}
	
	@Override
	public void play() {
		screen.createBufferStrategy();
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
		
		double unprocessedSeconds = 0;
		long lastTime = System.nanoTime();
		
		/* gamelogic updates per second */
		final int ticksPerSecond = 150;
		final double secondsPerTick = 1 / (float) ticksPerSecond;
		
		int frames = 0;
		int tickCount = 0;

		requestFocus();

		long now;
		long passedTime;
		boolean ticked;
		
		while (running) {
			now = System.nanoTime();
			passedTime = now - lastTime;
			lastTime = now;
			
			if (passedTime < 0) passedTime = 0;
			if (passedTime > 100000000) passedTime = 100000000;

			unprocessedSeconds += passedTime / 1000000000.0;

			ticked = false;
			while (unprocessedSeconds > secondsPerTick) {
				update((float)secondsPerTick);
				
				unprocessedSeconds -= secondsPerTick;
				ticked = true;

				tickCount++;
				if (tickCount % ticksPerSecond == 0) {
					/* reset framerate counter once a second */
					screen.setFPS(frames);
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
