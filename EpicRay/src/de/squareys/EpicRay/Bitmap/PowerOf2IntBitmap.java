package de.squareys.epicray.bitmap;

import de.squareys.epicray.util.Tuple;

public class PowerOf2IntBitmap extends FastIntBitmap {

	private final int m_hExp;
	//private final int m_wExp;

	public PowerOf2IntBitmap(final int width, final int height) {
		super(width, height);

		//m_wExp = (int) (Math.log(width) / Math.log(2));
		m_hExp = (int) (Math.log(height) / Math.log(2));
	}

	@Override
	public final int pointToIndex(final int x, final int y) {
		return ((x << m_hExp) + y);
	}

	@Override
	public final Tuple<Integer, Integer> indexToPoint(final int index) {
		int y = index % getHeight();
		int x = (index - y) >> m_hExp;

		return new Tuple<Integer, Integer>(x, y);
	}

	@Override
	public final Integer getPixel(final int x, final int y) {
		return m_pixels[((x << m_hExp) + y)];
	}

	@Override
	public final void putPixel(final int x, final int y, final Integer color) {
		m_pixels[((x << m_hExp) + y)] = color;
	}
}