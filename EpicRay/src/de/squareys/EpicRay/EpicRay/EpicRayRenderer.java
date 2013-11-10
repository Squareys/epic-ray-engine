package de.squareys.EpicRay.EpicRay;

import java.awt.Color;

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
		ITileMap tileMap = m_world.getTileMap();
		
		//calculate perpendicular camera plane
		m_planeX = (float)-m_camEntity.getViewDirectionY() * m_planeLength; //only works if rayDir is normalized!
		m_planeY = (float)m_camEntity.getViewDirectionX() * m_planeLength;
		
		m_bitmap.clear(Color.gray.getRGB());
		m_zBuffer.clear(Float.MAX_VALUE);
		
		FastIntBitmapCursor cursor = (FastIntBitmapCursor) m_bitmap.getCursor();
		FastFloatBitmapCursor zCursor = (FastFloatBitmapCursor) m_zBuffer.getCursor();
		
		float factor = (float) 2 / m_width;
		float f2 = 0.0f;
		int index = 0;
		
		for(int x = 0; x < m_width; x++, f2 += factor, index += m_height){
	      //calculate ray position and direction 
	      float cameraX = (float) f2 - 1.0f; //x-coordinate in camera space
	      
	      float rayDirX = (float)m_camEntity.getViewDirectionX() + m_planeX * cameraX;
		  float rayDirY = (float)m_camEntity.getViewDirectionY() + m_planeY * cameraX;
		   
		  cursor.setAbsolutePosition(index);
		  zCursor.setAbsolutePosition(index);
		  
		  cursor.setOffset();
		  zCursor.setOffset();
		  
	      EpicRayRay ray = new EpicRayRay(m_height, (float)m_camEntity.getX(), (float) m_camEntity.getY(), rayDirX, rayDirY, 
	    		  cursor, zCursor);
	      
	      ray.cast(tileMap);
		}
	}

	@Override
	public FastIntBitmap getRenderResult() {
		return m_bitmap;
	}

	public void setCameraEntity(IEntity e){
		m_camEntity = e;
	}
}