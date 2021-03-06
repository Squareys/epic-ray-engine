package de.squareys.epicray.gamelogic;

import de.squareys.epicray.rendering.ISprite;

/**
 * Interface for Entities
 * 
 * @author Squareys
 * 
 */
public interface IEntity extends Collideable, Updateable {

	/**
	 * Returns the sprite connected to this entity
	 * 
	 * @return
	 */
	public ISprite getSprite();

	/**
	 * Returns the Entities x position
	 * 
	 * @return
	 */
	public float getX();

	/**
	 * Returns the Entities y position
	 * 
	 * @return
	 */
	public float getY();

	/**
	 * Returns the Entities width
	 * 
	 * @return
	 */
	public int getWidth();

	/**
	 * Returns the Entities height
	 * 
	 * @return
	 */
	public int getHeight();

	/**
	 * Returns the world the Entity is in
	 * 
	 * @return
	 */
	public IWorld getWorld();

	/**
	 * Returns the tile the Entitiy is currently in.
	 * 
	 * @return
	 */
	public ITile getCurrentTile();

	/**
	 * Tells the Tile in which tile it is
	 * 
	 * @param Tile
	 *            so set the current Tile of the Entity to
	 */
	public void setCurrentTile(ITile tile);

	/**
	 * Get the X direction the entity is looking
	 */
	public float getViewDirectionX();

	/**
	 * Get the Y direction the entity is looking
	 * 
	 * @return
	 */
	public float getViewDirectionY();
}
