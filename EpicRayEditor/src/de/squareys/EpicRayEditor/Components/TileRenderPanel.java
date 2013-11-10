package de.squareys.EpicRayEditor.Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JPanel;

import de.squareys.EpicRay.Bitmap.FastIntBitmap;
import de.squareys.EpicRay.EpicRay.EpicRayRenderingAttributes;
import de.squareys.EpicRay.GameLogic.ITile;

public class TileRenderPanel extends JPanel {

	private ITile m_tile;
	private FastIntBitmap m_bitmap;
	
	private BufferedImage m_img;
	
	private int m_size;
	
	public TileRenderPanel(int w){
		m_size = w;
		setPreferredSize(new Dimension(w, w));
		
		m_bitmap = new FastIntBitmap(w, w);

		m_img = new BufferedImage(w, w, BufferedImage.TYPE_INT_RGB);		
		m_bitmap.m_pixels = ((DataBufferInt) m_img.getRaster().getDataBuffer()).getData();
	}
	
	public void setTile(ITile tile){
		m_tile = tile;
		repaint();
	}
	
	@Override
	public void paint(Graphics g){
		present();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(m_img, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
	}

	public void present() {
		m_bitmap.clear(Color.white.getRGB());
		
		if (m_tile != null) {		
			int halfSize = (int) Math.floor(m_size /2);
			int quatSize = (int) Math.floor(m_size /4);
			//draw floor
			EpicRayRenderingAttributes ra = (EpicRayRenderingAttributes) m_tile.getRenderingAttributes();
			for (int x = 0; x <= halfSize; x++){
				for ( int y = halfSize+quatSize - x/2; y < halfSize+quatSize+x/2; y++){
					m_bitmap.putPixel(y, x, ra.m_floorColor);
					m_bitmap.putPixel(y, m_size-x, ra.m_floorColor);
				}
			
				if (ra.m_wallColor != -1){
					for ( int y = quatSize+x/2; y < halfSize+quatSize+x/2; y++){
						m_bitmap.putPixel(y, x, ra.m_wallColor);
						m_bitmap.putPixel(y, m_size-x, ra.m_wallColor);
					}
				}
				
				for ( int y = quatSize - x/2; y < quatSize+x/2; y++){
					m_bitmap.putPixel(y, x, ra.m_ceilColor);
					m_bitmap.putPixel(y, m_size-x, ra.m_ceilColor);
				}
			}
		} 
	}
}
