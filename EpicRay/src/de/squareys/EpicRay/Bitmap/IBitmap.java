package de.squareys.EpicRay.Bitmap;

import de.squareys.EpicRay.Cursor.BitmapCursor;
import de.squareys.EpicRay.Resource.IResource;
import de.squareys.EpicRay.Util.Tuple;

public interface IBitmap<T> extends IResource<IBitmap<T>>, IDrawable<T> {

	/**
	 * Get index of pixel at x, y
	 * 
	 * @param x
	 *            x value of pixel
	 * @param y
	 *            y value of pixel
	 * @return
	 */
	public int pointToIndex(int x, int y);

	/**
	 * Get position of pixel in index
	 * 
	 * @param index
	 *            index of pixel
	 * @return
	 */
	public Tuple<Integer, Integer> indexToPoint(int index);

	/**
	 * Returns width of Bitmap
	 * 
	 * @return
	 */
	public int getWidth();

	/**
	 * Returns height of Bitmap
	 * 
	 * @return
	 */
	public int getHeight();

	/**
	 * Draw an other Bitmap on this Bitmap
	 * 
	 * @param bitmap
	 *            Bitmap to draw on this bitmap
	 * @param destX
	 *            where to start drawing it
	 * @param destY
	 */
	public void drawBitmap(IBitmap<T> bitmap, int destX, int destY);

	/**
	 * Get Color of pixel at position x, y
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public T getPixel(int x, int y);

	/**
	 * Get Color of pixel at index
	 * 
	 * @param index
	 * @return
	 */
	public T getPixel(int index);

	/**
	 * Set pixel at position x, y to color
	 * 
	 * @param x
	 * @param y
	 * @param color
	 */
	public void putPixel(int x, int y, T value);

	/**
	 * Set pixel at index to color
	 * 
	 * @param index
	 * @param color
	 */
	public void putPixel(int index, T value);

	/**
	 * Get a cursor on this bitmap
	 * 
	 * @return
	 */
	public BitmapCursor<T> getCursor();

	/**
	 * Get the length of the bitmap, usually this is width*height
	 * 
	 * @return
	 */
	public int getLength();

}
