package de.squareys.EpicRay.implementation;

import java.awt.Color;

import de.squareys.EpicRay.framework.IBitmap;
import de.squareys.EpicRay.framework.IEntity;
import de.squareys.EpicRay.framework.IRay;
import de.squareys.EpicRay.framework.ITexture;
import de.squareys.EpicRay.framework.ITile;
import de.squareys.EpicRay.framework.ITileMap;

public class EpicRayRay implements IRay {

	protected double m_x;
	protected double m_y;
	
	protected double m_dirX;
	protected double m_dirY;
		
	private IBitmap m_dest;
	private int m_drawOffs;
	protected int m_height; //length of m_pixels
	
	protected double m_length; //distance traveled
	
	public EpicRayRay(int height, double startposX, double startposY, double dirX, double dirY, IBitmap dest, int offset){
		m_x = startposX;
		m_y = startposY;
		
		m_dirX = dirX;
		m_dirY = dirY;
		
		m_height = height;
		m_drawOffs = offset;
		m_dest = dest;
	}

	@Override
	public double getX() {
		return m_x;
	}

	@Override
	public double getY() {
		return m_y;
	}

	@Override
	public double getDirX() {
		return m_dirX;
	}

	@Override
	public double getDirY() {
		return m_dirY;
	}

	@Override
	public double getLength() {
		return m_length;
	}

	@Override
	public int getPixelHeight() {
		return m_height;
	}

