package de.squareys.EpicRay.implementation;

import java.awt.Color;

import de.squareys.EpicRay.framework.Bitmap;
import de.squareys.EpicRay.framework.FastFloatBitmap;
import de.squareys.EpicRay.framework.FastIntBitmap;
import de.squareys.EpicRay.framework.IBitmap;
import de.squareys.EpicRay.framework.ICursor2D;
import de.squareys.EpicRay.framework.IEntity;
import de.squareys.EpicRay.framework.IGame;
import de.squareys.EpicRay.framework.IRenderer;
import de.squareys.EpicRay.framework.IRenderingAttributes;
import de.squareys.EpicRay.framework.ISprite;
import de.squareys.EpicRay.framework.ITile;
import de.squareys.EpicRay.framework.ITileMap;
import de.squareys.EpicRay.framework.IWorld;
import de.squareys.EpicRay.framework.RelativeCursor;

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
		
		ICursor2D<Integer> cursor = m_bitmap.getCursor();
		ICursor2D<Float> zCursor = m_zBuffer.getCursor();
		
		for(int x = 0; x < m_width; x++){
	      //calculate ray position and direction 
	      float cameraX = 2.0f * (float) x / ((float) m_width) - 1.0f; //x-coordinate in camera space
	      
	      float rayDirX = (float)m_camEntity.getViewDirectionX() + m_planeX * cameraX;
		  float rayDirY = (float)m_camEntity.getViewDirectionY() + m_planeY * cameraX;
		   
		  cursor.setPosition(x*m_height);
		  
	      EpicRayRay ray = new EpicRayRay(m_height, (float)m_camEntity.getX(), (float)m_camEntity.getY(), rayDirX, rayDirY, 
	    		  new RelativeCursor<Integer>(cursor), new RelativeCursor<Float>(zCursor));
	      
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
