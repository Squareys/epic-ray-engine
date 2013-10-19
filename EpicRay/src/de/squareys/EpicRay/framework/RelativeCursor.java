package de.squareys.EpicRay.framework;

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
	public void next() {
		m_cursor.next();
	}

	@Override
	public void setPosition(int index) {
		m_cursor.setPosition(index + m_indexOffset);
	}

	@Override
	public int getPosition() {
		return m_cursor.getPosition() - m_indexOffset;
	}

}