	@Override
	public void cast(ITileMap map) {
		  //the tile position
	      int mapX = (int) m_x;
	      int mapY = (int) m_y;
	       
	      //distance from position to next side
	      double sideDistX;
	      double sideDistY;
	       
	       //distance of side to next side
	      double deltaDistX = Math.sqrt(1 + (m_dirY * m_dirY) / (m_dirX * m_dirX));
	      double deltaDistY = Math.sqrt(1 + (m_dirX * m_dirX) / (m_dirY * m_dirY));
	      double perpWallDist = 0.0;
	       
	      //the directions signature (-1/+1)
	      int stepX;
	      int stepY;

	      ITile hitTile = null;
	      boolean hit = false; //wall hit flag
	      boolean outOfWorld = false;
	      int side = -1; //which side was hit
	      
	      //calculate step and initial sideDist
	      if (m_dirX < 0){
	        stepX = -1;
	        sideDistX = (m_x - mapX) * deltaDistX;
	      } else {
	        stepX = 1;
	        sideDistX = (mapX + 1.0 - m_x) * deltaDistX;
	      } 
	      
	      if (m_dirY < 0) {
	        stepY = -1;
	        sideDistY = (m_y - mapY) * deltaDistY;
	      } else {
	        stepY = 1;
	        sideDistY = (mapY + 1.0 - m_y) * deltaDistY;
	      }
	      
	      
	      
	      //cast the ray
	      while (!hit) {
	        //jump to next map square, OR in x-direction, OR in y-direction
	        if (sideDistX < sideDistY) {
	          sideDistX += deltaDistX;
	          mapX += stepX;
	          side = 0;
	        } else {
	          sideDistY += deltaDistY;
	          mapY += stepY;
	          side = 1;
	        }
	        //Check if ray has hit a wall
	        hitTile = map.getTileAt(mapX, mapY);
	        
	        if (hitTile == null){
	        	hit = true; //out of world
	        	outOfWorld = true;
	        } else if (hitTile.isOpaque()) {
	        	hit = true;
	        }
	      }    
	      
	      //Calculate distance projected on camera direction (oblique distance will give fisheye effect!)
	      if (side == 0) {
	    	  perpWallDist = Math.abs((mapX - m_x + (1.0 - stepX) / 2.0) / m_dirX);
	      } else {
	    	  perpWallDist = Math.abs((mapY - m_y + (1.0 - stepY) / 2.0) / m_dirY);
	      }
	      
	      	      
	      //Calculate height of line to draw on screen
	      int lineHeight = Math.abs((int)((double)m_height / perpWallDist));
	       
	      //calculate lowest and highest pixel to fill in current stripe
	      int drawStart = -lineHeight / 2 + m_height / 2;
	      
	      if(drawStart < 0) {
	    	  drawStart = 0;
	      }
	      
	      int drawEnd = lineHeight / 2 + m_height / 2;
	      
	      if (drawEnd >= m_height){
	    	  drawEnd = m_height - 1;
	      }

	      EpicRayRenderingAttributes ra;
	      if (!outOfWorld) {
	    	  ra = (EpicRayRenderingAttributes)hitTile.getRenderingAttributes();
	      } else {
	    	  ra = new EpicRayRenderingAttributes();
	    	  ra.m_wallColor = Color.BLACK.getRGB();
	    	  ra.m_floorColor = Color.green.getRGB();
	      }
	      
	      int texX = 0;
	      ITexture texture = null;
	      
	      if(ra.m_textured){
	    	  texture = ra.getWallTexture();
	      }
    		  
		  double wallX; //where exactly the wall was hit
	      if (side == 1) {
	    	  wallX = m_x + ((mapY - m_y + (1.0 - stepY) / 2.0) / m_dirY) * m_dirX;
	      } else {
	    	  wallX = m_y + ((mapX - m_x + (1.0 - stepX) / 2.0) / m_dirX) * m_dirY;
	      }
	      
	      wallX -= Math.floor(wallX);
	      
	      if (ra.m_textured){
	    	  texX = (int) (wallX * (float)(texture.getWidth()));
	      }
    		  
	      //draw the pixels of the stripe as a vertical line
	      for ( int i = drawStart; i < drawEnd; i++){
	    	  int color = -1;
	    	  
	    	  if (side == 1){
	    		  color = ra.m_wallColor / 2;
	    	  } else {
	    		  color = ra.m_wallColor;
	    	  }
	    	  
	    	 
	    	  if(ra.m_textured){
	    	      int texY = (int) (((float)(i+((lineHeight - m_height)/2.0))/lineHeight) * texture.getHeight());
	    		  
		          color = texture.getPixel(texX, texY);
		          
		          //make color darker for y-sides: R, G and B byte each divided through two with a "shift" and an "and"
		          if(side == 1) {
		        	  color = (color >> 1) & 8355711;
		          }
	    	  }
	    	  
	    	  m_dest.putPixel(m_drawOffs + i, color);
	      }
	      
		// FLOOR CASTING
		double floorXWall, floorYWall; // x, y position of the floor texel at
										// the bottom of the wall

		// 4 different wall directions possible
		if (side == 0 && m_dirX > 0) {
			floorXWall = mapX;
			floorYWall = mapY + wallX;
		} else if (side == 0 && m_dirX < 0) {
			floorXWall = mapX + 1.0;
			floorYWall = mapY + wallX;
		} else if (side == 1 && m_dirY > 0) {
			floorXWall = mapX + wallX;
			floorYWall = mapY;
		} else {
			floorXWall = mapX + wallX;
			floorYWall = mapY + 1.0;
		}

		double distWall, distPlayer, currentDist;

		distWall = perpWallDist;
		distPlayer = 0.0;

		if (drawEnd < 0)
			drawEnd = m_height; // becomes < 0 when the integer overflows

		// draw the floor from drawEnd to the bottom of the screen
		for (int y = drawEnd + 1; y < m_height; y++) {
			currentDist = m_height / (2.0 * y - m_height); // you could make a small lookup table for this instead

			double weight = (currentDist - distPlayer) / (distWall - distPlayer);

			double currentFloorX = weight * floorXWall + (1.0 - weight) * m_x;
			double currentFloorY = weight * floorYWall + (1.0 - weight) * m_y;

			/*
			 * int floorTexX, floorTexY; floorTexX = int(currentFloorX *
			 * texWidth) % texWidth; floorTexY = int(currentFloorY * texHeight)
			 * % texHeight;
			 * 
			 * //floor buffer[x][y] = (texture[3][texWidth * floorTexY +
			 * floorTexX] >> 1) & 8355711; //ceiling (symmetrical!) buffer[x][h
			 * - y] = texture[6][texWidth * floorTexY + floorTexX]; }
			 */
			
			ITile t = map.getTileAt((int) currentFloorX, (int) currentFloorY);
			EpicRayRenderingAttributes r = (EpicRayRenderingAttributes)t.getRenderingAttributes();
			
			if (r == null) continue;
			m_dest.putPixel(m_drawOffs + y, r.m_floorColor);
		}
	}
}
