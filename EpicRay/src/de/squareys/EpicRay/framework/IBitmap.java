package de.squareys.EpicRay.framework;



public interface IBitmap<T> extends IResource<IBitmap<T>>, IDrawable<T> {

	/**
	 * Get index of pixel at x, y
	 * @param x x value of pixel
	 * @param y y value of pixel
	 * @return
	 */
	public int pointToIndex(int x, int y);
	
	/**
	 * Get position of pixel in index
	 * @param index index of pixel
	 * @return
	 */
	public Tuple<Integer, Integer> indexToPoint(int index);
	
	/**
	 * Returns width of Bitmap
	 * @return
	 */
	public int getWidth();
	
	/**
	 * Returns height of Bitmap
	 * @return
	 */
	public int getHeight();
	
	/**
	 * Draw an other Bitmap on this Bitmap
	 * @param bitmap Bitmap to draw on this bitmap
	 * @param destX where to start drawing it
	 * @param destY
	 */
	public void drawBitmap(IBitmap<T> bitmap, int destX, int destY);
	
	/**
	 * Get Color of pixel at position x, y
	 * @param x
	 * @param y
	 * @return
	 */
	public T getPixel(int x, int y);
	
	/**
	 * Get Color of pixel at index 
	 * @param index
	 * @return
	 */
	public T getPixel(int index);
	
	/**
	 * Set pixel at position x, y to color
	 * @param x
	 * @param y
	 * @param color
	 */
	public void putPixel(int x, int y, T value);
	
	/**
	 * Set pixel at index to color
	 * @param index
	 * @param color
	 */
	public void putPixel(int index, T value);
	
	/**
	 * Draw a rectangle from (x/y) to (x2/y2)
	 * @param x
	 * @param y
	 * @param color 
	 * @param w
	 * @param h
	 */
	public void drawRect(int x, int y, int x2, int y2, T value);
	
	/**
	 * Draw a Arc around (x/y) with radius and angle
	 * @param x
	 * @param y
	 * @param r
	 */
	public void drawArc(int x, int y, int radius, float angle, T value);
	
	/**
	 * Draw a Line from (x1/y1) to (x2/y2)
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void drawLine(int x1, int y1, int x2, int y2, T value);
	
	/**
	 * Fills the hole bitmap with specific color
	 * @param col
	 */
	public void clear(T col);

	/**
	 * Get a cursor on this bitmap
	 * @return
	 */
	public BitmapCursor<T> getCursor();
	
}
