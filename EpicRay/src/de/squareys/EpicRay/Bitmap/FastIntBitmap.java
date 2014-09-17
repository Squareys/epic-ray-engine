package de.squareys.epicray.bitmap;

import de.squareys.epicray.cursor.BitmapCursor;
import de.squareys.epicray.cursor.FastIntBitmapCursor;
import de.squareys.epicray.util.Tuple;

/**
 * A Vertical First Bitmap
 * 
 * @author Squareys
 * 
 */

public class FastIntBitmap extends AbstractBitmap<Integer> {

	private final int m_width;
	private final int m_height;
	private final int m_length;

	public int m_pixels[];

	public FastIntBitmap(final int width, final int height) {
		m_width = width;
		m_height = height;
		m_length = m_width * m_height;

		m_pixels = new int[m_length];
	}

	@Override
	public int pointToIndex(final int x, final int y) {
		return (x * m_height + y);
	}

	@Override
	public Tuple<Integer, Integer> indexToPoint(final int index) {
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
	public Integer getPixel(final int x, final int y) {
		return m_pixels[pointToIndex(x, y)];
	}

	@Override
	public Integer getPixel(final int index) {
		return new Integer(m_pixels[index]);
	}

	@Override
	public void putPixel(final int x, final int y, final Integer color) {
		m_pixels[pointToIndex(x, y)] = color;
	}

	@Override
	public void putPixel(final int index, final Integer color) {
		m_pixels[index] = color;
	}

	@Override
	public void clear(final Integer col) {
		final int c = col; // no conversion every time.

		for (int i = 0; i < m_length; ++i) {
			m_pixels[i] = c;
		}
	}

	@Override
	public BitmapCursor<Integer> getCursor() {
		return new FastIntBitmapCursor(this);
	}

	@Override
	public final int getLength() {
		return m_length;
	}

	@Override
	public IBitmap<Integer> loadFromFile(String filename) {
		return null;
	}
}
