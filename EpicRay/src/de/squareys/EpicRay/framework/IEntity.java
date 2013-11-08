package de.squareys.EpicRay.framework;

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
	public double getX();

	/**
	 * Returns the Entities y position
	 * 
	 * @return
	 */
	public double getY();

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
	public double getViewDirectionX();

	/**
	 * Get the Y direction the entity is looking
	 * 
	 * @return
	 */
	public double getViewDirectionY();
}
