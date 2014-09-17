package de.squareys.epicray.editor;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import de.squareys.epicray.engine.Game;
import de.squareys.epicray.gamelogic.ITileMap;

public class GameView extends JPanel {
	
	Game m_game;
	
	ITileMap m_tileMap;

	public GameView(ITileMap map){
		m_tileMap = map;
	}
	
	public void stop(){
		m_game.stop();
	}
	
	public void play(){
		m_game = new Game(m_tileMap);
		
		this.removeAll();
		
		setLayout(new FlowLayout());
		add(m_game);
		
		setVisible(true);
		setPreferredSize(new Dimension(m_game.getWidth(), m_game.getHeight()));
		
		m_game.play();
	}
}
