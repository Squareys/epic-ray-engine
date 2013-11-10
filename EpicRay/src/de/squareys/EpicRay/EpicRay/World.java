package de.squareys.EpicRay.EpicRay;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import de.squareys.EpicRay.GameLogic.IEntity;
import de.squareys.EpicRay.GameLogic.ITileMap;
import de.squareys.EpicRay.GameLogic.IWorld;
import de.squareys.EpicRay.Resource.IGameFile;

public class World implements IWorld {

	protected ITileMap m_tileMap;
	protected Vector<IEntity> m_entities;
	
	public World(ITileMap p_tileMap){
		m_tileMap = p_tileMap;
	}
	
	@Override
	public boolean saveToFile(IGameFile gameFile) throws IOException {
	    gameFile.saveSaveable(m_tileMap);
		return true;
	}

	@Override
	public boolean loadFromFile(IGameFile gameFile) throws IOException {
		m_tileMap = (ITileMap) gameFile.readSaveable();
		return true;
	}

	@Override
	public String getTag() {
		return "World";
	}

	@Override
	public void onUpdate() {
		//Do nothing yet
	}

	@Override
	public ITileMap getTileMap() {
		return m_tileMap;
	}

	@Override
	public List<IEntity> getEntities() {
		return m_entities;
	}

}
