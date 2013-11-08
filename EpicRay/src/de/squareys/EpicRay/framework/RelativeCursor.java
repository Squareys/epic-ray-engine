package de.squareys.EpicRay.framework;

import java.util.Iterator;

public class RelativeCursor<T> implements ICursor1D<T> {
	
	protected int m_indexOffset;
	protected ICursor1D<T> m_cursor;
	
	
	public RelativeCursor(ICursor1D<T> cursor) {
		this(cursor, cursor.getPosition());
	}
	
	public RelativeCursor(ICursor1D<T> cursor, int offset) {
		setCursor(cursor);
		
		m_indexOffset = offset;
	}
	
	public void setCursor(ICursor1D<T> cursor) {
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
	public void fwd(int n) {
		m_cursor.fwd(n);
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
	public void bck(int n) {
		m_cursor.bck(n);
	}

	@Override
	public T prev() {
		return m_cursor.prev();
	}
	
	@Override
	public void reset() {
		m_cursor.setPosition(m_indexOffset);
	}

}
