package de.squareys.epicray.gamelogic;

import java.util.List;

import de.squareys.epicray.resource.Saveable;

public interface IWorld extends Saveable<IWorld>, Updateable {
	/**
	 * Returns TileMap of World
	 * 
	 * @return
	 */
	public ITileMap getTileMap();

	/**
	 * Returns List of Entities currently in World
	 * 
	 * @return
	 */
	public List<IEntity> getEntities();
}
