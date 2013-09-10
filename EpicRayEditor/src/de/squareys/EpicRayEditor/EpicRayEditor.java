package de.squareys.EpicRayEditor;

import java.awt.Dimension;

import javax.swing.JFrame;

public class EpicRayEditor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4698484870388447480L;


	public EpicRayEditor(){
		super();
		
		EpicRayEditorListener eventListener = new EpicRayEditorListener(this);
		
		//build window:
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
