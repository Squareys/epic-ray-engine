package de.squareys.EpicRay.Texture;

import de.squareys.EpicRay.Bitmap.IBitmap;
import de.squareys.EpicRay.Cursor.BitmapCursor;
import de.squareys.EpicRay.Util.Tuple;

public class Texture implements ITexture {
	protected IBitmap<Integer> m_parentBitmap;

	protected int m_startX;
	protected int m_startY;
	protected int m_endX;
	protected int m_endY;

	protected int m_width;
	protected int m_height;
	protected int m_length;

	public Texture(IBitmap<Integer> parent, int startx, int starty, int endx,
			int endy) {
		m_parentBitmap = parent;

		m_startX = startx;
		m_startY = starty;
		m_endX = endx;
		m_endY = endy;

		m_endX = Math.min(m_endX, m_parentBitmap.getWidth());
		m_endY = Math.min(m_endY, m_parentBitmap.getHeight());

		m_width = m_endX - m_startX - 1;
		m_height = m_endY - m_startY - 1;

		m_length = m_height + m_width;
	}

	@Override
	public int pointToIndex(int x, int y) {
		return m_parentBitmap.pointToIndex(x, y);
	}

	@Override
	public Tuple<Integer, Integer> indexToPoint(int index) {
		return m_parentBitmap.indexToPoint(index);
	}

	@Override
	public int getWidth() {
		return m_width;
	}

	@Override
	public int getHeight() {
		return m_height;
	}

	@Override
	public int getPixel(int x, int y) {
		return m_parentBitmap.getPixel(x, y);
	}

	@Override
	public int getPixel(int index) {
		return m_parentBitmap.getPixel(index);
	}

	@Override
	public IBitmap<Integer> getParentBitmap() {
		return m_parentBitmap;
	}

	@Override
	public BitmapCursor<Integer> getCursor() {
		return m_parentBitmap.getCursor();
	}

}
