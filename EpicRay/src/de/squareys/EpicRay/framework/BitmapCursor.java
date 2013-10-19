package de.squareys.EpicRay.framework;


public abstract class BitmapCursor<T> implements ICursor2D<T> {
	protected IBitmap<T> m_bitmap;
	
	protected int m_posIndex;
	
	public BitmapCursor (IBitmap<T> bmp) {
		this(bmp, 0, 0);
	}

	public BitmapCursor(IBitmap<T> bmp, int x, int y) {
		m_bitmap = bmp;
		setPosition(x, y);
	}

	public void setBitmap(IBitmap<T> bmp) {
		this.m_bitmap = bmp;
	}

	@Override
	public T get() {
		return m_bitmap.getPixel(m_posIndex);
	}

	@Override
	public void set(T value) {
		m_bitmap.putPixel(m_posIndex, value);
	}
	
	@Override
	public void setPosition(int index) {
		m_posIndex = index;
	}
	
	@Override
	public void setPosition(int x, int y) {
		m_posIndex = m_bitmap.pointToIndex(x, y);
	}
	
	@Override 
	public int getPosition(){
		return m_posIndex;
	}
	
	@Override 
	public Tuple<Integer, Integer> getPositionXY(){
		return m_bitmap.indexToPoint(m_posIndex);
	}
}
