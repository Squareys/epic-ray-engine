package de.squareys.epicray.editor;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class EpicRayEditorMenu extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JMenu fileMenu;
	public JMenuItem makeNewItem;
	public JMenuItem loadFileItem;
	public JMenuItem saveFileItem;
	public JMenuItem quitItem;
	
	public JMenu gameMenu;
	public JMenuItem startItem;
	public JMenuItem stopItem;
	
	public JMenu resourcesMenu;
	public JMenuItem editTexturesItem;
	
	public EpicRayEditorMenu(){
		super();
		
		fileMenu = new JMenu("File");
		
		this.add(fileMenu);
		
		makeNewItem 	= new JMenuItem("New");
		makeNewItem.setActionCommand("newMap");
		loadFileItem	= new JMenuItem("Load File");
		loadFileItem.setActionCommand("loadMap");
		saveFileItem	= new JMenuItem("Save File");
		saveFileItem.setActionCommand("saveMap");
		quitItem		= new JMenuItem("Quit");
		
		fileMenu.add(makeNewItem);
		fileMenu.add(loadFileItem);
		fileMenu.add(saveFileItem);
		fileMenu.addSeparator();
		fileMenu.add(quitItem);		
		
		gameMenu = new JMenu("Game");
		
		this.add(gameMenu);
		
		startItem = new JMenuItem("Start");
		startItem.setActionCommand("startGame");
		
		stopItem = new JMenuItem("Stop");
		stopItem.setActionCommand("stopGame");
		
		gameMenu.add(startItem);
		gameMenu.add(stopItem);
		
		resourcesMenu = new JMenu("Resources");
		this.add(resourcesMenu);
		
		editTexturesItem = new JMenuItem("Edit Textures");
		editTexturesItem.setActionCommand("editTextures"); 
		
		resourcesMenu.add(editTexturesItem);
	}

	public void addEventListener(EpicRayEditorListener eventListener) {
		quitItem.addActionListener(eventListener);
		
		makeNewItem.addActionListener(eventListener);
		saveFileItem.addActionListener(eventListener);
		loadFileItem.addActionListener(eventListener);
		
		startItem.addActionListener(eventListener);
		stopItem.addActionListener(eventListener);
		
		editTexturesItem.addActionListener(eventListener);
	}
}
