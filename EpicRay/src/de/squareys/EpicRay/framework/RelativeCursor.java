package de.squareys.EpicRay.framework;

import java.util.Iterator;

public class RelativeCursor<T> implements ICursor1D<T> {
	
	protected int m_indexOffset;
	protected ICursor2D<T> m_cursor;
	
	
	public RelativeCursor(ICursor2D<T> cursor) {
		setCursor(cursor);
		
		m_indexOffset = cursor.getPosition();
	}
	
	public void setCursor(ICursor2D<T> cursor) {
		m_cursor = cursor;
	}

	@Override
	public T get() {
		return m_cursor.get();
	}

	@Override
	public void set(T value) {
		m_cursor.set(value);
	}

	@Override
	public T next() {
		return m_cursor.next();
	}

	@Override
	public void setPosition(int index) {
		m_cursor.setPosition(index + m_indexOffset);
	}

	@Override
	public int getPosition() {
		return m_cursor.getPosition() - m_indexOffset;
	}

	@Override
	public Iterator<T> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return m_cursor.hasNext();
	}

	@Override
	public void remove() {
		return;
	}

	@Override
	public void fwd() {
		m_cursor.fwd();
	}

	@Override
	public ICursor1D<T> copy() {
		return this;
	}

	@Override
	public void bck() {
		m_cursor.bck();
	}

	@Override
	public T prev() {
		return m_cursor.prev();
	}

}
