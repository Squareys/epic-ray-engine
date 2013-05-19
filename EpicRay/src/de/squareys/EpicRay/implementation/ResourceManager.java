package de.squareys.EpicRay.implementation;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Vector;

import javax.imageio.ImageIO;

import de.squareys.EpicRay.framework.IBitmap;
import de.squareys.EpicRay.framework.IResourceManager;
import de.squareys.EpicRay.framework.ISound;
import de.squareys.EpicRay.framework.ITexture;

public class ResourceManager implements IResourceManager {
	private static ResourceManager m_instance;
	
	private Vector<ISound> m_sounds;
	private Vector<IBitmap> m_bitmaps;
	private Vector<ITexture> m_textures;
	
	private ResourceManager(){
		m_sounds   = new Vector<ISound>();
		m_bitmaps  = new Vector<IBitmap>();
		m_textures = new Vector<ITexture>();
	}

	@Override
	public ISound loadSound(String filename) {
		throw new RuntimeException("Unimplemented Method!");
	}

	@Override
	public IBitmap loadBitmap(String filename) {
		URL file = getClass().getResource(filename);
		
		try {
			BufferedImage img = ImageIO.read(file);

			int w = img.getWidth();
			int h = img.getHeight();

			Bitmap result = new Bitmap(w, h);
			img.getRGB(0, 0, w, h, result.m_pixels, 0, w);
			
			m_bitmaps.add(result);
			
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public ITexture createTexture(IBitmap bitmap, int startx, int starty,
			int endx, int endy) {
		ITexture texture = new Texture(bitmap, startx, starty, endx, endy);
		m_textures.add(texture);
		
		return texture;
	}
	
	@Override
	public ITexture createTexture(IBitmap bitmap) {
		ITexture texture = new Texture(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
		m_textures.add(texture);
		
		return texture;
	}

	public static IResourceManager getInstance() {
		if (m_instance == null){
			m_instance = new ResourceManager();
		}
		return m_instance;
	}
	
	public ITexture getTexture(int index){
		//if (index >= m_textures.size()) return null;
		return m_textures.get(index);
	}
	
}
