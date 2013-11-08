package de.squareys.EpicRay.framework;

import java.util.Iterator;

public class FastIntFloatCombinedCursor implements
		ICursor1D<Tuple<Integer, Float>> {

	protected FastIntBitmapCursor m_cursor1;
	protected FastFloatBitmapCursor m_cursor2;

	public FastIntFloatCombinedCursor(FastIntBitmapCursor c1,
			FastFloatBitmapCursor c2) {
		m_cursor1 = c1;
		m_cursor2 = c2;
	}

	@Override
	public Tuple<Integer, Float> get() {
		return new Tuple<Integer, Float>(m_cursor1.get(), m_cursor2.get());
	}

	@Override
	public void set(Tuple<Integer, Float> value) {
		m_cursor1.set(value.getA());
		m_cursor2.set(value.getB());
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
		return m_cursor1.getPosition();
	}

	public void set(Integer a, Float b) {
		m_cursor1.set(a);
		m_cursor2.set(b);
	}

	public void set(int a, float b) {
		m_cursor1.set(a);
		m_cursor2.set(b);
	}

	@Override
	public ICursor1D<Tuple<Integer, Float>> copy() {
		return new FastIntFloatCombinedCursor(
				(FastIntBitmapCursor) m_cursor1.copy(),
				(FastFloatBitmapCursor) m_cursor2.copy());
	}

	@Override
	public boolean hasNext() {
		return m_cursor1.hasNext() && m_cursor2.hasNext();
	}

	@Override
	public Tuple<Integer, Float> next() {
		this.fwd();
		return this.get();
	}

	@Override
	public void remove() {
		return;
	}

	@Override
	public Iterator<Tuple<Integer, Float>> iterator() {
		return this;
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

	public FastIntBitmapCursor getCursorA() {
		return m_cursor1;
	}

	public FastFloatBitmapCursor getCursorB() {
		return m_cursor2;
	}

	@Override
	public Tuple<Integer, Float> prev() {
		bck();
		return get();
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
