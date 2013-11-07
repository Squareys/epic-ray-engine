package de.squareys.EpicRay.framework;

public class HorizontalFirstBitmapCursor<T> extends BitmapCursor<T> {
	
	public HorizontalFirstBitmapCursor (IBitmap<T> bmp, int x, int y) {
		super(bmp, x, y);
	}
	
	public HorizontalFirstBitmapCursor (IBitmap<T> bmp, int index) {
		super(bmp, index);
	}
	public HorizontalFirstBitmapCursor (IBitmap<T> bmp) {
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
		++m_posIndex; //nextX
		return get();
	}

	@Override
	public ICursor1D<T> copy() {
		return new HorizontalFirstBitmapCursor<T>(m_bitmap, m_posIndex);
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
