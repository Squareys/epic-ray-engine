package de.squareys.EpicRay.EpicRay;

import java.util.ArrayList;

import de.squareys.EpicRay.Bitmap.FastFloatBitmap;
import de.squareys.EpicRay.Bitmap.FastIntBitmap;
import de.squareys.EpicRay.Cursor.FastFloatBitmapCursor;
import de.squareys.EpicRay.Cursor.FastIntBitmapCursor;
import de.squareys.EpicRay.GameLogic.IEntity;
import de.squareys.EpicRay.GameLogic.ITileMap;
import de.squareys.EpicRay.GameLogic.IWorld;
import de.squareys.EpicRay.Rendering.IRenderer;

/**
 * EpicRayRenderer is an implementation of Renderer
 * 
 * EpicRay renders the World with the Raycasting tecnique
 * and Entities as Sprites whose scale is affected by the
 * distance from the camera/player/viewer
 * 
 * @author Squareys
 *
 */

public class EpicRayRenderer implements IRenderer<FastIntBitmap> {

	private FastIntBitmap m_bitmap;
	private FastFloatBitmap m_zBuffer;
	
	private int m_width;
	private int m_height;
	
	protected IWorld m_world;
	protected IEntity m_camEntity; //camera entity
	
	protected float m_planeX = 0;
	protected float m_planeY = 0.66f;
	
	protected int fov = 66;
	protected float m_planeLength = .66f; //(Math.tan(fov/2)); //must be calculated for fov.
	
	public EpicRayRenderer (IWorld world, IEntity camEntity, int width, int height){
		m_width = width;
		m_height = height;
		
		m_bitmap = new FastIntBitmap(width, height);
		m_zBuffer = new FastFloatBitmap(width, height);
		
		m_world = world;
		m_camEntity = camEntity;
	}
	
	@Override
	public void render() {
		renderThreaded();
		if(true) return;
		ITileMap tileMap = m_world.getTileMap();
		
		//calculate perpendicular camera plane
		m_planeX = (float)-m_camEntity.getViewDirectionY() * m_planeLength; //only works if rayDir is normalized!
		m_planeY = (float)m_camEntity.getViewDirectionX() * m_planeLength;
		
		//m_bitmap.clear(Color.gray.getRGB());
		m_zBuffer.clear(Float.MAX_VALUE);
		
		final FastIntBitmapCursor cursor = (FastIntBitmapCursor) m_bitmap.getCursor();
		final FastFloatBitmapCursor zCursor = (FastFloatBitmapCursor) m_zBuffer.getCursor();
		
		final float factor = (float) 2 / m_width;
		float f2 = 0.0f;
		int index = 0;
		
		for(int x = 0; x < m_width; x++, f2 += factor, index += m_height){
	      //calculate ray position and direction 
		  final float cameraX = (float) f2 - 1.0f; //x-coordinate in camera space
	      
		  final float rayDirX = (float)m_camEntity.getViewDirectionX() + m_planeX * cameraX;
		  final float rayDirY = (float)m_camEntity.getViewDirectionY() + m_planeY * cameraX;
		   
		  cursor.setAbsolutePosition(index);
		  zCursor.setAbsolutePosition(index);
		  
		  cursor.setOffset();
		  zCursor.setOffset();
		  
		  final EpicRayRay ray = new EpicRayRay(m_height, (float)m_camEntity.getX(), (float) m_camEntity.getY(), rayDirX, rayDirY, 
				  (FastIntBitmapCursor)cursor.copy(), (FastFloatBitmapCursor)zCursor.copy());
	      
		  ray.cast(tileMap);
		}
	}
	
	public void renderThreaded() {
		ITileMap tileMap = m_world.getTileMap();
		
		//calculate perpendicular camera plane
		m_planeX = (float)-m_camEntity.getViewDirectionY() * m_planeLength; //only works if rayDir is normalized!
		m_planeY = (float)m_camEntity.getViewDirectionX() * m_planeLength;
		
		//m_bitmap.clear(Color.gray.getRGB());
		m_zBuffer.clear(Float.MAX_VALUE);
		
		final FastIntBitmapCursor cursor = (FastIntBitmapCursor) m_bitmap.getCursor();
		final FastFloatBitmapCursor zCursor = (FastFloatBitmapCursor) m_zBuffer.getCursor();
		
		final float factor = (float) 2 / m_width;
		float f2 = 0.0f;
		int index = 0;
		
		ArrayList<EpicRayRay> rays = new ArrayList<EpicRayRay>();
		
		for(int x = 0; x < m_width; x++, f2 += factor, index += m_height){
	      //calculate ray position and direction 
		  final float cameraX = (float) f2 - 1.0f; //x-coordinate in camera space
	      
		  final float rayDirX = (float)m_camEntity.getViewDirectionX() + m_planeX * cameraX;
		  final float rayDirY = (float)m_camEntity.getViewDirectionY() + m_planeY * cameraX;
		   
		  cursor.setAbsolutePosition(index);
		  zCursor.setAbsolutePosition(index);
		  
		  cursor.setOffset();
		  zCursor.setOffset();
		  
		  final EpicRayRay ray = new EpicRayRay(m_height, (float)m_camEntity.getX(), (float) m_camEntity.getY(), rayDirX, rayDirY, 
				  (FastIntBitmapCursor)cursor.copy(), (FastFloatBitmapCursor)zCursor.copy());
	      
		  rays.add(ray);
		}
		
		int numThreads = 4;
		
		int raysPerThread = (int) (rays.size() / numThreads);
		int overshoot = rays.size() - raysPerThread * numThreads;
		
		RenderThread[] threads = new RenderThread[numThreads];
		
		int startIndex = 0;
		int endIndex = 0;
		
		for (int i = 0; i < numThreads; ++i) {
			endIndex = startIndex + raysPerThread;
			
			if (overshoot > 0) {
				endIndex++;
				overshoot--;
			}
			
			threads[i] = new RenderThread(tileMap, startIndex, endIndex, rays);
			threads[i].start();
			
			startIndex += endIndex - startIndex;
		}
		
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} 
	}

	@Override
	public FastIntBitmap getRenderResult() {
		return m_bitmap;
	}

	public void setCameraEntity(IEntity e){
		m_camEntity = e;
	}
	
	
	class RenderThread extends Thread {
		ITileMap m_map;
		
		boolean m_running = false;
		
		int m_xfrom, m_xto;
		
		ArrayList<EpicRayRay> m_rays;
		
		public RenderThread (ITileMap map, int from, int to, ArrayList<EpicRayRay> rays) {
			super();
			m_map = map;
			m_xfrom = from;
			m_xto = to;
			
			m_rays = rays;
		}
		
		@Override
		public synchronized void run() {
			m_running = true;
			
			for (int i = m_xfrom; i < m_xto; ++i) {
				m_rays.get(i).cast(m_map);
			}
			
			m_running = false;
		}
	}
}
