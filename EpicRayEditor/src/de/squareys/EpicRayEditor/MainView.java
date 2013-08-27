package de.squareys.EpicRayEditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import de.squareys.EpicRay.framework.ITile;
import de.squareys.EpicRay.implementation.EpicRayRenderingAttributes;
import de.squareys.EpicRay.implementation.Tile;
import de.squareys.EpicRay.implementation.TileMap;
import de.squareys.EpicRayEditor.Components.TileCellRenderer;
import de.squareys.EpicRayEditor.Components.TileMapView2D;

public class MainView extends JPanel {

	TileMapView2D m_tileMapView;
	
	JPanel m_curTilePanel;
	JList<ITile> m_tileList; 
	DefaultListModel<ITile> m_tileListModel;
	
	JButton m_newTileBtn;
	JButton m_remTileBtn;
	JButton m_editTileBtn;
	
	public MainView(){
		setLayout(new GridBagLayout());

		GridBagConstraints gbc_tmv = new GridBagConstraints(); //tilemapview
		gbc_tmv.fill = GridBagConstraints.BOTH;
		gbc_tmv.gridwidth = 1;
		gbc_tmv.gridheight = 5;
		gbc_tmv.gridx = 0;
		gbc_tmv.gridy = 0;
		gbc_tmv.weightx = 1.0;
		
		m_tileMapView = new TileMapView2D(new TileMap(30, 40, true));
		JScrollPane tileMapViewScroll = new JScrollPane(m_tileMapView);
		tileMapViewScroll.setPreferredSize(new Dimension(640, 480));
		this.add(tileMapViewScroll, gbc_tmv);
		
		GridBagConstraints gbc_ctp = new GridBagConstraints(); //current Tile Panel
		gbc_ctp.fill = GridBagConstraints.BOTH;
		gbc_ctp.gridwidth = 3;
		gbc_ctp.gridheight = 1;
		gbc_ctp.gridx = 1;
		gbc_ctp.gridy = 0;
		gbc_ctp.weightx = .5;
		m_curTilePanel = new JPanel();
		m_curTilePanel.setPreferredSize(new Dimension(150, 150));
		
		this.add(m_curTilePanel, gbc_ctp);
		
		GridBagConstraints gbc_tl = new GridBagConstraints(); //tile List
		gbc_tl.fill = GridBagConstraints.BOTH;
		gbc_tl.gridwidth = 3;
		gbc_tl.gridheight = 2;
		gbc_tl.gridx = 1;
		gbc_tl.gridy = 1;
		gbc_tl.weightx = .5;
		
		m_tileListModel = new DefaultListModel<ITile>();
		m_tileList = new JList<ITile>(m_tileListModel);
		m_tileList.setPreferredSize(new Dimension(150, 300));
		m_tileList.setCellRenderer(new TileCellRenderer());
		
		this.add(m_tileList, gbc_tl);
		
		GridBagConstraints gbc_newBtn = new GridBagConstraints(); //addTileBtn
		gbc_newBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_newBtn.gridwidth = 1;
		gbc_newBtn.gridheight = 1;
		gbc_newBtn.gridx = 1;
		gbc_newBtn.gridy = 4;
		gbc_newBtn.weightx = .5;
		
		m_newTileBtn = new JButton("New");
		m_newTileBtn.setActionCommand("newTile");
		
		this.add(m_newTileBtn, gbc_newBtn);
		
		GridBagConstraints gbc_remBtn = new GridBagConstraints(); //addTileBtn
		gbc_remBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_remBtn.gridwidth = 1;
		gbc_remBtn.gridheight = 1;
		gbc_remBtn.gridx = 2;
		gbc_remBtn.gridy = 4;
		gbc_remBtn.weightx = .5;
		
		m_remTileBtn = new JButton("Remove");
		m_remTileBtn.setActionCommand("remTile");
		
		this.add(m_remTileBtn, gbc_remBtn);
		
		GridBagConstraints gbc_editBtn = new GridBagConstraints(); //addTileBtn
		gbc_editBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_editBtn.gridwidth = 1;
		gbc_editBtn.gridheight = 1;
		gbc_editBtn.gridx = 3;
		gbc_editBtn.gridy = 4;
		gbc_editBtn.weightx = .5;
		
		m_editTileBtn = new JButton("Edit");
		m_editTileBtn.setActionCommand("editTile");
		
		this.add(m_editTileBtn, gbc_editBtn);
		setVisible(true);		
	}
	
	public void addEventListener(EpicRayEditorListener eventListener) {
		m_newTileBtn.addActionListener(eventListener);
		m_remTileBtn.addActionListener(eventListener);
		m_editTileBtn.addActionListener(eventListener);
		
		m_tileList.getSelectionModel().addListSelectionListener(eventListener);
		
		eventListener.setTileListModel(m_tileListModel);
	}
}
