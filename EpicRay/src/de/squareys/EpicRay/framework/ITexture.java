package de.squareys.EpicRay.framework;

/**
 * ITexture interface for Textures in EpicRay
 * A Texture is a subbitmap on a parent Bitmap
 * Should have an instance of the parentbitmap 
 * and dimensions which define a region to 
 * what belongs to the Texture.
 * 
 * @author Squareys
 *
 */
public interface ITexture extends IBitmap {
	/**
	 * Returns the parent bitmap
	 * @return
	 */
	public IBitmap getParentBitmap();

}
