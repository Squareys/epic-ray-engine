package de.squareys.EpicRay.framework;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JPanel;

public abstract class Screen extends Canvas {
	protected IRenderer<? extends IBitmap<Integer>> renderer;

	protected int m_width;
	protected int m_height;

	protected int[] pixels;
	protected BufferedImage img;

	public Screen(int width, int height) {
		super();

		m_width = width;
		m_height = height;

		img = new BufferedImage(m_width, m_height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

		this.setSize(m_width, m_height);
		this.setPreferredSize(new Dimension(m_width, m_height));
	}

	public abstract void present();

	public BufferedImage getImage() {
		return img;
	}

	public void putPixel(int x, int y, int col) {
		pixels[y * m_width + x] = col;
	}
}
