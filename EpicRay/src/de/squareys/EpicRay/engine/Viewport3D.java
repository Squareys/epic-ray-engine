package de.squareys.epicray.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import de.squareys.epicray.bitmap.FastIntBitmap;
import de.squareys.epicray.gamelogic.IEntity;
import de.squareys.epicray.gamelogic.IWorld;
import de.squareys.epicray.util.Screen;

public class Viewport3D extends Screen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9040265491916095531L;


	public Viewport3D (IWorld p_world, IEntity camEntity, int width, int height){ 
		super(width, height);
		
		renderer = new EpicRayRenderer(p_world, camEntity, m_width, m_height);
	}
	
	@Override
	public final void present() {
		renderer.render();
		final FastIntBitmap result = (FastIntBitmap) renderer.getRenderResult();
		
		int i2 = 0;
		int x = 0;
		int y = 0;
		
		for (int i = 0; i < m_length; ++i) {
				putPixel(i2, result.m_pixels[i]);
				
				i2 += m_width;
				++y;
				
				if (y == m_height) {
					i2 = ++x;
					y = 0;
				}
		}
		
		draw(); 
	}

	
	public final void draw(){
		final BufferStrategy bs = getBufferStrategy();
		
		final Graphics g = bs.getDrawGraphics();
		g.setColor(Color.WHITE);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		g.drawString(m_fps + " fps", 0, 10);
		g.dispose();
		bs.show();
	}

}
