package de.squareys.epicray.engine;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Vector;

import javax.imageio.ImageIO;

import de.squareys.epicray.bitmap.FastIntBitmap;
import de.squareys.epicray.bitmap.IBitmap;
import de.squareys.epicray.bitmap.PowerOf2IntBitmap;
import de.squareys.epicray.bitmap.PowerOf2IntMipMap;
import de.squareys.epicray.resource.IResourceManager;
import de.squareys.epicray.resource.ISound;
import de.squareys.epicray.util.Tuple;

public class ResourceManager implements IResourceManager {
	private static ResourceManager m_instance;
	
	private Vector<ISound> m_sounds;
	private Vector<IBitmap<Integer>> m_bitmaps;
	
	private ResourceManager(){
		m_sounds   = new Vector<ISound>();
		m_bitmaps  = new Vector<IBitmap<Integer>>();
	}

	@Override
	public ISound loadSound(String filename) {
		throw new RuntimeException("Unimplemented Method!");
	}

	public IBitmap<Integer> loadBitmap(URL file) {
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
	
	@Override
	public IBitmap<Integer> loadBitmap(String filename) {
		URL file = getClass().getResource(filename);
		return loadBitmap(file);
	}
	
	private boolean isPowerOf2(int x) {
		return (x & (x - 1)) == 0;
	}
	
	public IBitmap<Integer> createTexture(IBitmap<Integer> bitmap) {

		IBitmap<Integer> texture = bitmap;
		if (bitmap.getHeight() == bitmap.getWidth() && bitmap instanceof PowerOf2IntBitmap) {
			texture = createMitMap((PowerOf2IntBitmap) bitmap);
		} 
		
		return texture;
	}

	private IBitmap<Integer> createMitMap(PowerOf2IntBitmap bitmap) {
		int exp = (int) (Math.log(bitmap.getHeight())/ Math.log(2));
		
		PowerOf2IntBitmap[] bitmaps = new PowerOf2IntBitmap[exp];
		
		bitmaps[0] = bitmap;
		
		for (int i = 1; i < exp; ++i) {
			bitmaps[i] = resizeSquaredBitmap(bitmap, 1 << exp-i);
		}
		
		return new PowerOf2IntMipMap(bitmaps);
	}

	private PowerOf2IntBitmap resizeSquaredBitmap(PowerOf2IntBitmap bitmap, int size) {
		PowerOf2IntBitmap resized = new PowerOf2IntBitmap(size, size);
		
		for (int x = 0; x < resized.getWidth(); ++x) {
			int srcX = (int) ((float)x / (float) size * (float)bitmap.getWidth());
			for (int y = 0; y < resized.getHeight(); ++y) {
				int srcY = (int) ((float)y / (float) size * (float)bitmap.getHeight());
				resized.putPixel(x, y, bitmap.getPixel(srcX, srcY));
			}
		}
		
		return resized;
	}

	public static ResourceManager getInstance() {
		if (m_instance == null){
			m_instance = new ResourceManager();
		}
		return m_instance;
	}
	
	public IBitmap<Integer> getBitmap(int index){
		//if (index >= m_textures.size()) return null;
		return m_bitmaps.get(index);
	}
	
}
