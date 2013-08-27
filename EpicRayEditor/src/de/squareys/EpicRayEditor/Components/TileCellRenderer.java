package de.squareys.EpicRayEditor.Components;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import de.squareys.EpicRay.framework.ITile;

public class TileCellRenderer extends JPanel implements ListCellRenderer<ITile> {

	boolean m_hasLabel;
	boolean m_hasTilePic;
	
	JLabel m_label;
	TileRenderPanel m_tilePanel;
	
	public TileCellRenderer(){
		this(true, true);
	}
	
	public TileCellRenderer(boolean tilePic, boolean label) {
		m_hasLabel = label;
	    m_hasTilePic = tilePic;
	    
		setOpaque(true);
		
		m_tilePanel = new TileRenderPanel(64);
		m_label = new JLabel();

	}
	 
	@Override
	public Component getListCellRendererComponent(JList<? extends ITile> list,
			ITile value, int index, boolean isSelected, boolean cellHasFocus) {
		m_label.setText(value.getName());
		
		if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
		
		removeAll();
		
		if (m_hasTilePic){
			m_tilePanel.setTile(value);
			add(m_tilePanel);
		}
		
		if (m_hasLabel) {
			add(m_label);
		}
		
		return this;
	}

}

