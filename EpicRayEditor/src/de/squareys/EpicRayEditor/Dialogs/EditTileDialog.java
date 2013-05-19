package de.squareys.EpicRayEditor.Dialogs;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.squareys.EpicRay.framework.ITile;
import de.squareys.EpicRay.implementation.EpicRayRenderingAttributes;
import de.squareys.EpicRay.implementation.Tile;
import de.squareys.EpicRayEditor.Components.ColorEditor;
import de.squareys.EpicRayEditor.Components.TileRenderPanel;

/**
 * CreateTileMapDialog
 * 
 * Dialog Panel for creating TileMaps.
 * 
 * @author Squareys
 *
 */

public class EditTileDialog extends JDialog implements ActionListener, ChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4878665188616766577L;

	
	
	//Property Labels
	private JLabel m_nameLabel = new JLabel("Name");
	private JLabel m_floorLabel = new JLabel("Floor");
	private JLabel m_wallLabel = new JLabel("Wall");
	private JLabel m_ceilLabel = new JLabel("Ceiling");
	
	
	private JPanel m_contentPanel;
	private TileRenderPanel m_tilePreview;
	
	private JButton m_createBtn;
	private JButton m_cancelBtn;
	
	//Property editors
	private JTextField m_nameField;
	
	private JCheckBox m_solidCB;
	private JCheckBox m_opaqueCB;
	
	private ColorEditor m_wallColEdit;
	private ColorEditor m_floorColEdit;
	private ColorEditor m_ceilColEdit;
	
	private boolean m_positiveClose;
	
	private ITile m_tile;
	EpicRayRenderingAttributes m_ra;
	
	public EditTileDialog(Frame aFrame, ITile tile){
		super(aFrame, true);
		
		m_tile = new Tile (tile, false);
		m_ra = new EpicRayRenderingAttributes((EpicRayRenderingAttributes) tile.getRenderingAttributes());
		m_tile.setRenderingAttributes(m_ra);
		
		//---GUI---
		
		setTitle("Edit Tile");
	
		
		m_tilePreview = new TileRenderPanel(200);
		m_tilePreview.setTile(m_tile);
		
		m_nameField = new JTextField(10);
		
		m_solidCB = new JCheckBox("solid", m_tile.isSolid());
		m_opaqueCB = new JCheckBox("opaque", m_tile.isOpaque());
		
		m_wallColEdit = new ColorEditor(m_ra.m_wallColor);
		m_wallColEdit.addChangeListener(this);
		m_floorColEdit = new ColorEditor(m_ra.m_floorColor);
		m_floorColEdit.addChangeListener(this);
		m_ceilColEdit = new ColorEditor(m_ra.m_ceilColor);
		m_ceilColEdit.addChangeListener(this);
						
		m_createBtn = new JButton ("Apply");
		m_cancelBtn = new JButton ("Cancel");
		
		//Add Button Functionality
		m_cancelBtn.addActionListener(this);
		m_cancelBtn.setActionCommand("cancel");
		
		m_createBtn.addActionListener(this);
		m_createBtn.setActionCommand("done");

		Insets noInset = new Insets(0, 0, 0, 0);
		GridBagConstraints gbc_preview 	 = new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, noInset, 0, 0);
		GridBagConstraints gbc_nameLabel = new GridBagConstraints(0, 1, 1, 1, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.BOTH, noInset, 0, 0);
		GridBagConstraints gbc_nameField = new GridBagConstraints(1, 1, 1, 1, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.BOTH, noInset, 0, 0);
		GridBagConstraints gbc_solid	 = new GridBagConstraints(0, 2, 1, 1, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.BOTH, noInset, 0, 0);
		GridBagConstraints gbc_opaque	 = new GridBagConstraints(1, 2, 1, 1, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.BOTH, noInset, 0, 0);
		GridBagConstraints gbc_wallLbl	 = new GridBagConstraints(0, 3, 1, 1, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.BOTH, noInset, 0, 0);
		GridBagConstraints gbc_wallEdit	 = new GridBagConstraints(1, 3, 1, 1, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.BOTH, noInset, 0, 0);
		GridBagConstraints gbc_floorLbl	 = new GridBagConstraints(0, 4, 1, 1, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.BOTH, noInset, 0, 0);
		GridBagConstraints gbc_floorEdit = new GridBagConstraints(1, 4, 1, 1, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.BOTH, noInset, 0, 0);
		GridBagConstraints gbc_ceilLbl	 = new GridBagConstraints(0, 5, 1, 1, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.BOTH, noInset, 0, 0);
		GridBagConstraints gbc_ceilEdit  = new GridBagConstraints(1, 5, 1, 1, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.BOTH, noInset, 0, 0);
		GridBagConstraints gbc_createBtn = new GridBagConstraints(0, 6, 1, 1, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.BOTH, noInset, 0, 0);
		GridBagConstraints gbc_cancelBtn = new GridBagConstraints(1, 6, 1, 1, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.BOTH, noInset, 0, 0);
		
		m_contentPanel = new JPanel();
		m_contentPanel.setLayout(new GridBagLayout());
		m_contentPanel.setPreferredSize(new Dimension(200, 500));
		
		m_contentPanel.add(m_tilePreview, gbc_preview);
		
		m_contentPanel.add(m_nameLabel, gbc_nameLabel);
		m_contentPanel.add(m_nameField, gbc_nameField);
		m_contentPanel.add(m_solidCB, gbc_solid);
		m_contentPanel.add(m_opaqueCB, gbc_opaque);
		
		m_contentPanel.add(m_wallLabel, gbc_wallLbl);
		m_contentPanel.add(m_wallColEdit, gbc_wallEdit);
		
		m_contentPanel.add(m_floorLabel, gbc_floorLbl);
		m_contentPanel.add(m_floorColEdit, gbc_floorEdit);
		
		m_contentPanel.add(m_ceilLabel, gbc_ceilLbl);
		m_contentPanel.add(m_ceilColEdit, gbc_ceilEdit);
		
		m_contentPanel.add(m_createBtn, gbc_createBtn);
		m_contentPanel.add(m_cancelBtn, gbc_cancelBtn);
		
		setContentPane(m_contentPanel);
		pack();
		
		
		m_positiveClose = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		if (command.equals("cancel")){ 
			m_positiveClose = false;
			this.setVisible(false);
		}
		
		if (command.equals("done")){
			//apply changes to the tile
			
			m_tile.setOpaque(m_opaqueCB.isSelected());
			m_tile.setSolid (m_solidCB.isSelected());
			m_tile.setName(m_nameField.getText());
			
			m_positiveClose = true;
			this.setVisible(false);
		}
		
	}
	
	public boolean success(){
		return m_positiveClose;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		m_ra.m_ceilColor = m_ceilColEdit.getColor().getRGB();
		m_ra.m_floorColor = m_floorColEdit.getColor().getRGB();
		m_ra.m_wallColor = m_wallColEdit.getColor().getRGB();
		
		m_tilePreview.repaint();
	}

	public ITile getTile() {
		return m_tile;
	}
	
}
