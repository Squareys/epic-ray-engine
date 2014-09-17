package de.squareys.epicray.gamelogic;

import de.squareys.epicray.resource.Saveable;

public interface ITileMap extends Saveable<ITileMap> {

	/**
	 * Returns Tile at given Position
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public ITile getTileAtPos(double x, double y);

	/**
	 * Set Tile at given Position
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public void setTileAtPos(double x, double y, ITile tile);

	/**
	 * Returns Tile at given tile coords
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public ITile getTileAt(int x, int y);

	/**
	 * sets Tile to 'tile' at given tile coords
	 * 
	 * @param x
	 * @param y
	 * @param tile
	 */
	public void setTileAt(int x, int y, ITile tile);

	/**
	 * Returns true if Map is changeable
	 * 
	 * @return
	 */
	public boolean isEditable();

	/**
	 * Returns the width in tiles
	 * 
	 * @return
	 */
	public int getWidth();

	/**
	 * Returns the height in tiles
	 * 
	 * @return
	 */
	public int getHeight();

}
