package de.squareys.EpicRay.implementation;

import java.awt.geom.Point2D;

import de.squareys.EpicRay.framework.IBitmap;
import de.squareys.EpicRay.framework.ITexture;

public class Texture implements ITexture {
	protected IBitmap m_parentBitmap;
	
	protected int m_startX;
	protected int m_startY;
	protected int m_endX;
	protected int m_endY;
	
	protected int m_width;
	protected int m_height;
	
	public Texture(IBitmap parent, int startx, int starty, int endx, int endy){
		m_parentBitmap = parent;
		
		m_startX = startx;
		m_startY = starty;
		m_endX = endx;
		m_endY = endy;
		
		m_endX = Math.min(m_endX, m_parentBitmap.getWidth());
		m_endY = Math.min(m_endY, m_parentBitmap.getHeight());
		
		m_width = m_endX - m_startX-1;
		m_height = m_endY - m_startY-1;
	}
	
	@Override
	public int pointToIndex(int x, int y) {
		return m_parentBitmap.pointToIndex(x, y);
	}

	@Override
	public Point2D indexToPoint(int index) {
		// TODO Auto-generated method stub
		return null;
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
	public void drawBitmap(IBitmap bitmap, int destX, int destY) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getPixel(int x, int y) {
		return m_parentBitmap.getPixel(x, y);
	}

	@Override
	public int getPixel(int index) {
		return m_parentBitmap.getPixel(index);
	}

	@Override
	public void putPixel(int x, int y, int color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putPixel(int index, int color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawRect(int x, int y, int x2, int y2, int color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawArc(int x, int y, int radius, float angle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear(int col) {
		// TODO Auto-generated method stub

	}

	@Override
	public IBitmap loadFromFile(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void drawOnto(IBitmap bitmap) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawHorizontalStripe(IBitmap dest, int x, int y, int length) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawVerticalStripe(IBitmap dest, int x, int y, int length) {
		int index = pointToIndex(x, y);
		for (int i = y; i < y+length; i++, index++){
			dest.putPixel(x, i, m_parentBitmap.getPixel(index));
		}

	}

	@Override
	public IBitmap getParentBitmap() {
		// TODO Auto-generated method stub
		return null;
	}

}
