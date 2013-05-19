package de.squareys.EpicRayEditor.Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import de.squareys.EpicRay.framework.ITile;
import de.squareys.EpicRay.framework.ITileMap;
import de.squareys.EpicRay.implementation.EpicRayRenderingAttributes;
import de.squareys.EpicRay.implementation.Tile;

public class TileMapView2D extends JPanel implements MouseListener {

	protected ITileMap m_tileMap;
	
	private ITile m_brushTile;
	
	private int m_tileW;
	private int m_tileH;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4704804691541365251L;
	
	
	public TileMapView2D(ITileMap map){
		super();
		
		m_tileW = 32;
		m_tileH = 32;
		
		m_tileMap = map;
		
		EpicRayRenderingAttributes ra = new EpicRayRenderingAttributes();
		ra.m_wallColor = Color.ORANGE.getRGB();
		m_brushTile = new Tile(true, true, ra);
		
		setPreferredSize(new Dimension(m_tileW * m_tileMap.getWidth(), m_tileH * m_tileMap.getHeight()));
		
		addMouseListener(this);
	}

	@Override
	public void paint(final Graphics g){
		super.paint(g);
		
		if (m_tileMap == null) return;
		
		/*BufferedImage buffer = new BufferedImage(m_tileW * m_tileMap.getWidth(), m_tileH * m_tileMap.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2D = (Graphics2D) buffer.getGraphics(); */
		
		for (int y = 0; y < m_tileMap.getHeight(); y++){
			for (int x = 0; x < m_tileMap.getWidth(); x++){
				ITile tile = m_tileMap.getTileAt(x, y);
				
				EpicRayRenderingAttributes ra = (EpicRayRenderingAttributes) tile.getRenderingAttributes();
				
				if ( ra == null) continue;
				g.setColor(new Color(ra.m_wallColor));
				g.fillRect(x*m_tileW, y*m_tileH, m_tileW, m_tileH);
			}
		}
		
		//((Graphics2D) g).drawImage(buffer, null, 0, 0);
	}
	
	public void setTileMap(ITileMap tileMap){
		this.m_tileMap = tileMap;
	}
	
	public ITileMap getTileMap(){
		return m_tileMap;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point p = e.getPoint();
		
		m_tileMap.setTileAt(p.x / m_tileW, p.y / m_tileH, m_brushTile);
		
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void setBrushTile(ITile tile){
		m_brushTile = tile;
	}

}
