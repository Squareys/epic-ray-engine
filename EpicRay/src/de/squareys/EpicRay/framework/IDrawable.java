package de.squareys.EpicRay.framework;

public interface IDrawable {
	/**
	 * draw this IDrawable on a bitmap
	 * @param bitmap
	 */
	public void drawOnto(IBitmap bitmap);
	
	/**
	 * Draw a horizontal Stripe of the bitmaps pixels onto dest
	 * (the destiny Bitmap).
	 * @param dest
	 * @param x
	 * @param y
	 * @param length
	 */
	public void drawHorizontalStripe(IBitmap dest, int x, int y, int length);
	
	/**
	 * Draw a vertical Stripe of the bitmaps pixels onto dest
	 * @param dest
	 * @param x
	 * @param y
	 * @param length
	 */
	public void drawVerticalStripe(IBitmap dest, int x, int y, int length);
}
