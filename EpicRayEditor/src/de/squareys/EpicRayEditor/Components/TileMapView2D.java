package de.squareys.EpicRayEditor.Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import de.squareys.EpicRay.framework.ITile;
import de.squareys.EpicRay.framework.ITileMap;
import de.squareys.EpicRay.implementation.EpicRayRenderingAttributes;
import de.squareys.EpicRay.implementation.Tile;

public class TileMapView2D extends JPanel implements MouseListener, MouseMotionListener {

	protected ITileMap m_tileMap;
	
	private ITile m_brushTile;
	
	private int m_tileW;
	private int m_tileH;
	
	private int m_mouseX;
	private int m_mouseY;
	
	private boolean m_gotMouse;
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
		ra.m_floorColor = Color.BLUE.getRGB();
		ra.m_ceilColor = Color.DARK_GRAY.getRGB();
		
		m_brushTile = new Tile(true, true, ra);
		
		setPreferredSize(new Dimension(m_tileW * m_tileMap.getWidth(), m_tileH * m_tileMap.getHeight()));
		
		m_mouseX = -1;
		m_mouseY = -1;
		
		m_gotMouse = false;
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void paint(final Graphics g){
		super.paint(g);
		
		if (m_tileMap == null) return;
		
		/*BufferedImage buffer = new BufferedImage(m_tileW * m_tileMap.getWidth(), m_tileH * m_tileMap.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2D = (Graphics2D) buffer.getGraphics(); */
		
		g.clearRect(0, 0, getWidth(), getHeight());
		
		for (int y = 0; y < m_tileMap.getHeight(); y++){
			for (int x = 0; x < m_tileMap.getWidth(); x++){
				ITile tile = m_tileMap.getTileAt(x, y);
				
				EpicRayRenderingAttributes ra = (EpicRayRenderingAttributes) tile.getRenderingAttributes();
				
				if (ra == null) continue;
				g.setColor(new Color(ra.m_wallColor));
				g.fillRect(x*m_tileW, y*m_tileH, m_tileW, m_tileH);
			}
		}
		
		if	(m_gotMouse){
			//draw outline for current tile:			
			g.setColor(Color.darkGray);
			g.drawRect(m_mouseX, m_mouseY, m_tileW, m_tileH);
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
	public void mouseEntered(MouseEvent evt) {
		m_gotMouse = true;
		
		m_mouseX = evt.getX();
		m_mouseY = evt.getY();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		m_gotMouse = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point p = e.getPoint();
		
		m_tileMap.setTileAt(p.x / m_tileW, p.y / m_tileH, m_brushTile);
		
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
	public void setBrushTile(ITile tile){
		m_brushTile = tile;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (!m_gotMouse) {
			return;
		}
		
		Point p = e.getPoint();
		
		int x = e.getX();
		m_mouseX = x - (x % m_tileW);
		
		int y = e.getY();
		m_mouseY = y - (y % m_tileH);
		
		m_tileMap.setTileAt(p.x / m_tileW, p.y / m_tileH, m_brushTile);
		
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
		if (!m_gotMouse) {
			return;
		}
		
		int x = evt.getX();
		x = x - (x % m_tileW);
		
		int y = evt.getY();
		y = y - (y % m_tileH);
		
		if (m_mouseX != x || m_mouseY != y){
			m_mouseX = x;
			m_mouseY = y;
		
			repaint();
		}
	}

}
