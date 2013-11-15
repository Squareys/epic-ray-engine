package de.squareys.EpicRay.Util;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JPanel;

import de.squareys.EpicRay.Bitmap.IBitmap;
import de.squareys.EpicRay.Rendering.IRenderer;

public abstract class Screen extends Canvas {
	protected IRenderer<? extends IBitmap<Integer>> renderer;

	protected int m_width;
	protected int m_height;
	protected int m_length;

	protected int[] pixels;
	protected BufferedImage img;

	public Screen(int width, int height) {
		super();

		m_width = width;
		m_height = height;
		m_length = m_width * m_height;

		img = new BufferedImage(m_width, m_height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

		this.setSize(m_width, m_height);
		this.setPreferredSize(new Dimension(m_width, m_height));
	}

	public abstract void present();

	public final BufferedImage getImage() {
		return img;
	}
	
	public final void createBufferStrategy() {
		createBufferStrategy(3);
	}

	public final void putPixel(int x, int y, int col) {
		pixels[y * m_width + x] = col;
	}
	
	public final void putPixel(int i, int col) {
		pixels[ i ] = col;
	}
	
	protected int m_fps = 0;

	public void setFPS(int frames) {
		m_fps = frames;
	}
}
