package de.squareys.EpicRay.framework;


public class FastIntBitmapCursor extends BitmapCursor<Integer> {
	protected FastIntBitmap m_bitmap;
	
	public FastIntBitmapCursor (FastIntBitmap bmp) {
		super(bmp);
		m_bitmap = bmp;
	}

	public FastIntBitmapCursor(FastIntBitmap bmp, int index) {
		super(bmp, index);
		m_bitmap = bmp;
	}
	
	public FastIntBitmapCursor(FastIntBitmap bmp, int x, int y) {
		super(bmp, x, y);
		m_bitmap = bmp;
	}

	public void setBitmap(FastIntBitmap bmp) {
		this.m_bitmap = bmp;
	}
	
	public int getNativeInt() {
		return m_bitmap.m_pixels[m_posIndex];
	}

	@Override
	public Integer get() {
		return m_bitmap.m_pixels[m_posIndex];
	}

	@Override
	public void set(Integer value) {
		m_bitmap.m_pixels[m_posIndex] = value;
	}
	
	public void set(int value) {
		m_bitmap.m_pixels[m_posIndex] = value;
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

	@Override
	public Integer next() {
		return m_bitmap.m_pixels[++m_posIndex];
	}

	@Override
	public void nextX() {
		m_posIndex += m_bitmap.getHeight();
	}

	@Override
	public void nextY() {
		++m_posIndex;
	}

	@Override
	public ICursor1D<Integer> copy() {
		return new FastIntBitmapCursor(m_bitmap, m_posIndex);
	}

	@Override
	public void bck() {
		--m_posIndex;
	}

	@Override
	public Integer prev() {
		return m_bitmap.m_pixels[--m_posIndex];
	}
}
