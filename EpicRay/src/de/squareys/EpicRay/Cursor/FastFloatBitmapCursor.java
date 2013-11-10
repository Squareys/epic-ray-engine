package de.squareys.EpicRay.Cursor;

import java.util.Iterator;

import de.squareys.EpicRay.Bitmap.FastFloatBitmap;

public class FastFloatBitmapCursor extends BitmapCursor<Float> {
	protected FastFloatBitmap m_bitmap;

	public FastFloatBitmapCursor(FastFloatBitmap bmp) {
		super(bmp);
		m_bitmap = bmp;
	}

	public FastFloatBitmapCursor(FastFloatBitmap bmp, int index) {
		super(bmp, index);
		m_bitmap = bmp;
	}

	public FastFloatBitmapCursor(FastFloatBitmap bmp, int x, int y) {
		super(bmp, x, y);
		m_bitmap = bmp;
	}

	public void setBitmap(FastFloatBitmap bmp) {
		this.m_bitmap = bmp;
	}

	public float getNativeInt() {
		return m_bitmap.m_pixels[m_posIndex];
	}

	@Override
	public Float get() {
		return m_bitmap.m_pixels[m_posIndex];
	}
	
	public float getNative() {
		return m_bitmap.m_pixels[m_posIndex];
	}

	@Override
	public void set(Float value) {
		m_bitmap.m_pixels[m_posIndex] = value;
	}

	public void set(float value) {
		m_bitmap.m_pixels[m_posIndex] = value;
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
	public ICursor1D<Float> copy() {
		return new FastFloatBitmapCursor(m_bitmap, m_posIndex);
	}

	@Override
	public Float next() {
		return m_bitmap.m_pixels[++m_posIndex];
	}

	@Override
	public Float prev() {
		return m_bitmap.m_pixels[--m_posIndex];
	}

}
