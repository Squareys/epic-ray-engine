package de.squareys.EpicRayEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.squareys.EpicRay.framework.ITile;
import de.squareys.EpicRay.framework.ITileMap;
import de.squareys.EpicRay.implementation.EpicRayRenderingAttributes;
import de.squareys.EpicRay.implementation.GameFile;
import de.squareys.EpicRay.implementation.Tile;
import de.squareys.EpicRay.implementation.TileMap;
import de.squareys.EpicRayEditor.Dialogs.CreateTileMapDialog;
import de.squareys.EpicRayEditor.Dialogs.EditTileDialog;
import de.squareys.EpicRayEditor.Dialogs.TextureEditorDialog;

public class EpicRayEditorListener implements ActionListener, ListSelectionListener {

	private EpicRayEditor m_editor;
	
	private MainView m_mainView;
	private GameView m_gameView;
	private ITile m_curTile;
	
	private DefaultListModel<ITile> m_tileListModel;
	
	public EpicRayEditorListener (EpicRayEditor editor){
		m_editor = editor;
		
		m_mainView = new MainView();
		m_mainView.addEventListener(this);
		
		editor.setContentPane(m_mainView);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String command = e.getActionCommand();
		
		if (command.equals("startGame")){
			if (m_gameView == null){
				m_gameView = new GameView(m_mainView.m_tileMapView.getTileMap());
			}
			
			m_editor.setContentPane(m_gameView);	
			m_gameView.play();	
			m_editor.revalidate();
			
			return;
		}
		
		if (command.equals("stopGame")){
			m_gameView.stop();
			
			m_editor.setContentPane(m_mainView);
			m_editor.revalidate();
			
			return;
		}
		
		if (command.equals("newMap")){
			CreateTileMapDialog dialog = new CreateTileMapDialog(m_editor);
			
			dialog.setLocationRelativeTo(m_editor);
            dialog.setVisible(true);
            
            //evaluate on dialog
            if (dialog.success()){
            	int w = dialog.getTileWidth();
            	int h = dialog.getTileHeight();
            	
            	TileMap map = new TileMap(w, h, true);
            	
            	m_mainView.m_tileMapView.setTileMap(map);
				m_mainView.revalidate();
            }
            
            dialog.dispose();
            
            return;
		}
		
		if (command.equals("saveMap")){
			GameFile file = new GameFile("File.dat");
			
			try {
				file.saveSaveable(m_mainView.m_tileMapView.getTileMap());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			return;
		}
		
		if (command.equals("loadMap")){
			GameFile file = new GameFile("File.dat");
			
			try {
				ITileMap tileMap = (ITileMap) file.readSaveable();
				m_mainView.m_tileMapView.setTileMap(tileMap);
				m_mainView.revalidate();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			return;
		}
		
		if (command.equals("newTile")){
			EpicRayRenderingAttributes def = new EpicRayRenderingAttributes();
			m_curTile = new Tile(false, false, def);
			
			editTile();
			
			m_tileListModel.addElement(m_curTile);
			
			return;
		}
		
		if (command.equals("editTile")){
			editTile();
			return;
		}
		
		if (command.equals("editTextures")){
			editTextures();
		}
	}
	
	private void editTextures() {
		TextureEditorDialog dialog = new TextureEditorDialog(m_editor);
		
		dialog.setLocationRelativeTo(m_editor);
        dialog.setVisible(true);
        
        //waiting for dialog
        
        dialog.dispose();
	}

	private void editTile(){
		EditTileDialog dialog = new EditTileDialog(m_editor, m_curTile);
		
		dialog.setLocationRelativeTo(m_editor);
        dialog.setVisible(true);
        
        //evaluate on dialog
        if (dialog.success()){
        	m_curTile = new Tile(dialog.getTile(), false);
        }
        
        dialog.dispose();
	}

	public void setTileListModel(DefaultListModel<ITile> listModel) {
		m_tileListModel = listModel;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		Object source = e.getSource();
		
		if (source instanceof ListSelectionModel){
			m_curTile = m_tileListModel.getElementAt(((ListSelectionModel) source).getMinSelectionIndex());
			
			m_mainView.m_tileMapView.setBrushTile(m_curTile);
		}
	}

}
