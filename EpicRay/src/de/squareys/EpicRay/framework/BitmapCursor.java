package de.squareys.EpicRay.framework;

import java.util.Iterator;


public abstract class BitmapCursor<T> implements ICursor2D<T> {
	protected IBitmap<T> m_bitmap;
	
	protected int m_posIndex;
	
	public BitmapCursor (IBitmap<T> bmp) {
		m_bitmap = bmp;
		m_posIndex = 0;
	}

	public BitmapCursor(IBitmap<T> bmp, int x, int y) {
		m_bitmap = bmp;
		setPosition(x, y);
	}
	
	public BitmapCursor(IBitmap<T> bmp, int index) {
		m_bitmap = bmp;
		setPosition(index);
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
	
	@Override
	public void fwd() {
		++m_posIndex;
	}
	
	@Override
	public Iterator<T> iterator() {
		return this;
	}
	
	@Override
	public boolean hasNext() {
		return m_posIndex + 1 > m_bitmap.getLength();
	}

	@Override
	public void remove() {
		return; // one does not simply... "remove" pixels.
	}
	
	@Override
	public void reset() {
		m_posIndex = 0;
	}
}
