package de.squareys.EpicRay.framework;

/**
 * A Vertical First Bitmap
 * @author Squareys
 *
 */

public class FastIntBitmap implements IBitmap<Integer> {
	
	private int m_width;
	private int m_height;
	
	public int m_pixels[];
	
	public FastIntBitmap (int width, int height){
		m_width = width;
		m_height = height;
		
		m_pixels = new int[width * m_height];
	}

	@Override
	public IBitmap<Integer> loadFromFile(String filename) {
		return null;
	}

	@Override
	public void drawOnto(IBitmap<Integer> bitmap) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawHorizontalStripe(IBitmap<Integer> dest, int x, int y, int length) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawVerticalStripe(IBitmap<Integer> dest, int x, int y, int length) {
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
	public void drawBitmap(IBitmap<Integer> bitmap, int destX, int destY) {
		//TODO: What the hell is this? Performance...
		int maxX = Math.max(bitmap.getWidth() + destX, getWidth());
		int maxY = Math.max(bitmap.getHeight() + destY, getHeight());
		
		for (int x = 0; x < maxX; x++){
			for ( int y = 0; y < maxY; y++){
				putPixel(x + destX, y + destY, bitmap.getPixel(x, y));
			}
		}
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
	public void drawRect(int p_x, int p_y, int p_x2, int p_y2, Integer p_col) {
		int index;
		int c = p_col;
		
		index = p_x * m_height + p_y;
		for (int x = p_x; x < p_x2; x++, index += m_height){
			for ( int y = p_y; y < p_y2; y++, index++){
				putPixel(index, c);
			}
		}

	}

	@Override
	public void drawArc(int x, int y, int radius, float angle, Integer value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2, Integer value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear(Integer col) {
		int c = col; //no conversion every time.
		
		for (int i = 0; i < m_width * m_height; i++){
			m_pixels[i] = c;
		}
	}
	
	@Override
	public BitmapCursor<Integer> getCursor() {
		return new FastIntBitmapCursor(this);
	}
}
