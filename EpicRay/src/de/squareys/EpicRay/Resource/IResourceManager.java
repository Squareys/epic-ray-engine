package de.squareys.EpicRay.Resource;

import de.squareys.EpicRay.Bitmap.IBitmap;

/**
 * ResourceManager
 * 
 * loads and keeps track of all the ressources/assets needed in our game.
 * 
 * TODO Texture handling is probably not to well done here.
 * 
 * @author Squareys
 */

public interface IResourceManager {

	/**
	 * Loads a sound resource
	 * 
	 * @param filename
	 * @return
	 */
	public ISound loadSound(String filename);

	/**
	 * Loads a bitmap resource
	 * 
	 * @param filename
	 * @return
	 */
	public IBitmap<Integer> loadBitmap(String filename);

	/**
	 * Get ITexture by Index
	 * 
	 * @param index
	 * @return
	 */
	public IBitmap<Integer> getBitmap(int index);
}
