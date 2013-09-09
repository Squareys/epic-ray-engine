package de.squareys.EpicRay.implementation;

import java.awt.Color;

import de.squareys.EpicRay.framework.IBitmap;
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
	private IBitmap m_zBuf;
	private int m_drawOffs;
	protected int m_height; //length of m_pixels
	
	protected double m_length; //distance traveled
	protected double m_nPerpWallDist = 0.0; //precalculated perpWallDist for floor rendering
	
	public EpicRayRay(int height, double startposX, double startposY, double dirX, double dirY, IBitmap dest, IBitmap zBuffer, int offset){
		m_x = startposX;
		m_y = startposY;
		
		m_dirX = dirX;
		m_dirY = dirY;
		
		m_height = height;
		m_drawOffs = offset;
		m_dest = dest;
		m_zBuf = zBuffer;
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
	      
	      double perpWallDist;
	       
	       //distance of side to next side
	      double deltaDistX = Math.sqrt(1 + (m_dirY * m_dirY) / (m_dirX * m_dirX));
	      double deltaDistY = Math.sqrt(1 + (m_dirX * m_dirX) / (m_dirY * m_dirY));
	       
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
	        
	        if (m_nPerpWallDist == 0.0) {
	        	if (side == 0) {
	 	        	double add = (stepX == 1) ? 0.0 : 1.0;
	 	        	m_nPerpWallDist = Math.abs(((mapX) - m_x + add) / m_dirX);
	 	        } else {
	 	        	double add = (stepY == 1) ? 0.0 : 1.0;
	 	        	m_nPerpWallDist = Math.abs(((mapY) - m_y + add) / m_dirY);
	 	        }
	        }
	        
	        //Calculate distance projected on camera direction (oblique distance will give fisheye effect!)
	        //precalc 
	        
	        int nSide = -1;
	        int nmapX = mapX;
	        int nmapY = mapY;
	        if (sideDistX < sideDistY) {
	          nmapX += stepX;
	          nSide = 0;
	        } else {
	          nmapY += stepY;
	          nSide = 1;
	        }
	        
	        perpWallDist = m_nPerpWallDist;
	        
	        if (nSide == 0) {
	        	double add = (stepX == 1) ? 0.0 : 1.0;
	        	m_nPerpWallDist = Math.abs(((nmapX) - m_x + add) / m_dirX);
	        } else {
	        	double add = (stepY == 1) ? 0.0 : 1.0;
	        	m_nPerpWallDist = Math.abs(((nmapY) - m_y + add) / m_dirY);
	        }
	        
		      
	        //draw the tile:
	        drawTile(hitTile, mapX, mapY, stepX, stepY, side, perpWallDist, outOfWorld);
	      }    
	}

	private void drawTile(ITile tile, int mapX, int mapY, int stepY, int stepX, int side, double perpWallDist, boolean outOfWorld) {
	      	      
	      //Calculate height of line to draw on screen
		  int lineHeight = 2* (int)(((double) m_height / perpWallDist) / 2.0);
	       
	      //calculate lowest and highest pixel to fill in current stripe
	      int drawStart = (m_height - lineHeight  ) / 2;
	      int startIndex = drawStart;
	      
	      if(drawStart < 0) {
	    	  drawStart = 0;
	      }
	      
	      int drawEnd = (lineHeight + m_height) / 2;
	      
	      if (drawEnd >= m_height){
	    	  drawEnd = m_height - 1;
	      }
	      
	      EpicRayRenderingAttributes ra;
	      if (!outOfWorld) {
	    	  ra = (EpicRayRenderingAttributes) tile.getRenderingAttributes();
	      } else {
	    	  ra = new EpicRayRenderingAttributes();
	    	  ra.m_wallColor = Color.BLACK.getRGB();
	    	  ra.m_floorColor = Color.green.getRGB();
	      }
	      
	      if ((ra.m_wallTexture != null && ra.m_textured) || ra.m_wallColor != -1) {
		      int texX = 0;
		      ITexture texture = null;
	  		  
			  double wallX; //where exactly the wall was hit
			  
		      if (side == 1) {
		    	  double add = (stepX == 1) ? 0.0 : 1.0;
		    	  wallX = m_x + (((double) mapY - m_y + add) / m_dirY) * m_dirX;
		      } else {
		    	  double add = (stepY == 1) ? 0.0 : 1.0;
		    	  wallX = m_y + (((double) mapX - m_x + add) / m_dirX) * m_dirY;
		      }
		      
		      wallX -= Math.floor(wallX);
		     
		      
		      if (ra.m_textured){
		    	  texture = ra.getWallTexture();
		    	  texX = (int) (wallX * (double) texture.getWidth());
		    	  
		    	  //This is some unnecessary code to flip the texture 
		    	  if(side == 0 && m_dirX > 0) {
		    		  texX = texture.getWidth() - texX - 1;
		    	  } else if(side == 1 && m_dirY < 0){
		    		  texX = texture.getWidth() - texX - 1;
		    	  }
		      }
	  		  
		      int zValue = (int) (perpWallDist * 1024.0); //TODO: Hard coded z-Buffer resolution here... Warning: perpWallDist can be anything way over 1.0
		      
		      //draw the pixels of the stripe as a vertical line
		      for ( int i = drawStart; i < drawEnd; i++){
		    	  int z = m_zBuf.getPixel(m_drawOffs + i);
		    	  //zBuffer Check
		    	  if (z < zValue) {
		    		  continue;
		    	  }
		    	  
		    	  int color = -1;
		    	  
		    	  if (side == 1){
		    		  color = ra.m_wallColor;
		    	  } else {
		    		  color = ra.m_wallColor;
		    	  }
		    	  
		    	 
		    	  if(ra.m_textured){
		    	      int texY = (int) ((double)(( (i-startIndex) * texture.getHeight()) / (double) lineHeight));
		    		  
		    	      if (texX < 0) {
		    	    	  texX = 0;
		    	      }
		    	      
		    	      if (texY < 0){
		    	    	  texY = 0;
		    	      }
		    	      
		    	      if (texX > texture.getWidth()-1) {
		    	    	  texX = texture.getWidth()-1;
		    	      }
		    	      
		    	      if (texY > texture.getHeight()-1) {
		    	    	  texY = texture.getHeight() -1;
		    	      }
			          color = texture.getPixel(texX, texY);
			          
			          //make color darker for y-sides: R, G and B byte each divided through two with a "shift" and an "and"
			          if(side == 1) {
			        	  color = (color >> 1) & 8355711;
			          }
		    	  }
		    	  
		    	  m_dest.putPixel(m_drawOffs + i, color);
		    	  m_zBuf.putPixel(m_drawOffs + i, zValue);
		      }
	      }
	      	      
	      //TODO: Lot of optimisation potential
	      int nLineHeight = 2 * (int)Math.floor(((double) m_height / m_nPerpWallDist) /2.0); //next Line Height, asuring round numbers
	      int nLineStart = (m_height - nLineHeight) / 2;
	      int nLineEnd = (m_height + nLineHeight) / 2;
	      	      
	      if (nLineStart < 0){
	    	  nLineStart = 0;
	      }
	      	      
	      if (nLineEnd >= m_height){
	    	  nLineEnd = m_height-1;
	      }
	      
	      int nInvLineHeight = nLineStart - drawStart;
	    
	      int zValue = (int) (m_nPerpWallDist * 1024.0 - 5.0);
	      
	      // draw the floor and ceiling
	      for (int y = 0; y < nInvLineHeight; y++) {
			/*
			 * int floorTexX, floorTexY; floorTexX = int(currentFloorX *
			 * texWidth) % texWidth; floorTexY = int(currentFloorY * texHeight)
			 * % texHeight;
			 * 
			 * //floor buffer[x][y] = (texture[3][texWidth * floorTexY +
			 * floorTexX] >> 1) & 8355711; //ceiling (symmetrical!) buffer[x][h
			 * - y] = texture[6][texWidth * floorTexY + floorTexX]; }
			 */

	    	//zBuffer Check
			if ( m_zBuf.getPixel(m_drawOffs + y + drawStart) >= zValue) {
				m_dest.putPixel(m_drawOffs + y + drawStart, ra.m_ceilColor); //ceiling
			}
			
			//zBuffer Check
			if (m_zBuf.getPixel(m_drawOffs + y + nLineEnd) >= zValue) {
				m_dest.putPixel(m_drawOffs + y + nLineEnd, ra.m_floorColor); //floor
			}
		}
	}
}
