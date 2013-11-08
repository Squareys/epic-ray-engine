package de.squareys.EpicRay.framework;

public abstract class AbstractBitmap<T> implements IBitmap<T> {
	@Override
	public void drawRect(int p_x, int p_y, int p_x2, int p_y2, T p_col) {
		int index;

		for (int x = p_x; x < p_x2; x++) {
			index = this.pointToIndex(x, 0);
			for (int y = p_y; p_y < p_y2; y++, index++) {
				putPixel(index, p_col);
			}
		}

	}

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
	public void drawArc(int x, int y, int radius, float angle, T value) {

	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2, T value) {

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
