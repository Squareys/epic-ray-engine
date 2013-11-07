package de.squareys.EpicRay.framework;

/**
 * A Vertical First Bitmap
 * @author Squareys
 *
 */

public class FastIntBitmap extends AbstractBitmap<Integer> {
	
	protected int m_width;
	protected int m_height;
	protected int m_length;
	
	public int m_pixels[];
	
	public FastIntBitmap (int width, int height){
		m_width = width;
		m_height = height;
		m_length = m_width * m_height;
		
		m_pixels = new int[m_length];
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
	public Integer getPixel(int x, int y) {
		return m_pixels[pointToIndex(x, y)];
	}

	@Override
	public Integer getPixel(int index) {
		return new Integer(m_pixels[index]);
	}

	@Override
	public void putPixel(int x, int y, Integer color) {
		m_pixels[pointToIndex(x, y)] = color;
	}

	@Override
	public void putPixel(int index, Integer color) {
		m_pixels[index] = color;
	}

	@Override
	public void clear(Integer col) {
		int c = col; //no conversion every time.
		
		for (int i = 0; i < m_length; i++){
			m_pixels[i] = c;
		}
	}
	
	@Override
	public BitmapCursor<Integer> getCursor() {
		return new FastIntBitmapCursor(this);
	}

	@Override
	public int getLength() {
		return m_length;
	}


	@Override
	public IBitmap<Integer> loadFromFile(String filename) {
		return null;
	}
}
