package de.squareys.EpicRay.Cursor;

import java.util.Iterator;

import de.squareys.EpicRay.Util.Tuple;

//TODO: May need a little rework to get offset in nicely...
public class CombinedCursor<S, T> implements ICursor1D<Tuple<S, T>> {

	protected ICursor1D<S> m_cursor1;
	protected ICursor1D<T> m_cursor2;
	
	protected int m_offset;

	public CombinedCursor(ICursor1D<S> c1, ICursor1D<T> c2) {
		m_cursor1 = c1;
		m_cursor2 = c2;
		
		m_offset = 0;
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
	public Tuple<S, T> next() {
		return new Tuple<S, T>(m_cursor1.next(), m_cursor2.next());
	}

	@Override
	public void setPosition(int index) {
		m_cursor1.setPosition(index);
		m_cursor2.setPosition(index);
	}
	
	@Override
	public void setAbsolutePosition(int index) {
		m_cursor1.setAbsolutePosition(index);
		m_cursor2.setAbsolutePosition(index);
	}

	@Override
	public int getPosition() {
		return 0;
	}

	public void set(S a, T b) {
		m_cursor1.set(a);
		m_cursor2.set(b);
	}

	@Override
	public Iterator<Tuple<S, T>> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return m_cursor1.hasNext() && m_cursor2.hasNext();
	}

	@Override
	public void remove() {
		return;
	}

	@Override
	public void fwd() {
		m_cursor1.fwd();
		m_cursor2.fwd();
	}

	@Override
	public void fwd(int n) {
		m_cursor1.fwd(n);
		m_cursor2.fwd(n);
	}

	@Override
	public ICursor1D<Tuple<S, T>> copy() {
		return new CombinedCursor<S, T>(m_cursor1, m_cursor2);
	}

	@Override
	public void bck() {
		m_cursor1.bck();
		m_cursor2.bck();
	}

	@Override
	public void bck(int n) {
		m_cursor1.bck(n);
		m_cursor2.bck(n);
	}

	@Override
	public Tuple<S, T> prev() {
		return new Tuple<S, T>(m_cursor1.prev(), m_cursor2.prev());
	}

	@Override
	public void reset() {
		m_cursor1.reset();
		m_cursor2.reset();
	}

	@Override
	public int getRelativePosition() {
		return 0;
	}

	@Override
	public void setOffset() {
		return;
	}

	@Override
	public int getOffset() {
		return 0;
	}

}