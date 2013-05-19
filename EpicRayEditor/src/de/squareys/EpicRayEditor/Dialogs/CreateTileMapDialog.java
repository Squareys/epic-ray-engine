package de.squareys.EpicRayEditor.Dialogs;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * CreateTileMapDialog
 * 
 * Dialog Panel for creating TileMaps.
 * 
 * @author Squareys
 *
 */

public class CreateTileMapDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4878665188616766577L;

	private JTextField m_tileWidthField;
	private JTextField m_tileHeightField;
	
	private JLabel m_heightLabel;
	private JLabel m_widthLabel;
	
	private JPanel m_contentPanel;
	
	private JButton m_createBtn;
	private JButton m_cancelBtn;
	
	private int m_value_height;
	private int m_value_width;
	
	private boolean m_positiveClose;
	
	public CreateTileMapDialog(Frame aFrame){
		super(aFrame, true);
		
		setTitle("New Tilemap...");
		
		setLayout(new GridLayout(3, 2));
		m_tileWidthField = new JTextField(20);
		m_tileHeightField = new JTextField(20);
		
		m_heightLabel = new JLabel ("Map Height");
		m_widthLabel  = new JLabel ("Map Width ");
		
		m_createBtn = new JButton ("Create");
		m_cancelBtn = new JButton ("Cancel");
		
		//Add Button Functionality
		m_cancelBtn.addActionListener(this);
		m_cancelBtn.setActionCommand("cancel");
		
		m_createBtn.addActionListener(this);
		m_createBtn.setActionCommand("done");
		
		m_contentPanel = new JPanel();
		m_contentPanel.setPreferredSize(new Dimension(280, 200));
		
		m_contentPanel.add(m_widthLabel);
		m_contentPanel.add(m_tileWidthField);
		m_contentPanel.add(m_heightLabel);
		m_contentPanel.add(m_tileHeightField);
		
		m_contentPanel.add(m_createBtn);
		m_contentPanel.add(m_cancelBtn);
		
		setContentPane(m_contentPanel);
		pack();
		
		
		m_value_height = 0;
		m_value_width = 0;
		
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
			m_value_width = Integer.parseInt(m_tileWidthField.getText());
			m_value_height = Integer.parseInt(m_tileHeightField.getText());
			
			if (m_value_width == 0 || m_value_height == 0){
				//invalid values
				//TODO: Inform User!
				return;
			}
			m_positiveClose = true;
			this.setVisible(false);
		}
		
	}
	
	public boolean success(){
		return m_positiveClose;
	}
	
	public int getTileWidth(){
		return m_value_width;
	}
	
	public int getTileHeight(){
		return m_value_height;
	}
}
