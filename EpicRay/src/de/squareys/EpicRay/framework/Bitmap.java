package de.squareys.EpicRay.framework;

import de.squareys.EpicRay.type.IType;


/**
 * A Vertical First Bitmap
 * @author Squareys
 *
 */

public class Bitmap<T> implements IBitmap<T> {
	
	private int m_width;
	private int m_height;
	private int m_length;
	
	public T m_pixels[];
	
	public Bitmap (int width, int height, IType<T> t){
		m_width = width;
		m_height = height;
		m_length = m_height * m_width;
		
		m_pixels = t.createArray(m_width * m_height);
	}

	@Override
	public IBitmap<T> loadFromFile(String filename) {
		return null;
	}

	@Override
	public void drawOnto(IBitmap<T> bitmap) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawHorizontalStripe(IBitmap<T> dest, int x, int y, int length) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawVerticalStripe(IBitmap<T> dest, int x, int y, int length) {
		// TODO Auto-generated method stub

	}

	@Override
	public int pointToIndex(int x, int y) {
		return (x * m_height + y);
	}

	@Override
	public Tuple<Integer, Integer> indexToPoint(int index) {
		int y = index % m_height;
		int x = (index - y) / m_height;
		
		return new Tuple<Integer, Integer>(x, y);
	}

	@Override
	public int getWidth() {
		return m_width;
	}

	@Override
	public int getHeight() {
		return m_height;
	}

	@Override
	public void drawBitmap(IBitmap<T> bitmap, int destX, int destY) {
		int maxX = Math.max(bitmap.getWidth() + destX, getWidth());
		int maxY = Math.max(bitmap.getHeight() + destY, getHeight());
		
		for (int x = 0; x < maxX; x++){
			for ( int y = 0; y < maxY; y++){
				putPixel(x + destX, y + destY, bitmap.getPixel(x, y));
			}
		}
	}

	@Override
	public T getPixel(int x, int y) {
		return m_pixels[pointToIndex(x, y)];
	}

	@Override
	public T getPixel(int index) {
		return m_pixels[index];
	}

	@Override
	public void putPixel(int x, int y, T color) {
		m_pixels[pointToIndex(x, y)] = color;
	}

	@Override
	public void putPixel(int index, T color) {
		m_pixels[index] = color;
	}

	@Override
	public void drawRect(int p_x, int p_y, int p_x2, int p_y2, T p_col) {
		int index;
		
		for (int x = p_x; x < p_x2; x++){
			index = x * m_height + p_y;
			for ( int y = p_y; p_y < p_y2; y++, index++){
				putPixel(index, p_col);
			}
		}

	}

	@Override
	public void drawArc(int x, int y, int radius, float angle, T value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2, T value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear(T col) {
		for (int i = 0; i < m_width * m_height; i++){
			m_pixels[i] = col;
		}
	}

	@Override
	public BitmapCursor<T> getCursor() {
		return new VerticalFirstBitmapCursor<T>(this);
	}

	@Override
	public int getLength() {
		return m_length;
	}
}
