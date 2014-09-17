package de.squareys.epicray.bitmap;

public interface IDrawable<T> {
	/**
	 * draw this IDrawable on a bitmap
	 * 
	 * @param bitmap
	 */
	public void drawOnto(IBitmap<T> bitmap);

	/**
	 * Draw a horizontal Stripe of the bitmaps pixels onto dest (the destiny
	 * Bitmap).
	 * 
	 * @param dest
	 * @param x
	 * @param y
	 * @param length
	 */
	public void drawHorizontalStripe(IBitmap<T> dest, int x, int y, int length);

	/**
	 * Draw a vertical Stripe of the bitmaps pixels onto dest
	 * 
	 * @param dest
	 * @param x
	 * @param y
	 * @param length
	 */
	public void drawVerticalStripe(IBitmap<T> dest, int x, int y, int length);
	
	/**
	 * Fills the hole bitmap with specific color
	 * 
	 * @param col
	 */
	public void clear(T col);
}
