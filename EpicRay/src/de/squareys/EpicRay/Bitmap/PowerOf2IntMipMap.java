package de.squareys.EpicRay.Bitmap;

import de.squareys.EpicRay.Cursor.BitmapCursor;
import de.squareys.EpicRay.Texture.ITexture;
import de.squareys.EpicRay.Util.Tuple;

public class PowerOf2IntMipMap implements ITexture {

	protected int m_curIndex;
	protected PowerOf2IntBitmap[] m_bitmaps;
	protected PowerOf2IntBitmap m_cur;

	public PowerOf2IntMipMap(PowerOf2IntBitmap[] bitmaps) {
		m_bitmaps = bitmaps;
		
		m_curIndex = 0;
		m_cur = m_bitmaps[0];
	}

	@Override
	public int pointToIndex(int x, int y) {
		return m_cur.pointToIndex(x, y);
	}

	@Override
	public Tuple<Integer, Integer> indexToPoint(int index) {
		return m_cur.indexToPoint(index);
	}

	@Override
	public int getWidth() {
		return m_cur.getWidth();
	}

	@Override
	public int getHeight() {
		return m_cur.getHeight();
	}

	@Override
	public BitmapCursor<Integer> getCursor() {
		return m_cur.getCursor();
	}

	@Override
	public IBitmap<Integer> getParentBitmap() {
		return m_cur;
	}

	@Override
	public int getPixel(int index) {
		return m_cur.getPixel(index);
	}

	@Override
	public int getPixel(int x, int y) {
		return m_cur.getPixel(x, y);
	}

	public int getMipLevel() {
		return m_curIndex;
	}

	public void setMipLevel(int lvl) {
		if (lvl >= 0 && lvl < m_bitmaps.length) {
			m_curIndex = lvl;
			m_cur = m_bitmaps[m_curIndex];
		}
	}

	public int getNumMips() {
		return m_bitmaps.length;
	}
	
}
