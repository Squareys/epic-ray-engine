package de.squareys.EpicRayEditor.Dialogs;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;

import de.squareys.EpicRay.Bitmap.IBitmap;
import de.squareys.EpicRay.EpicRay.ResourceManager;
import de.squareys.EpicRay.GameLogic.ITile;
import de.squareys.EpicRay.Texture.ITexture;
import de.squareys.EpicRayEditor.Components.TexturePanel;

public class TextureEditorDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1497693934528315218L;
	
	private JList<ITexture> m_textureList;
	private TexturePanel m_texturePanel;
	
	private JButton m_closeButton;
	private JButton m_newTexButton;
	
	public TextureEditorDialog (Frame aFrame){
		super(aFrame, true);
		
		setLayout(new GridBagLayout());
	
		GridBagConstraints gbc_texPanel = new GridBagConstraints();
		GridBagConstraints gbc_texList  = new GridBagConstraints();
		GridBagConstraints gbc_closeBtn = new GridBagConstraints();
		GridBagConstraints gbc_newTexBtn= new GridBagConstraints();
		
		m_texturePanel = new TexturePanel();
		m_textureList  = new JList<ITexture>();
		
		m_closeButton  = new JButton("Close");
		m_closeButton.addActionListener(this);
		m_closeButton.setActionCommand("close");
		
		m_newTexButton = new JButton("New Texture");
		m_newTexButton.addActionListener(this);
		m_newTexButton.setActionCommand("newTex");
		
		this.add(m_textureList, gbc_texList);
		this.add(m_texturePanel, gbc_texPanel);
		this.add(m_closeButton, gbc_closeBtn);
		this.add(m_newTexButton, gbc_newTexBtn);
		
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
			JFileChooser fc = new JFileChooser();
			int ret = fc.showOpenDialog(this);
			
			if (ret == JFileChooser.APPROVE_OPTION){
				String filename = fc.getSelectedFile().getAbsolutePath();
				
				IBitmap bitmap = ResourceManager.getInstance().loadBitmap(filename);
				ITexture tex = ResourceManager.getInstance().createTexture(bitmap);
				
				m_texturePanel.setTexture(tex);
			}
			
			return;
		}
	} 
}
