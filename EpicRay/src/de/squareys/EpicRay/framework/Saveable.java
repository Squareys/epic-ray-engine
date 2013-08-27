package de.squareys.EpicRay.framework;

import java.io.IOException;

/**
 * Interface for Classes saveable by 
 * the IGameFile implementations
 * 
 * @author Squareys
 *
 * @param <T>
 */
public interface Saveable<T> {
	/**
	 * Save to a IGameFile
	 * @param gameFile
	 * @return
	 */
	public boolean saveToFile(IGameFile gameFile) throws IOException;
	
	/**
	 * Load from a IGameFile
	 * @param gameFile
	 * @return
	 */
	public boolean loadFromFile(IGameFile gameFile) throws IOException;
	
	/**
	 * Returns saving tag of Saveable
	 * example: a Wall tile could return
	 * "Wall Tile"
	 * @return
	 */
	public String getTag();
}
