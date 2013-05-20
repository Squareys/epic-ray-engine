package de.squareys.EpicRay.implementation;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.squareys.EpicRay.framework.IBitmap;

public class Main extends JFrame {

	Game game;
	
	public Main() {
		super("EpicRay Game");
		

		IBitmap bmp = ResourceManager.getInstance().loadBitmap("CheepTexture.png");
		ResourceManager.getInstance().createTexture(bmp);
		
		IBitmap bmp1 = ResourceManager.getInstance().loadBitmap("CrappyHiresTexture.png");
		ResourceManager.getInstance().createTexture(bmp1);
		
		setLayout(new FlowLayout());
		
		TileMap map = new TileMap (40, 40, true);
		map.autoGenerate();
		game = new Game(map);
		
		add(game);		
		
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		createBufferStrategy(3);
		
		
		game.play();
	}
	
	public static void main(String arguments[]){
		Main m = new Main();
	}
}
