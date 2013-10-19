package de.squareys.EpicRay.framework;

public class CombinedCursor<S, T> implements ICursor1D<Tuple<S, T>> {

	protected ICursor1D<S> m_cursor1;
	protected ICursor1D<T> m_cursor2;
	
	public CombinedCursor(ICursor1D<S> c1, ICursor1D<T> c2) {
		m_cursor1 = c1;
		m_cursor2 = c2;
	}
	
	
	@Override
	public Tuple<S, T> get() {
		return new Tuple<S, T>(m_cursor1.get(), m_cursor2.get());
	}

	@Override
	public void set(Tuple<S, T> value) {
		m_cursor1.set(value.getA());
		m_cursor2.set(value.getB());
	}

	@Override
	public void next() {
		m_cursor1.next();
		m_cursor2.next();
	}

	@Override
	public void setPosition(int index) {
		m_cursor1.setPosition(index);
		m_cursor1.setPosition(index);
	}

	@Override
	public int getPosition() {
		return m_cursor1.getPosition();
	}


	public void set(S a, T b ) {
		m_cursor1.set(a);
		m_cursor2.set(b);
	}

}