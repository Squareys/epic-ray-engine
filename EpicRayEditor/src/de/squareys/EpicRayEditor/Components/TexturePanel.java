package de.squareys.EpicRayEditor.Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JPanel;

import de.squareys.EpicRay.framework.Bitmap;
import de.squareys.EpicRay.framework.FastIntBitmap;
import de.squareys.EpicRay.framework.ITexture;

public class TexturePanel extends JPanel {
	private ITexture m_texture;
	private FastIntBitmap m_bitmap;
	
	private BufferedImage m_img;
		
	public TexturePanel(){
		m_texture = null;
	}
	
	public TexturePanel(ITexture texture){
		setTexture(texture);
	}
	
	public void setTexture(ITexture texture){
		m_texture = texture;
		
		int w = m_texture.getParentBitmap().getWidth();
		int h = m_texture.getParentBitmap().getHeight();
		
		setPreferredSize(new Dimension(w, h));
		
		m_bitmap = new FastIntBitmap(w, h);
		m_img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);		
		m_bitmap.m_pixels = ((DataBufferInt) m_img.getRaster().getDataBuffer()).getData();
	}
	
	@Override
	public void paint(Graphics g){
		present();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(m_img, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
	}

	public void present() {
		if (m_bitmap == null) {
			return;
		}
		
		m_bitmap.clear(Color.white.getRGB());
		
		if (m_texture != null) {		
			m_bitmap.drawBitmap(m_texture.getParentBitmap(), 0, 0);
		} 
	}
}
