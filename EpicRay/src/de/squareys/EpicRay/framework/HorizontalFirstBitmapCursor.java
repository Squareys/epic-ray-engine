package de.squareys.EpicRay.framework;

public class HorizontalFirstBitmapCursor<T> extends BitmapCursor<T> {
	
	public HorizontalFirstBitmapCursor (IBitmap<T> bmp, int x, int y) {
		super(bmp, x, y);
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
	public void next() {
		++m_posIndex; //nextX
	}
}
