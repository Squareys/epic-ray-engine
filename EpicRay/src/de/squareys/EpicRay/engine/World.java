package de.squareys.epicray.engine;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import de.squareys.epicray.gamelogic.IEntity;
import de.squareys.epicray.gamelogic.ITileMap;
import de.squareys.epicray.gamelogic.IWorld;
import de.squareys.epicray.resource.IGameFile;

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
	public void onUpdate(float timeDiff) {
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
