package de.squareys.EpicRay.implementation;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import de.squareys.EpicRay.framework.IBitmap;
import de.squareys.EpicRay.framework.IEntity;
import de.squareys.EpicRay.framework.IWorld;
import de.squareys.EpicRay.framework.Screen;

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
	public void present() {
		renderer.render();
		
		IBitmap result = renderer.getRenderResult();
		for (int x = 0; x < m_width; x++) {
			for (int y = 0; y < m_height; y++){
				putPixel(x, y, result.getPixel(x, y));
			}
		}
		
		draw(); 
	}

	
	public void draw(){
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

}
