package de.squareys.EpicRay.implementation;

import java.awt.Color;
import java.io.IOException;

import de.squareys.EpicRay.framework.IGameFile;
import de.squareys.EpicRay.framework.ITile;
import de.squareys.EpicRay.framework.ITileMap;

public class TileMap implements ITileMap {
	
	protected ITile m_tiles[];
	
	protected int m_width;
	protected int m_height;

	protected boolean m_editable;
	
	public TileMap(){
		m_width = 0;
		m_height = 0;
		
		m_editable = false;
		
		m_tiles = null;
	}
	
	public TileMap(int p_w, int p_h, boolean p_editable){ 
		m_width = p_w;
		m_height = p_h;
		
		m_editable = p_editable;
		
		m_tiles = new ITile[m_width * m_height];
		
		Tile air = new Tile(false, false, null);
		
		for (int i = 0; i < m_width * m_height; i++){
			m_tiles[i] = new Tile(air, false);
		}
	}
	
	public void autoGenerate(){
		EpicRayRenderingAttributes ra = new EpicRayRenderingAttributes();
		ra.m_wallColor = Color.blue.getRGB();
		ra.m_wallTexture = ResourceManager.getInstance().getTexture(0);
		ra.m_textured = true;
		EpicRayRenderingAttributes ra1 = new EpicRayRenderingAttributes();
		ra1.m_wallColor = Color.yellow.getRGB();
		
		Tile wall1Tile = new Tile(true, true, ra);
		Tile wall2Tile  = new Tile(true, true, ra1);
		Tile airTile = new Tile(false, false, ra);
		
		for (int i = 0; i < m_width * m_height; i++){
			if (i < m_width || i > m_width * (m_height-1) || i % m_width == 0 || i % m_width == m_width-1) {
				if (i % 10 == 0){
					m_tiles[i] = new Tile(wall1Tile, false);
				} else {
					m_tiles[i] = new Tile(wall2Tile, false);
				}
			} else if (i % 25 == 0){
				m_tiles[i] = new Tile(wall1Tile, false);
			} else {
				m_tiles[i] = new Tile(airTile, false);
			}
		}
	}

	@Override
	public boolean saveToFile(IGameFile gameFile) throws IOException {
		// TODO 
		
		gameFile.writeInt(m_width);
		gameFile.writeInt(m_height);
		
		for (ITile tile : m_tiles){
			gameFile.saveSaveable(tile);
		}
		return true;
	}

	@Override
	public boolean loadFromFile(IGameFile gameFile) throws IOException {
		// TODO 
		
		m_width = gameFile.readInt();
		m_height = gameFile.readInt();

		int length = m_width * m_height;
		m_tiles = new ITile[length];
		
		for ( int i = 0; i < length; i++){
			m_tiles[i] = (ITile) gameFile.readSaveable();
		}
		
		return true;
	}

	@Override
	public String getTag() {
		return "TileMap";
	}

	@Override
	public ITile getTileAt(int x, int y) {
		if (x >= m_width || y >= m_height || x < 0 || y < 0) {
			return null;
		} 
		
		return m_tiles[x + y * m_width];
	}

	@Override
	public void setTileAt(int x, int y, ITile tile) {
		m_tiles[x + y * m_width] = tile;
	}

	@Override
	public boolean isEditable() {
		return m_editable;
	}

	@Override
	public ITile getTileAtPos(double x, double y) {
		return getTileAt((int) x, (int) y);
	}

	@Override
	public void setTileAtPos(double x, double y, ITile tile) {
		setTileAt((int) x, (int) y, tile);
	}

	@Override
	public int getWidth() {
		return m_width;
	}

	@Override
	public int getHeight() {
		return m_height;
	}

}