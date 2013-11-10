package de.squareys.EpicRay.Texture;

import de.squareys.EpicRay.Bitmap.IBitmap;
import de.squareys.EpicRay.Cursor.BitmapCursor;
import de.squareys.EpicRay.Util.IDimension;
import de.squareys.EpicRay.Util.Tuple;

/**
 * ITexture interface for Textures in EpicRay A Texture is a subbitmap on a
 * parent Bitmap Should have an instance of the parent bitmap and dimensions
 * which define a region to what belongs to the Texture.
 * 
 * @author Squareys
 * 
 */
public interface ITexture extends IDimension {
	/**
	 * Returns the parent bitmap
	 * 
	 * @return
	 */
	public IBitmap<Integer> getParentBitmap();
	
	public int getPixel(int index);
	public int getPixel(int x, int y);
	
	public int pointToIndex(int x, int y);
	
	public Tuple<Integer, Integer> indexToPoint(int index);
	
	public BitmapCursor<Integer> getCursor();

}
