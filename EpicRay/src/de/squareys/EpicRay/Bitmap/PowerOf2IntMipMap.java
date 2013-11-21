package de.squareys.EpicRay.Bitmap;

import de.squareys.EpicRay.Cursor.BitmapCursor;
import de.squareys.EpicRay.Util.Tuple;

public class PowerOf2IntMipMap extends AbstractBitmap<Integer> {

	private int m_curIndex;
	private final PowerOf2IntBitmap[] m_bitmaps;
	private PowerOf2IntBitmap m_cur;

	public PowerOf2IntMipMap(final PowerOf2IntBitmap[] bitmaps) {
		m_bitmaps = bitmaps;
		
		m_curIndex = 0;
		m_cur = m_bitmaps[0];
	}

	@Override
	public final int pointToIndex(final int x, final int y) {
		return m_cur.pointToIndex(x, y);
	}

	@Override
	public final Tuple<Integer, Integer> indexToPoint(int index) {
		return m_cur.indexToPoint(index);
	}

	@Override
	public final int getWidth() {
		return m_cur.getWidth();
	}

	@Override
	public final int getHeight() {
		return m_cur.getHeight();
	}

	@Override
	public final BitmapCursor<Integer> getCursor() {
		return m_cur.getCursor();
	}

	@Override
	public final Integer getPixel(int index) {
		return new Integer(m_cur.getPixel(index));
	}
	
	public final int getNative(int index) {
		return m_cur.getPixel(index);
	}

	@Override
	public final Integer getPixel(final int x, final int y) {
		return m_cur.getPixel(x, y);
	}
	
	public final int getNative(final int x, final int y) {
		return m_cur.getPixel(x, y);
	}

	public final int getMipLevel() {
		return m_curIndex;
	}

	public final void setMipLevel(final int lvl) {
		if (lvl >= 0 && lvl < m_bitmaps.length) {
			m_curIndex = lvl;
			m_cur = m_bitmaps[m_curIndex];
		}
	}
	
	public final PowerOf2IntMipMap copy() {
		return new PowerOf2IntMipMap(m_bitmaps);
	}

	public final int getNumMips() {
		return m_bitmaps.length;
	}

	@Override
	public void putPixel(int x, int y, Integer value) {
		m_cur.putPixel(x, y, value);
	}

	@Override
	public void putPixel(int index, Integer value) {
		m_cur.putPixel(index, value);
	}

	@Override
	public IBitmap<Integer> loadFromFile(String filename) {
		return null;
	}
	
}
