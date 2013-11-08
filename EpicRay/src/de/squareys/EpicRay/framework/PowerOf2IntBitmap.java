package de.squareys.EpicRay.framework;

public class PowerOf2IntBitmap extends FastIntBitmap {

	protected int m_hExp;
	protected int m_wExp;

	public PowerOf2IntBitmap(int width, int height) {
		super(width, height);

		m_wExp = (int) (Math.log(m_width) / Math.log(2));
		m_hExp = (int) (Math.log(m_height) / Math.log(2));
	}

	@Override
	public int pointToIndex(int x, int y) {
		return ((x << m_hExp) + y);
	}

	@Override
	public Tuple<Integer, Integer> indexToPoint(int index) {
		int y = index % m_height;
		int x = (index - y) >> m_hExp;

		return new Tuple<Integer, Integer>(x, y);
	}

	@Override
	public Integer getPixel(int x, int y) {
		return m_pixels[pointToIndex(x, y)];
	}

	@Override
	public void putPixel(int x, int y, Integer color) {
		m_pixels[pointToIndex(x, y)] = color;
	}

	@Override
	public IBitmap<Integer> loadFromFile(String filename) {
		return null;
	}
}