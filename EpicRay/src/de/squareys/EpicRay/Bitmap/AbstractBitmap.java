package de.squareys.EpicRay.Bitmap;


public abstract class AbstractBitmap<T> implements IBitmap<T> {

	@Override
	public void drawOnto(IBitmap<T> bitmap) {

	}

	@Override
	public void drawHorizontalStripe(IBitmap<T> dest, int x, int y, int length) {

	}

	@Override
	public void drawVerticalStripe(IBitmap<T> dest, int x, int y, int length) {

	}

	@Override
	public void drawBitmap(IBitmap<T> bitmap, int destX, int destY) {
		int maxX = Math.max(bitmap.getWidth() + destX, getWidth());
		int maxY = Math.max(bitmap.getHeight() + destY, getHeight());

		for (int x = 0; x < maxX; x++) {
			for (int y = 0; y < maxY; y++) {
				putPixel(x + destX, y + destY, bitmap.getPixel(x, y));
			}
		}
	}

	@Override
	public void clear(T col) {
		for (int i = 0; i < getLength(); i++) {
			putPixel(i, col);
		}
	}

	@Override
	public int getLength() {
		return getWidth() * getHeight();
	}
}
