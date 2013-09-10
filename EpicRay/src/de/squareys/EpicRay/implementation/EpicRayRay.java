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
	protected int m_height; // length of m_pixels

	@Deprecated
	protected double m_length; // distance traveled (deprecated)

	protected class RenderVariables {
		double perpWallDist;
		
		// distance from position to next side
		double sideDistX;
		double sideDistY;
		
		// the tile position
		int mapX;
		int mapY;
		
		int side;
		
		public RenderVariables() {
			perpWallDist = 0.0;
			
			sideDistX = 0.0;
			sideDistY = 0.0;
			
			mapX = 0;
			mapY = 0;
			
			side = -1;
		}
	}
	
	protected class VariableStorage {
		boolean cur;
		
		RenderVariables a;
		RenderVariables b;
		
		public VariableStorage() {
			cur = false;
			
			a = new RenderVariables();
			b = new RenderVariables();
		}
		
		RenderVariables getVariables() {
			return (cur) ? a : b;
		}
		
		RenderVariables getNextVariables() {
			return (cur) ? b : a;
		}
		
		void exchange () {
			cur = !cur;
		}
	}
	
	VariableStorage stor;
	
	public EpicRayRay(int height, double startposX, double startposY,
			double dirX, double dirY, IBitmap dest, IBitmap zBuffer, int offset) {
		m_x = startposX;
		m_y = startposY;

		m_dirX = dirX;
		m_dirY = dirY;

		m_height = height;
		m_drawOffs = offset;
		m_dest = dest;
		m_zBuf = zBuffer;
		
		stor = new VariableStorage();
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
		RenderVariables cur = stor.getVariables();
		RenderVariables next = stor.getNextVariables();
		
		// the tile position
		next.mapX = (int) m_x;
		next.mapY = (int) m_y;


		// distance of side to next side
		double deltaDistX = Math
				.sqrt(1 + (m_dirY * m_dirY) / (m_dirX * m_dirX));
		double deltaDistY = Math
				.sqrt(1 + (m_dirX * m_dirX) / (m_dirY * m_dirY));

		// the directions signature (-1/+1)
		int stepX;
		int stepY;

		ITile hitTile = null;
		boolean hit = false; // wall hit flag
		boolean outOfWorld = false;
		

		// calculate step and initial sideDist
		if (m_dirX < 0) {
			stepX = -1;
			next.sideDistX = (m_x - next.mapX) * deltaDistX;
		} else {
			stepX = 1;
			next.sideDistX = (next.mapX + 1.0 - m_x) * deltaDistX;
		}

		if (m_dirY < 0) {
			stepY = -1;
			next.sideDistY = (m_y - next.mapY) * deltaDistY;
		} else {
			stepY = 1;
			next.sideDistY = (next.mapY + 1.0 - m_y) * deltaDistY;
		}

		if (next.sideDistX < next.sideDistY) {
			double add = (stepX == 1) ? 0.0 : 1.0;
			next.perpWallDist = Math.abs(((double) next.mapX - m_x + add) / m_dirX);
		} else {
			double add = (stepY == 1) ? 0.0 : 1.0;
			next.perpWallDist = Math.abs(((double) next.mapY - m_y + add) / m_dirY);
		}

		// cast the ray
		do {			
			stor.exchange();
			cur = stor.getVariables();
			next = stor.getNextVariables();
			
			// jump to next map square, OR in x-direction, OR in y-direction
			if (cur.sideDistX < cur.sideDistY) {
				next.sideDistX = cur.sideDistX + deltaDistX;
				next.sideDistY = cur.sideDistY;
				next.mapX = cur.mapX + stepX;
				next.mapY = cur.mapY;
				next.side = 0;
			} else {
				next.sideDistY = cur.sideDistY + deltaDistY;
				next.sideDistX = cur.sideDistX;
				next.mapY = cur.mapY + stepY;
				next.mapX = cur.mapX;
				next.side = 1;
			}

			if (next.side == 0) {
				double add = (stepX == 1) ? 0.0 : 1.0;
				next.perpWallDist = Math.abs(((next.mapX) - m_x + add) / m_dirX);
			} else {
				double add = (stepY == 1) ? 0.0 : 1.0;
				next.perpWallDist = Math.abs(((next.mapY) - m_y + add) / m_dirY);
			}
			
			hitTile = map.getTileAt(cur.mapX, cur.mapY);
			
			if (hitTile == null) {
				outOfWorld = true;
				hit = true;
			} else if(hitTile.isOpaque()){
				hit = true;
			}
			
			// draw the tile:
			drawTile(hitTile, stepX, stepY,	outOfWorld);
		} while (!hit);
	}

	private void drawTile(ITile tile, int stepY, int stepX, boolean outOfWorld) {
		RenderVariables cur = stor.getVariables();
		RenderVariables next = stor.getNextVariables();
		
		// Calculate height of line to draw on screen
		int lineHeight = 2 * (int) (((double) m_height / cur.perpWallDist) / 2.0);

		// calculate lowest and highest pixel to fill in current stripe
		int drawStart = (m_height - lineHeight) / 2;
		int startIndex = drawStart;

		if (drawStart < 0) {
			drawStart = 0;
		}

		int drawEnd = (lineHeight + m_height) / 2;

		if (drawEnd >= m_height) {
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

			double wallX; // where exactly the wall was hit

			if (cur.side == 1) {
				double add = (stepX == 1) ? 0.0 : 1.0;
				wallX = m_x + (((double) cur.mapY - m_y + add) / m_dirY) * m_dirX;
			} else {
				double add = (stepY == 1) ? 0.0 : 1.0;
				wallX = m_y + (((double) cur.mapX - m_x + add) / m_dirX) * m_dirY;
			}

			wallX -= Math.floor(wallX);

			if (ra.m_textured) {
				texture = ra.getWallTexture();
				texX = (int) (wallX * (double) texture.getWidth());

				// This is some unnecessary code to flip the texture
				if (cur.side == 0 && m_dirX > 0) {
					texX = texture.getWidth() - texX - 1;
				} else if (cur.side == 1 && m_dirY < 0) {
					texX = texture.getWidth() - texX - 1;
				}
			}

			int zValue = (int) (cur.perpWallDist * 1024.0); // TODO: Hard coded
														// z-Buffer resolution
														// here... Warning:
														// perpWallDist can be
														// anything way over 1.0

			// draw the pixels of the stripe as a vertical line
			for (int i = drawStart; i < drawEnd; i++) {
				int z = m_zBuf.getPixel(m_drawOffs + i);
				// zBuffer Check
				if (z < zValue) {
					continue;
				}

				int color = -1;

//				if (cur.side == 1) { //for shading
//					color = ra.m_wallColor;
//				} else {
					color = ra.m_wallColor;
//				}

				if (ra.m_textured) {
					int texY = (int) ((double) (((i - startIndex) * texture
							.getHeight()) / (double) lineHeight));

					if (texX < 0) {
						texX = 0;
					}

					if (texY < 0) {
						texY = 0;
					}

					if (texX > texture.getWidth() - 1) {
						texX = texture.getWidth() - 1;
					}

					if (texY > texture.getHeight() - 1) {
						texY = texture.getHeight() - 1;
					}
					color = texture.getPixel(texX, texY);

					// make color darker for y-sides: R, G and B byte each
					// divided through two with a "shift" and an "and"
					if (cur.side == 1) {
						color = (color >> 1) & 8355711;
					}
				}

				m_dest.putPixel(m_drawOffs + i, color);
				//m_zBuf.putPixel(m_drawOffs + i, zValue);
			}
		}
		
		if(tile.isOpaque()) return; //no floor visible.
		
		// TODO: Lot of optimisation potential
		int nLineHeight = 2 * (int) Math
				.floor(((double) m_height / next.perpWallDist) / 2.0); // next
																		// Line
																		// Height,
																		// asuring
																		// round
																		// numbers

		int nLineStart = (m_height - nLineHeight) / 2;
		int nLineEnd = (m_height + nLineHeight) / 2;

		if (nLineStart < 0) {
			nLineStart = 0;
		}

		if (nLineEnd >= m_height) {
			nLineEnd = m_height - 1;
		}

		int nInvLineHeight = nLineStart - drawStart;

		if (nInvLineHeight < 1) {
			return; // floor not visible here.
		}

		int zValue = (int) (next.perpWallDist * 1024.0 - 5.0);

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

			// zBuffer Check
			if (m_zBuf.getPixel(m_drawOffs + y + drawStart) >= zValue) {
				m_dest.putPixel(m_drawOffs + y + drawStart, ra.m_ceilColor); // ceiling
			}

			// zBuffer Check
			if (m_zBuf.getPixel(m_drawOffs + y + nLineEnd) >= zValue) {
				m_dest.putPixel(m_drawOffs + y + nLineEnd, ra.m_floorColor); // floor
			}
		}
	}
}
