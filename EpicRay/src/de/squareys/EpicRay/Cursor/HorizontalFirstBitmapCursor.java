package de.squareys.epicray.cursor;

import de.squareys.epicray.bitmap.IBitmap;

public class HorizontalFirstBitmapCursor<T> extends BitmapCursor<T> {

	public HorizontalFirstBitmapCursor(IBitmap<T> bmp, int x, int y, int offset) {
		super(bmp, x, y, offset);
	}

	public HorizontalFirstBitmapCursor(IBitmap<T> bmp, int index, int offset) {
		super(bmp, index, offset);
	}

	public HorizontalFirstBitmapCursor(IBitmap<T> bmp) {
		super(bmp);
	}

	@Override
	public void nextX() {
		++m_posIndex;
	}

	@Override
	public void nextY() {
		m_posIndex += m_bitmap.getWidth();
	}

	@Override
	public T next() {
		++m_posIndex; // nextX
		return get();
	}

	@Override
	public ICursor1D<T> copy() {
		return new HorizontalFirstBitmapCursor<T>(m_bitmap, m_posIndex, m_offset);
	}

	@Override
	public void bck() {
		--m_posIndex;
	}

	@Override
	public T prev() {
		bck();
		return get();
	}
}
