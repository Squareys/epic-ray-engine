package de.squareys.epicray.gamelogic;

import java.util.List;

import de.squareys.epicray.rendering.IRenderingAttributes;
import de.squareys.epicray.rendering.ISprite;
import de.squareys.epicray.resource.Saveable;

public interface ITile extends Saveable<ITile> {

	/**
	 * Returns true if Tile does not let any light pass through. (no transparent
	 * parts/holes)
	 * 
	 * @return
	 */
	public boolean isOpaque();

	/**
	 * Returns true if tile blocks movement.
	 * 
	 * @return
	 */
	public boolean isSolid();

	/**
	 * Sets opaque property
	 * 
	 * @param b
	 */
	public void setOpaque(boolean b);

	/**
	 * Sets solid property
	 * 
	 * @param b
	 */

	public void setSolid(boolean b);

	/**
	 * Returns name of Tile
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * Returns typeId of Tile
	 * 
	 * @return
	 */
	public String getTypeId();

	/**
	 * Returns List of Sprites decorating this Tile
	 * 
	 * @return
	 */
	public List<ISprite> getSprites();

	/**
	 * Returns List of Entities currently in this Tile
	 * 
	 * @return
	 */
	public List<IEntity> getEntities();

	/**
	 * Adds an Entity on the Tile Should also set the entity's current tile.
	 * 
	 * @param entity
	 */
	public void addEntity(IEntity entity);

	/**
	 * Adds a decorating Sprite to the Tile
	 * 
	 * @param sprite
	 */
	public void addSprite(ISprite sprite);

	/**
	 * Gets the Rendering Attributes of this Tile
	 * 
	 * @return the Rendering Attributes of this Tile
	 */
	public IRenderingAttributes getRenderingAttributes();

	/**
	 * Sets the Rendering Attributes of this tile
	 */
	public void setRenderingAttributes(IRenderingAttributes ra);

	/**
	 * Sets the Tiles name.
	 * 
	 * @param text
	 */
	public void setName(String text);

}
