package de.squareys.EpicRay.EpicRay;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Vector;

import javax.imageio.ImageIO;

import de.squareys.EpicRay.Bitmap.FastIntBitmap;
import de.squareys.EpicRay.Bitmap.IBitmap;
import de.squareys.EpicRay.Bitmap.PowerOf2IntBitmap;
import de.squareys.EpicRay.Resource.IResourceManager;
import de.squareys.EpicRay.Resource.ISound;
import de.squareys.EpicRay.Texture.ITexture;
import de.squareys.EpicRay.Texture.Texture;
import de.squareys.EpicRay.Util.Tuple;

public class ResourceManager implements IResourceManager {
	private static ResourceManager m_instance;
	
	private Vector<ISound> m_sounds;
	private Vector<IBitmap<?>> m_bitmaps;
	private Vector<ITexture> m_textures;
	
	private ResourceManager(){
		m_sounds   = new Vector<ISound>();
		m_bitmaps  = new Vector<IBitmap<?>>();
		m_textures = new Vector<ITexture>();
	}

	@Override
	public ISound loadSound(String filename) {
		throw new RuntimeException("Unimplemented Method!");
	}

	@Override
	public IBitmap<Integer> loadBitmap(String filename) {
		URL file = getClass().getResource(filename);
		
		try {
			BufferedImage img = ImageIO.read(file);

			int w = img.getWidth();
			int h = img.getHeight();

			FastIntBitmap loaded = new FastIntBitmap(w, h);
			
			img.getRGB(0, 0, w, h, loaded.m_pixels, 0, w);
			
			FastIntBitmap result;
			
			if (isPowerOf2(loaded.getWidth()) && isPowerOf2(loaded.getHeight())) {
				result = new PowerOf2IntBitmap(h, w);
			} else {
				result = new FastIntBitmap(h, w);
			}
			
			for (int i = 0; i < w * h; ++i){
				Tuple<Integer, Integer> p = result.indexToPoint(i);
				result.m_pixels[i] = loaded.getPixel((int)p.getB(), (int)p.getA());
			}
			//switch vertical and horizontal
			m_bitmaps.add(result);
			
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private boolean isPowerOf2(int x) {
		return (x & (x - 1)) == 0;
	}

	@Override
	public ITexture createTexture(IBitmap<Integer> bitmap, int startx, int starty,
			int endx, int endy) {
		ITexture texture = new Texture(bitmap, startx, starty, endx, endy);
		m_textures.add(texture);
		
		return texture;
	}
	
	@Override
	public ITexture createTexture(IBitmap<Integer> bitmap) {
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