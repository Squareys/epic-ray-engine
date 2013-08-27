package de.squareys.EpicRayEditor;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class EpicRayEditor extends JFrame {

	public EpicRayEditor(){
		super();
		
		EpicRayEditorListener eventListener = new EpicRayEditorListener(this);
		
		//buiwld window:
		setSize(new Dimension(1000, 600));
		EpicRayEditorMenu menu = new EpicRayEditorMenu();
		menu.addEventListener(eventListener);
		setJMenuBar(menu);
			
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		pack();
		setVisible(true);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EpicRayEditor editor = new EpicRayEditor();
	}

}
