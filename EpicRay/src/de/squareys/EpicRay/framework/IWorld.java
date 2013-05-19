package de.squareys.EpicRay.framework;

import java.util.List;

public interface IWorld extends Saveable<IWorld>, Updateable {
	/**
	 * Returns TileMap of World
	 * @return
	 */
	public ITileMap getTileMap();
	
	/**
	 * Returns List of Entities currently in World
	 * @return
	 */
	public List<IEntity> getEntities();
}
