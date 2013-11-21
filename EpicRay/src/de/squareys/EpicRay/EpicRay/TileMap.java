package de.squareys.EpicRay.EpicRay;

import java.awt.Color;
import java.io.IOException;

import de.squareys.EpicRay.GameLogic.ITile;
import de.squareys.EpicRay.GameLogic.ITileMap;
import de.squareys.EpicRay.Resource.IGameFile;

public class TileMap implements ITileMap {
	
	private ITile m_tiles[];
	
	private int m_width;
	private int m_height;

	private boolean m_editable;
	
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
		
		EpicRayRenderingAttributes air_ra = new EpicRayRenderingAttributes();
		air_ra.m_wallColor = -1;
		air_ra.m_ceilColor = air_ra.m_floorColor = Color.DARK_GRAY.getRGB();
		
		Tile air = new Tile("TileMapAir", false, false, air_ra);
		
		for (int i = 0; i < m_width * m_height; i++){
			m_tiles[i] = new Tile(air, false);
		}
	}
	
	public final void autoGenerate(){
		EpicRayRenderingAttributes ra = new EpicRayRenderingAttributes();
		ra.m_wallColor = Color.blue.getRGB();
		ra.m_wallTexture = ResourceManager.getInstance().getBitmap(0);
		ra.m_floorColor = Color.white.getRGB();
		ra.m_textured = true;
		
		EpicRayRenderingAttributes ra0 = new EpicRayRenderingAttributes();
		ra0.m_textured = true;
		ra0.m_floorTexture = ResourceManager.getInstance().getBitmap(2);
		ra0.m_ceilTexture = ResourceManager.getInstance().getBitmap(1);
		
		EpicRayRenderingAttributes ra1 = new EpicRayRenderingAttributes();
		ra1.m_wallColor = Color.yellow.getRGB();
		ra1.m_textured = true;
		ra1.m_wallTexture = ResourceManager.getInstance().getBitmap(1);
		
		Tile wall1Tile = new Tile("TileMapGenWall1", true, true, ra); //Wall
		Tile wall2Tile  = new Tile("TileMapGenWall2", true, true, ra1);
		Tile airTile = new Tile("TileMapGenAir", false, false, ra0); 
		Tile floorTile = new Tile("TileMapGenFloor", false, false, ra0);
		
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
				if (i % 2 == 0){
					m_tiles[i] = new Tile(airTile, false);
				} else {
					m_tiles[i] = new Tile(floorTile, false);
				}
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
	public final ITile getTileAt(int x, int y) {
//		if (x >= m_width || y >= m_height || x < 0 || y < 0) {
//			return null;
//		} 
		
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
	public final ITile getTileAtPos(double x, double y) {
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
