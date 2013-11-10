package de.squareys.EpicRay.Rendering;

import de.squareys.EpicRay.GameLogic.ITileMap;

/**
 * IRay Interface
 * 
 * A ray to be cast through a tilemap.
 * 
 * @author Squareys
 * 
 */
public interface IRay {

	/**
	 * Get the Rays x position
	 * 
	 * @return
	 */
	public double getX();

	/**
	 * Get the Rays y position
	 * 
	 * @return
	 */
	public double getY();

	/**
	 * Get the Rays direction x component
	 * 
	 * @return
	 */
	public double getDirX();

	/**
	 * Get the Rays direction y component
	 * 
	 * @return
	 */
	public double getDirY();

	/**
	 * Get the distance traveled
	 * 
	 * @return
	 */
	public double getLength();

	/**
	 * Get the length of the rendered stripe of pixels
	 * 
	 * @return
	 */
	public int getPixelHeight();

	/**
	 * Cast the Ray in a world
	 * 
	 */
	public void cast(ITileMap map);
}
