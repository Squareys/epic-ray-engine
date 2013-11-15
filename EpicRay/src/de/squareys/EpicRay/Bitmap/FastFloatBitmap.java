package de.squareys.EpicRay.Bitmap;

import de.squareys.EpicRay.Cursor.BitmapCursor;
import de.squareys.EpicRay.Cursor.FastFloatBitmapCursor;
import de.squareys.EpicRay.Util.Tuple;

/**
 * A Vertical First Bitmap
 * 
 * @author Squareys
 * 
 */

public class FastFloatBitmap implements IBitmap<Float> {

	private final int m_width;
	private final int m_height;
	private final int m_length;

	public final float m_pixels[];

	public FastFloatBitmap(final int width, final int height) {
		m_width = width;
		m_height = height;
		m_length = m_width * m_height;

		m_pixels = new float[m_length];
	}

	@Override
	public final IBitmap<Float> loadFromFile(String filename) {
		return null;
	}

	@Override
	public final void drawOnto(IBitmap<Float> bitmap) {
	}

	@Override
	public final void drawHorizontalStripe(IBitmap<Float> dest, int x, int y,
			int length) {
	}

	@Override
	public final void drawVerticalStripe(IBitmap<Float> dest, int x, int y, int length) {
	}

	@Override
	public final int pointToIndex(int x, int y) {
		return (x * m_height + y);
	}

	@Override
	public final Tuple<Integer, Integer> indexToPoint(int index) {
		int y = index % m_height;
		int x = (index - y) / m_height;

		return new Tuple<Integer, Integer>(x, y);
	}

	@Override
	public final int getWidth() {
		return m_width;
	}

	@Override
	public final int getHeight() {
		return m_height;
	}

	@Override
	public void drawBitmap(IBitmap<Float> bitmap, int destX, int destY) {
		// TODO: What the hell is this? Performance...
		int maxX = Math.max(bitmap.getWidth() + destX, getWidth());
		int maxY = Math.max(bitmap.getHeight() + destY, getHeight());

		for (int x = 0; x < maxX; x++) {
			for (int y = 0; y < maxY; y++) {
				putPixel(x + destX, y + destY, bitmap.getPixel(x, y));
			}
		}
	}

	@Override
	public final Float getPixel(int x, int y) {
		return m_pixels[pointToIndex(x, y)];
	}

	@Override
	public final Float getPixel(int index) {
		return new Float(m_pixels[index]);
	}

	@Override
	public final void putPixel(int x, int y, Float color) {
		m_pixels[pointToIndex(x, y)] = color;
	}

	@Override
	public final void putPixel(int index, Float color) {
		m_pixels[index] = color;
	}

	@Override
	public final void clear(final Float col) {
		final float c = col; // no conversion every time.

		for (int i = 0; i < m_length; ++i) {
			m_pixels[i] = c;
		}
	}

	@Override
	public final BitmapCursor<Float> getCursor() {
		return new FastFloatBitmapCursor(this);
	}

	@Override
	public final int getLength() {
		return m_length;
	}
}
