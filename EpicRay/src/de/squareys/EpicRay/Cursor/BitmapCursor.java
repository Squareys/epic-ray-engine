package de.squareys.EpicRay.Cursor;

import java.util.Iterator;

import de.squareys.EpicRay.Bitmap.IBitmap;
import de.squareys.EpicRay.Util.Tuple;

public abstract class BitmapCursor<T> implements ICursor2D<T> {
	protected IBitmap<T> m_bitmap;

	protected int m_posIndex;
	protected int m_offset;

	public BitmapCursor(IBitmap<T> bmp) {
		m_bitmap = bmp;
		m_posIndex = 0;
		m_offset = 0;
	}

	public BitmapCursor(IBitmap<T> bmp, int x, int y, int offset) {
		m_bitmap = bmp;
		m_offset = offset;
		setPosition(x, y);
	}

	public BitmapCursor(IBitmap<T> bmp, int index, int offset) {
		m_bitmap = bmp;
		setAbsolutePosition(index);
		m_offset = offset;
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
		m_posIndex = index + m_offset;
	}
	
	@Override
	public void setAbsolutePosition(int index) {
		m_posIndex = index;
	}

	@Override
	public void setPosition(int x, int y) {
		m_posIndex = m_bitmap.pointToIndex(x, y);
	}

	@Override
	public int getPosition() {
		return m_posIndex;
	}
	
	@Override
	public int getRelativePosition() {
		return m_posIndex - m_offset;
	}

	@Override
	public Tuple<Integer, Integer> getPositionXY() {
		return m_bitmap.indexToPoint(m_posIndex);
	}

	@Override
	public void fwd() {
		++m_posIndex;
	}

	@Override
	public void fwd(int n) {
		m_posIndex += n;
	}

	@Override
	public void bck() {
		--m_posIndex;
	}

	@Override
	public void bck(int n) {
		m_posIndex -= n;
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
		m_posIndex = m_offset;
	}
	
	@Override
	public void setOffset() {
		m_offset = m_posIndex;
	}
	
	@Override
	public int getOffset() {
		return m_offset;
	}
}
