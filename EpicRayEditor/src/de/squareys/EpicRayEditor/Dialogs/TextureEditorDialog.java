package de.squareys.EpicRayEditor.Dialogs;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JScrollPane;

import de.squareys.EpicRay.Bitmap.IBitmap;
import de.squareys.EpicRay.EpicRay.ResourceManager;
import de.squareys.EpicRayEditor.Components.GUIUtils;
import de.squareys.EpicRayEditor.Components.TexturePanel;

public class TextureEditorDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1497693934528315218L;
	
	private JList<IBitmap<Integer>> m_textureList;
	private TexturePanel m_texturePanel;
	
	private JButton m_closeButton;
	private JButton m_newTexButton;
	private JButton m_loadTexButton;
	
	public TextureEditorDialog (Frame aFrame){
		super(aFrame, true);
		
		setLayout(new GridBagLayout());
	
		GridBagConstraints gbc_texPanel = new GridBagConstraints(0, 0, 2, 1, 1.0, 0.5, GridBagConstraints.FIRST_LINE_START, GUIUtils.BOTH, GUIUtils.NO_INSETS, 0, 0);
		GridBagConstraints gbc_texList  = new GridBagConstraints(0, 1, 2, 1, 1.0, 0.5, GridBagConstraints.FIRST_LINE_START, GUIUtils.BOTH, GUIUtils.NO_INSETS, 0, 0);
		GridBagConstraints gbc_closeBtn = new GridBagConstraints(0, 3, 2, 1, 1.0, 0.5, GridBagConstraints.FIRST_LINE_START, GUIUtils.BOTH, GUIUtils.NO_INSETS, 0, 0);
		GridBagConstraints gbc_newTexBtn= new GridBagConstraints (0, 2, 1, 1, 1.0, 0.5, GridBagConstraints.FIRST_LINE_START, GUIUtils.BOTH, GUIUtils.NO_INSETS, 0, 0);
		GridBagConstraints gbc_loadTexBtn= new GridBagConstraints(1, 2, 1, 1, 1.0, 0.5, GridBagConstraints.FIRST_LINE_START, GUIUtils.BOTH, GUIUtils.NO_INSETS, 0, 0);
		
		m_texturePanel = new TexturePanel();
		m_texturePanel.setPreferredSize(new Dimension(200, 200));
		
		m_textureList  = new JList<IBitmap<Integer>>();
		JScrollPane listScroll = new JScrollPane(m_textureList);
		listScroll.setPreferredSize(new Dimension(200, 300));
		
		m_closeButton  = new JButton("Close");
		m_closeButton.addActionListener(this);
		m_closeButton.setActionCommand("close");
		
		m_newTexButton = new JButton("New");
		m_newTexButton.addActionListener(this);
		m_newTexButton.setActionCommand("newTex");
		
		m_loadTexButton = new JButton("Load");
		m_loadTexButton.addActionListener(this);
		m_loadTexButton.setActionCommand("loadTex");
		
		this.add(listScroll, gbc_texList);
		this.add(m_texturePanel, gbc_texPanel);
		this.add(m_closeButton, gbc_closeBtn);
		this.add(m_newTexButton, gbc_newTexBtn);
		this.add(m_loadTexButton, gbc_loadTexBtn);
		
		pack();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		if ( command.equals("close") ){
			this.setVisible(false); //"close" the dialog
			return;
		}
		
		if ( command.equals("newTex") ){
			return;
		} 
		
		if ( command.equals("loadTex") ) {
			JFileChooser fc = new JFileChooser();
			int ret = fc.showOpenDialog(this);
			
			if (ret == JFileChooser.APPROVE_OPTION){
				IBitmap<Integer> bitmap;
				try {
					bitmap = ResourceManager.getInstance().loadBitmap(fc.getSelectedFile().toURI().toURL());
				
					IBitmap<Integer> tex = ResourceManager.getInstance().createTexture(bitmap);
					
					m_texturePanel.setTexture(tex);
					m_texturePanel.repaint();
				} catch (MalformedURLException e1) {
					//will not happen
				}
			}
			return;
		}
	} 
}
