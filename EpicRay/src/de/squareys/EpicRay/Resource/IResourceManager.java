package de.squareys.EpicRay.Resource;

import de.squareys.EpicRay.Bitmap.IBitmap;
import de.squareys.EpicRay.Texture.ITexture;

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
	public IBitmap loadBitmap(String filename);

	/**
	 * Creates a texture as a subbitmap on a bitmap
	 * 
	 * @param bitmap
	 * @param startx
	 * @param starty
	 * @param endx
	 * @param endy
	 * @return
	 */
	public ITexture createTexture(IBitmap<Integer> bitmap, int startx, int starty,
			int endx, int endy);

	/**
	 * Creates a texture from a complete Bitmap
	 * 
	 * @param bitmap
	 * @return
	 */
	public ITexture createTexture(IBitmap<Integer> bitmap);

	/**
	 * Get ITexture by Index
	 * 
	 * @param index
	 * @return
	 */
	public ITexture getTexture(int index);
}
