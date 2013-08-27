package de.squareys.EpicRay.framework;

/**
 * TODO: Think about whether to extend Saveable
 * @author Squareys
 *
 */
public interface ISprite {
	
	/**
	 * Returns x position of sprite
	 * @return
	 */
	public int getX();
	
	/**
	 * Returns y position of sprite
	 * @return
	 */
	public int getY();
	
	/**
	 * Returns width of sprite
	 * @return
	 */
	public int getWidth();
	
	/**
	 * Returns height of sprite
	 * @return
	 */
	public int getHeight();

	/**
	 * Returns the sprites texture
	 * @return
	 */
	public ITexture getTexture();
}
