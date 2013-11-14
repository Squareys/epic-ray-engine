package de.squareys.EpicRay.Cursor;

import de.squareys.EpicRay.Bitmap.FastIntBitmap;

public class FastIntBitmapCursor extends BitmapCursor<Integer> {
	protected FastIntBitmap m_bitmap;

	public FastIntBitmapCursor(FastIntBitmap bmp) {
		super(bmp);
		m_bitmap = bmp;
	}

	public FastIntBitmapCursor(FastIntBitmap bmp, int index, int offset) {
		super(bmp, index, offset);
		m_bitmap = bmp;
	}

	public FastIntBitmapCursor(FastIntBitmap bmp, int x, int y, int offset) {
		super(bmp, x, y, offset);
		m_bitmap = bmp;
	}

	public final void setBitmap(FastIntBitmap bmp) {
		this.m_bitmap = bmp;
	}

	public final int getNativeInt() {
		return m_bitmap.m_pixels[m_posIndex];
	}

	@Override
	public final Integer get() {
		return m_bitmap.m_pixels[m_posIndex];
	}
	
	public final int getNative() {
		return m_bitmap.m_pixels[m_posIndex];
	}

	@Override
	public final void set(Integer value) {
		m_bitmap.m_pixels[m_posIndex] = value;
	}

	public final void set(int value) {
		m_bitmap.m_pixels[m_posIndex] = value;
	}

	@Override
	public final Integer next() {
		return m_bitmap.m_pixels[++m_posIndex];
	}

	@Override
	public final void nextX() {
		m_posIndex += m_bitmap.getHeight();
	}

	@Override
	public final void nextY() {
		++m_posIndex;
	}

	@Override
	public final ICursor1D<Integer> copy() {
		return new FastIntBitmapCursor(m_bitmap, m_posIndex, m_offset);
	}

	@Override
	public final Integer prev() {
		return m_bitmap.m_pixels[--m_posIndex];
	}
}
