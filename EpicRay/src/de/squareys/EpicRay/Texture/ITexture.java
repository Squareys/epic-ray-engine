package de.squareys.EpicRay.Texture;

import de.squareys.EpicRay.Bitmap.IBitmap;

/**
 * ITexture interface for Textures in EpicRay A Texture is a subbitmap on a
 * parent Bitmap Should have an instance of the parentbitmap and dimensions
 * which define a region to what belongs to the Texture.
 * 
 * @author Squareys
 * 
 */
public interface ITexture extends IBitmap<Integer> {
	/**
	 * Returns the parent bitmap
	 * 
	 * @return
	 */
	public IBitmap<Integer> getParentBitmap();

}
