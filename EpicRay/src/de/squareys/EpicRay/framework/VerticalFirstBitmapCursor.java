package de.squareys.EpicRay.framework;

public class VerticalFirstBitmapCursor<T> extends BitmapCursor<T> {
	
	public VerticalFirstBitmapCursor (IBitmap<T> bmp, int x, int y) {
		super(bmp, x, y);
	}
	
	public VerticalFirstBitmapCursor (IBitmap<T> bmp) {
		super(bmp);
	}

	@Override
	public void nextX() {
		m_posIndex++;
	}

	@Override
	public void nextY() {
		m_posIndex += m_bitmap.getWidth();
	}

	@Override
	public void next() {
		nextX();
	}
}
