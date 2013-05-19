package de.squareys.EpicRay.implementation;

import java.awt.Color;

import de.squareys.EpicRay.framework.IBitmap;
import de.squareys.EpicRay.framework.IEntity;
import de.squareys.EpicRay.framework.IGame;
import de.squareys.EpicRay.framework.IRenderer;
import de.squareys.EpicRay.framework.IRenderingAttributes;
import de.squareys.EpicRay.framework.ISprite;
import de.squareys.EpicRay.framework.ITile;
import de.squareys.EpicRay.framework.ITileMap;
import de.squareys.EpicRay.framework.IWorld;

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

public class EpicRayRenderer implements IRenderer {

	private IBitmap bitmap;
	
	private int m_width;
	private int m_height;
	
	protected IWorld m_world;
	protected IEntity m_camEntity; //camera entity
	
	protected double m_planeX = 0;
	protected double m_planeY = 0.66;
	
	protected double fov = 66;
	protected double m_planeLength = .66; //(Math.tan(fov/2)); //must be calculated for fov.
	
	public EpicRayRenderer (IWorld world, IEntity camEntity, int width, int height){
		m_width = width;
		m_height = height;
		
		bitmap = new Bitmap(width, height);
		
		m_world = world;
		m_camEntity = camEntity;
	}
	
	@Override
	public void render() {
		ITileMap tileMap = m_world.getTileMap();
		
		//calculate perpendicular camera plane
		m_planeX = -m_camEntity.getViewDirectionY() * m_planeLength; //only works if rayDir is normalized!
		m_planeY = m_camEntity.getViewDirectionX() * m_planeLength;
		
		bitmap.clear(Color.gray.getRGB());
		
		for(int x = 0; x < m_width; x++){
	      //calculate ray position and direction 
	      double cameraX = 2.0 * (double) x / ((double) m_width) - 1.0; //x-coordinate in camera space
	      
	      double rayDirX = m_camEntity.getViewDirectionX() + m_planeX * cameraX;
		  double rayDirY = m_camEntity.getViewDirectionY() + m_planeY * cameraX;
		   
	      EpicRayRay ray = new EpicRayRay(m_height, m_camEntity.getX(), m_camEntity.getY(), rayDirX, rayDirY);
	      
	      ray.cast(tileMap);
	      
	      //copy pixels
	      
	      for (int i = 0; i < m_height; i++){
	    	  int col = ray.m_pixels[i];
	    	  if (col == -1) continue;
	    	  bitmap.putPixel(x, i, col);
	      }
		}
	}

	@Override
	public IBitmap getRenderResult() {
		return bitmap;
	}

	public void setCameraEntity(IEntity e){
		m_camEntity = e;
	}
}